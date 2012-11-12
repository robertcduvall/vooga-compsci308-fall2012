package vooga.platformer.leveleditor;

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

    private String myTag;
    private int myX;
    private int myY;
    private int myWidth;
    private int myHeight;
    private String myImagePath;
    private Collection<Map<String, String>> myUpdateStrategies;
    private Map<String, String> myAttributes;

    public Sprite (String tag, int x, int y, int width, int height, String imagePath) {
        myTag = tag;
        myX = x;
        myY = y;
        myWidth = width;
        myHeight = height;
        myImagePath = imagePath;
        myUpdateStrategies = new ArrayList<Map<String, String>>();
        myAttributes = new HashMap<String, String>();
    }

    public String getType () {
        return myTag;
    }

    public int getX () {
        return myX;
    }

    public void setX (int x) {
        myX = x;
    }

    public int getY () {
        return myY;
    }

    public void setY (int y) {
        myY = y;
    }

    public int getWidth () {
        return myWidth;
    }

    public void setWidth (int width) {
        myWidth = width;
    }

    public int getHeight () {
        return myHeight;
    }

    public void setHeight (int height) {
        myHeight = height;
    }

    // TODO support animations
    public String getImagePath () {
        return myImagePath;
    }

    // TODO consider type parameter
    public void addUpdateStrategy (Map<String, String> strategy) {
        myUpdateStrategies.add(strategy);
    }

    public Collection<Map<String, String>> getUpdateStrategies () {
        return myUpdateStrategies;
    }

    public void addAttribute (String tag, String value) {
        myAttributes.put(tag, value);
    }

    public Map<String, String> getAttributes () {
        return myAttributes;
    }
}
