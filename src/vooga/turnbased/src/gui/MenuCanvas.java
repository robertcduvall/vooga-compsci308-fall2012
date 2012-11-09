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

    public MenuCanvas (GameWindow frame) {
        myFrame = frame;
    }

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

    public void paint (Graphics g) {

    }

    @Override
    public void initialize () {
        myResources = myFrame.getResources();
        createButtons();
        myFrame.refreshScreen();
    }
}
