package vooga.turnbased.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
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

    private String myDefaultXML = "vooga.turnbased.resources.level";

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
                JFileChooser fc = new JFileChooser();
                int returnVal = fc.showOpenDialog(MenuPane.this);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fc.getSelectedFile();
                } 
            }
        });
        add(loadXMLButton);
    }


    public void createXMLEntryTextField () {
        JTextField tf = new JTextField(30);
        tf.setText(myDefaultXML + ".Level1Final");
        add(tf);
    }
}
