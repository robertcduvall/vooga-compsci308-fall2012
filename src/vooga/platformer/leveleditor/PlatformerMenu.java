package vooga.platformer.leveleditor;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JPanel;
import util.ingamemenu.GameButton;

public class PlatformerMenu extends JPanel{
    private static final long serialVersionUID = 1L;
    private PlatformerFrame myParent;
    public  PlatformerMenu(PlatformerFrame parent) {
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
        setLayout(new GridLayout(3, 1));
        Dimension buttonSize = new Dimension(parent.getWidth(), parent.getHeight()/3);
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
