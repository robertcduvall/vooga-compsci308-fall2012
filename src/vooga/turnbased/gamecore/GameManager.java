package vooga.turnbased.gamecore;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import vooga.turnbased.gamecore.gamemodes.BattleMode;
import vooga.turnbased.gamecore.gamemodes.GameMode;
import vooga.turnbased.gamecore.gamemodes.GameOverMode;
import vooga.turnbased.gamecore.gamemodes.OptionMode;
import vooga.turnbased.gamecreation.GameLevelManager;
import vooga.turnbased.gamecreation.LevelXmlParser;
import vooga.turnbased.gameobject.GameObject;
import vooga.turnbased.gameobject.battleobject.BattleObject;
import vooga.turnbased.gameobject.mapobject.MapObject;
import vooga.turnbased.gui.GamePane;
import vooga.turnbased.gui.GameWindow;
import vooga.turnbased.gui.InputAPI;
import vooga.turnbased.sprites.Sprite;


/**
 * GameManager class that manages interactions between the map and battle modes
 * of the game.
 * 
 * @author Turnbased team
 * 
 */
public class GameManager implements InputAPI {

    private final GamePane myGamePane;
    // private GameLevelManager myLevelManager;
    private boolean myGameIsOver;
    private HashMap<Integer, Sprite> mySprites;
    private HashMap<Integer, GameMode> myGameModes;
    private List<ModeEvent> myModeEvents;
    private List<MouseAction> myMouseActions;
    private GameMode myActiveGameMode;
    private String myNewMapResource;
    private int myPlayerSpriteID;

    /**
     * Constructor of GameManager
     * 
     * @param gameCanvas
     *        The GameCanvas it paints to
     */
    public GameManager (GamePane gameCanvas) {
        myGamePane = gameCanvas;
        myGameIsOver = false;
        mySprites = new HashMap<Integer, Sprite>();
        myGameModes = new HashMap<Integer, GameMode>();
        myModeEvents = new LinkedList<ModeEvent>();
        myMouseActions = new LinkedList<MouseAction>();
        // myLevelManager = new GameLevelManager(this);
        // myGameLogic = new GameLogic(this);
        initializeGameLevel(GameWindow.importString("Entrance"));
        configureInputHandling();
    }

    /**
     * Starts a new level, and puts the RPG in mapMode in that new level.
     * 
     * @param levelFileName
     *        The name of the level that will be initialized
     * @param enteringObject
     *        The MapObject which will used for the MapMode of this level.
     */
    public void initializeGameLevel (String levelFileName) {
        LevelXmlParser test = new LevelXmlParser(new File(levelFileName), this);

        // need to get mode event mappings too

        myActiveGameMode = test.getMapMode();
        myGameModes.put(myActiveGameMode.getID(), myActiveGameMode);

        addSprites(test.parseSprites());

        myPlayerSpriteID = test.getPlayerID();

        myActiveGameMode.resume();
    }

    /**
     * find the Sprite with specific ID
     * 
     * @param id
     *        ID of the Sprite
     * @return the Sprite found (null if no Sprite with that ID was found)
     */
    public Sprite findSpriteWithID (int id) {
        return mySprites.get(id);
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
     * @param c
     *        Class of desired GameObjects.
     * @param <T>
     *        Type of class in the list.
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
     * @param spriteID
     *        Int ID of sprite to be removed.
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
        return myGameIsOver;
    }

    /**
     * Updates the actve game mode and handles any events occurring.
     */
    public void update () {
        handleEvents();
        handleMouseActions();
        myActiveGameMode.update();
    }

    /**
     * Paints the images to the buffer.
     * 
     * @param g
     *        The Graphics object of the offScreenImage.
     */
    public void paint (Graphics g) {
        myActiveGameMode.paint(g);
    }

    /**
     * Adds an event to the list of events to handle.
     * 
     * @param eventName
     *        String name of event to add.
     * @param involvedSpriteIDs
     *        List of integer IDs of sprites involved in given action.
     */
    // deprecated - use the one below
    public void flagEvent (String eventName, List<Integer> involvedSpriteIDs) {
        myModeEvents.add(new ModeEvent(eventName, involvedSpriteIDs));
    }

    /**
     * GameModes (mapMode, BattleMode, etc., collect their local events, then
     * decide which ones should be reported to GameManager, then report at the
     * end of update cycle using this method.
     * 
     * @param m
     *        This is the event that the GameMode is passing in for the
     *        GameManager to handle
     */

    /**
     * Takes events to be handled and deals with each according to the mode and
     * sprites involved.
     */
    private void handleEvents () {
        while (!myModeEvents.isEmpty()) {
            ModeEvent m = myModeEvents.remove(0);
            handleEvent(m);
        }
    }
    
    private void handleMouseActions() {
        while(!myMouseActions.isEmpty()){
            MouseAction m = myMouseActions.remove(0);
            myActiveGameMode.processMouseInput(m.myMousePosition,m.myMouseButton);
        }
    }

