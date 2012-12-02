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
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import vooga.platformer.gameobject.Brick;
import vooga.platformer.gameobject.GameObject;
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
 * @author Paul Dannenberg, Sam Rang
 * 
 */
public class LevelBoard extends JPanel implements ISavable {

    private static final int SCROLL_SPEED = 2;
    private static final long serialVersionUID = -3528519211577278934L;
    private Collection<GameObject> myGameObjects;
    private Collection<String> myAvailableAttributes;
    private IEditorMode myCurrentMode;
    private BufferedImage myBuffer;
    private Graphics2D myBufferGraphics;
    private LevelEditorMouseListener myMouseListener;
    private Image myBackground;
    private GameObject myCurrentObject;
    private String myLevelName;
    private String myLevelType;
    private String myBackgroundPath;
    private int mouseX;
    private int mouseY;
    private int myLength;
    private int myOffset;
    private KeyListener myKeyListener;
    private List<Integer> myKeyHeld; 

    /**
     * Creates a new LevelBoard, visible to the user. The LevelBoard starts
     * off empty.
     * 
     * @param d Dimension for initial level size (determined by Frame)
     */
    public LevelBoard(Dimension d) {
        setSize(d);
        myKeyHeld = new ArrayList<Integer>();
        myGameObjects = new ArrayList<GameObject>();
        myAvailableAttributes = new ArrayList<String>();
        myAvailableAttributes.add("HP"); myAvailableAttributes.add("Shooting"); myAvailableAttributes.add("Flying");
        myAvailableAttributes.add("Patrolling"); myAvailableAttributes.add("Teammate");
        myCurrentMode = new PlacementMode();
        myBackground = null;
        myBuffer = new BufferedImage(d.width, d.height, BufferedImage.TYPE_INT_RGB);
        myBufferGraphics = myBuffer.createGraphics();
        setupMouseInput();
        myOffset = 0;
    }

    public void incLength() {

    }

    private void setupMouseInput() {
        LevelEditorMouseListener mouseListener = new LevelEditorMouseListener() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (myCurrentObject != null) {
                    myCurrentObject = null;
                }
                else {
                    for (GameObject g : myGameObjects) {
                        if (g.containsPoint(e.getPoint())) {
                            if (e.getButton() == MouseEvent.BUTTON3) {
                                objectPopupMenu(g, e);
                            }
                            else if (e.getButton() == MouseEvent.BUTTON1) {
                                myCurrentObject = g;
                            }
                            return;
                        }
                    }
                    if (e.getButton() == MouseEvent.BUTTON3) {
                        JFileChooser chooser = new JFileChooser(System.getProperty("user.dir"));
                        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                                "JPG & GIF Images", "jpg", "gif");
                        chooser.setFileFilter(filter);
                        int returnVal = chooser.showOpenDialog(chooser);
                        if (returnVal == JFileChooser.APPROVE_OPTION)  {
                            try {
                                myBackground = ImageIO.read(chooser.getSelectedFile());
                                myBackgroundPath = chooser.getSelectedFile().getPath();
                            }
                            catch (IOException io) {
                                System.out.println("File not found. Try again");
                                myBackground = null;
                            }
                        }
                    }

                }
            }
            @Override
            public void mouseMoved(MouseEvent e) {
                mouseX = e.getX();
                mouseY = e.getY();
            }
        };
        myKeyListener = new KeyAdapter() {
            @Override
            public void keyPressed (KeyEvent ke) {
                myKeyHeld.add(ke.getKeyCode());
                int accel = 0;
                for(Integer kevent : myKeyHeld) {
                    if(kevent == KeyEvent.VK_LEFT) {
                        accel++;
                    }
                }
                switch (ke.getKeyCode()) {
                    case KeyEvent.VK_LEFT:
                        if (myOffset == 0) {
                            bumpLeft();
                        }
                        else {
                            myOffset -= SCROLL_SPEED+accel;
                        }
                        break;
                    case KeyEvent.VK_RIGHT: 
                        if(myOffset+getWidth() == myLength) {
                            myLength += SCROLL_SPEED+accel;
                        }
                        myOffset += SCROLL_SPEED+accel;
                }
            }

            @Override
            public void keyReleased (KeyEvent ke) {
                while(myKeyHeld.contains((Integer) ke.getKeyCode())) {
                    myKeyHeld.remove((Integer) ke.getKeyCode());
                }
            }

            private void bumpLeft () {
                System.out.println("Already at beginning!");
                //TODO: add code to animate bumping motion (if theres time)
            }
        };
        addMouseListener(mouseListener);
        addMouseMotionListener(mouseListener);
        myMouseListener = mouseListener;
    }



    /**
     * Passes the LevelEditorMouseListner as a MouseListener
     * to any components that need it.
     * 
     * @return MouseListener attached to component
     */
    public MouseListener getMouseListener() {
        return myMouseListener;
    }

    public KeyListener getKeyListener() {
        return myKeyListener;
    }

    /**
     * Passes the LevelEditorMouseListener as a MouseMotionListener.
     * 
     * @return MouseMotionListener attached to component
     */
    public MouseMotionListener getMouseMotionListener() {
        return myMouseListener;
    }

    /**
     * Updates the buffer preparing for the next paint call.
     */
    @Override
    public void update(Graphics g) {
        myBufferGraphics.clearRect(0, 0, myBuffer.getWidth(), myBuffer.getHeight());
        myBufferGraphics.drawImage(
                myBackground, 0, 0, myBuffer.getWidth(), myBuffer.getHeight(), this);
        for (GameObject obj : myGameObjects) {
            myBufferGraphics.drawImage(obj.getCurrentImage(), (int) obj.getX(), (int) obj.getY(), (int) obj.getWidth(), (int) obj.getHeight(), null);
        }
        if (myCurrentObject != null) {
            myCurrentObject.setX(mouseX - myCurrentObject.getWidth() / 2);
            myCurrentObject.setY(mouseY - myCurrentObject.getHeight() / 2);
            myBufferGraphics.setColor(Color.ORANGE);
        }
        myBufferGraphics.drawString("Current Sprite = ("+mouseX+", "+mouseY+")", getWidth()-250, 30);
        for(int i = 0; i < myKeyHeld.size(); i++ ) {
            myBufferGraphics.drawString(((Integer) myKeyHeld.get(i)).toString(), 20, (i+1)*10);
        }
    }

    /**
     * Paints the most recent iteration of the buffer to the Canvas.
     * 
     * @param g Graphics attached to level.
     */
    public void paint(Graphics g) {
        update(g);
        g.drawImage(myBuffer, 0, 0, myBuffer.getWidth(), myBuffer.getHeight(), this);
        super.paintComponents(g);

    }

    @Override
    public void save() {
        JFileChooser fc = new JFileChooser();
        FileFilter filter = new FileNameExtensionFilter("XML file", "xml");
        fc.addChoosableFileFilter(filter);

        int returnVal = fc.showSaveDialog(this);
        File saveFile = null;
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            saveFile = fc.getSelectedFile();
            //This is where a real application would save the file.

            //            log.append("Saving: " + file.getName() + "." + newline);
        } else {
            saveFile = new File(System.getProperty("user.dir"), "myLevel.xml");
            //            log.append("Save command cancelled by user." + newline);
        }
