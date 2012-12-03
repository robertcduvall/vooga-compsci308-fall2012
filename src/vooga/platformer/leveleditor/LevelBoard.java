package vooga.platformer.leveleditor;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import vooga.platformer.gameobject.Enemy;
import vooga.platformer.gameobject.Player;
import vooga.platformer.gameobject.StaticObject;
import vooga.platformer.gameobject.GameObject;
import vooga.platformer.level.condition.Condition;
import vooga.platformer.level.levelplugin.LevelPlugin;
import vooga.platformer.leveleditor.leveldrawer.IEditorObject;
import vooga.platformer.levelfileio.LevelFileReader;
import vooga.platformer.levelfileio.LevelFileWriter;


/*
 * I've decided to decouple this class from the actual act of sprite placement.
 * The reason for this is that sprite placement has the potential to become
 * VERY complicated, particularly if we implement the "square grid" idea, in
 * which the sprite snaps into position based on the position of the
 * closest grid square. We also may not want sprites to be placed on top of
 * each other etc. Therefore, I've decided to delegate responsibility of
 * actually placing sprites to a sprite placement manager which can interact
 * with this class through its protected methods.
 */

/**
 * Represents the main window for the level editor. Will display a collection
 * of Sprites and will oversee the results of all user actions.
 * 
 * @author Paul Dannenberg
 * @author Sam Rang
 * 
 */
public class LevelBoard extends JPanel {
    private static final String DATA_PATH = "/src/vooga/platformer/data/";
    private static final String DEFAULT_CAMERA = "FollowingCamera";
    private static final String DEFAULT_COLLISION_CHECKER = "BasicCollisionChecker";
    private static final int DEFAULT_SIZE = 30;

    //Editor fields
    private Collection<String> myAttributes;
    private int myObjID;
    private IEditorMode myCurrentMode;
    private BufferedImage myBuffer;
    private Graphics2D myBufferGraphics;
    private MouseAdapter myPlacementManager;
    private KeyListener myKeyListener;
    private MouseListener myButtonListener;
    private int mouseX;
    private int mouseY;
    private int myLength;
    private int myOffset;

    //Level state
    private Collection<GameObject> myGameObjects;
    private Collection<Condition> myConditions;
    private Collection<LevelPlugin> myPlugins;
    private GameObject myCurrentObject;
    private Player myPlayer;
    private Image myBackground;
    private String myBackgroundPath;
    private String myLevelName;
    private String myCamera;
    //    private List<Integer> myKeyHeld;

    /**
     * Creates a new LevelBoard, visible to the user. The LevelBoard starts
     * off empty.
     * 
     * @param d Dimension for initial level size (determined by Frame)
     */
    public LevelBoard (Dimension d) {
        setSize(d); 
        initLevelDefaults();
        myCurrentMode = new PlacementMode();
        myBackground = null;
        myBuffer = new BufferedImage(d.width, d.height, BufferedImage.TYPE_INT_RGB);
        myBufferGraphics = myBuffer.createGraphics();
        myAttributes = getAttributes();
        setupInput();
    }

    private Collection<String> getAttributes () {

        return null;
    }

    public void initLevelDefaults () {
        myObjID = 0;
        myCamera = DEFAULT_CAMERA;
        myLevelName = "Level";
        myGameObjects = new ArrayList<GameObject>();
        myConditions = new ArrayList<Condition>();
        myPlugins = new ArrayList<LevelPlugin>(); 
        myPlayer = null;
    }

