package vooga.turnbased.gui.interactionpanel;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import vooga.turnbased.gameobject.optionobject.OptionObject;
import vooga.turnbased.gui.GameWindow;


/**
 * A GUI panel for dialogue box etc. to be displayed in game
 * 
 * @author rex
 * 
 */
public class InteractionPanel {

    private static final int ROW_NUMBER = 3;
    private static final int COLUMN_NUMBER = 2;
    private static final double MARGIN_PROPORTION = 0.1;
    private Image myBulletPointImage;
    private Dimension myBulletSize;

    private int myBulletPointIndex;
    private Point myPreviousPosition;

    private Image myPanelImage;

    // to be passed down for drawing
    private Graphics myImageGraphics;
    private List<OptionObject> myOptionObjects;
    private List<Point> myOptionPositions;

    /**
     * constructor of Interaction Panel
     * 
     * @param optionObjects the list of OptionObject that is to be displayed on
     *        the panel
     */
    public InteractionPanel (List<OptionObject> optionObjects) {
        this();
        myOptionObjects = optionObjects;
        for (int i = 0; i < myOptionObjects.size(); i++) {
            myOptionObjects.get(i).setPosition(myOptionPositions.get(i));
        }
    }

    protected InteractionPanel () {
        initializePanelImage();
        myOptionObjects = new ArrayList<OptionObject>();
        myOptionPositions = initializeOptionPositions();
        myBulletPointIndex = 0;
        myPreviousPosition = null;
        int size = Integer.parseInt(GameWindow.importString("BulletSize"));
        myBulletSize = new Dimension(size, size);
        myBulletPointImage =
                Toolkit.getDefaultToolkit().createImage(GameWindow.importString("BulletImage"));
    }

    /**
     * render the image of the interaction panel
     * 
     * @return an image that could be painted on a graphics object
     */
    public Image renderImage () {
        initializePanelImage();
        for (OptionObject option : myOptionObjects) {
            option.paintOption(myImageGraphics);
        }
        drawBulletPoint(myImageGraphics);
        return myPanelImage;
    }

    private void initializePanelImage () {
        try {
            myPanelImage =
                    ImageIO.read(new File("src/vooga/turnbased/resources/image/GUI/dialogue1.png"));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        myImageGraphics = myPanelImage.getGraphics();
    }

    /**
     * highlight any option, if applicable
     * 
     * @param position current position of focus
     * @return if anything is highlighted (when the focus is on certain option
     */
    public boolean highlightOption (Point position) {
        boolean highlighted = false;
        for (int i = 0; i < myOptionObjects.size(); i++) {
            if (myOptionObjects.get(i).highlight(position)) {
                highlighted = true;
            }
            if (myOptionObjects.get(i).optionIsHighlighted()) {
                myBulletPointIndex = i;
            }
        }
        if (!highlighted) {
            myPreviousPosition = position;
        }
        return highlighted;
    }

    /**
     * de-highlight the options
     */
    public void dehighlightOption () {
        for (OptionObject option : myOptionObjects) {
            option.dehighlight();
        }
        myPreviousPosition = null;
    }

    private void drawBulletPoint (Graphics g) {
        int x = myOptionPositions.get(myBulletPointIndex).x - myBulletSize.width;
        int y = myOptionPositions.get(myBulletPointIndex).y - myBulletSize.height / 2;
        g.drawImage(myBulletPointImage, x, y, myBulletSize.width, myBulletSize.height, null);
    }

    private List<Point> initializeOptionPositions () {
        List<Point> positions = new ArrayList<Point>();
        int width = myPanelImage.getWidth(null);
        int height = myPanelImage.getHeight(null);
        for (int i = 1; i <= ROW_NUMBER; i++) {
            for (int j = 0; j < COLUMN_NUMBER; j++) {
                int xCoordinate =
                        (int) Math.round(MARGIN_PROPORTION * width) + width * j / COLUMN_NUMBER;
                positions.add(new Point(xCoordinate, height * i / (ROW_NUMBER + 1)));
            }
        }
        return positions;
    }

    /**
     * get the size of the Interaction panel
     * @return a dimension object representing the size
     */
    public Dimension getPanelSize () {
        return new Dimension(myPanelImage.getWidth(null), myPanelImage.getHeight(null));
    }

    /**
     * get previous position of the mouse on this panel
     * 
     * @return previous position of the mouse
     */
    public Point getPreviousPosition () {
        return myPreviousPosition;
    }

    /**
     * set the previous position to the new position
     * It is called when the previous position has been processed
     * 
     * @param newPosition new position of the mouse
     */
    public void setPreviousPosition (Point newPosition) {
        myPreviousPosition = newPosition;
    }

    /**
     * trigger the option that is currently selected by the user
     * 
     * @param focusPosition the position at which the user is focusing (by mouse
     *        or other input devices)
     * @return an OptionObject that is currently being chosen
     */
    public OptionObject triggerOption (Point focusPosition) {
        for (OptionObject option : myOptionObjects) {
            if (option.isTriggered(focusPosition)) { return option; }
        }
        return null;
    }
}
