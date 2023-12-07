import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class MapBuilder extends JFrame {
    private int[][] map;
    JButton[][] mapButtons;
    private static final int ROWS = 10;
    private static final int COLS = 10;
    private static final int TILE_SIZE = 40;
    private static final int NUM_STATS = 5;

    public MapBuilder() {
        map = new int[ROWS][COLS];
        mapButtons = new JButton[ROWS][COLS];

        setTitle("Map Builder");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                mapButtons[row][col] = new JButton();
                mapButtons[row][col].setBounds(col * TILE_SIZE, row * TILE_SIZE, TILE_SIZE, TILE_SIZE);

                // Set the initial color for each tile
                setInitialTileColor(row, col);

                final int r = row;
                final int c = col;

                mapButtons[row][col].addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        toggleTile(r, c);
                    }
                });

                add(mapButtons[row][col]);
            }
        }

        JButton saveButton = new JButton("Save Map");
        saveButton.setBounds(0, ROWS * TILE_SIZE, TILE_SIZE * COLS, TILE_SIZE);
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mapSave();
            }
        });

        add(saveButton);

        setSize(COLS * TILE_SIZE, (ROWS + 1) * TILE_SIZE);
        setLocationRelativeTo(null);
    }

    private void mapSave() {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("map.ser"));
            oos.writeObject(map);
            oos.close();

            System.out.println("Map has been serialized and saved to map.ser");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    void toggleTile(int row, int col) {
        map[row][col] = (map[row][col] + 1) % NUM_STATS;
        updateTileColor(row, col);
    }

    private void updateTileColor(int row, int col) {
        Color tileColor;
        int stat = map[row][col];

        switch (stat) {
            case 0:
                tileColor = Color.BLACK;
                break;
            case 1:
                tileColor = Color.BLUE;
                break;
            case 2:
                tileColor = Color.YELLOW;
                break;
            default:
                tileColor = Color.BLACK;
        }

        mapButtons[row][col].setBackground(tileColor);
    }

    private void setInitialTileColor(int row, int col) {
        int stat = map[row][col];
        updateTileColor(row, col);
    }

    public static void MapBuild() {
        SwingUtilities.invokeLater(() -> {
            MapBuilder mapBuilder = new MapBuilder();
            mapBuilder.setVisible(true);
        });
    }
}
