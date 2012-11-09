package vooga.platformer.leveleditor;

import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import vooga.platformer.utility.*;

public class LevelEditor extends JFrame{
    private static final Dimension DEFAULT_FRAME_SIZE = new Dimension(640, 480);
    public LevelEditor () {
        super("LevelEditor");
        setPreferredSize(DEFAULT_FRAME_SIZE);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        catch (InstantiationException e) {
            e.printStackTrace();
        }
        catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        setVisible(true);
        pack();
    }

}
