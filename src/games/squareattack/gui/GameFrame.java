package games.squareattack.gui;

import games.squareattack.GameManager;
import games.squareattack.objects.SoundManager;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Graphics;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

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
   

    /**
     * Constructor
     * 
     * @throws SecurityException Exception.
     * @throws NoSuchMethodException Exception.
     */
    public GameFrame () {
        myMenu = new SetUpGameMenu(this);
      
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
        GameManager gameScreen = new GameManager(this, controllerConfig);
        add(gameScreen, BorderLayout.CENTER);
        
        gameScreen.startGame();
        this.repaint();
        this.validate();
        gameScreen.requestFocus();
    }
    
   

}
