package vooga.turnbased.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import util.sound.SoundPlayer;


@SuppressWarnings("serial")
/**
 * Prints game menu buttons before the game is loaded or the game quits
 * Buttons will responds to user input and the screen will show game, editor or
 * some new menus
 * 
 * @author rex, volodymyr
 *
 */
public class MenuPane extends DisplayPane {

    /**
     * Constructor
     * 
     * @param gameWindow GameWindow on which its components will be displayed
     */
    public MenuPane (GameWindow gameWindow) {
        super(gameWindow);
        createButtons();
    }

    /**
     * create buttons for user to choose
     */
    private void createButtons () {
        ImageIcon gameIcon = new ImageIcon(GameWindow.importImage("StartButtonImage"));
        JButton startButton = new JButton(gameIcon);
        startButton.addActionListener(new ActionListener() {
            public void actionPerformed (ActionEvent e) {
                getGameWindow().changeActivePane(GameWindow.GAME);
            }
        });
        add(startButton);

        ImageIcon editorIcon = new ImageIcon(GameWindow.importImage("EditorButtonImage"));
        JButton editorButton = new JButton(editorIcon);
        editorButton.addActionListener(new ActionListener() {
            public void actionPerformed (ActionEvent e) {
                getGameWindow().changeActivePane(GameWindow.EDITOR);
            }
        });
        add(editorButton);
    }
}
