package vooga.turnbased.gui;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.JComponent;
import javax.swing.JPanel;
import util.input.core.KeyboardController;
import util.input.core.MouseController;
import util.input.factories.ControllerFactory;

@SuppressWarnings("serial")
/**
 * The abstract class that describe the feature of a displayable pane in this game
 * including editor, game, menu 
 * @author rex, volodymyr
 *
 */
public abstract class DisplayPane extends JPanel {

    /**
     * The keyboard controller that takes input for our game
     */
    public static KeyboardController keyboardController;
    /**
     * The mouse input controller that takes input for our game
     */
    public static MouseController mouseController;
    private GameWindow myGameWindow;

    /**
     * Constructor
     * @param gameWindow The gameWindow where this will exist
     */
    public DisplayPane (GameWindow gameWindow) {
        myGameWindow = gameWindow;
        setSize(myGameWindow.getSize());
        enableFocus();
        setControllers();
    }

    private void setControllers () {
        keyboardController =
                (KeyboardController) ControllerFactory.createKeyboardController(this);
        mouseController = (MouseController) ControllerFactory.createMouseController(this);
    }

    /**
     * Gets the game window.
     * @return This is the game window
     */
    public GameWindow getGameWindow () {
        return myGameWindow;
    }

    /**
     * Allows the display to have a focus
     */
    public void enableFocus () {
        setFocusable(true);
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown (ComponentEvent e) {
                ((JComponent) e.getSource()).requestFocusInWindow();
            }
        });
    }

    /**
     * Initializes the pane, however, is currently empty
     */
    public void initialize () {
    }
}
