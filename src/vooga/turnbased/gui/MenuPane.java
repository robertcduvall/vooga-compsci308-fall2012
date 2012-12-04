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

    private String myDefaultFilepath = "src/vooga/turnbased/resources/level/Level1Final.xml";
    private JTextField myTextField;
    private int myTextFieldLength = 75;

    /**
     * Constructor
     * 
     * @param gameWindow GameWindow on which its components will be displayed
     */
    public MenuPane (GameWindow gameWindow) {
        super(gameWindow);
        createButtons();
        createXMLEntryTextField();
    }

    /**
     * create buttons for user to choose
     */
    private void createButtons () {
        ImageIcon gameIcon = new ImageIcon(GameWindow.importImage("StartButtonImage"));
        JButton startButton = new JButton(gameIcon);
        startButton.addActionListener(new ActionListener() {
            public void actionPerformed (ActionEvent e) {
                getGameWindow().setXmlPath(myTextField.getText());
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

        JButton loadXMLButton = new JButton("Load XML");
        loadXMLButton.addActionListener(new ActionListener() {
            public void actionPerformed (ActionEvent e) {
                JFileChooser fc = new JFileChooser(myDefaultFilepath);
                int returnVal = fc.showOpenDialog(MenuPane.this);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fc.getSelectedFile();
                    myTextField.setText(file.getPath());
                } 
            }
        });
        add(loadXMLButton);
    }

    /**
     * Creates a text field on the menu and populates it with default xml level filepath.
     */
    public void createXMLEntryTextField () {
        myTextField = new JTextField(myTextFieldLength);
        myTextField.setText(myDefaultFilepath);
        getGameWindow().setXmlPath(myDefaultFilepath);
        add(myTextField);
    }
}
