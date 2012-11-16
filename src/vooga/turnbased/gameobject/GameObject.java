package vooga.turnbased.gameobject;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;

import vooga.turnbased.gamecore.GameManager;

/**
 * GameObject is the parent class of all important objects in this game. Each object will 
 * actually be only some sort of Object associated with that game mode where the object exists.
 * Thus there will not be any actual classes that implement GameObject directly, but rather
 * BattleObject or MapObject, etc.
 * @author Michael Elgart
 *
 */
public abstract class GameObject {

    private int myID;
    private GameManager.GameEvent myGameEvent;
    private Image myImage;

    /**
     * Construct the new game object.
     * @param objectID The ID number of the new object.
     */
    public GameObject(int objectID, GameManager.GameEvent gameEvent, Image image) {
        myID = objectID;
        myGameEvent = gameEvent;
        setImage(image);
    }
    
    public void setID(int newID) {
        myID = newID;
    }
    
    public int getID() {
        return myID;
    }
    
    public GameManager.GameEvent getEvent() {
    	return myGameEvent;
    }

    public void paint (Graphics g, int x, int y, int width, int height) {
        g.drawImage(myImage, x, y, width, height, null);
    }
    
    public void setImage(Image img) {
        myImage = img;
    }
    
    public void setImage(String imageLocation) {
        ImageIcon ii = new ImageIcon(this.getClass().getResource(imageLocation));
        myImage = ii.getImage();
    }
    
    public Image getImage() {
        return myImage;
    }

    public abstract void update (int delayTime);
}
