package vooga.platformer.leveleditor;

import arcade.IArcadeGame;
import arcade.gamemanager.GameSaver;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;


@SuppressWarnings("serial")
public class PlatformerFrame extends JFrame implements IArcadeGame {
    private static final String DESCRIPTION =
            "This level editor allows you to edit your levels with supreme ease. It may also let you save your changes at the end!";
    private static final Dimension DEFAULT_FRAME_SIZE = new Dimension(640, 480);
    private static final int ONE_SECOND = 1000;
    private static final int FRAMES_PER_SECOND = 20;
    private static final String MAIN_IMAGE = "levelEditorMain.jpg";
    private Timer myTimer;
    private ActionListener myActionListener;
    private JPanel myContent;

    /**
     * Creates a new instance of the PlatformerFrame, a frame which holds the
     * level editor and all of its components.
     */
    public PlatformerFrame () {
        frameBuild();
        setContentPane(myContent);
        myContent.add(new PlatformerMenu(this), BorderLayout.CENTER);
        pack();
        setVisible(true);
        myTimer = new Timer(ONE_SECOND / FRAMES_PER_SECOND, new ActionListener() {
            @Override
            public void actionPerformed (ActionEvent e) {
                repaint();
            }
        });
        myTimer.start();
    }

    /**
     * Opens a new instance of the LevelEditor.
     * 
     * @param args ignored in this method
     */
    public static void main (String[] args) {
        new PlatformerFrame();
    }

    private void frameBuild () {
        setSize(DEFAULT_FRAME_SIZE);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
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
        myContent = new JPanel();
        // {
        // @Override
        // public void addKeyListener(KeyListener kl) {
        // MainMenu.this.addKeyListener(kl);
        // }
        // };
        myContent.setLayout(new BorderLayout());
    }

    public void levelEdit () {
        myContent.removeAll();
        LevelEditor le = new LevelEditor(DEFAULT_FRAME_SIZE);
        setTitle("Level Editor");
        myContent.add(le);
        addKeyListener(le.getKeyListener());
        validate();
        repaint();
    }

    /**
     * An uncompleted method that would bring up a new, blank level in the
     * editor.
     */
    public void newGame () {
        System.out.println("make a new game or something");
    }

    @Override
    public void runGame (String userPreferences, GameSaver s) {
        // Something tells me this won't actually start the game
        // properly yet.
        newGame();
    }

    @Override
    public List<Image> getScreenshots () {
        List<Image> screenShots = new ArrayList<Image>();
        screenShots.add(getMainImage());
        return screenShots;
    }

    @Override
    public Image getMainImage () {
        ImageIcon mainImage = new ImageIcon(getClass().getResource(MAIN_IMAGE));
        return mainImage.getImage();
    }

    @Override
    public String getDescription () {
        return DESCRIPTION;
    }

}
