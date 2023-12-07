import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.awt.Color;
import static org.junit.jupiter.api.Assertions.*;

class MapBuilderTest {

    private MapBuilder mapBuilder;

    @BeforeEach
    void setUp() {
        mapBuilder = new MapBuilder();
    }

    @Test
    void testToggleTileColorChange() {
        mapBuilder.toggleTile(0, 0);
        Color initialColor = mapBuilder.mapButtons[0][0].getBackground();
        mapBuilder.toggleTile(0, 0);
        assertNotEquals(initialColor, mapBuilder.mapButtons[0][0].getBackground());
    }
}