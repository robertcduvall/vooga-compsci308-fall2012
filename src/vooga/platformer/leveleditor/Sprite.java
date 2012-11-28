package vooga.platformer.leveleditor;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import javax.imageio.ImageIO;
import vooga.platformer.levelfileio.XmlTags;


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

    private String myClass;
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
     * @param clazz fully qualified class name of the GameObject this Srite
     *        represents
     * @param x x position of the sprite at level load
     * @param y y position of the sprite at level load
     * @param width width of the sprite in pixels
     * @param height height of the sprite in pixels
     * @param imagePath location of the image in the file system representing
     *        the sprite
     */
    public Sprite (String clazz, int x, int y, int width, int height, String imagePath) {
        myClass = clazz;
        myX = x;
        myY = y;
        myWidth = width;
        myHeight = height;
        myImagePath = imagePath;
        myImage = getImage(myImagePath);
        myUpdateStrategies = new ArrayList<Map<String, String>>();
        myAttributes = new HashMap<String, String>();
    }

    protected Sprite () {

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
     * Gets the name of the class that should be instantiated to render the
     * Sprite as a GameObject at load time.
     * 
     * @return fully-qualified class name of the GameObject subclass
     */
    public String getClassName () {
        return myClass;
    }

    /**
     * Using the imagePath, obtains the image for a sprite and
     * paints it to whatever component Graphics g is connected to.
     * 
     * @param g Graphics of a Component, Image, or Canvas
     * @param c Compnent containing sprite so the sprite knows where it is in
     *        the window.
     */
    public void paint (Graphics g, Component c) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(myImage, myX, myY, myX + myWidth, myY + myHeight, 0, 0,
                      myImage.getWidth(null), myImage.getHeight(null), c);
    }

    /**
     * Flips the sprites image across it's vertical axis.
     * 
     */
    public void flipImage () {
        AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
        tx.translate(-myImage.getWidth(null), 0);
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        myImage = op.filter((BufferedImage) myImage, null);
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
        strategy.put(XmlTags.CLASS_NAME, strategyType);
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

    /**
     * Tests to see if this sprite intersects with any other sprite.
     * 
     * @param other The sprite to test for overlap with.
     * @return true if this object intersects with the other sprite.
     *         false otherwise.
     */
    public boolean isIntersecting (Sprite other) {
        PathIterator outlineIterator = other.getOutline().getPathIterator(null);
        return intersects(outlineIterator);
    }

    /**
     * Tests to see whether any point specified by the PathIterator lies within
     * this object's outline.
     * 
     * @param iterator The PathIterator specifying the points to check.
     * @return true if any single point lies within the outline of this object.
     *         false otherwise.
     */
    private boolean intersects (PathIterator iterator) {
        while (!iterator.isDone()) {
            double[] coords = new double[2];
            iterator.currentSegment(coords);
            if (getOutline().contains(coords[0], coords[1])) { return true; }
            iterator.next();
        }
        return false;
    }

    /**
     * Checks to see whether the coordinates x and y are contained
     * within the sprite's outline.
     * 
     * @param x The x coordinate.
     * @param y The y coordinate.
     * @return true if x and y are contained within the sprite's outline.
     *         false otherwise.
     */
    public boolean contains (int x, int y) {
        return isIntersecting(new Rectangle(x, y));
    }

    protected void setImage (Image img) {
        myImage = img;
    }
}
