package vooga.turnbased.gameobject;

import java.awt.Graphics;
import java.awt.Image;
import java.util.Set;
import javax.swing.ImageIcon;


/**
 * GameObject is the parent class of all important objects in this game. Each object will
 * actually be only some sort of Object associated with that game mode where the object exists.
 * Thus there will not be any actual classes that implement GameObject directly, but rather
 * BattleObject or MapObject, etc.
 * 
 * @author Michael Elgart, volodymyr, Rex
 * 
 */
public abstract class GameObject {

    protected Image myImage;
    private int myID;
    private Set<String> myAllowableModes;
    private final String myConditionFlag;


    /**
     * Construct the new game object.
     * 
     * @param allowableModes A set of strings where this Object can exist in. 
     * @param condition The condition flagged my interacting with this object.
     * @param image The image associated with the new object.
     */
    public GameObject (Set<String> allowableModes, String condition, Image image) {
        myAllowableModes = allowableModes;
        myConditionFlag = condition;
        setImage(image);
    }

    /**
     * Will check to see if this object can exist in this mode.
     * @param modeName The mode you checking
     * @return True if this object can exist in the given mode
     */
    public boolean isValidMode(String modeName) {
        return myAllowableModes.contains(modeName);
    }

    /**
     * Sets the ID of the object to the given parameter.
     * 
     * @param newID The ID to be used.
     */
    public void setID (int newID) {
        myID = newID;
    }

    /**
     * Returns the ID of the GameObject.
     * 
     * @return myID The object's current ID.
     */
    public int getID () {
        return myID;
    }

    /**
     * Returns the GameEvent currently associated with the GameObject.
     * 
     * @return myGameEvent The GameEvent that does with this object.
     */
    public String getConditionFlag () {
        return myConditionFlag;
    }

    /**
     * Paints the GameObject.
     * @param g Image to be painted.
     * @param x X location of object.
     * @param y Y location of object.
     * @param width Width of image.
     * @param height Height of image.
     */
    public void drawRectangularImage (Graphics g, int x, int y, int width, int height) {
        g.drawImage(myImage, x, y, width, height, null);
    }

    /**
     * Sets image of object to parameter.
     * @param img Image to be set.
     */
    public void setImage (Image img) {
        myImage = img;
    }

    /**
     * Sets image of object to be object at directory location in parameter.
     * @param imageLocation String location of image.
     */
    public void setImage (String imageLocation) {
        ImageIcon ii = new ImageIcon(this.getClass().getResource(imageLocation));
        myImage = ii.getImage();
    }

    /**
     * Returns image associated with the GameObject.
     * @return myImage Image of object.
     */
    public Image getImage () {
        return myImage;
    }

    /**
     * Updates game after some time delay (to be implemented by child classes).
     */
    public abstract void update ();

    /**
     * Paints the object
     * @param g The graphics which is used to paint the object.
     */
    public abstract void paint (Graphics g);

    /**
     * Remove all occurences of this object in the program.
     */
    public abstract void clear();
}
