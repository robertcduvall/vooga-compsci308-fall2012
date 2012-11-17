/**
 * Manage Game Sprites, and change the game modes when certain events are
 * triggered
 * Check gameover conditions and goals of games
 * 
 * @author rex, Vo
 */
package vooga.turnbased.gamecore;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import util.input.core.KeyboardController;
import util.input.core.MouseController;
import vooga.turnbased.gameobject.BattleObject;
import vooga.turnbased.gameobject.GameObject;
import vooga.turnbased.gameobject.MapObject;
import vooga.turnbased.gameobject.TestMonster;
import vooga.turnbased.gui.GamePane;
import vooga.turnbased.gui.GameWindow;
import vooga.turnbased.sprites.Sprite;


// public class GameManager implements Observer {
public class GameManager {

    private final GamePane myGamePane;
    private GameMode myMapMode; // Fix me once the factory opens!s
    private GameMode myBattleMode;
    private GameMode myCurrentGameMode;
    // private Factory myFactory;
    // private MapObject myPlayer;
    private final boolean isOver;
    private HashMap<Integer, Sprite> mySprites;
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
        generateHardcodedSprites();
        myMapMode = new MapMode(this, MapObject.class);
        myBattleMode = new BattleMode(this, BattleObject.class);
        myCurrentGameMode = myMapMode;
        myCurrentGameMode.resume();
        configureInputHandling();
    }

    private void generateHardcodedSprites () { // factory will do this job
        // eventually...
        Sprite s = new Sprite();
        s.addGameObject(new TestMonster(0, GameEvent.NO_ACTION, 1, 2, 3, GameWindow
                .importImage("something")));

        mySprites.put(s.getID(), s);

        s = new Sprite();
        s.addGameObject(new TestMonster(1, GameEvent.NO_ACTION, 1, 2, 3, GameWindow
                .importImage("PlayerImage")));

        mySprites.put(s.getID(), s);

    }

    public ArrayList<GameObject> getModesObjects (Class c) {
        ArrayList<GameObject> modeObjects = new ArrayList<GameObject>();
        for (Sprite s : mySprites.values()) {
            modeObjects.addAll(s.getObject(c));
        }
        return modeObjects;
    }

    public void deleteSprite (int spriteID) {
        mySprites.remove(spriteID);
    }

    public boolean isOver () {
        return isOver;
    }

    public void update () {
        myCurrentGameMode.update();
    }

    /**
     * paint the images to the buffer
     * 
     * @param g
     *        the Graphics object of the offScreenImage
     */
    public void paint (Graphics g) {
        myCurrentGameMode.paint(g);
    }

    public void handleEvents (List<Map<GameEvent, List<Integer>>> events) {
        for (Map<GameEvent, List<Integer>> e : events) {
            for (GameEvent ge : e.keySet()) {
                handleEvent(ge, e.get(ge));
            }
        }
    }

    public void handleEvent (GameEvent eventName, List<Integer> myInvolvedIDs) {
        switch (eventName) {
            case NO_ACTION:
                break;
            case MAP_COLLISION:
                if (myInvolvedIDs.size() >= 2) {// this should be in map mode!
                    changeCurrentMode(myBattleMode);
                }
                break;
            case BATTLE_OVER:
                changeCurrentMode(myMapMode);
                break;
        }
    }

    public void changeCurrentMode (GameMode mode) {
        myCurrentGameMode.pause();
        myCurrentGameMode = mode;
        myCurrentGameMode.resume();
    }

    public void handleKeyPressed (KeyEvent e) {
        myCurrentGameMode.handleKeyPressed(e);
    }

    public void handleKeyReleased (KeyEvent e) {
        myCurrentGameMode.handleKeyReleased(e);
    }

    public void handleMouseClicked (MouseEvent e) {
        myCurrentGameMode.handleMouseClicked(e);
    }

    public Dimension getPaneDimension () {
        return myGamePane.getSize();
    }

    public int getDelayTime () {
        return myGamePane.getDelayTime();
    }

    public enum GameEvent {
        MAP_COLLISION, BATTLE_OVER, NO_ACTION
    }

    public KeyboardController getKeyboardController () {
        return myKeyboardController;
    }

    public MouseController getMouseController () {
        return myMouseController;
    }

    private void configureInputHandling () {
        // handle actions that shouldn't be passed down to individual gamemodes,
        // i.e. game pause
    }

}
