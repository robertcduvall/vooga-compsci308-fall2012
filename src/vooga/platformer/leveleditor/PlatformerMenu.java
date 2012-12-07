package vooga.platformer.leveleditor;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import javax.swing.JPanel;
import util.ingamemenu.GameButton;


/**
 * A simple graphic to show on start up of the level editor that allows the user
 * to choose from level editing, game play, or to close the frame.
 * 
 * @author Sam Rang
 * 
 */
public class PlatformerMenu extends JPanel {
    private static final long serialVersionUID = 1L;

    private static final int NUMBER_OF_OPTIONS = 3;

    private PlatformerFrame myParent;

    /**
     * Creates a new platformer menu in the specified parent container.
     * 
     * @param parent container in which to display this panel
     */
    public PlatformerMenu (PlatformerFrame parent) {
        myParent = parent;
        MouseListener buttonListener = new MouseAdapter() {
            @Override
            public void mouseReleased (MouseEvent e) {
                String check = e.getComponent().getName();
                if ("Level Editor".equals(check)) {
                    myParent.levelEdit();
                }
                else if ("New Game".equals(check)) {
                    myParent.newGame();
                }
                else if ("Quit".equals(check)) {
                    WindowEvent wev = new WindowEvent(myParent, WindowEvent.WINDOW_CLOSING);
                    Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(wev);
                }
            }
        };
        setLayout(new GridLayout(NUMBER_OF_OPTIONS, 1));
        Dimension buttonSize =
                new Dimension(parent.getWidth(), parent.getHeight() / NUMBER_OF_OPTIONS);
        GameButton b = new GameButton("Level Editor");
        b.addMouseListener(buttonListener);
        b.setSize(buttonSize);
        add(b);
        b = new GameButton("New Game");
        b.addMouseListener(buttonListener);
        b.setSize(buttonSize);
        add(b);
        b = new GameButton("Quit");
        b.addMouseListener(buttonListener);
        b.setSize(buttonSize);
        add(b);
    }
}
