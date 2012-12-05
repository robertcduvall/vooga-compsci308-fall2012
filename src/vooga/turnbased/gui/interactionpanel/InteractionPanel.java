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


/**
 * A GUI panel for dialogue box etc. to be displayed in game
 * 
 * @author rex
 * 
 */
public class InteractionPanel {

    private static final String QUIT_MESSAGE = "Quit";
    private static final int ROW_NUMBER = 3;
    private static final int COLUMN_NUMBER = 2;
    private static final double MARGIN_PROPORTION = 0.1;
    private static Image myBulletPointImage = Toolkit.getDefaultToolkit()
           .createImage("src/vooga/turnbased/resources/image/GUI/bullet-point-1.png");
    private static Dimension ourBulletSize = new Dimension(40, 40);

    private int myBulletPointIndex;
    private Point myPreviousPosition;

    private Image myPanelImage;

    // to be passed down for drawing
    private Graphics myImageGraphics;
    private List<OptionObject> myOptionObjects;
    private List<Point> myOptionPositions;
    
    public InteractionPanel (List<OptionObject> optionObjects) {
        this();
        myOptionObjects = optionObjects;
        for (int i = 0; i < myOptionObjects.size(); i++) {
            myOptionObjects.get(i).setPosition(myOptionPositions.get(i));
        }
    }
    
    protected InteractionPanel() {
        initializePanelImage();
        myOptionObjects = new ArrayList<OptionObject>();
        myOptionPositions = initializeOptionPositions();
        myBulletPointIndex = 0;
        myPreviousPosition = null;
    }

    public Image renderImage () {
        initializePanelImage();
        for (OptionObject option : myOptionObjects) {
            option.paint(myImageGraphics);
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
        myImageGraphics = (myPanelImage.getGraphics());
    }

    /**
     * highlight any option, if applicable
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

    public void dehighlightOption () {
        for (OptionObject option : myOptionObjects) {
            option.dehighlight();
        }
        myPreviousPosition = null;
    }

    private void drawBulletPoint (Graphics g) {
        int x = myOptionPositions.get(myBulletPointIndex).x - ourBulletSize.width;
        int y = myOptionPositions.get(myBulletPointIndex).y - ourBulletSize.height / 2;
        g.drawImage(myBulletPointImage, x, y, ourBulletSize.width, ourBulletSize.height, null);
    }

    private List<Point> initializeOptionPositions () {
        List<Point> positions = new ArrayList<Point>();
        int width = myPanelImage.getWidth(null);
        int height = myPanelImage.getHeight(null);
        for (int i = 1; i <= ROW_NUMBER; i++) {
            for (int j = 0; j < COLUMN_NUMBER; j++) {
                int xCoordinate =
                        (int) Math.round(MARGIN_PROPORTION * width) + width * j / COLUMN_NUMBER;
                positions.add(new Point(xCoordinate, height * i / 4));
            }
        }
        return positions;
    }

    public Dimension getPanelSize () {
        return new Dimension(myPanelImage.getWidth(null), myPanelImage.getHeight(null));
    }
    
    /**
     * get previous position of the mouse on this panel
     * @return previous position of the mouse
     */
    public Point getPreviousPosition() {
        return myPreviousPosition;
    }
    
    /**
     * set the previous position to the new position
     * It is called when the previous position has been processed
     * @param newPosition new position of the mouse
     */
    public void setPreviousPosition(Point newPosition) {
        myPreviousPosition = newPosition;
    }
    
    public OptionObject triggerOption(Point focusPosition) {
        for (OptionObject option: myOptionObjects) {
            if (option.isTriggered(focusPosition)) {
                return option;
            }
        }
        return null;
    }
}