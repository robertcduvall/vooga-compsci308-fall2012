package vooga.turnbased.gui;

import java.awt.CardLayout;
import java.awt.Container;
import java.awt.Image;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import util.sound.SoundPlayer;


/**
 * The main game frame that switch between menu, game and editor canvases.
 * Also It is responsible to load general game settings from the resource file
 * 
 * @author David, Rex, Tony, volodymyr, Jenni
 **/
@SuppressWarnings("serial")
public class GameWindow extends JFrame {
    /**
     * Index values for each card in the CardLayout
     */
    public static final String MENU = "0";
    public static final String EDITOR = "1";
    public static final String CONTROLS = "2";
    public static final String GAME = "3";

    /**
     * Location for game configuration resource bundles
     */
    private static ResourceBundle myResources;
    private final String RESOURCES_LOCATION = "vooga.turnbased.resources";

    private Container myContentPane;
    private CardLayout myLayout;
    private String myXmlPath;

    /**
     * Constructor construct a game window given the size of the window
     * 
     * @param title The title of the game
     * @param settingsResource name of game configuration file
     * @param width Width of the window
     * @param height Height of the window
     */
    public GameWindow (String title, String settingsResource, int width, int height, String xmlPath) {
        setTitle(title);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(width, height);
        setResizable(true);
        addResourceBundle(settingsResource);
        setXmlPath(xmlPath);
        initializeGamePanes();
        setVisible(true);
    }

    /**
     * initialize the Canvases as prototypes
     */
    private void initializeGamePanes () {
        myContentPane = getContentPane();
        myLayout = new CardLayout();
        myContentPane.setLayout(myLayout);
        myContentPane.add(new MenuPane(this, myXmlPath), MENU);
        myContentPane.add(new EditorPane(this), EDITOR);
        myContentPane.add(new ControlPane(this), CONTROLS);
        myContentPane.add(new GamePane(this), GAME);
        changeActivePane(MENU);
    }

    /**
     * change the current Canvas to a specific canvas specified by canvasIndex
     * 
     * @param paneName MENU="Menu"; EDITOR="Editor"; GAME="Game"
     */
    public void changeActivePane (String paneName) {
        myLayout.show(myContentPane, paneName);
        DisplayPane myCurrentPane = (DisplayPane) myContentPane.getComponent(Integer
                .parseInt(paneName));
        myCurrentPane.initialize();
    }

    /**
     * import images from ResourceBundle
     * 
     * @param imageName image name under the image folder
     * @return Image object
     */
    public static Image importImage (String imageName) {
        String imageFolder = myResources.getString("ImageFolder");
        ImageIcon imageIcon;
        try {
        	imageIcon = new ImageIcon(imageFolder + importString(imageName));
        }
        catch (Exception e) {
        	imageIcon = new ImageIcon(importString(imageName));
        }
        return imageIcon.getImage();
    }

    /**
     * import string from ResourceBundle
     * 
     * @param stringName string name in resource bundle
     * @return
     */
    public static String importString (String stringName) {
        return myResources.getString(stringName);
    }

    /**
     * play sound on demand
     * 
     * @param string string name in resource bundle
     */
    public static void playSound (String string) {
        SoundPlayer p = new SoundPlayer(importString(string));
        p.playOnce();
    }

    /**
     * add the ResourceBundle to the canvas
     * 
     * @param resource Path to the resource bundle
     */
    private void addResourceBundle (String resource) {
    	try {
    		myResources = ResourceBundle.getBundle(RESOURCES_LOCATION + "." + resource);
    	}
    	catch (MissingResourceException e) {
    		myResources = ResourceBundle.getBundle(resource);
    	}
    }

    /**
     * Sets the xml path to be the given string.
     * 
     * @param path String path name to xml level.
     */
    public void setXmlPath (String path) {
        myXmlPath = path;
    }

    /**
     * Returns the string path of the xml file to be loaded.
     * 
     * @return String path.
     */
    public String getXmlPath () {
        return myXmlPath;
    }
}
