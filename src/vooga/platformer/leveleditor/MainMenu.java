package vooga.platformer.leveleditor;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class MainMenu extends JFrame{
    private static final Dimension DEFAULT_FRAME_SIZE = new Dimension(640, 480);
    private MouseAdapter myMouseListener;
    public MainMenu () {
        frameBuild();
        createListeners();
    }
    private void frameBuild() {
        setPreferredSize(DEFAULT_FRAME_SIZE);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }
    private void createListeners() {
        myMouseListener = new MouseAdapter() {
            public void MouseClicked(MouseEvent e) {
                //use reflection to instantiate the right object
                //add object to the frame
            }
        };
        addMouseListener(myMouseListener);
    }
    private void createGameMenu() {
        JPanel menu = new JPanel();
        menu.setLayout(new GridBagLayout());
    }
}
