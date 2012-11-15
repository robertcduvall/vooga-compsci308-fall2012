package vooga.platformer.leveleditor;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;


/**
 * A data wrapper class for sprites the user drags, drops, and modifies while
 * editing a level. Unlike a GameObject, these objects just store data about the
 * objects, and cannot actually perform updateStrategies, but rather keeps a
 * list of these strategies to be written into a data file. Similarly, the
 * instances only store string path names to images, not java Image types
 * themselves.
 * 
 * @author Grant Oakley
 * 
 */
public class Sprite {

    private String myType;
    private int myX;
    private int myY;
    private int myWidth;
    private int myHeight;
    private String myImagePath;
    private Image myImage;
    private Collection<Map<String, String>> myUpdateStrategies;
    private Map<String, String> myAttributes;

    /**
     * Creates a new instance of Sprite of the type, position, size, and
     * appearance specified.
     * 
     * @param type attribute of the sprite describing what its role in the game
     *        is
     * @param x x position of the sprite at level load
     * @param y y position of the sprite at level load
     * @param width width of the sprite in pixels
     * @param height height of the sprite in pixels
     * @param imagePath location of the image in the file system representing
     *        the sprite
     */
    public Sprite (String type, int x, int y, int width, int height, String imagePath) {
        myType = type;
        myX = x;
        myY = y;
        myWidth = width;
        myHeight = height;
        myImagePath = imagePath;
        myImage = getImage(myImagePath);
        myUpdateStrategies = new ArrayList<Map<String, String>>();
        myAttributes = new HashMap<String, String>();
    }

    private Image getImage (String filename) {
        Image ret = null;
        try {
            ret = ImageIO.read(new File(filename));
        }
        catch (IOException e) {
            System.out.println("file was not found");
            e.printStackTrace();
        }
        return ret;
    }

    /**
     * Gets the attribute describing the sprites role in the game.
     * 
     * @return String representation of the sprites type
     */
    public String getType () {
        return myType;
    }

    /**
     * Using the imagePath, obtains the image for a sprite and
     * paints it to whatever component Graphics g is connected to.
     * 
     * @param g Graphics of a Component, Image, or Canvas
     * @param c Compnent containing sprite so the sprite knows where it is in the window. 
     */
    public void paint (Graphics g, Component c) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(myImage, myX, myY, myX + myWidth, myY + myHeight, 
                0, 0, myImage.getWidth(null), myImage.getHeight(null), c);
    }

    /**
     * Flips the sprites image across it's vertical axis.
     * 
     */
    public void flipImage() {
        BufferedImage bufferedImage = new BufferedImage(
                myImage.getWidth(null), myImage.getHeight(null), BufferedImage.TYPE_INT_RGB);
        AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
        tx.translate(-myImage.getWidth(null), 0);
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        bufferedImage = op.filter(bufferedImage, null);
        myImage = bufferedImage;
    }

    /**
     * Gets the x location of the sprite at level load.
     * 
     * @return x location in pixels
     */
    public int getX () {
        return myX;
    }

    /**
     * Sets where the sprite should appear on level load
     * 
     * @param x starting x location of the sprite in pixels
     */
    public void setX (int x) {
        myX = x;
    }

    /**
     * Gets the y location of the sprite at level load.
     * 
     * @return y location in pixels
     */
    public int getY () {
        return myY;
    }

    /**
     * Sets where the sprite should appear on level load
     * 
     * @param y starting y location of the sprite in pixels
     */
    public void setY (int y) {
        myY = y;
    }

    /**
     * Gets the width of the sprite.
     * 
     * @return width of the sprite in pixels
     */
    public int getWidth () {
        return myWidth;
    }

    /**
     * Sets the width of the sprite.
     * 
     * @param width new width of the sprite in pixels
     */
    public void setWidth (int width) {
        myWidth = width;
    }

    /**
     * Gets the height of the sprite.
     * 
     * @return height of the sprite in pixels
     */
    public int getHeight () {
        return myHeight;
    }

    /**
     * Sets the height of the sprite.
     * 
     * @param height new height of the sprite in pixels
     */
    public void setHeight (int height) {
        myHeight = height;
    }

    /**
     * Returns the image that represents the Sprite during level editing.
     * 
     * @return Image rendered using the file path specified in the Sprite's
     *         constructor
     */
    public Image getImage () {
        return getImage(myImagePath);
    }

    /**
     * Gets the path to file that is the image to represent the Sprite during
     * level editing.
     * 
     * @return path to the Sprite's image as a String
     */
    public String getImagePath () {
        // TODO support animations
        return myImagePath;
    }

    // TODO clarify type declaration
    /**
     * Adds update strategy to the Sprite. This is added as Map.
     * 
     * @param strategyType Name of the update strategy type to use. Must be
     *        subclass of Strategy.
     * @param strategy Map representing the update strategy. Each key is a
     *        String representing a parameter name for the update strategy. This
     *        should map to the value of this parameter, also a String.
     */
    public void addUpdateStrategy (String strategyType, Map<String, String> strategy) {
        strategy.put("type", strategyType);
        myUpdateStrategies.add(strategy);
    }

    /**
     * Gets the update strategies that the sprite should implement. The value is
     * returned as a collection of Maps. Each Map represents an update strategy.
     * The keys of this map are the parameter names of the update strategy, and
     * these keys map onto corresponding the values of the parameters as
     * Strings.
     * 
     * @return collection of maps representing the parameters of the update
     *         strategy
     */
    public Collection<Map<String, String>> getUpdateStrategies () {
        return myUpdateStrategies;
    }

    /**
     * Adds an attribute to the sprite.
     * 
     * @param tag name for the attribute
     * @param value value for the attribute
     */
    public void addAttribute (String tag, String value) {
        myAttributes.put(tag, value);
    }

    /**
     * Returns all the attributes of a sprite as a Map. The keys of this map are
     * the attribute names, and they map onto their corresponding values. Both
     * are stored as Strings.
     * 
     * @return Map of the sprite's attributes
     */
    public Map<String, String> getAttributes () {
        return myAttributes;
    }

    /**
     * Determines if sprite is intersecting with a given rectangular region.
     * 
     * @param region Rectangle being checked for intersection
     * @return Returns true if the rectangle intersects, and false if there is
     *         no intersection
     */
    public boolean isIntersecting (Rectangle region) {
        Rectangle boundingBox = getOutline();
        return boundingBox.intersects(region);
    }

    /**
     * Gives the bounding box of a sprite.
     * 
     * @return Returns a Rectangle representing the bounding region of a sprite.
     */
    public Rectangle getOutline () {
        return new Rectangle(myX, myY, myWidth, myHeight);
    }
}
