import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class ScoreTest {

    @Test
    void testTopScoresCalculation() {
        List<Integer> scores = Arrays.asList(100, 200, 50, 300);
        List<Integer> expectedTopScores = Arrays.asList(300, 200, 100);

        Score.displayTopScores(scores, "Test Scores");
        assertEquals(expectedTopScores.subList(0, 3), scores.subList(0, 3));
    }
}