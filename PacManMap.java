import javax.swing.*;
import java.awt.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class PacManMap extends JPanel {
    private final int[][] map = loadMapFromFile();
    private final int tileSize = 40; // Size of each tile in pixels

    public PacManMap() {
        setPreferredSize(new Dimension(map[0].length * tileSize, map.length * tileSize));
    }

    public static int[][] loadMapFromFile() {
        int[][] loadedMap = null;
        try {
            // Deserialize the 2D array from the file
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("map.ser"));
            loadedMap = (int[][]) ois.readObject();
            ois.close();

            System.out.println("Map has been deserialized from map.ser");
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return loadedMap;
    }




}
