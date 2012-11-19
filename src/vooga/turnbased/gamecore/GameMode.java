package vooga.turnbased.gamecorE;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import util.input.core.KeyboardController;
import util.input.core.MouseController;
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
    private final GameManager myGameManager;
    private final Class myObjectType;

    private ArrayList<GameObject> myObjects;
    private KeyboardController myKeyboardController;
    private MouseController myMouseController;

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
        setControllers();
        configureInputHandling();
        // myObjects = new
        // ArrayList<GameObject>(myGameManager.getModesObjects(modeObjectType));
    }

    private void setControllers () {
        myKeyboardController = getGameManager().getKeyboardController();
        myMouseController = getGameManager().getMouseController();
    }

    // these methods already exist in game manager...isn't one of them redundant?

    /**
     * Returns the current KeyboardController.
     * 
     * @return KeyboardController in use.
     */
    public KeyboardController getKeyboardController () {
        return myKeyboardController;
    }

    /**
     * Returns the current MouseController.
     * 
     * @return MouseController in use.
     */
    public MouseController getMouseController () {
        return myMouseController;
    }

    protected GameManager getGameManager () {
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
    
    public abstract void init ();

    /**
     * Method that will paint the different objects in the mode.
     * @param g Graphics.
     */
    public abstract void paint (Graphics g);

    /**
     * Method that will update the objects in the GameMode when called.
     */
    public abstract void update ();

    // public abstract void processGameEvents ();

    /**
     * Method that will handle key pressed.
     * @param e KeyEvent to be handled.
     */
    public abstract void handleKeyPressed (KeyEvent e);

    /**
     * Method that will handle keys released.
     * @param e KeyEvent to be handled.
     */
    public abstract void handleKeyReleased (KeyEvent e);

    /**
     * Method to configure how inputs will be handled (possibly using input API?)
     */
    public abstract void configureInputHandling ();

    /**
     * Override if any sub-mode needs to handle MouseClicked events.
     * @param e MouseEvent to be handled.
     */
    public void handleMouseClicked (MouseEvent e) {
    }
}
