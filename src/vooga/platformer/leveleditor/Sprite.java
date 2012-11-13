package vooga.platformer.leveleditor;

import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.PathIterator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


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
    public Sprite(String type, int x, int y, int width, int height,
            String imagePath) {
        myType = type;
        myX = x;
        myY = y;
        myWidth = width;
        myHeight = height;
        myImagePath = imagePath;
        myUpdateStrategies = new ArrayList<Map<String, String>>();
        myAttributes = new HashMap<String, String>();
    }

    /**
     * Gets the attribute describing the sprites role in the game.
     * 
     * @return String representation of the sprites type
     */
    public String getType() {
        return myType;
    }

    /**
     * Gets the x location of the sprite at level load.
     * 
     * @return x location in pixels
     */
    public int getX() {
        return myX;
    }

    /**
     * Sets where the sprite should appear on level load
     * 
     * @param x starting x location of the sprite in pixels
     */
    public void setX(int x) {
        myX = x;
    }

    /**
     * Gets the y location of the sprite at level load.
     * 
     * @return y location in pixels
     */
    public int getY() {
        return myY;
    }

    /**
     * Sets where the sprite should appear on level load
     * 
     * @param y starting y location of the sprite in pixels
     */
    public void setY(int y) {
        myY = y;
    }

    /**
     * Gets the width of the sprite.
     * 
     * @return width of the sprite in pixels
     */
    public int getWidth() {
        return myWidth;
    }

    /**
     * Sets the width of the sprite.
     * 
     * @param width new width of the sprite in pixels
     */
    public void setWidth(int width) {
        myWidth = width;
    }

    /**
     * Gets the height of the sprite.
     * 
     * @return height of the sprite in pixels
     */
    public int getHeight() {
        return myHeight;
    }

    /**
     * Sets the height of the sprite.
     * 
     * @param width new height of the sprite in pixels
     */
    public void setHeight(int height) {
        myHeight = height;
    }

    // TODO support animations
    public String getImagePath() {
        return myImagePath;
    }

    // TODO consider type parameter
    public void addUpdateStrategy(Map<String, String> strategy) {
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
    public Collection<Map<String, String>> getUpdateStrategies() {
        return myUpdateStrategies;
    }

    /**
     * Adds an attribute to the sprite.
     * 
     * @param tag name for the attribute
     * @param value value for the attribute
     */
    public void addAttribute(String tag, String value) {
        myAttributes.put(tag, value);
    }

    /**
     * Returns all the attributes of a sprite as a Map. The keys of this map are
     * the attribute names, and they map onto their corresponding values. Both
     * are stored as Strings.
     * 
     * @return Map of the sprite's attributes
     */
    public Map<String, String> getAttributes() {
        return myAttributes;
    }

    /**
     * This method paints the sprite.
     * 
     * @param pen The Graphics2D object to draw this sprite with.
     */
    public void paint(Graphics2D pen) {
        // TODO
    }

    /**
     * 
     * @return A shape representing the border of this sprite. In
     *         many cases this will probably just be a rectangle of the
     *         sprite's image.
     */
    public Shape getOutline() {
        // TODO
        return null;
    }

    /**
     * Determines if this sprite is intersecting another object.
     * This is determined by iterating over the outline of this
     * sprite, and seeing if any of those points lie inside the
     * parameter, other.
     * 
     * @param other The shape which may or may not intersect with
     *        this sprite.
     * 
     * @return true if this sprite intersects with the other. If not,
     *         return false.
     */
    public boolean isIntersecting(Shape other) {
        /*
         * I suggest using a PathIterator for the Shape object.
         */
        return false;
    }
}
