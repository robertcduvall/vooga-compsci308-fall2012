package gui;

import java.awt.Dimension;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import editor.EditorCanvas;


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
     * Constructor
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

    public void refreshScreen () {
        Dimension mySize = getSize();
        setSize(new Dimension(mySize.width, mySize.height - 1));
        setSize(mySize);
    }
    
    public void initializeCanvas() {
        myCanvases.add(new MenuCanvas(this));
        myCanvases.add(new EditorCanvas(this));
        myCanvases.add(new GameCanvas(this));
        myCurrentCanvas = myCanvases.get(0);
        //default: start with MenuCanvas
        changeCurrentCanvas(MENU);
    }

    public void changeCurrentCanvas (int canvasIndex) {
        remove(myCurrentCanvas);
        myCurrentCanvas = myCanvases.get(canvasIndex);
        add(myCurrentCanvas);
        myCurrentCanvas.initialize();
        refreshScreen();
    }

    public static Image importImage(ResourceBundle resources, String address) {
    	String ImageFolder = resources.getString("ImageFolder");
        ImageIcon myImageIcon = new ImageIcon(ImageFolder + resources.getString(address));
        return myImageIcon.getImage();   
    }

    public void addResourceBundle (String resource) {
        myResources = ResourceBundle.getBundle("resources." + resource);
    }

    public ResourceBundle getResources () {
        return myResources;
    }

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
