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
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenuBar;

/**
 * contains a Canvas, and menu that respond to change in games
 * 
 * @author Rex
 **/
public class GameWindow extends JFrame {

    //denotes the index of the prototype in myCanvases
    public static int MENU = 0;
    public static int EDITOR = 1;
    public static int GAME = 2;

    private int myInfoHeight = 100;
    private int myWidth;
    private int myHeight;

    private GameCanvas myCanvas;
    private ResourceBundle myResources;

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
        remove(myCurrentCanvas);
        myCurrentCanvas = myCanvases.get(canvasIndex);
        add(myCurrentCanvas);
        myCurrentCanvas.initialize();
        refreshScreen();
    }

    /**
     * import images from ResourceBundle
     * @param resources ResourceBundle object
     * @param address image name under the image folder
     * @return Image object
     */
    public static Image importImage(ResourceBundle resources, String address) {
    	String ImageFolder = resources.getString("ImageFolder");
        ImageIcon myImageIcon = new ImageIcon(ImageFolder + resources.getString(address));
        return myImageIcon.getImage();   
    }

    /**
     * add the ResourceBundle to the canvas
     * @param resource The name of the resource bundle
     */
    public void addResourceBundle (String resource) {
        myResources = ResourceBundle.getBundle("resources." + resource);
    }

    /**
     * get the ResourceBundle that is currently used by the Canvas
     * @return ResourceBundle object used for the canvas
     */
    public ResourceBundle getResources () {
        return myResources;
    }

    /**
     * set a ResourceBundle to be used by the canvas
     * @param myResources the ResourceBundle to be used
     */
    public void setResources (ResourceBundle myResources) {
        this.myResources = myResources;
    }
    /*
     * public void startFirstGame() {
     * myCanvas = new Canvas(myResources, myWidth, myHeight-myInfoHeight, this);
     * add(myCanvas, BorderLayout.NORTH);
     * }
     */
}
