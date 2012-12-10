package vooga.turnbased.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTextField;

@SuppressWarnings("serial")
/**
 * Prints game menu buttons before the game is loaded or the game quits
 * Buttons will responds to user input and the screen will show game, editor or
 * some new menus
 * 
 * @author rex, volodymyr, Jenni
 *
 */
public class MenuPane extends DisplayPane {

	private JTextField myXmlTextField;
	private int myTextFieldLength = 65;

	/**
	 * Constructor
	 * 
	 * @param gameWindow
	 *            GameWindow on which its components will be displayed
	 */
	public MenuPane(GameWindow gameWindow, String xmlPath) {
		super(gameWindow);
		createButtons();
		myXmlTextField = createXMLEntryTextField(xmlPath);
	}

	/**
	 * create buttons for user to choose
	 */
	private void createButtons() {
		ImageIcon gameIcon = new ImageIcon(
				GameWindow.importImage("StartButtonImage"));
		JButton startButton = new JButton(gameIcon);
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if ((new File(myXmlTextField.getText())).exists()) {
					getGameWindow().setXmlPath(myXmlTextField.getText());
					getGameWindow().changeActivePane(GameWindow.GAME);
				}
			}
		});
		add(startButton);

		ImageIcon editorIcon = new ImageIcon(
				GameWindow.importImage("EditorButtonImage"));
		JButton editorButton = new JButton(editorIcon);
		editorButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getGameWindow().changeActivePane(GameWindow.EDITOR);
			}
		});
		add(editorButton);

		JButton controlButton = new JButton("See Controls");
		controlButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getGameWindow().changeActivePane(GameWindow.CONTROLS);
			}
		});
		add(controlButton);

		JButton loadXmlButton = new JButton("Load XML");
		loadXmlButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser(System
			            .getProperties().getProperty("user.dir"));
				int returnVal = fc.showOpenDialog(MenuPane.this);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					myXmlTextField.setText(file.getPath());
				}
			}
		});
		add(loadXmlButton);
	}

	/**
	 * Creates a text field on the menu and populates it with default xml level
	 * filepath.
	 */
	public JTextField createXMLEntryTextField(String defaultValue) {
		JTextField textField = new JTextField(myTextFieldLength);
		textField.setText(defaultValue);
		textField.setEditable(false);
		add(textField);
		return textField;
	}
}
