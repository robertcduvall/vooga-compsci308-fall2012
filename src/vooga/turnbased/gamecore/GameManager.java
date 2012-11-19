package vooga.turnbased.gamecoRe;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import util.imageprocessing.ImageLoop;
import util.input.core.KeyboardController;
import util.input.core.MouseController;
import vooga.turnbased.gameobject.BattleObject;
import vooga.turnbased.gameobject.GameObject;
import vooga.turnbased.gameobject.MapObject;
import vooga.turnbased.gameobject.MapPlayerObject;
import vooga.turnbased.gameobject.MapTileObject;
import vooga.turnbased.gameobject.MovingMapObject;
import vooga.turnbased.gameobject.TestMonster;
import vooga.turnbased.gui.GamePane;
import vooga.turnbased.gui.GameWindow;
import vooga.turnbased.sprites.Sprite;


/**
 * GameManager class that manages interations between the map and battle modes
 * of the game.
 * 
 * @author Turnbased team
 * 
 */
public class GameManager {

    private final GamePane myGamePane;
    private MapMode myMapMode; // Fix me once the factory opens!s
    private BattleMode myBattleMode;
    private GameMode myCurrentGameMode;
    // private Factory myFactory;
    // private MapObject myPlayer;
    private final boolean isOver;
    private HashMap<Integer, Sprite> mySprites;
    private List<ModeEvent> myEvents;
    private KeyboardController myKeyboardController;
    private MouseController myMouseController;

    /**
     * Constructor of GameManager
     * 
     * @param gameCanvas
     *        The GameCanvas it paints to
     */
    public GameManager (GamePane gameCanvas) {
        myGamePane = gameCanvas;
        myKeyboardController = gameCanvas.getKeyboardController();
        myMouseController = gameCanvas.getMouseController();
        isOver = false;
        // mySprites =
        // myFactory.initializeSprites(myGameCanvas.getInitialMapFile());
        mySprites = new HashMap<Integer, Sprite>();
        myEvents = new LinkedList<ModeEvent>();
        myMapMode = new MapMode(this, MapObject.class);
        myBattleMode = new BattleMode(this, BattleObject.class);
        generateHardcodedLevel();
        myCurrentGameMode = myMapMode;
        myCurrentGameMode.init();
        configureInputHandling();
    }

    private void generateHardcodedLevel () { // factory will do this job
        // eventually...
        myMapMode.setNumDisplayRows(Integer.parseInt(GameWindow.importString("CameraHeight")));
        myMapMode.setNumDisplayCols(Integer.parseInt(GameWindow.importString("CameraWidth")));
        myMapMode.setBottomRight(new Point(20, 30));
        Sprite s = new Sprite();
        for (int i = 0; i < myMapMode.getBottomRight().x; i++) {
            for (int j = 0; j < myMapMode.getBottomRight().y; j++) {
                Point p = new Point(i, j);
                s = new Sprite();
                s.addGameObject(new MapTileObject(s.getID(), "NO_ACTION", p, GameWindow
                        .importImage("GrassImage"), myMapMode));
                mySprites.put(s.getID(), s);
            }
        }

        s = new Sprite();
        s.addGameObject(new TestMonster(0, "NO_ACTION", 1, 2, 3, GameWindow
                .importImage("Charmeleon")));
        Point center = new Point(5, 5);
        MovingMapObject test1 =
                new MovingMapObject(0, "MAP_COLLISION", center,
                                    GameWindow.importImage("something"), myMapMode);

        s.addGameObject(test1);

        mySprites.put(s.getID(), s);

        s = new Sprite();
        s.addGameObject(new TestMonster(1, "NO_ACTION", 1, 2, 3, GameWindow
                .importImage("MyPikachu")));

        mySprites.put(s.getID(), s);

        center = new Point(8, 8);
        Map<String, Image> images = new HashMap<String, Image>();
        images.put("left", GameWindow.importImage("PlayerLeft"));
        images.put("right", GameWindow.importImage("PlayerRight"));
        images.put("down", GameWindow.importImage("PlayerDown"));
        images.put("up", GameWindow.importImage("PlayerUp"));
        Map<String, ImageLoop> imageLoops = new HashMap<String, ImageLoop>();
        Image left = GameWindow.importImage("PlayerLeft");
        Image left1 = GameWindow.importImage("PlayerLeft1");
        Image left2 = GameWindow.importImage("PlayerLeft2");
        Image right = GameWindow.importImage("PlayerRight");
        Image right1 = GameWindow.importImage("PlayerRight1");
        Image right2 = GameWindow.importImage("PlayerRight2");
        Image up = GameWindow.importImage("PlayerUp");
        Image up1 = GameWindow.importImage("PlayerUp1");
        Image up2 = GameWindow.importImage("PlayerUp2");
        Image down = GameWindow.importImage("PlayerDown");
        Image down1 = GameWindow.importImage("PlayerDown1");
        Image down2 = GameWindow.importImage("PlayerDown2");
        List<Image> leftList = new ArrayList<Image>();
        leftList.add(left);
        leftList.add(left1);
        leftList.add(left2);
        imageLoops.put("left", new ImageLoop(leftList));
        List<Image> rightList = new ArrayList<Image>();
        rightList.add(right);
        rightList.add(right1);
        rightList.add(right2);
        imageLoops.put("right", new ImageLoop(rightList));
        List<Image> upList = new ArrayList<Image>();
        upList.add(up);
        upList.add(up1);
        upList.add(up2);
        imageLoops.put("up", new ImageLoop(upList));
        List<Image> downList = new ArrayList<Image>();
        downList.add(down);
        downList.add(down1);
        downList.add(down2);
        imageLoops.put("down", new ImageLoop(downList));

        s = new Sprite();
        MapPlayerObject player =
                new MapPlayerObject(s.getID(), "MAP_COLLISION", center, images, myMapMode);
        player.setImageLoops(imageLoops);
        myMapMode.setPlayer(player);
        s.addGameObject(player);
        mySprites.put(s.getID(), s);
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
    public void update () {
        myCurrentGameMode.update();
        handleEvents();
    }

    /**
     * Paints the images to the buffer.
     * 
     * @param g The Graphics object of the offScreenImage.
     */
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
     * Passes key input to the GameMode.
     * 
     * @param e KeyEvent to be handled.
     */
    public void handleKeyPressed (KeyEvent e) {
        myCurrentGameMode.handleKeyPressed(e);
    }

    /**
     * Passes key input to the GameMode.
     * 
     * @param e KeyEvent to be handled.
     */
    public void handleKeyReleased (KeyEvent e) {
        myCurrentGameMode.handleKeyReleased(e);
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

    /**
     * Returns the current time delay for the game window.
     * 
     * @return Current time delay.
     */
    public int getDelayTime () {
        return myGamePane.getDelayTime();
    }

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

    private void configureInputHandling () {
        // handle actions that shouldn't be passed down to individual gamemodes,
        // i.e. game pause
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
