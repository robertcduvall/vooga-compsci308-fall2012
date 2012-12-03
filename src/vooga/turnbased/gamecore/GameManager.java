package vooga.turnbased.gamecore;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import util.input.core.KeyboardController;
import util.sound.SoundPlayer;
import vooga.turnbased.gamecore.gamemodes.GameMode;
import vooga.turnbased.gamecreation.LevelXmlParser;
import vooga.turnbased.gameobject.GameObject;
import vooga.turnbased.gui.GamePane;
import vooga.turnbased.gui.GameWindow;
import vooga.turnbased.gui.InputAPI;
import vooga.turnbased.sprites.Sprite;


/**
 * GameManager class that manages interactions between the map and battle modes
 * of the game.
 * 
 * @author RPGs team
 * 
 */
public class GameManager implements InputAPI {

    private final GamePane myGamePane;
    // private GameLevelManager myLevelManager;
    private GameLogic myGameLogic;
    private boolean myGameIsOver;
    private Map<Integer, Sprite> mySprites;
    private Map<String, Class> myAvailableModeTypes;
    private List<ModeEvent> myModeEvents;
    private List<MouseAction> myMouseActions;
    private List<GameMode> myGameModes;
    private String myNewMapResource;
    private int myPlayerSpriteID;
    private Dimension myMapSize;
    private Dimension myCameraSize;
    private SoundPlayer myGameSoundTrack;

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
        myAvailableModeTypes = new HashMap<String, Class>();
        myGameModes = new ArrayList<GameMode>();

