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

	private final String myDefaultFilePath = "src/vooga/turnbased/resources/level/Level1Final.xml";
	private final String myDefaultPlayerPath = "src/vooga/turnbased/resources/level/PlayerFinal.xml";
	private JTextField myXmlTextField;
	private JTextField myPlayerTextField;
	private int myTextFieldLength = 65;

	/**
	 * Constructor
	 * 
	 * @param gameWindow
	 *            GameWindow on which its components will be displayed
	 */
	public MenuPane(GameWindow gameWindow) {
		super(gameWindow);
		createButtons();
		myXmlTextField = createXMLEntryTextField(myDefaultFilePath);
		getGameWindow().setXmlPath(myDefaultPlayerPath);
		myPlayerTextField = createXMLEntryTextField(myDefaultPlayerPath);
		getGameWindow().setPlayerXml(myDefaultFilePath);
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
				if ((new File(myXmlTextField.getText())).exists() &&
						(new File(myPlayerTextField.getText())).exists()) {
					getGameWindow().setXmlPath(myXmlTextField.getText());
					getGameWindow().setPlayerXml(myPlayerTextField.getText());
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
				JFileChooser fc = new JFileChooser(myDefaultFilePath);
				int returnVal = fc.showOpenDialog(MenuPane.this);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					myXmlTextField.setText(file.getPath());
				}
			}
		});
		add(loadXmlButton);

		JButton playerXmlButton = new JButton("Load XML");
		playerXmlButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser(myDefaultFilePath);
				int returnVal = fc.showOpenDialog(MenuPane.this);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					myPlayerTextField.setText(file.getPath());
				}
			}
		});
		add(playerXmlButton);
	}

	/**
	 * Creates a text field on the menu and populates it with default xml level
	 * filepath.
	 */
	public JTextField createXMLEntryTextField(String defaultValue) {
		JTextField textField = new JTextField(myTextFieldLength);
		textField.setText(defaultValue);
		add(textField);
		return textField;
	}
}
