package vooga.turnbased.gamecore.gamemodes;

import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import util.sound.SoundPlayer;
import vooga.turnbased.gamecore.GameManager;
import vooga.turnbased.gameobject.GameObject;
import vooga.turnbased.gui.GameWindow;


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
    private String myModeName;
    private boolean myHasFocus;
    private boolean isActive;
    private boolean isOver;

    /**
     * Constructor for GameMode.
     * 
     * @param gm The GameManager which receives information about how sprites
     *        interact.
     * @param modeName String name of the type of mode to create.
     * @param involvedIDs List of IDs of sprites involved in the gamemode.
     */
    public GameMode (GameManager gm, String modeName, List<Integer> involvedIDs) {
        myGameManager = gm;
        myModeName = modeName;
        myHasFocus = true;
        isActive = true;
        isOver = false;
        acquireGameObjects();
    }

    protected void acquireGameObjects () {
        myGameObjects = new ArrayList<GameObject>();
        myGameObjects.addAll(myGameManager.getGameObjects(getName()));
    }

    protected List<? extends GameObject> getGameObjects () {
        return myGameObjects;
    }

    protected List<? extends GameObject> getGameObjectsByID (int spriteID) {
        List<GameObject> gameObjectsOfID = new ArrayList<GameObject>();
        for (GameObject go : myGameObjects) {
            if (go.getID() == spriteID) {
                gameObjectsOfID.add(go);
            }
        }
        return gameObjectsOfID;
    }

    /**
     * Returns the name of the mode.
     * 
     * @return Name of mode.
     */
    public String getName () {
        return myModeName;
    }

    /**
     * Returns the GameManager currently associated with the mode.
     * 
     * @return myGameManager
     */

    public GameManager getGameManager () {
        return myGameManager;
    }

    /**
     * Associates the list of involved sprites with the condition detailed in the string.
     * 
     * @param conditionName String name of condition to be assigned to the sprites.
     * @param involvedSpriteIDs List of sprites to which the condition will be applied.
     */
    public void flagCondition (String conditionName, List<Integer> involvedSpriteIDs) {
        myGameManager.flagCondition(conditionName, involvedSpriteIDs);
    }

    /**
     * Call when gamemode if first created.
     */
    public abstract void initialize ();

    /**
     * Suspend a mode while entering a different mode.
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

    /**
     * Returns whether or not the mode has focus.
     * 
     * @return myHasFocus
     */
    public boolean hasFocus () {
        return myHasFocus;
    }

    protected void setFocus (boolean isFocus) {
        myHasFocus = isFocus;
    }

    protected void setActive () {
        isActive = true;
    }

    protected void setInactive () {
        isActive = false;
    }

    /**
     * Returns whether or not the mode is active.
     * 
     * @return Boolean true if mode is active, false if not.
     */
    public boolean isActive () {
        return isActive;
    }

    /**
     * Returns whether or not the mode should be over.
     * 
     * @return Boolean true if mode should be over, false if not.
     */
    public boolean isOver () {
        return isOver;
    }

    /**
     * Takes mouse input and decides how to deal with this input; to be implemented by child
     * classes.
     * 
     * @param mousePressed int 1 if mouse is pressed, 0 if not.
     * @param mousePosition Point position of mouse cursor.
     * @param mouseButton int of button of mouse.
     */
    public abstract void processMouseInput (int mousePressed, Point mousePosition, int mouseButton);

    public void setModeIsOver () {
        isOver = true;
    }

    protected void playModeEntranceSound(String soundFileURL) {
        getGameManager().turnOffSoundTrack();
        SoundPlayer p = new SoundPlayer(GameWindow.importString(soundFileURL));
        p.playOnce();
    }
}
