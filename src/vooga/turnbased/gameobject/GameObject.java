package vooga.turnbased.gameobject;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;


/**
 * GameObject is the parent class of all important objects in this game. Each object will
 * actually be only some sort of Object associated with that game mode where the object exists.
 * Thus there will not be any actual classes that implement GameObject directly, but rather
 * BattleObject or MapObject, etc.
 * 
 * @author Michael Elgart
 * 
 */
public abstract class GameObject {

    private int myID;
    private final String myConditionFlag;
    protected Image myImage;

    /**
     * Construct the new game object.
     * 
     * @param objectID The ID number of the new object.
     * @param condition The condition flagged my interacting with this object.
     * @param image The image associated with the new object.
     */
    public GameObject (String condition, Image image) {
        myConditionFlag = condition;
        setImage(image);
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
    
    public abstract void paint (Graphics g);

    /**
     * Remove all occurences of this object in the program.
     */
    public abstract void clear();
}
