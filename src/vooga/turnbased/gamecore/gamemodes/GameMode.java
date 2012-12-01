package vooga.turnbased.gamecore.gamemodes;

import java.awt.Graphics;
import java.awt.Point;
import java.util.List;
import vooga.turnbased.gamecore.GameManager;


/**
 * Abstract class that represent a mode in a game,
 * such as map mode where players walk around, or battle mode where players
 * fight against enemies
 * 
 * @author rex, Volodymyr
 */
// public abstract class GameMode extends Observable {
public abstract class GameMode {
    private final int myID;
    private final GameManager myGameManager;
    private final Class myObjectType;
    private boolean myHasFocus;

    /**
     * Constructor for GameMode.
     * 
     * @param gm The GameManager which receives information about how sprites
     *        interact.
     * @param modeObjectType Type of GameObject associated with GameMode
     *        being constructed.
     */
    public GameMode (int ID, GameManager gm, Class modeObjectType) {
        myID = ID;
        myGameManager = gm;
        myObjectType = modeObjectType;
        myHasFocus = true;
    }

    public GameManager getGameManager () {
        return myGameManager;
    }

    public void flagEvent (String eventName, List<Integer> involvedSpriteIDs) {
        myGameManager.flagEvent(eventName, involvedSpriteIDs);
    }

    /**
     * Returns type of GameObject associated with current mode.
     * 
     * @return Type of object that current mode uses.
     */
    public Class getObjectType () {
        return myObjectType;
    }

    public int getID () {
        return myID;
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

    protected boolean hasFocus () {
        return myHasFocus;
    }

    protected void setFocus (boolean isFocus) {
        myHasFocus = isFocus;
    }

    public void changeDisplayPosition (Point position) {
    }
    public abstract void processMouseInput (Boolean mousePressed, Point mousePosition, int mouseButton);
}
