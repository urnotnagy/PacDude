import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

class Ghost {
    private int x;
    private int y;

    public Ghost(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x =x;
    }


    public void setY(int y) {
        this.y = y;
    }


    public void moveRandomly(int[][] matrix) {
        Random random = new Random();
        int direction = random.nextInt(4); // 0: Up, 1: Down, 2: Left, 3: Right

        int newX = x;
        int newY = y;

        switch (direction) {
            case 0:
                newY--;
                break;
            case 1:
                newY++;
                break;
            case 2:
                newX--;
                break;
            case 3:
                newX++;
                break;
        }

        if (isValidMove(newX, newY, matrix)) {
            x = newX;
            y = newY;
        }
    }




    private boolean isValidMove(int x, int y, int[][] matrix) {
        if (x >= 0 && x < matrix.length && y >= 0 && y < matrix[0].length) {
            return matrix[y][x] != 1; // Allow movement only if the cell is not a wall
        }
        return false;
    }



}