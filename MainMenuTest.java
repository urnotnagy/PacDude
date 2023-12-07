import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javax.swing.*;
import java.awt.event.ActionListener;
import static org.junit.jupiter.api.Assertions.*;

class MainMenuTest {

    private MainMenu mainMenu;

    @BeforeEach
    void setUp() {
        mainMenu = new MainMenu();
    }

    @Test
    void testButtonActions() {
        JPanel mainPanel = (JPanel) mainMenu.getContentPane().getComponent(0);
        JButton loadMapButton = (JButton) mainPanel.getComponent(0);
        JButton createMapButton = (JButton) mainPanel.getComponent(2);
        JButton highScoresButton = (JButton) mainPanel.getComponent(4);

        assertTrue(loadMapButton.getActionListeners().length > 0);
        assertTrue(createMapButton.getActionListeners().length > 0);
        assertTrue(highScoresButton.getActionListeners().length > 0);

    }
}