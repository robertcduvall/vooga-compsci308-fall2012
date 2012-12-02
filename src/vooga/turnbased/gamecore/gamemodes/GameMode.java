package vooga.turnbased.gamecore.gamemodes;

import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import vooga.turnbased.gamecore.GameManager;
import vooga.turnbased.gameobject.GameObject;


/**
 * Abstract class that represent a mode in a game,
 * such as map mode where players walk around, or battle mode where players
 * fight against enemies
 * 
 * @author rex, Volodymyr
 */
// public abstract class GameMode extends Observable {
public abstract class GameMode {
    private List<GameObject> myGameObjects;
    private final GameManager myGameManager;
    private final String myModeName;
    private boolean myHasFocus;
    private boolean isActive;
    private boolean isOver;

    /**
     * Constructor for GameMode.
     * 
     * @param gm The GameManager which receives information about how sprites
     *        interact.
     * @param allowableModes Type of GameObject associated with GameMode
     *        being constructed.
     */
    public GameMode (GameManager gm, String modeName, List<Integer> involvedIDs) {
        myGameManager = gm;
        myModeName = modeName;
        myHasFocus = true;
        isActive = true;
        isOver = false;
        acquireGameObjects();
    }
    
    private void acquireGameObjects() {
        myGameObjects = new ArrayList<GameObject>();
        myGameObjects.addAll(myGameManager.getGameObjects(getName()));
    }
    
    protected List<? extends GameObject> getGameObjects() {
        return myGameObjects;
    }
    
    protected List<? extends GameObject> getGameObjectsByID(int spriteID) {
        List<GameObject> gameObjectsOfID = new ArrayList<GameObject>();
        for(GameObject go : myGameObjects) {
            if (go.getID() == spriteID) {
                gameObjectsOfID.add(go);
            }
        }
        return gameObjectsOfID;
    }
    
    public String getName() {
        return myModeName;
    }

    public GameManager getGameManager () {
        return myGameManager;
    }

    public void flagCondition (String conditionName, List<Integer> involvedSpriteIDs) {
        myGameManager.flagCondition(conditionName, involvedSpriteIDs);
    }

    /**
     * Call when gamemode if first created
     */
    public abstract void initialize ();

    /**
     * Suspend a mode while entering a different mode
     */
    public abstract void pause ();

    /**
     * Method that will initialize the game mode from a paused state.
     */
    public abstract void resume ();

    /**
     * Method that will paint the different objects in the mode.
     * 
     * @param g Graphics.
     */
    public abstract void paint (Graphics g);

    /**
     * Method that will update the objects in the GameMode when called.
     */
    public abstract void update ();

    public boolean hasFocus () {
        return myHasFocus;
    }

    protected void setFocus (boolean isFocus) {
        myHasFocus = isFocus;
    }
    
    protected void setActive() {
        isActive = true;
    }
    
    protected void setInactive() {
        isActive = false;
    }
    
    public boolean isActive() {
        return isActive;
    }
    
    public boolean isOver() {
        return isOver;
    }

    public abstract void processMouseInput (int mousePressed, Point mousePosition, int mouseButton);
    
    protected void setModeIsOver() {
        isOver = true;
    }
}
