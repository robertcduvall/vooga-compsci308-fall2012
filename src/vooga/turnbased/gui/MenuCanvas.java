/**
 * Prints game menu buttons before the game is loaded or the game quits
 * Buttons will responds to user input and the screen will show game, editor or
 * some new menus
 * 
 * @author Rex, Volodymyr
 */
package vooga.turnbased.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;


public class MenuCanvas extends Canvas {

    private GameWindow myGameWindow;

    /**
     * Constructor
     * 
     * @param frame GameWindow on which its components will be displayed
     */
    public MenuCanvas (GameWindow frame) {
        myGameWindow = frame;
    }

    /**
     * create buttons for user to choose
     */
    private void createButtons () {
        ImageIcon gameIcon = new ImageIcon(GameWindow.importImage("StartButtonImage"));
        JButton startButton = new JButton(gameIcon);
        startButton.addActionListener(new ActionListener() {
            public void actionPerformed (ActionEvent e) {
                myGameWindow.changeCurrentCanvas(GameWindow.GAME);
            }
        });
        add(startButton);
        
        ImageIcon editorIcon = new ImageIcon(GameWindow.importImage("EditorButtonImage"));
        JButton editorButton = new JButton(editorIcon);
        editorButton.addActionListener(new ActionListener() {
            public void actionPerformed (ActionEvent e) {
                myGameWindow.changeCurrentCanvas(GameWindow.EDITOR);
            }
        });
        add(editorButton);
    }

    @Override
    /**
     * initialize the canvas when user switch to Menu
     */
    public void initialize () {
        createButtons();
        myGameWindow.refreshScreen();
    }
}