//        LevelFileWriter.writeLevel(saveFile.getPath(), "mylevelType", "LevelTitle", getWidth(), getHeight(),
//                myBackgroundPath, myGameObjects, "myCollision", "myCamera");
    }

    @Override
    public void load(URL path) {
        new LevelFileReader(path.getPath());
    }

    public void clear() {
        myGameObjects.clear();
    }

    protected void objectPopupMenu (GameObject g, MouseEvent e) {
        JPopupMenu pop = new JPopupMenu();
        SelectionHelper sh = new SelectionHelper(g);
        JMenuItem j = new JMenuItem("Flip");
        j.addActionListener(sh);
        pop.add(j);
        JMenuItem j2 = new JMenuItem("Duplicate");
        j2.addActionListener(sh);
        pop.add(j2);
        JMenuItem j3 = new JMenuItem("Edit");
        j3.addActionListener(sh);
        pop.add(j3);
        JMenuItem j4 = new JMenuItem("Delete");
        j4.addActionListener(sh);
        pop.add(j4);
        pop.show(this.getParent(), (int)(g.getX()+g.getWidth()/2), (int)(g.getY()+g.getHeight()));
    }
    /**
     * @return An unmodifiable Collection of the sprites
     *         currently positioned on the board.
     */
    protected Collection<GameObject> getSprites() {
        return Collections.unmodifiableCollection(myGameObjects);
    }

    /**
     * Adds a sprite to the board. This method does not
     * check for any conditions (such as sprites being)
     * added on top of each other.
     * 
     * @param sprite
     */
    protected void add(GameObject obj) {
        myGameObjects.add(obj);
        myCurrentObject = obj;
    }

    /**
     * Will remove a sprite from the board.
     * 
     * @param sprite The sprite that should
     *        be removed.
     */
    protected void remove(Sprite sprite) {
        myGameObjects.remove(sprite);
    }

    private class SelectionHelper implements ActionListener {
        private GameObject myObject;
        public SelectionHelper(GameObject obj) {
            myObject = obj;
        }
        @Override
        public void actionPerformed(ActionEvent event) {
            if ("Flip".equals(event.getActionCommand())) {
                myObject.flipImage();
            }
            else if ("Duplicate".equals(event.getActionCommand())) {
                
//                GameObject nobj = new Brick(myObject.getConfigStringParams());
//                LevelBoard.this.add(nobj);
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
        private void createAttributeWindow() {
            JPopupMenu pop = new JPopupMenu();
            for (String att : myAvailableAttributes) {
                JMenuItem j = new JMenuItem(att);
                j.addActionListener(this);
                pop.add(j);
            }
            pop.show(LevelBoard.this, (int)(myObject.getX()+myObject.getWidth()/2), (int)(myObject.getY()+myObject.getHeight()/2));


            /*           create a list of attributes from the resource file 
             and get appropriate values for certain attributes.
             */
        }
    }

    /**
     * A TEMPORARY mouse listener for the current modes this level editor supports.
     * And yes, I realize we have 3 different mouse listeners for this class.
     * @author Paul Dannenberg
     *
     */
    private class LevelEditorMouseListener implements MouseListener, MouseMotionListener {

        @Override
        public void mouseDragged (MouseEvent e) {

        }

        @Override
        public void mouseMoved (MouseEvent e) {
            myCurrentMode.sendCursorPosition(e.getX(), e.getY());
        }

        @Override
        public void mouseClicked (MouseEvent e) {

        }

        @Override
        public void mouseEntered (MouseEvent e) {

        }

        @Override
        public void mouseExited (MouseEvent e) {

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

        @Override
        public void mouseReleased (MouseEvent e) {

        }

    }

}
