package vooga.turnbased.gamecore;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import vooga.turnbased.gamecore.gamemodes.BattleMode;
import vooga.turnbased.gamecore.gamemodes.GameMode;
import vooga.turnbased.gamecore.gamemodes.GameOverMode;
import vooga.turnbased.gamecore.gamemodes.MapMode;
import vooga.turnbased.gamecore.gamemodes.OptionMode;
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
    private GameLogic myGameLogic;
    private boolean myGameIsOver;
    private Map<Integer, Sprite> mySprites;
    private Map<String, Class> myAvailableModeTypes;
    private List<ModeEvent> myModeEvents;
    private List<MouseAction> myMouseActions;
    private List<GameMode> myGameModes;
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
        myAvailableModeTypes = new HashMap<String, Class>();
        myGameModes = new ArrayList<GameMode>();

        myModeEvents = new LinkedList<ModeEvent>();
        myMouseActions = new LinkedList<MouseAction>();
        // myLevelManager = new GameLevelManager(this);
        // myGameLogic = new GameLogic(this);
        // initializeGameLevel(GameWindow.importString("Entrance"));
        initializeGameLevel(GameWindow.importString("OtherLevel"));
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
    private void initializeGameLevel (String levelFileName) {
        LevelXmlParser test = new LevelXmlParser(new File(levelFileName), this);
        
        MapMode mapMode = (MapMode) test.getMapMode();
        myGameModes.add(mapMode);
        
        addSprites(test.parseSprites());
        myPlayerSpriteID = test.getPlayerID();
        myAvailableModeTypes = test.getUserDefinedModes();
        myGameLogic = new GameLogic(this, test.getEventConditionMapping());

        //startFirstMode(test.getStartMode());
        myGameModes.get(0).resume();
    }
    
//    private void startFirstMode(String entryMode) {
//        handleEvent(new ModeEvent(entryMode, new ArrayList<Integer>()));
//        myGameModes.get(0).resume();
//    }

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
            else {
                if (mode.isActive()) {
                    mode.update();
                }
                // if (mode.hasFocus()) {
                // handleMouseActions(mode);
                // }
            }
        }
        handleMouseActions(myGameModes.get(myGameModes.size() - 1)); //assume latest is focus
        for (GameMode mode : finishedModes) { // avoid concurrent modifcation
                                              // over myGameModes list
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

        public String getName () {
            return myName;
        }

        public List<Integer> getInvolvedIDs () {
            return myInvolvedIDs;
        }
    }

    public void flagCondition (String eventName, List<Integer> involvedSpriteIDs) {
        myGameLogic.flagCondition(eventName, involvedSpriteIDs);
    }

    /**
     * Adds an event to the list of events to handle.
     * 
     * @param eventName - String name of event to add.
     * @param involvedSpriteIDs - List of integer IDs of sprites involved in
     *        given action.
     */
    protected void flagEvent (String eventName, List<Integer> involvedSpriteIDs) {
        myModeEvents.add(new ModeEvent(eventName, involvedSpriteIDs));
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

    private void handleEvent (ModeEvent event) {
        String eventName = event.getName();
        List<Integer> myInvolvedIDs = event.getInvolvedIDs();
        if (myAvailableModeTypes.containsKey(eventName)) {
            if (!myGameModes.isEmpty()) {
                myGameModes.get(myGameModes.size() - 1).pause(); // TODO: again
                                                                 // assuming
                                                                 // latest is
                                                                 // active for
                                                                 // now
            }
            Class c = myAvailableModeTypes.get(eventName);
            Constructor[] newC = c.getConstructors();
            /*try {
                myGameModes.add((GameMode) newC[0]
                        .newInstance(this, MapObject.class, myInvolvedIDs));
            }
            catch (InstantiationException | IllegalAccessException | IllegalArgumentException
                    | InvocationTargetException e) {
                System.out.println("Check XML file for mistyped mode class");
            }*/
            try {
                myGameModes.add((GameMode) newC[0].newInstance(this, MapObject.class, myInvolvedIDs));
            }
            catch (Exception e) {
                System.out.println("Check XML file for mistyped mode class");
            }
        }
    }

    public void addMouseAction (int mousePressed, Point mousePos, int mouseButton) {
        myMouseActions.add(new MouseAction(mousePressed, mousePos, mouseButton));
        // System.out.println("press: "+mousePressed+", pos: "+mousePos+", button: "+mouseButton);
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

    // public void handleMouseDragged(Point mousePosition) {
    // myActiveGameMode.changeDisplayPosition(mousePosition);
    // }

    private void killMode (GameMode mode) {
        myGameModes.remove(mode);
        if (!myGameModes.isEmpty()) {
            myGameModes.get(myGameModes.size() - 1).resume();
        }
    }
}