    private void setupInput () {
        myPlacementManager = new PlacementMouseListener(this);
        //                        JFileChooser chooser = new JFileChooser(System.getProperty("user.dir"));
        //                        FileNameExtensionFilter filter =
        //                                new FileNameExtensionFilter("JPG & GIF Images", "jpg", "gif", "png");
        //                        chooser.setFileFilter(filter);
        //                        int returnVal = chooser.showOpenDialog(chooser);
        //                        if (returnVal == JFileChooser.APPROVE_OPTION) {
        //                            try {
        //                                myBackground = ImageIO.read(chooser.getSelectedFile());
        //                                myBackgroundPath = chooser.getSelectedFile().getPath();
        //                            }
        //                            catch (IOException io) {
        //                                System.out.println("File not found. Try again");
        //                                myBackground = null;
        //                            }
        //                        }
        myKeyListener = new ScrollingKeyListener(this);

        myButtonListener = new MouseAdapter() {
            @Override
            public void mouseReleased (MouseEvent e) {
                GameObject obj = null;
                try {
                    String cmmd = e.getComponent().getName();
                    File f = new File(System.getProperty("user.dir") + DATA_PATH + "Default.png");
                    ImageIcon ii = new ImageIcon(ImageIO.read(f));
                    int x = LevelBoard.this.getWidth() / 2;
                    int y = LevelBoard.this.getHeight() / 2;
                    int w = ii.getIconWidth() / ii.getIconHeight() * DEFAULT_SIZE;
                    int h = ii.getIconHeight() / ii.getIconWidth() * DEFAULT_SIZE;
                    if ("StaticObject".equals(cmmd)) {
                        obj = new StaticObject((double)x, (double)y, (double)w, (double)h, myObjID++, f);
                    }
                    else if ("Enemy".equals(cmmd)) {
                        obj = new Enemy((double)x, (double)y, (double)w, (double)h, myObjID++, f);
                    }
                    else if ("Player".equals(cmmd)) {
                        obj = new Player((double)x, (double)y, (double)w, (double)h, myObjID++, f);
                    }
                    else if ("Plugin".equals(cmmd)) {
                        System.out.println("plugin");
                    }
                    ((PlacementMouseListener)myPlacementManager).setCurrent(obj);
                    myGameObjects.add(obj);
                }
                catch(IOException ex) {
                    ex.printStackTrace();
                }

            }

        };
        addMouseListener(myPlacementManager);
        addMouseMotionListener(myPlacementManager);
    }

    /**
     * Passes the LevelEditorMouseListner as a MouseListener
     * to any components that need it.
     * 
     * @return MouseListener attached to component
     */
    public MouseListener getMouseListener () {
        return myPlacementManager;
    }

    public KeyListener getKeyListener () {
        return myKeyListener;
    }

    public MouseListener getButtonListener () {
        return myButtonListener;
    }

    /**
     * Passes the MouseAdapter as a MouseMotionListener.
     * 
     * @return MouseMotionListener attached to component
     */
    public MouseMotionListener getMouseMotionListener () {
        return myPlacementManager;
    }

    public void setOffset(int offset) {
        myOffset = offset;
    }

    public void setMouseLoc(int xloc, int yloc) {
        mouseX = xloc;
        mouseY = yloc;
    }

    /**
     * Updates the buffer preparing for the next paint call.
     */
    public void update () {
        myCurrentObject = ((PlacementMouseListener)myPlacementManager).getCurrent();
        if(myLength <= getWidth() + myOffset) {
            myLength = getWidth() + myOffset;
        }
        myBufferGraphics.clearRect(0, 0, myBuffer.getWidth(), myBuffer.getHeight());
        myBufferGraphics.drawImage(myBackground, 0, 0, myBuffer.getWidth(), myBuffer.getHeight(),
                this);
        for (GameObject obj : myGameObjects) {
            myBufferGraphics.drawImage(obj.getCurrentImage(), (int) obj.getX() - myOffset, (int) obj.getY(),
                    (int) obj.getWidth(), (int) obj.getHeight(), null);
        }
        String mousemsg = "";
        if (myCurrentObject != null) {
            myCurrentObject.setX(mouseX - myCurrentObject.getWidth() / 2 + myOffset);
            myCurrentObject.setY(mouseY - myCurrentObject.getHeight() / 2);
            myBufferGraphics.setColor(Color.ORANGE);
            myBufferGraphics.drawRect((int)myCurrentObject.getX()-myOffset, (int)myCurrentObject.getY(),
                    (int)myCurrentObject.getWidth(), (int)myCurrentObject.getHeight());
            mousemsg = "Current Sprite = (";
        }
        else {
            mousemsg = "Mouse Location = ";
        }
        myBufferGraphics.drawString(mousemsg + mouseX + ", " + mouseY + ")",
                getWidth() - 250, 30);
    }

    /**
     * Paints the most recent iteration of the buffer to the Canvas.
     * 
     * @param g Graphics attached to level.
     */
    @Override
    public void paint (Graphics g) {
        update();
        g.drawImage(myBuffer, 0, 0, myBuffer.getWidth(), myBuffer.getHeight(), this);
        super.paintComponents(g);

    }

