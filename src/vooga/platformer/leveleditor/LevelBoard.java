package vooga.platformer.leveleditor;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;


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
 * 
 */
public class LevelBoard extends Canvas implements ISavable {

    private static final long serialVersionUID = -3528519211577278934L;
    private Collection<Sprite> mySprites;
    private ISpritePlacementManager myPlacementManager;
    private BufferedImage myBuffer;
    private Graphics2D myBufferGraphics;
    private Graphics myFrameGraphics;
    private Image myBackground;
    private Sprite myCurrentSprite;

    /**
     * Creates a new LevelBoard, visible to the user. The LevelBoard starts
     * off empty.
     */
    public LevelBoard(Dimension d) {
        mySprites = new ArrayList<Sprite>();
        myPlacementManager = new SpritePlacementManager(this);
        myBackground = null;
        myBuffer = new BufferedImage(d.width, d.height, BufferedImage.TYPE_INT_RGB);
        myBufferGraphics = myBuffer.createGraphics();
        setupMouseInput();
    }

    private void setupMouseInput() {
        SelectionMouseListener mouseListener = new SelectionMouseListener(myPlacementManager) {
            @Override
            public void mousePressed(MouseEvent e) {
                if (myCurrentSprite != null) {
                    myCurrentSprite = null;
                }
                else if (e.getButton() == MouseEvent.BUTTON3) {
                    for (Sprite s : mySprites) {
                        if (e.getX() >= s.getX() && e.getX() <= s.getX() + s.getWidth() &&
                                e.getY() >= s.getY() && e.getY() <= s.getY() + s.getHeight()) {
                            //Something with sprites (Popup maybe?)
                        }
                        //                        else {
                        //                            JFileChooser chooser = new JFileChooser();
                        //                            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                        //                                    "JPG & GIF Images", "jpg", "gif");
                        //                            chooser.setFileFilter(filter);
                        //                            int returnVal = chooser.showOpenDialog(myContainer);
                        //                            if (returnVal == JFileChooser.APPROVE_OPTION)  {
                        //                                try {
                        //                                    myBackground = ImageIO.read(chooser.getSelectedFile());
                        //                                }
                        //                                catch (IOException io) {
                        //                                    System.out.println("File not found. Try again");
                        //                                    myBackground = null;
                        //                                }
                        //                            }
                        //                        }
                    }
                }
            }
        };
        addMouseListener(mouseListener);
        addMouseMotionListener(mouseListener);
    }

    public void update() {
        myBufferGraphics.clearRect(0, 0, myBuffer.getWidth(), myBuffer.getHeight());
                myBufferGraphics.drawImage(
                        myBackground, 0, 0, myBuffer.getWidth(), myBuffer.getHeight(), null);
        if (myCurrentSprite != null) {
            myCurrentSprite.setX(MouseInfo.getPointerInfo().getLocation().x);
            myCurrentSprite.setY(MouseInfo.getPointerInfo().getLocation().y);
        }
        for (Sprite s : mySprites) {
            s.paint(myBufferGraphics);
        }
        myBufferGraphics.setColor(Color.WHITE);
        for (int i = 0; i < 100; i++) {

        }
    }

    public void paint(Graphics g) {
        g.drawImage(myBuffer, 0, 0, myBuffer.getWidth(), myBuffer.getHeight(), null);
    }

    @Override
    public void save() {
        // TODO Auto-generated method stub

    }

    @Override
    public void load(URL path) {
        // TODO Auto-generated method stub

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

}
