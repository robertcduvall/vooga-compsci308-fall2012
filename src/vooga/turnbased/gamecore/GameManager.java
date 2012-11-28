package vooga.turnbased.gamecore;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import vooga.turnbased.gamecreation.GameLevelManager;
import vooga.turnbased.gameobject.GameObject;
import vooga.turnbased.gameobject.battleobject.BattleObject;
import vooga.turnbased.gameobject.mapobject.MapObject;
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
    private GameLevelManager myLevelManager;
    private MapMode myMapMode;
    private BattleMode myBattleMode;
    private GameMode myCurrentGameMode;
    private boolean isOver;
    private HashMap<Integer, Sprite> mySprites;
    private List<GameEvent> myEvents;

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
        myEvents = new LinkedList<GameEvent>();
        myBattleMode = new BattleMode(this, BattleObject.class);
        myLevelManager = new GameLevelManager(this);
        initializeGameLevel(GameWindow.importString("Entrance"), null);
        configureInputHandling();
    }

    public void initializeGameLevel (String levelFileName, MapObject enteringObject) {
        myLevelManager.enterMap(levelFileName, enteringObject);
        myMapMode = myLevelManager.getCurrentMapMode();
        mySprites.clear();
        addSprites(myLevelManager.getCurrentSprites());
        myCurrentGameMode = myMapMode;
        myCurrentGameMode.initialize();
    }

    /**
     * find the Sprite with specific ID
     * 
     * @param ID ID of the Sprite
     * @return the Sprite found (null if no Sprite with that ID was found)
     */
    public Sprite findSpriteWithID (int ID) {
        return mySprites.get(ID);
    }

    /**
     * add a list of sprites to the GameManager
     * 
     * @param sprites
     */
    private void addSprites (List<Sprite> sprites) {
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
     * game. Trigger removal of corresponding gameobjects inside each sprite.
     * 
     * @param spriteID Int ID of sprite to be removed.
     */
    public void deleteSprite (int spriteID) {
        findSpriteWithID(spriteID).clear();
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
    // deprecated - use the one below
    public void flagEvent (String eventName, List<Integer> involvedSpriteIDs) {
        myEvents.add(new GameEvent(eventName, involvedSpriteIDs));
    }
    
    // gamemodes collect their local events, then decide which ones should be
    // reported to gamemanager, then report at the end of update cycle using this method
    public void flagEvent (GameEvent m) {
        myEvents.add(m);
    }

    /**
     * Takes events to be handled and deals with each according to the mode and
     * sprites involved.
     */
    private void handleEvents () {
        while (!myEvents.isEmpty()) {
            GameEvent m = myEvents.remove(0);
            handleEvent(m);
        }
    }

    private void handleEvent (GameEvent event) {
        String eventName = event.getModeEventName();
        List<Integer> myInvolvedIDs = event.getEventInvolvedIDs();
        if ("NO_ACTION".equals(eventName)) {
            // do nothing
        }
        // deprecated - gamemanager shouldn't be notified about every single
        // collision
        /*else if ("MAP_COLLISION".equals(eventName)) {
            if (myInvolvedIDs.size() >= 2) {// this should be in map mode!
                changeCurrentMode(myBattleMode);
            }
        }*/
        else if ("BATTLE_START".equals(eventName)) {
            myBattleMode = new BattleMode(this, BattleObject.class, myInvolvedIDs);
            changeCurrentMode(myBattleMode);
        }
        else if ("BATTLE_OVER".equals(eventName)) {
            changeCurrentMode(myMapMode);
        }
        else if ("SWITCH_LEVEL".equals(eventName)) {
            // Rex can you add code for switching to new level here? smth like:
            /*
            Sprite teleportSprite = findSpriteWithID(myInvolvedIDs.get(0));
            MapTeleportObject teleport = teleportSprite.getObject(MapTeleportObject.class).get(0);
            String newLevelXML = teleport.getLevelXML();
            myMapMode = myLevelManager.getLevelBySpecifiedInXML(newLevelXML);
            changeCurrentMode(myMapMode);
            */
        }
        else if ("DIALOGUE_START".equals(eventName)) {
            //myDialogueMode = new DialogueMode(this, BattleObject.class, myInvolvedIDs);
            //changeCurrentMode(myDialogueMode);
        }
        else if ("DIALOGUE_OVER".equals(eventName)) {
            changeCurrentMode(myMapMode);
        }
        else if ("INTERACTION_COMPLETED".equals(eventName)) { 
            // to win, need to interact with specific (or all) sprites, i.e.
            // NPCs, Enemies, Pickup-able items, teleports, etc.
            /*
            myGameLogic.processInteraction(event);
            if(myGameLogic.winningConditionsAreMet()) { processGameWin(); }
            */
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
        // GamePane.keyboardController.setControl(KeyEvent.VK_ESCAPE,
        // KeyboardController.PRESSED, this, "gameOver");
    }
}
