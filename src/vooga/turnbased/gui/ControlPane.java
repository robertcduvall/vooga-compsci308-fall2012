package vooga.turnbased.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JTextArea;


/**
 * Displays the basic controls of the game.
 * @author Jenni
 * 
 */
@SuppressWarnings("serial")
public class ControlPane extends DisplayPane {

    private String myDisplayText = "WELCOME TO THE TURNBASED RPG \naka the game with copious " +
            "amounts of swag\n\n The controls for the RPG game are as follows: \n\n MapMode:\n " +
            "- Right click on a tile to have your player automatically move there\n " +
            "- Use the arrow keys to move the player manually\n - Press the 'r' key to switch " +
            "between running and walking\n\nBattleMode:\n - Use the arrow keys to move your " +
            "cursor over the various battle options\n - Press enter when your cursor is next to " +
            "the desired action to have your player execute that action\n\n In all modes:\n - " +
            "Press the 'm' key to play music";

    /**
     * Constructor.
     * @param gameWindow GameWindow in which the pane will be displayed.
     */
    public ControlPane (GameWindow gameWindow) {
        super(gameWindow);
        displayControls();
        addReturnToMenuButton();
    }

    private void addReturnToMenuButton () {
        JButton menuButton = new JButton("Go back to menu");
        menuButton.addActionListener(new ActionListener() {
            public void actionPerformed (ActionEvent e) {
                getGameWindow().changeActivePane(GameWindow.MENU);
            }
        });
        add(menuButton);
    }

    private void displayControls () {
        JTextArea textArea = new JTextArea(30, 70);
        textArea.setText(myDisplayText);
        textArea.setEditable(false);
        add(textArea);
    }

}
