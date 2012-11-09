/**
 * Prints game menu buttons before the game is loaded or the game quits
 * Buttons will responds to user input and the screen will show game, editor or some new menus
 * @author Rex, Volodymyr
 */
package gui;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MenuCanvas extends Canvas {

    private ResourceBundle myResources;
    private GameWindow myFrame;

    /**
     * Constructor
     * @param frame GameWindow on which its components will be displayed
     */
    public MenuCanvas (GameWindow frame) {
        myFrame = frame;
    }

    /**
     * create buttons for user to choose
     */
    private void createButtons() {
		String ImageFolder = myResources.getString("ImageFolder");
		ImageIcon myImageIcon = new ImageIcon(ImageFolder + myResources.getString("StartButtonImage"));
		JButton startButton = new JButton(myImageIcon);
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				myFrame.changeCurrentCanvas(GameWindow.GAME);
			}
		});
		add(startButton);
	}

    @Override
    /**
     * initialize the canvas when user switch to Menu
     */
    public void initialize () {
        myResources = myFrame.getResources();
        createButtons();
        myFrame.refreshScreen();
    }
}
