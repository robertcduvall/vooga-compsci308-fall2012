package games.squareattack.gui;

import games.squareattack.engine.GameManager;
import games.squareattack.engine.SoundManager;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;


/**
 * 
 * @author Ben Schwab
 * 
 */
public class GameFrame extends JFrame {

    public static final int GameWidth = 1100;
    public static final int GameHeight = 700;
    private SetUpGameMenu myMenu;
    private SoundManager mySounds = SoundManager.getInstance();
    private JMenuBar myMenuBar;
    private GameManager gameScreen;

    /**
     * Constructor
     * 
     * @throws SecurityException Exception.
     * @throws NoSuchMethodException Exception.
     */
    public GameFrame () {
        myMenu = new SetUpGameMenu(this);
        setUpMenu();

        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        setSize(GameWidth, GameHeight);
        setTitle("Square Attack");
        add(myMenu, BorderLayout.CENTER);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
        mySounds.playSound(SoundManager.THEME_ONE, true);
    }

    public void startGame (String[] controllerConfig) {
        remove(myMenu);
        gameScreen = new GameManager(this, controllerConfig);
        add(gameScreen, BorderLayout.CENTER);

        gameScreen.startGame();
        this.repaint();
        this.validate();
        gameScreen.requestFocus();
    }

    private void setUpMenu () {
        // Creates a menubar for a JFrame
        myMenuBar = new JMenuBar();

        // Add the menubar to the frame
        setJMenuBar(myMenuBar);

        // Define and add two drop down menu to the menubar
        JMenu gameMenu = new JMenu("Game");

        JMenuItem pause = new JMenuItem("Pause/Resume");
        gameMenu.add(pause);
        pause.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed (ActionEvent arg0) {
                if (gameScreen != null) {
                    gameScreen.togglePlay();
                }

            }

        });

        myMenuBar.add(gameMenu);

    }

}
