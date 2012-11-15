package vooga.turnbased.gameobject;

import java.awt.Graphics;
import java.awt.event.KeyEvent;

import vooga.turnbased.gamecore.GameManager;

/**
 * This class is a bucket. It has no functionality. We are looking into possible ways
 * around this class having no functionality, but because MapObject and BattleObject
 * are also abstract, any actual objects in the game could not inherit anything from
 * GameObject, as that would be multiple inheritance. Thus, there is no point to add 
 * functionality to this class as multiple inheritance assures that functionality 
 * would never be used.
 * @author Michael Elgart
 *
 */
public abstract class GameObject {

    private int myID;
    private GameManager.GameEvent myGameEvent;

    /**
     * Construct the new game object.
     * @param objectID The ID number of the new object.
     */
    public GameObject(int objectID, GameManager.GameEvent gameEvent) {
        myID = objectID;
        myGameEvent = gameEvent;
    }
    
    public int getID() {
        return myID;
    }
    
    public GameManager.GameEvent getEvent() {
    	return myGameEvent;
    }

    public abstract void handleKeyReleased (KeyEvent e);

    public abstract void handleKeyPressed (KeyEvent e);

    public abstract void paint (Graphics g, int x, int y, int width, int height);

    public abstract void update (int delayTime);
}
