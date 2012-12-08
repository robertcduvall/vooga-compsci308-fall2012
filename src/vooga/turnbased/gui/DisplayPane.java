package vooga.turnbased.gui;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import javax.swing.JComponent;
import javax.swing.JPanel;
import util.input.core.KeyboardController;
import util.input.core.MouseController;

@SuppressWarnings("serial")
/**
 * The abstract class that describe the feature of a displayable pane in this game
 * including editor, game, menu 
 * @author rex, volodymyr
 *
 */
public abstract class DisplayPane extends JPanel {

    private KeyboardController myKeyboardController;
    private MouseController myMouseController;
    private GameWindow myGameWindow;

    /**
     * Constructor
     * @param gameWindow The gameWindow where this will exist
     */
    public DisplayPane (GameWindow gameWindow) {
        myGameWindow = gameWindow;
        setSize(myGameWindow.getSize());
        enableFocus();
        myKeyboardController = new KeyboardController(this);
        myMouseController = new MouseController(this);
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
    
    /**
     * Reset input controllers
     */
    public void resetControllers () {
        KeyListener[] kl = this.getKeyListeners();
        for (int i = 0; i < kl.length; i++) {
            this.removeKeyListener(kl[i]);
        }
        myKeyboardController = new KeyboardController(this);
    }
    
    /**
     * get access to program's keyboard controller
     * 
     * @return keyboard controller
     */
    public KeyboardController getKeyboardController() {
        return myKeyboardController;
    }
    
    /**
     * get access to program's mouse controller
     * 
     * @return mouse controller
     */
    public MouseController getMouseController() {
        return myMouseController;
    }
}
