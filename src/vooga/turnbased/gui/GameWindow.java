/**
 * The main game frame that switch between menu, game and editor canvases.
 * Also It is responsible to load general game settings from the resource file
 * @author Rex, Volodymyr
 */
package vooga.turnbased.gui;

import java.awt.Dimension;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenuBar;

/**
 * contains a Canvas, and menu that respond to change in games
 * 
 * @author Rex, Tony
 **/
public class GameWindow extends JFrame {

    //denotes the index of the prototype in myCanvases
    public static int MENU = 0;
    public static int EDITOR = 1;
    public static int GAME = 2;

    private int myInfoHeight = 100;
    private int myWidth;
    private int myHeight;
    private final String RESOURCES_LOCATION = "vooga.turnbased.resources";

    private GameCanvas myCanvas;
    private static ResourceBundle myResources;

    private JMenuBar myMenuBar;
    private Canvas myCurrentCanvas;
    //contains MenuCanvas, EditorCanvas and GameCanvas
    private List<Canvas> myCanvases = new ArrayList<Canvas>();

    /**
     * Constructor construct a game window given the size of the window
     * @param width Width of the window
     * @param height Height of the window
     * @param title The title of the game
     */
    public GameWindow (int width, int height, String title) {
        myWidth = width;
        myHeight = height;
        setTitle(title);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(width, height);
        setVisible(true);
        setResizable(true);
    }

    /**
     * refresh the screen to update new changes to its components
     */
    public void refreshScreen () {
        Dimension mySize = getSize();
        setSize(new Dimension(mySize.width, mySize.height - 1));
        setSize(mySize);
    }
    
    /**
     * initialize the Canvases as prototypes
     */
    public void initializeCanvas() {
        myCanvases.add(new MenuCanvas(this));
        myCanvases.add(new EditorCanvas(this));
        myCanvases.add(new GameCanvas(this));
        myCurrentCanvas = myCanvases.get(MENU);
        //default: start with MenuCanvas
        changeCurrentCanvas(MENU);
    }

    /**
     * change the current Canvas to a specific canvas specified by canvasIndex
     * @param canvasIndex MENU=0; EDITOR=1; GAME=2
     */
    public void changeCurrentCanvas (int canvasIndex) {
        myCurrentCanvas.removeAll();
        remove(myCurrentCanvas);
        myCurrentCanvas = myCanvases.get(canvasIndex);
        add(myCurrentCanvas);
        myCurrentCanvas.initialize();
        refreshScreen();
    }

    /**
     * import images from ResourceBundle
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
    public void addResourceBundle (String resource) {
        myResources = ResourceBundle.getBundle(RESOURCES_LOCATION + "." + resource);
    }
    
    /*
     * public void startFirstGame() {
     * myCanvas = new Canvas(myResources, myWidth, myHeight-myInfoHeight, this);
     * add(myCanvas, BorderLayout.NORTH);
     * }
     */
}
