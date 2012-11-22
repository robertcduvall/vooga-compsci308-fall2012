package vooga.turnbased.gamecore;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import vooga.turnbased.gamecreation.GameLevelManager;
import vooga.turnbased.gameobject.GameObject;
import vooga.turnbased.gameobject.battleobject.BattleObject;
import vooga.turnbased.gui.GamePane;
import vooga.turnbased.gui.GameWindow;
import vooga.turnbased.gui.InputAPI;
import vooga.turnbased.sprites.Sprite;


/**
 * GameManager class that manages interations between the map and battle modes
 * of the game.
 * 
 * @author Turnbased team
 * 
 */
public class GameManager implements GameLoopMember, InputAPI {

    private final GamePane myGamePane;
    private MapMode myMapMode;
    private BattleMode myBattleMode;
    private GameMode myCurrentGameMode;
    private boolean isOver;
    private HashMap<Integer, Sprite> mySprites;
    private List<ModeEvent> myEvents;

    /**
     * Constructor of GameManager
     * 
     * @param gameCanvas
     *        The GameCanvas it paints to
     */
    public GameManager (GamePane gameCanvas) {
        myGamePane = gameCanvas;
        isOver = false;
        mySprites = new HashMap<Integer, Sprite>();
        myEvents = new LinkedList<ModeEvent>();
        // myMapMode = new MapMode(this, MapObject.class);
        myBattleMode = new BattleMode(this, BattleObject.class);

        GameLevelManager levelManager =
                new GameLevelManager(this, GameWindow.importString("Entrance"));
        myMapMode = levelManager.getCurrentMapMode();
        // generateHardcodedLevel();
        myCurrentGameMode = myMapMode;
        myCurrentGameMode.initialize();
        configureInputHandling();
    }

    public void addSprites (List<Sprite> sprites) {
        for (Sprite s : sprites) {
            mySprites.put(s.getID(), s);
        }
    }

    /**
     * Returns a list of GameObjects of the indicated type.
     * 
     * @param c Class of desired GameObjects.
     * @param <T> Type of class in the list.
     * @return modeObjects A list of all requested GameObjects within all
     *         sprites.
     */
    public <T extends GameObject> List<T> getGameObjectsOfSpecificMode (Class c) {
        List<T> modeObjects = new ArrayList<T>();
        for (Sprite s : mySprites.values()) {
            modeObjects.addAll(s.getObject(c));
        }
        return modeObjects;
    }

    /**
     * Removes the sprite with the given ID from the list of sprites in the
     * game.
     * 
     * @param spriteID Int ID of sprite to be removed.
     */
    public void deleteSprite (int spriteID) {
        mySprites.get(spriteID).clear();
        mySprites.remove(spriteID);
    }

    /**
     * Checks whether the game is over.
     * 
     * @return isOver True if game is over, false if not.
     */
    public boolean isOver () {
        return isOver;
    }

    /**
     * Updates the actve game mode and handles any events occurring.
     */
    @Override
    public void update () {
        myCurrentGameMode.update();
        handleEvents();
    }

    /**
     * Paints the images to the buffer.
     * 
     * @param g The Graphics object of the offScreenImage.
     */
    @Override
    public void paint (Graphics g) {
        myCurrentGameMode.paint(g);
    }

    /**
     * Adds an event to the list of events to handle.
     * 
     * @param eventName String name of event to add.
     * @param involvedSpriteIDs List of integer IDs of sprites involved in given
     *        action.
     */
    public void flagEvent (String eventName, List<Integer> involvedSpriteIDs) {
        myEvents.add(new ModeEvent(eventName, involvedSpriteIDs));
    }

    /**
     * Takes events to be handled and deals with each according to the mode and
     * sprites involved.
     */
    private void handleEvents () {
        while (!myEvents.isEmpty()) {
            ModeEvent m = myEvents.remove(0);
            handleEvent(m.getModeEventName(), m.getEventInvolvedIDs());
        }
    }

    private void handleEvent (String eventName, List<Integer> myInvolvedIDs) {
        if ("NO_ACTION".equals(eventName)) {
            // do nothing
        }
        else if ("MAP_COLLISION".equals(eventName)) {
            if (myInvolvedIDs.size() >= 2) {// this should be in map mode!
                changeCurrentMode(myBattleMode);
            }
        }
        else if ("BATTLE_OVER".equals(eventName)) {
            changeCurrentMode(myMapMode);
        }
        else {
            System.err.println("Unrecognized mode event requested.");
        }
    }

    /**
     * Pauses current mode, switches to given GameMode, and begins that mode.
     * 
     * @param mode GameMode type to be switched to.
     */
    public void changeCurrentMode (GameMode mode) {
        myCurrentGameMode.pause();
        myCurrentGameMode = mode;
        myCurrentGameMode.resume();
    }

    /**
     * Passes mouse input to the GameMode.
     * 
     * @param e MouseEvent to be handled.
     */
    public void handleMouseClicked (MouseEvent e) {
        myCurrentGameMode.handleMouseClicked(e);
    }

    /**
     * Returns the Dimension associated with the current game window.
     * 
     * @return Size of current GamePane.
     */
    public Dimension getPaneDimension () {
        return myGamePane.getSize();
    }

    public void configureInputHandling () {
        // handle actions that shouldn't be passed down to individual gamemodes,
        // GamePane.keyboardController.setControl(KeyEvent.VK_ESCAPE, KeyboardController.PRESSED, this, "gameOver");
    }

    private class ModeEvent {
        private final String myName;
        private final List<Integer> myInvolvedIDs;

        public ModeEvent (String eventName, List<Integer> involvedIDs) {
            myName = eventName;
            myInvolvedIDs = new ArrayList<Integer>(involvedIDs);
        }

        public String getModeEventName () {
            return myName;
        }

        public List<Integer> getEventInvolvedIDs () {
            return myInvolvedIDs;
        }
    }
}
