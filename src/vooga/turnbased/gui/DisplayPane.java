package vooga.turnbased.gui;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyListener;
import javax.swing.JComponent;
import javax.swing.JPanel;
import util.input.core.KeyboardController;
import util.input.core.MouseController;
import util.input.factories.ControllerFactory;


@SuppressWarnings("serial")
public abstract class DisplayPane extends JPanel {
    private GameWindow myGameWindow;
    public static KeyboardController keyboardController;
    public static MouseController mouseController;

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
    
    public GameWindow getGameWindow () {
        return myGameWindow;
    }

    public void enableFocus () {
        setFocusable(true);
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown (ComponentEvent e) {
                ((JComponent) e.getSource()).requestFocusInWindow();
            }
        });
    }

    public void initialize () {
    }
}
