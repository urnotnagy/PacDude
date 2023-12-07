import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;
import java.net.URL;

public class PacMan extends JFrame {
    private int[][] matrix = PacManMap.loadMapFromFile();

    private int spriteX = 0;
    private int spriteY = 0;

    private int score = 0;
    private int lives = 3;
    int standardCellSize = 50;

    private ImageIcon pacmanIcon;
    Image pacmanImage;
    private ImageIcon ghostIcon;
    Image ghostImage;
    private ImageIcon tilesIcon;
    Image tilesImage;
    private ImageIcon coinIcon;
    Image coinImage;
    private ImageIcon addIcon;
    Image addImage;

    Image pacmanUpImage ;
    Image pacmanDownImage ;
    Image pacmanLeftImage ;
    Image pacmanRightImage ;


    

    private ArrayList<Ghost> ghosts;

    public PacMan() {
        setTitle("Game");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        pacmanUpImage = loadScaledImage("/imgs/pacman-up/1.png", standardCellSize, standardCellSize);
        pacmanDownImage = loadScaledImage("/imgs/pacman-down/1.png", standardCellSize, standardCellSize);
        pacmanLeftImage = loadScaledImage("/imgs/pacman-left/1.png", standardCellSize, standardCellSize);
        pacmanRightImage = loadScaledImage("/imgs/pacman-right/1.png", standardCellSize, standardCellSize);
        pacmanImage = pacmanRightImage;

        Random random = new Random();
        ghosts = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            int x = random.nextInt(10);
            int y = random.nextInt(10);
            while (!isValidMove(x, y)) {
                x = random.nextInt(10);
                y = random.nextInt(10);
            }
            ghosts.add(new Ghost(x, y));
        }

        spriteX = 0;
        spriteY = 0;
        standardCellSize = 50;

        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                handleKeyPress(e);
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }

        });

        setFocusable(true);

    }

    void handleKeyPress(KeyEvent e) {
        int keyCode = e.getKeyCode();

        switch (keyCode) {
            case KeyEvent.VK_UP:
                moveSprite(0, -1);
                pacmanImage = pacmanUpImage;
                checkNearbyGhost();
                break;
            case KeyEvent.VK_DOWN:
                moveSprite(0, 1);
                pacmanImage = pacmanDownImage;
                checkNearbyGhost();
                break;
            case KeyEvent.VK_LEFT:
                moveSprite(-1, 0);
                pacmanImage = pacmanLeftImage;
                checkNearbyGhost();
                break;
            case KeyEvent.VK_RIGHT:
                moveSprite(1, 0);
                pacmanImage = pacmanRightImage;
                checkNearbyGhost();
                break;
        }

        for (Ghost ghost : ghosts) {
            ghost.moveRandomly(matrix);

        }
        checkNearbyGhost();
        checkWin();
        checkCoinCollection();
        repaint();

    }

    private void checkWin() {
        int remainingCoins = countRemainingCoins();
        if (remainingCoins == 0) {
            JOptionPane.showMessageDialog(this, "You Win! \n" + " You scored: " + score + "!");
            Score.saveScore(score);
            System.exit(0);
        }
    }

    private int countRemainingCoins() {
        int count = 0;
        for (int[] row : matrix) {
            for (int cellValue : row) {
                if (cellValue == 2) {
                    count++;
                }
            }
        }
        return count;
    }

    private void checkNearbyGhost() {

        for (Ghost ghost : ghosts) {
            
                int adjx = ghost.getX() ; 
                int adjy = ghost.getY() ;
                if (adjx == spriteX && adjy == spriteY) {
                    lives--;
                    if (lives > 0) {
                        respawnPacMan();
                    } else {
                        JOptionPane.showMessageDialog(this, "You Lost! \n" + " You scored: " + score + "!");
                        Score.saveScore(score);
                        System.exit(0);
                    }

                }
        }
    }

    private void respawnPacMan() {
        Random random = new Random();
        do {
            spriteX = (int) (random.nextInt(matrix.length));
            spriteY = (int) (random.nextInt(matrix[0].length));
        } while (matrix[spriteY][spriteX] == 1);
    }

    void checkCoinCollection() {
        if (matrix[spriteY][spriteX] == 2) {
            matrix[spriteY][spriteX] = 0; 
            System.out.println("Coin collected!");
            score++;
        }
    }

    private Image loadScaledImage(String path, int width, int height) {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        URL imageUrl = getClass().getResource(path);
        if (imageUrl == null) {
            System.err.println("Resource not found: " + path);
            return null;
        }
        Image image = toolkit.getImage(imageUrl);
        return image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);


        ghostImage = loadScaledImage("/imgs/ghosts/clyde.png", standardCellSize, standardCellSize);
        coinImage = loadScaledImage("/imgs/other/apple.png", standardCellSize, standardCellSize);
        tilesImage = loadScaledImage("/imgs/tiles/spr_wood_texture_0.png", standardCellSize, standardCellSize);
        addImage = loadScaledImage("/imgs/other/dot.png", standardCellSize, standardCellSize);
        ghostIcon = new ImageIcon(ghostImage);
        pacmanIcon = new ImageIcon(pacmanImage);
        coinIcon = new ImageIcon(coinImage);
        tilesIcon = new ImageIcon(tilesImage);
        addIcon = new ImageIcon(addImage);
        


        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                ImageIcon kk = addIcon;
                

                switch (matrix[i][j]) {
                    case 1:
                        kk = tilesIcon;
                        break;
                    case 2:
                        kk = coinIcon;
                        break;
                }

                g.setColor(Color.BLACK);
                g.fillRect(j * standardCellSize , i * standardCellSize , standardCellSize,
                        standardCellSize);
                if(kk != addIcon)
                    g.drawImage(kk.getImage(),j*standardCellSize, i*standardCellSize, this);
            }
        }

        if (pacmanIcon != null) {
            g.drawImage(pacmanIcon.getImage(), spriteX * standardCellSize, spriteY * standardCellSize, this);
        }

        for (Ghost ghost : ghosts) {
            if (ghostIcon != null)
                g.drawImage(ghostIcon.getImage(), ghost.getX() * standardCellSize, ghost.getY() * standardCellSize,
                        this);
        }

    }

    private void moveSprite(int deltaX, int deltaY) {
        int newSpriteX = spriteX + deltaX;
        int newSpriteY = spriteY + deltaY;

        if (isValidMove(newSpriteX, newSpriteY)) {
            spriteX = newSpriteX;
            spriteY = newSpriteY;
        }
    }

    private boolean isValidMove(int x, int y) {
        if (x >= 0 && x < matrix.length && y >= 0 && y < matrix[0].length) {
            return ((matrix[y][x] == 0) || (matrix[y][x] == 2));
        }
        return false;
    }

    public static void runGame() {
        SwingUtilities.invokeLater(() -> {
            PacMan pacMan = new PacMan();
            pacMan.setVisible(true);
        });
    }

}
