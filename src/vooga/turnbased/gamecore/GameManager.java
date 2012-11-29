package vooga.turnbased.gamecore;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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
    private boolean isOver;
    private HashMap<Integer, Sprite> mySprites;
    private List<GameEvent> myEvents;
    private List<GameMode> myActiveModes;
    private String myNewMapResource;

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
        myActiveModes = new LinkedList<GameMode>();
        myLevelManager = new GameLevelManager(this);
        initializeGameLevel(GameWindow.importString("Entrance"), null);
        configureInputHandling();
    }

    public void initializeGameLevel (String levelFileName, MapObject enteringObject) {
        myActiveModes.remove(myLevelManager.getCurrentMapMode());
        myLevelManager.enterMap(levelFileName, enteringObject);
        MapMode mapMode = myLevelManager.getCurrentMapMode();
        myActiveModes.add(0, mapMode);
        mySprites.clear();
        addSprites(myLevelManager.getCurrentSprites());
        mapMode.initialize();
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
        for (GameMode mode : myActiveModes) {
            mode.update();
        }
        handleEvents();
    }

    /**
     * Paints the images to the buffer.
     * 
     * @param g The Graphics object of the offScreenImage.
     */
    @Override
    public void paint (Graphics g) {
        for (GameMode mode : myActiveModes) {
            mode.paint(g);
        }
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
    // reported to gamemanager, then report at the end of update cycle using
    // this method
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
        else if ("BATTLE_START".equals(eventName)) {
            BattleMode battleMode = new BattleMode(this, BattleObject.class, myInvolvedIDs);
            myActiveModes.add(battleMode);
            changeCurrentMode(battleMode);
        }
        else if ("BATTLE_OVER".equals(eventName)) {
            removeInactiveModes();
            resumeModes();
        }
        else if ("SWITCH_LEVEL".equals(eventName)) {
            MapObject enteringMapObject = findMapObjectWithID(myInvolvedIDs.get(0));
            initializeGameLevel(myNewMapResource, enteringMapObject);
        }
        else if ("CONVERSATION_START".equals(eventName)) {
            MapObject targetMapObject = findMapObjectWithID(myInvolvedIDs.get(0));
            ConversationMode conversationMode = new ConversationMode(this, MapObject.class, targetMapObject);
            myActiveModes.add(conversationMode);
        }
        else if ("CONVERSATION_OVER".equals(eventName)) {

        }
        else if ("INTERACTION_COMPLETED".equals(eventName)) {
            // to win, need to interact with specific (or all) sprites, i.e.
            // NPCs, Enemies, Pickup-able items, teleports, etc.
            /*
             * myGameLogic.processInteraction(event);
             * if(myGameLogic.winningConditionsAreMet()) { processGameWin(); }
             */
        }
        else {
            // System.err.println("Unrecognized mode event requested.");
        }
    }

    /**
     * Pauses current modes, resume the active mode
     * 
     * @param mode GameMode object to be switched to.
     */
    protected void changeCurrentMode (GameMode activeMode) {
        for (GameMode mode : myActiveModes) {
            if (mode == activeMode) {
                continue;
            }
            mode.pause();
        }
        activeMode.resume();
    }

    protected void resumeModes () {
        for (GameMode mode : myActiveModes) {
            mode.resume();
        }
    }

    /**
     * Passes mouse input to the GameMode.
     * 
     * @param e MouseEvent to be handled.
     */
    public void handleMouseClicked (MouseEvent e) {
        // myCurrentGameMode.handleMouseClicked(e);
        for (GameMode mode : myActiveModes) {
            mode.handleMouseClicked(e);
        }
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

    private void removeInactiveModes () {
        Iterator<GameMode> iterator = myActiveModes.iterator();
        while (iterator.hasNext()) {
            GameMode mode = iterator.next();
            if (!mode.isActive()) {
                iterator.remove();
            }
        }
    }

    public void setNewMapResources (String URI) {
        myNewMapResource = URI;
    }
    
    private MapObject findMapObjectWithID(int ID) {
        return findSpriteWithID(ID).getMapObject();
    }
}
