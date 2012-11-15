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

<<<<<<< HEAD
    //denotes the index of the prototype in myCanvases
    public static final String MENU = "0";
    public static final String EDITOR = "1";
    public static final String GAME = "2";
=======
    // denotes the index of the prototype in myCanvases
    public static int MENU = 0;
    public static int EDITOR = 1;
    public static int GAME = 2;
>>>>>>> master

    private final String RESOURCES_LOCATION = "vooga.turnbased.resources";
    private static ResourceBundle myResources;

<<<<<<< HEAD
    private Container myContentPane;
    private CardLayout myLayout;
=======
    private GameCanvas myCanvas;
    private ResourceBundle myResources;

    private JMenuBar myMenuBar;
    private Canvas myCurrentCanvas;
    // contains MenuCanvas, EditorCanvas and GameCanvas
    private List<Canvas> myCanvases = new ArrayList<Canvas>();
>>>>>>> master

    /**
     * Constructor construct a game window given the size of the window
     * 
     * @param width Width of the window
     * @param height Height of the window
     * @param title The title of the game
     */
<<<<<<< HEAD
    public GameWindow (String title, String settingsResource, int width, int height) {
=======
    public GameWindow(int width, int height, String title) {
        myWidth = width;
        myHeight = height;
>>>>>>> master
        setTitle(title);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(width, height);
        setVisible(true);
        setResizable(true);
<<<<<<< HEAD
        addResourceBundle(settingsResource);
        initializeGamePanes();
=======
    }

    /**
     * refresh the screen to update new changes to its components
     */
    public void refreshScreen() {
        Dimension mySize = getSize();
        setSize(new Dimension(mySize.width, mySize.height - 1));
        setSize(mySize);
>>>>>>> master
    }

    /**
     * initialize the Canvases as prototypes
     */
<<<<<<< HEAD
    private void initializeGamePanes() {
    	myContentPane = getContentPane();
    	myLayout = new CardLayout();
    	myContentPane.setLayout(myLayout);
    	myContentPane.add(new MenuPane(this), MENU);
    	myContentPane.add(new EditorPane(this), EDITOR);
    	myContentPane.add(new GamePane(this), GAME);
        changeActivePane(MENU);
=======
    public void initializeCanvas() {
        myCanvases.add(new MenuCanvas(this));
        myCanvases.add(new EditorCanvas(this));
        myCanvases.add(new GameCanvas(this));
        myCurrentCanvas = myCanvases.get(MENU);
        // default: start with MenuCanvas
        changeCurrentCanvas(MENU);
>>>>>>> master
    }

    /**
     * change the current Canvas to a specific canvas specified by canvasIndex
<<<<<<< HEAD
     * @param paneName MENU="Menu"; EDITOR="Editor"; GAME="Game"
     */
    public void changeActivePane (String paneName) {
    	myLayout.show(myContentPane, paneName);
    	DisplayPane myCurrentPane = (DisplayPane) myContentPane.getComponent(
    			Integer.parseInt(paneName));
    	myCurrentPane.initialize();
=======
     * 
     * @param canvasIndex MENU=0; EDITOR=1; GAME=2
     */
    public void changeCurrentCanvas(int canvasIndex) {
        remove(myCurrentCanvas);
        myCurrentCanvas = myCanvases.get(canvasIndex);
        add(myCurrentCanvas);
        myCurrentCanvas.initialize();
        refreshScreen();
>>>>>>> master
    }

    /**
     * import images from ResourceBundle
     * 
     * @param resources ResourceBundle object
     * @param imageName image name under the image folder
     * @return Image object
     */
<<<<<<< HEAD
    public static Image importImage(String imageName) {
    	String imageFolder = myResources.getString("ImageFolder");
        ImageIcon imageIcon = new ImageIcon(imageFolder + importString(imageName));
        return imageIcon.getImage();   
=======
    public static Image importImage(ResourceBundle resources, String address) {
        String ImageFolder = resources.getString("ImageFolder");
        ImageIcon myImageIcon = new ImageIcon(ImageFolder
                + resources.getString(address));
        return myImageIcon.getImage();
    }

    /**
     * add the ResourceBundle to the canvas
     * 
     * @param resource The name of the resource bundle
     */
    public void addResourceBundle(String resource) {
        myResources = ResourceBundle.getBundle("resources." + resource);
>>>>>>> master
    }
    
    /**
<<<<<<< HEAD
     * import string from ResourceBundle
     * 
     * @param stringName string name in resource bundle
     * @return
     */
    public static String importString(String stringName) {
        return myResources.getString(stringName); 
=======
     * get the ResourceBundle that is currently used by the Canvas
     * 
     * @return ResourceBundle object used for the canvas
     */
    public ResourceBundle getResources() {
        return myResources;
>>>>>>> master
    }
    
    /**
<<<<<<< HEAD
     * add the ResourceBundle to the canvas
     * @param resource Path to the resource bundle
     */
    private void addResourceBundle (String resource) {
        myResources = ResourceBundle.getBundle(RESOURCES_LOCATION + "." + resource);
=======
     * set a ResourceBundle to be used by the canvas
     * 
     * @param myResources the ResourceBundle to be used
     */
    public void setResources(ResourceBundle myResources) {
        this.myResources = myResources;
>>>>>>> master
    }
}