    public void save () {
        JFileChooser fc = new JFileChooser(System.getProperty("user.dir"));
        FileFilter filter = new FileNameExtensionFilter("XML file", "xml");
        fc.addChoosableFileFilter(filter);
        int returnVal = fc.showSaveDialog(this);
        File saveFile = null;
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            saveFile = fc.getSelectedFile();
            // This is where a real application would save the file.

            // log.append("Saving: " + file.getName() + "." + newline);
        }
        else {
            saveFile = new File(System.getProperty("user.dir"), "myLevel.xml");
            // log.append("Save command cancelled by user." + newline);
        }
        LevelFileWriter.writeLevel(saveFile.getPath(), myLevelName, myLength, getHeight(),
                myBackgroundPath, myGameObjects,
                myConditions, myPlugins,
                myCamera,
                "Default_Collision_Checker");
        // "LevelTitle", getWidth(), getHeight(),
        // myBackgroundPath, myGameObjects, "myCollision", "myCamera");
    }

    public void load (URL path) {
        new LevelFileReader(path.getPath());
    }

    public void clear () {
        myGameObjects.clear();
    }

    protected void objectPopupMenu (GameObject g, MouseEvent e) {
        JPopupMenu pop = new JPopupMenu();
        SelectionHelper sh = new SelectionHelper(g);
        JMenuItem j = new JMenuItem("Flip");
        j.addActionListener(sh);
        pop.add(j);
        JMenuItem j2 = new JMenuItem("Edit");
        j2.addActionListener(sh);
        pop.add(j2);
        JMenuItem j3 = new JMenuItem("Delete");
        j3.addActionListener(sh);
        pop.add(j3);
        pop.show(this.getParent(), (int) (g.getX() + g.getWidth() / 2),
                (int) (g.getY() + g.getHeight()));
    }

    /**
     * @return An unmodifiable Collection of the sprites
     *         currently positioned on the board.
     */
    protected Collection<GameObject> getGameObjects () {
        return Collections.unmodifiableCollection(myGameObjects);
    }

    /**
     * Adds a sprite to the board. This method does not
     * check for any conditions (such as sprites being)
     * added on top of each other.
     * 
     * @param obj GameObject being added
     */
    protected void addObject (GameObject obj) {
        myCurrentObject = obj;
        myGameObjects.add(obj);
    }

    /**
     * Will remove a sprite from the board.
     * 
     * @param sprite The sprite that should
     *        be removed.
     */
    protected void remove (GameObject obj) {
        myGameObjects.remove(obj);
    }

    protected void setGrav(int value) {
        System.out.println(value);
    }

    private class SelectionHelper implements ActionListener {
        private GameObject myObject;

        public SelectionHelper (GameObject obj) {
            myObject = obj;
        }

        @Override
        public void actionPerformed (ActionEvent event) {
            if ("Flip".equals(event.getActionCommand())) {
                myObject.flipImage();
            }
            else if ("Add attribute".equals(event.getActionCommand())) {
                createAttributeWindow();
            }
            else if ("Delete".equals(event.getActionCommand())) {
                myGameObjects.remove(myObject);
            }
            else {
                System.out.println("Added " + event.getActionCommand() + " as an attribute");
            }

        }

        private void createAttributeWindow () {
            JPopupMenu pop = new JPopupMenu();
            for (String att : myAttributes) {
                JMenuItem j = new JMenuItem(att);
                j.addActionListener(this);
                pop.add(j);
            }
            pop.show(LevelBoard.this, (int) (myObject.getX() + myObject.getWidth() / 2),
                    (int) (myObject.getY() + myObject.getHeight() / 2));

            /*
             * create a list of attributes from the resource file
             * and get appropriate values for certain attributes.
             */
        }
    }

    /**
     * A TEMPORARY mouse listener for the current modes this level editor
     * supports.
     * And yes, I realize we have 3 different mouse listeners for this class.
     * 
     * @author Paul Dannenberg
     * 
     */
    private class LevelEditorMouseListener extends MouseAdapter {

        @Override
        public void mouseMoved (MouseEvent e) {
            myCurrentMode.sendCursorPosition(e.getX(), e.getY());
        }

        @Override
        public void mousePressed (MouseEvent e) {
            if (e.getButton() == MouseEvent.BUTTON1) {
                myCurrentMode.primaryButtonPress(e.getX(), e.getY());
            }
            else if (e.getButton() == MouseEvent.BUTTON3) {
                myCurrentMode.secondaryButtonPress(e.getX(), e.getY());
            }
        }
    }

    /**
     * Switches from one mode to the next, saving objects from the first and
     * ensuring
     * these are successfully handed to the next mode.
     * 
     * @param currentMode The current mode the editor is in.
     * @param nextMode The next mode the editor is about to transition to.
     */
    private void transitionBetweenModes (IEditorMode currentMode, IEditorMode nextMode) {
        if (currentMode != null) {
            Collection<IEditorObject> editorObjectsToKeep = currentMode.getEditorObjects();
            for (IEditorObject objectFromPreviousMode : editorObjectsToKeep) {
                nextMode.add(objectFromPreviousMode);
            }
        }
    }

    public void setMode (IEditorMode nextMode) {
        if (nextMode == null) { throw new IllegalArgumentException(); }
        transitionBetweenModes(myCurrentMode, nextMode);
        myCurrentMode = nextMode;
    }
}
