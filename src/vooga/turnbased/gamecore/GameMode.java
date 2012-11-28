package vooga.turnbased.gamecore;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import vooga.turnbased.gameobject.GameObject;


/**
 * Abstract class that represent a mode in a game,
 * such as map mode where players walk around, or battle mode where players
 * fight against enemies
 * 
 * @author rex, Volodymyr
 */
// public abstract class GameMode extends Observable {
public abstract class GameMode implements GameLoopMember {
    private final GameManager myGameManager;
    private final Class myObjectType;
    private List<GameEvent> myModeEvents;
    private ArrayList<GameObject> myObjects;

    /**
     * Constructor for GameMode.
     * 
     * @param gm The GameManager which receives information about how sprites
     *        interact.
     * @param modeObjectType Type of GameObject associated with GameMode
     *        being constructed.
     */
    public GameMode (GameManager gm, Class modeObjectType) {
        myGameManager = gm;
        myObjectType = modeObjectType;
        myModeEvents = new LinkedList<GameEvent>();
    }

    public GameManager getGameManager () {
        return myGameManager;
    }

    public void setObjects () { // to be deleted later, really only want sprites
                                // correlating to involvedSpriteIDs in
                                // gamemanager's handleEvent
        myObjects = new ArrayList<GameObject>(
                myGameManager.getGameObjectsOfSpecificMode(myObjectType));
    }

    /**
     * Returns list of GameObjects associated with current mode.
     * @return List of mode's GameObjects.
     */
    public ArrayList<GameObject> getObjects () {
        return myObjects;
    }
    
    public List<GameEvent> getModeEvents() {
        return myModeEvents;
    }

    /**
     * Returns type of GameObject associated with current mode.
     * @return Type of object that current mode uses.
     */
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

    /**
     * Method that will initialize the game mode from a paused state.
     */
    public abstract void resume ();
    
    /**
     * Call when gamemode if first created
     */
    public abstract void initialize ();

    /**
     * Method that will paint the different objects in the mode.
     * @param g Graphics.
     */
    @Override
    public abstract void paint (Graphics g);

    /**
     * Method that will update the objects in the GameMode when called.
     */
    @Override
    public abstract void update ();

    // public abstract void processGameEvents ();

    /**
     * Override if any sub-mode needs to handle MouseClicked events.
     * @param e MouseEvent to be handled.
     */
    public void handleMouseClicked (MouseEvent e) {
    }
}