    /**
     * Pauses current modes, resume the active mode
     * 
     * @param mode
     *        GameMode object to be switched to.
     */
    private void changeCurrentMode (GameMode mode) {
        myActiveGameMode.pause();
        myActiveGameMode = mode;
        myActiveGameMode.resume();
    }

    private void changeCurrentMode (int modeID) {
        myActiveGameMode.pause();
        myActiveGameMode = myGameModes.get(modeID);
        myActiveGameMode.resume();
    }

    /**
     * Returns the Dimension associated with the current game window.
     * 
     * @return Size of current GamePane.
     */
    public Dimension getPaneDimension () { // TODO: shouldn't need this, just
                                           // paint an BufferedImage and then
                                           // scale it once it gets returned to
                                           // pane
        return myGamePane.getSize();
    }

    public void configureInputHandling () {
        // handle actions that shouldn't be passed down to individual gamemodes,
        // GamePane.keyboardController.setControl(KeyEvent.VK_ESCAPE,
        // KeyboardController.PRESSED, this, "gameOver");
    }

    /**
     * Calling this functions sets the next level from the input URI.
     * 
     * @param URI
     *        The location string of where to find the new map file.
     */
    public void setNewMapResources (String URI) {
        myNewMapResource = URI;
    }

    public int getPlayerSpriteID () {
        return myPlayerSpriteID;
    }

    protected String getNewMapResource () {
        return myNewMapResource;
    }

    private class ModeEvent {
        private final String myName;
        private final List<Integer> myInvolvedIDs;

        public ModeEvent (String eventName, List<Integer> involvedIDs) {
            myName = eventName;
            myInvolvedIDs = new ArrayList<Integer>(involvedIDs);
        }
    }

    // this whole thing to be wrapped into hashmap: event -> destinationModeId
    private void handleEvent (ModeEvent event) {
        String eventName = event.myName;
        List<Integer> myInvolvedIDs = event.myInvolvedIDs;
        if ("NO_ACTION".equals(eventName)) {
            // do nothing
        }
        else if ("BATTLE_START".equals(eventName)) {
            BattleMode battleMode = new BattleMode(5, this, BattleObject.class, myInvolvedIDs);// magic ID number
            changeCurrentMode(battleMode);
            // idOfRespectiveMode = myInvolvedIDs.get(0),
            // e.g. id of enemy sprite is equal to id of target battle mode... ?
            // changeCurrentMode(idOfRespectiveMode);
        }
        else if ("BATTLE_OVER".equals(eventName)) {
            changeCurrentMode(1);
            // changeCurrentMode(idOfLatestMapMode);
        }
        else if ("SWITCH_LEVEL".equals(eventName)) {
            myModeEvents.clear();
            myMouseActions.clear();
            mySprites.clear();
            myGameModes.clear();//TODO: Fix this, not how things really happen
            initializeGameLevel(GameWindow.importString("OtherLevel"));
            // idOfRespectiveLevel = myInvolvedIDs.get(0),
            // e.g. id of teleport sprite is equal to id of target mapmode
            // changeCurrentMode(idOfRespectiveLevel);
        }
        else if ("CONVERSATION_START".equals(eventName)) {
            OptionMode conversationMode =
                new OptionMode(2, this, MapObject.class, myInvolvedIDs);
        // do not add the same conversation twice
//        for (GameMode mode : myActiveModes) {
//            if ((mode instanceof OptionMode) &&
//                (conversationMode.equalsTo((OptionMode) (mode)))) { return; }
//        }
            changeCurrentMode(conversationMode);
            // idOfRespectiveMode = myInvolvedIDs.get(0),
            // e.g. id of NPC sprite is equal to id of target conversation mode
            // changeCurrentMode(idOfRespectiveMode);
        }
        else if ("CONVERSATION_OVER".equals(eventName)) {
            // changeCurrentMode(idOfLatestMapMode);
        }
        else if ("GAME_WON".equals(eventName)) { 
            // this event is thrown to gamemanager by gamelogic
            // changeCurrentMode(idOfGameWonMode);
        }
        else if ("GAME_LOST".equals(eventName)) { 
            // this event is thrown to gamemanager by gamelogic
            // changeCurrentMode(idOfGameOverMode);
            GameOverMode overMode = new GameOverMode(3, this, null);
            changeCurrentMode(overMode);
        }
        else {
            // please no more new features that don't fit in
            // "mode event -> destination mode" design
        }
    }
    
    public void addMouseAction(Point mousePos, int mouseButton){
        myMouseActions.add(new MouseAction(mousePos, mouseButton));
        //System.out.println("pos: "+mousePos+", button: "+mouseButton);
    }
    
    private class MouseAction {
        private Point myMousePosition;
        private int myMouseButton;
        
        public MouseAction(Point mousePos, int mouseButton){
            myMousePosition = mousePos;
            myMouseButton = mouseButton;
        }
    }   
    
    public void handleMouseDragged(MouseEvent e) {
    }
}
