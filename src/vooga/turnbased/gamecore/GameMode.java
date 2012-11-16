/**
 * Abstract class that represent a mode in a game,
 * such as map mode where players walk around, or battle mode where players
 * fight against enemies
 * 
 * @author rex, Volodymyr
 */
package vooga.turnbased.gamecore;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import vooga.turnbased.gameobject.GameObject;
import vooga.turnbased.gameobject.MapObject;


// public abstract class GameMode extends Observable {
public abstract class GameMode {
    private final GameManager myGameManager;
    private final Class myObjectType;

    private ArrayList<GameObject> myObjects;

    /**
     * Constructor
     * 
     * @param gm The GameManager which receive information about how sprites
     *        interact
     */
    public GameMode (GameManager gm, Class modeObjectType) {
        myGameManager = gm;
        myObjectType = modeObjectType;
        // myObjects = new
        // ArrayList<GameObject>(myGameManager.getModesObjects(modeObjectType));
    }

    protected GameManager getGameManager () {
        return myGameManager;
    }

    public void setObjects () { // to be deleted later, really only want sprites
                                // correlating to involvedSpriteIDs in
                                // gamemanager's handleEvent
        myObjects = new ArrayList<GameObject>(myGameManager
                .getModesObjects(myObjectType));
    }

    public ArrayList<GameObject> getObjects () {
        return myObjects;
    }

    public Class getObjectType () {
        return myObjectType;
    }

    /**
     * Each game mode should paint everything that should be currently displayed
     * 
     * @param g
     * @param canvasWidth
     * @param canvasHeight
     */
    public abstract void pause ();

    public abstract void resume ();

    public abstract void paint (Graphics g);

    public abstract void update ();

    public abstract void processGameEvents ();

    public abstract void handleKeyPressed (KeyEvent e);

    public abstract void handleKeyReleased (KeyEvent e);

    /**
     * Override if any sub-mode needs to handle MouseClicked events
     */
    public void handleMouseClicked (MouseEvent e) {
    }
}
