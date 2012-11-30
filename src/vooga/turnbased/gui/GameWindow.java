/**
 * The main game frame that switch between menu, game and editor canvases.
 * Also It is responsible to load general game settings from the resource file
 * 
 * @author Rex, Volodymyr
 */
package vooga.turnbased.gui;

import java.awt.CardLayout;
import java.awt.Container;
import java.awt.Image;
import java.util.ResourceBundle;
import javax.swing.ImageIcon;
import javax.swing.JFrame;


/**
 * contains a Canvas, and menu that respond to change in games
 * 
 * @author Rex, Tony
 **/
public class GameWindow extends JFrame {

    //denotes the index of the prototype in myCanvases
    public static final String MENU = "0";
    public static final String EDITOR = "1";
    public static final String GAME = "2";

    private final String RESOURCES_LOCATION = "vooga.turnbased.resources";
    private static ResourceBundle myResources;

    private Container myContentPane;
    private CardLayout myLayout;

    /**
     * Constructor construct a game window given the size of the window
     * 
     * @param width Width of the window
     * @param height Height of the window
     * @param title The title of the game
     */
    public GameWindow (String title, String settingsResource, int width, int height) {

        setTitle(title);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(width, height);
        setVisible(true);
        setResizable(true);
        addResourceBundle(settingsResource);
        initializeGamePanes();
    }

    /**
     * initialize the Canvases as prototypes
     */
    private void initializeGamePanes() {
    	myContentPane = getContentPane();
    	myLayout = new CardLayout();
    	myContentPane.setLayout(myLayout);
    	myContentPane.add(new MenuPane(this), MENU);
    	myContentPane.add(new EditorPane(this), EDITOR);
    	myContentPane.add(new GamePane(this), GAME);
        changeActivePane(MENU);

    }

    /**
     * change the current Canvas to a specific canvas specified by canvasIndex
     * @param paneName MENU="Menu"; EDITOR="Editor"; GAME="Game"
     */
    public void changeActivePane (String paneName) {
    	myLayout.show(myContentPane, paneName);
    	DisplayPane myCurrentPane = (DisplayPane) myContentPane.getComponent(
    			Integer.parseInt(paneName));
    	myCurrentPane.initialize();
    }

    /**
     * import images from ResourceBundle
     * 
     * @param resources ResourceBundle object
     * @param imageName image name under the image folder
     * @return Image object
     */
    public static Image importImage(String imageName) {
    	String imageFolder = myResources.getString("ImageFolder");
        ImageIcon imageIcon = new ImageIcon(imageFolder + importString(imageName));
        return imageIcon.getImage();   
    }
    
    /**
     * import string from ResourceBundle
     * 
     * @param stringName string name in resource bundle
     * @return
     */
    public static String importString(String stringName) {
        return myResources.getString(stringName);
    }
    
    /**
     * add the ResourceBundle to the canvas
     * @param resource Path to the resource bundle
     */
    private void addResourceBundle (String resource) {
        myResources = ResourceBundle.getBundle(RESOURCES_LOCATION + "." + resource);
    }
}
