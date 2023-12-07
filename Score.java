import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.*;


public class Score {


    public static void saveScore(int score) {
        try (PrintWriter writer = new PrintWriter(new FileWriter("scoreboard.txt", true))) {
            writer.println(score);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void displayTopScores(List<Integer> scores, String title) {
        Collections.sort(scores, Collections.reverseOrder());

        StringBuilder message = new StringBuilder("Top 3 Scores:\n");
        for (int i = 0; i < Math.min(3, scores.size()); i++) {
            message.append((i + 1)).append(". ").append(scores.get(i)).append("\n");
        }

        JOptionPane.showMessageDialog(null, message.toString(), title, JOptionPane.INFORMATION_MESSAGE);
    }

    public static List<Integer> loadScores() {
        List<Integer> scores = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("scoreboard.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    int score = Integer.parseInt(line.trim());
                    scores.add(score);
                } catch (NumberFormatException e) {
                    
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return scores;
    }

    public static void scoreMenu (){
        List<Integer> scores = loadScores();
        displayTopScores(scores, "Top Scores");
    }

}
