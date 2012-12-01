package vooga.platformer.leveleditor;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
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

    private static final long serialVersionUID = -3528519211577278934L;
    private Collection<Sprite> mySprites;
    private Collection<String> myAvailableAttributes;
    private IEditorMode myCurrentMode;
    private BufferedImage myBuffer;
    private Graphics2D myBufferGraphics;
    private LevelEditorMouseListener myMouseListener;
    private Image myBackground;
    private Sprite myCurrentSprite;
    private int myWidth;
    private int myHeight;
    private String myLevelName;
    private String myLevelType;
    private String myBackgroundPath;
    private int mouseX;
    private int mouseY;

    /**
     * Creates a new LevelBoard, visible to the user. The LevelBoard starts
     * off empty.
     */
    public LevelBoard(Dimension d) {
        mySprites = new ArrayList<Sprite>();
        myAvailableAttributes = new ArrayList<String>();
        myAvailableAttributes.add("HP"); myAvailableAttributes.add("Shooting"); myAvailableAttributes.add("Flying");
        myAvailableAttributes.add("Patrolling"); myAvailableAttributes.add("Teammate");
        myCurrentMode = new PlacementMode();
        myBackground = null;
        myBuffer = new BufferedImage(d.width, d.height, BufferedImage.TYPE_INT_RGB);
        myBufferGraphics = myBuffer.createGraphics();
        
        setupMouseInput();
    }

    private void setupMouseInput() {
        LevelEditorMouseListener mouseListener = new LevelEditorMouseListener() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (myCurrentSprite != null) {
                    myCurrentSprite = null;
                }
                else {
                    for (Sprite s : mySprites) {
                        if (e.getX() >= s.getX() && e.getX() <= s.getX() + s.getWidth() &&
                                e.getY() >= s.getY() && e.getY() <= s.getY() + s.getHeight()) {
                            if (e.getButton() == MouseEvent.BUTTON3) {
                                spritePopupMenu(s, e);
                            }
                            else if (e.getButton() == MouseEvent.BUTTON1) {
                                myCurrentSprite = s;
                            }
                            return;
                        }
                    }
                    if (e.getButton() == MouseEvent.BUTTON3) {
                        JFileChooser chooser = new JFileChooser();
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
        addMouseListener(mouseListener);
        addMouseMotionListener(mouseListener);
        myMouseListener = mouseListener;

    }



    /**
     * Passes the MouseListener to any components that need it.
     * 
     * @return MouseListener attached to component
     */
    public LevelEditorMouseListener getMouseListener() {
        return myMouseListener;
    }

    /**
     * Updates the buffer preparing for the next paint call.
     */
    public void update() {
        myBufferGraphics.clearRect(0, 0, myBuffer.getWidth(), myBuffer.getHeight());
        myBufferGraphics.drawImage(
                myBackground, 0, 0, myBuffer.getWidth(), myBuffer.getHeight(), this);
        for (Sprite s : mySprites) {
            s.paint(myBufferGraphics, this);
            myBufferGraphics.setColor(Color.WHITE);
        }
        if (myCurrentSprite != null) {
            myCurrentSprite.setX(mouseX - myCurrentSprite.getWidth() / 2);
            myCurrentSprite.setY(mouseY - myCurrentSprite.getHeight() / 2);
        }
    }

    /**
     * Paints the most recent iteration of the buffer to the Canvas.
     * 
     * @param g Graphics attached to level.
     */
    public void paint(Graphics g) {
        super.paint(g);
        g.drawImage(myBuffer, 0, 0, myBuffer.getWidth(), myBuffer.getHeight(), this);
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
        LevelFileWriter.writeLevel(saveFile.getPath(), "mylevelType", "LevelTitle", myWidth, myHeight,
                myBackgroundPath, mySprites, "myCollision", "myCamera");
    }

    @Override
    public void load(URL path) {
        new LevelFileReader(path.getPath());
    }

    public void clear() {
        mySprites.clear();
    }

    protected void spritePopupMenu (Sprite s, MouseEvent e) {
        JPopupMenu pop = new JPopupMenu();
        SelectionHelper sh = new SelectionHelper(s);
        JMenuItem j = new JMenuItem("Flip");
        j.addActionListener(sh);
        pop.add(j);
        JMenuItem j2 = new JMenuItem("Duplicate");
        j2.addActionListener(sh);
        pop.add(j2);
        JMenuItem j3 = new JMenuItem("Add attribute");
        j3.addActionListener(sh);
        pop.add(j3);
        JMenuItem j4 = new JMenuItem("Delete");
        j4.addActionListener(sh);
        pop.add(j4);
        pop.show(this.getParent(), e.getX(), e.getY());
    }
    /**
     * @return An unmodifiable Collection of the sprites
     *         currently positioned on the board.
     */
    protected Collection<Sprite> getSprites() {
        return Collections.unmodifiableCollection(mySprites);
    }

    /**
     * Adds a sprite to the board. This method does not
     * check for any conditions (such as sprites being)
     * added on top of each other.
     * 
     * @param sprite
     */
    protected void add(Sprite sprite) {
        mySprites.add(sprite);
        myCurrentSprite = sprite;
    }

    /**
     * Will remove a sprite from the board.
     * 
     * @param sprite The sprite that should
     *        be removed.
     */
    protected void remove(Sprite sprite) {
        mySprites.remove(sprite);
    }

    private class SelectionHelper implements ActionListener {
        private Sprite mySprite;
        public SelectionHelper(Sprite s) {
            mySprite = s;
        }
        @Override
        public void actionPerformed(ActionEvent event) {
            if ("Flip".equals(event.getActionCommand())) {
                mySprite.flipImage();
            }
            else if ("Duplicate".equals(event.getActionCommand())) {
                Sprite ns = new Sprite(mySprite.getClassName(), mySprite.getX(), mySprite.getY(),
                        mySprite.getWidth(), mySprite.getHeight(), mySprite.getID(), mySprite.getImagePath());
                LevelBoard.this.add(ns);
            }
            else if ("Add attribute".equals(event.getActionCommand())) {
                createAttributeWindow();
            }
            else if ("Delete".equals(event.getActionCommand())) {
                mySprites.remove(mySprite);
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
             pop.show(LevelBoard.this, mySprite.getX()+mySprite.getWidth()/2, mySprite.getY()+mySprite.getHeight()/2);


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