        myModeEvents = new LinkedList<ModeEvent>();
        myMouseActions = new LinkedList<MouseAction>();
        // myLevelManager = new GameLevelManager(this);
        myGameLogic = new GameLogic(this);
        myGameSoundTrack = new SoundPlayer(GameWindow.importString("GameSoundTrack"));
        initializeGameLevel(GameWindow.importString("GameXML"),
                            GameWindow.importString("PlayerXML"));
        configureInputHandling();
    }

    /**
     * Starts a new level, and puts the RPG in mapMode in that new level.
     * 
     * @param gameFileName
     *        The name of the level that will be initialized
     * @param enteringObject
     *        The MapObject which will used for the MapMode of this level.
     */
    private void initializeGameLevel (String gameFileName, String playerFileName) {
        LevelXmlParser levelLoader = new LevelXmlParser(gameFileName, playerFileName, this);
        myMapSize = levelLoader.getMapSize();
        myCameraSize = levelLoader.getCameraSize();

        addSprites(levelLoader.parseSprites());

        myPlayerSpriteID = levelLoader.getPlayerID();

        myAvailableModeTypes = levelLoader.getUserDefinedModes();

        myGameLogic.addEventConditions(levelLoader.getEventConditionMapping());

        startFirstMode(levelLoader.getStartMode());
    }
    private void startFirstMode (String entryMode) {
        handleEvent(new ModeEvent(entryMode, new ArrayList<Integer>()));
    }
    /**
     * Get the dimension of the map
     * @return
     */
    public Dimension getMapSize () {
        return myMapSize;
    }
    /**
     * Get the dimension of the camera window
     * @return
     */
    public Dimension getCameraSize () {
        return myCameraSize;
    }
    /**
     * find the Sprite with specific ID
     * 
     * @param id ID of the Sprite
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
     * Get a list of all the GameObjects that belong to the given certain GameMode
     * @param modeName 
     * @return
     */
    public List<GameObject> getGameObjects (String modeName) {
        List<GameObject> modeObjects = new ArrayList<GameObject>();
        for (Sprite s : mySprites.values()) {
            modeObjects.addAll(s.getObjects(modeName));
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
    public void clearSprite (int spriteID) {
        findSpriteWithID(spriteID).clear();
        // mySprites.put(spriteID, null);
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
        updateGameModes();
        // myGameModes.get(myGameModes.size()-1).update(); //assume latest is
        // active for now
        // handleMouseActions(myGameModes.get(myGameModes.size()-1));
    }

    private void updateGameModes () {
        ArrayList<GameMode> finishedModes = new ArrayList<GameMode>();
        for (GameMode mode : myGameModes) {
            if (mode.isOver()) {
                finishedModes.add(mode);
            }
            // else {
            // if (mode.isActive()) {
            // mode.update();
            // }
            // if (mode.hasFocus()) { // TODO this is wrong
            // handleMouseActions(mode);
            // }
            // }
        }
        myGameModes.get(myGameModes.size() - 1).update();
        handleMouseActions(myGameModes.get(myGameModes.size() - 1));
        for (GameMode mode : finishedModes) { 
            killMode(mode);
        }
    }

    /**
     * Paints the images to the buffer.
     * 
     * @param g
     *        The Graphics object of the offScreenImage.
     */
    public void paint (Graphics g) {
        for (GameMode mode : myGameModes) {
            if (mode.isActive()) {
                mode.paint(g);
            }
        }
        // myGameModes.get(myGameModes.size()-1).paint(g);
    }

    private void handleMouseActions (GameMode mode) {
        while (!myMouseActions.isEmpty()) {
            MouseAction m = myMouseActions.remove(0);
            mode.processMouseInput(m.myMouseEventType, m.myMousePosition, m.myMouseButton);
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

    @Override
    public void configureInputHandling () {
        try {
            GamePane.keyboardController.setControl(KeyEvent.VK_M, KeyboardController.RELEASED,
                                                   this, "toggleSoundTrack");
            GamePane.keyboardController.setControl(KeyEvent.VK_ESCAPE, KeyboardController.PRESSED,
                                                   myGamePane, "returnToMenu");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Calling this functions sets the next level from the input URI.
     * 
     * @param uri The location string of where to find the new map file.
     */
    public void setNewMapResources (String uri) {
        myNewMapResource = uri;
    }
    /**
     * Get the sprite ID of the player
     * @return
     */
    public int getPlayerSpriteID () {
        return myPlayerSpriteID;
    }

    protected String getNewMapResource () {
        return myNewMapResource;
    }


    /**
     * Flag an event using the GameLogic
     * @param eventName 
     * @param involvedSpriteIDs 
     */
    public void flagCondition (String eventName, List<Integer> involvedSpriteIDs) {
        myGameLogic.flagCondition(eventName, involvedSpriteIDs);
    }

    /**
     * Adds an event to the list of events to handle.
     * 
     * @param modeName - String name of event to add.
     * @param involvedSpriteIDs - List of integer IDs of sprites involved in
     *        given action.
     */
    protected void flagEvent (String modeName, List<Integer> involvedSpriteIDs) {
        myModeEvents.add(new ModeEvent(modeName, involvedSpriteIDs));
    }

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
    private boolean modeAlreadyExists (String modeName) {
        for (GameMode g : myGameModes) {
            if (modeName.equals(g.getName())) {
                myGameModes.get(myGameModes.size() - 1).pause();
                myGameModes.remove(g); 
                myGameModes.add(g);
                g.resume();
                return true;
            }
        }
        return false;
    }

    private void handleEvent (ModeEvent event) {
        // System.out.println("doing event: "+event.getName());
        // System.out.println("Going to make class: "+myAvailableModeTypes.get(event.getName()));
        String modeName = event.getName();
        List<Integer> myInvolvedIDs = event.getInvolvedIDs();
        if (!modeAlreadyExists(modeName)) {
            if (myAvailableModeTypes.containsKey(modeName)) {
                if (!myGameModes.isEmpty()) {
                    myGameModes.get(myGameModes.size() - 1).pause();
                }
                Class c = myAvailableModeTypes.get(modeName);
                Constructor[] newC = c.getConstructors();

                try {
                    myGameModes.add((GameMode) newC[0].newInstance(this, modeName, myInvolvedIDs));
                }
                catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Unable to create mode " + modeName + " of class " +
                                       c.toString());
                }
            }
        }
    }

    /**
     * Add a mouse action
     * @param mousePressed 
     * @param mousePos 
     * @param mouseButton 
     */
    public void addMouseAction (int mousePressed, Point mousePos, int mouseButton) {
        myMouseActions.add(new MouseAction(mousePressed, mousePos, mouseButton));
    }

    private void killMode (GameMode mode) {
        myGameModes.remove(mode);
        if (!myGameModes.isEmpty()) {
            myGameModes.get(myGameModes.size() - 1).resume();
        }
    }
    /**
     * Toggle the background sound track being played
     */
    public void toggleSoundTrack () {
        if (myGameSoundTrack.loopIsRunning()) {
            myGameSoundTrack.stopLoop();
        }
        else {
            myGameSoundTrack.startLoop();
        }
    }
    
    /**
     * Turn off background soundtrack if any.
     */
    public void turnOffSoundTrack() {
        myGameSoundTrack.stopLoop();
    }
    
    private class MouseAction {
        private int myMouseEventType;
        private Point myMousePosition;
        private int myMouseButton;

        public MouseAction (int mouseEventType, Point mousePos, int mouseButton) {
            myMouseEventType = mouseEventType;
            myMousePosition = mousePos;
            myMouseButton = mouseButton;
        }
    }
    private class ModeEvent {
        private final String myName;
        private final List<Integer> myInvolvedIDs;

        public ModeEvent (String modeName, List<Integer> involvedIDs) {
            myName = modeName;
            myInvolvedIDs = new ArrayList<Integer>(involvedIDs);
        }

        public String getName () {
            return myName;
        }

        public List<Integer> getInvolvedIDs () {
            return myInvolvedIDs;
        }
    }
}
