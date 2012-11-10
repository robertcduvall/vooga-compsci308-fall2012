/**
 * Manage Game Sprites, and change the game modes when certain events are triggered
 * Check gameover conditions and goals of games
 * @author rex, Vo
 */
package vooga.turnbased.src.gamecore;

import gameobject.Sprite;
import gui.GameCanvas;
import java.awt.Point;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

public class GameManager implements Observer {

    private final GameCanvas myGameCanvas;
    private HashMap<Sprite, Point> mySprites;
    private final GameMode myCurrentGameMode;
    private Factory myFactory;
    private Sprite myPlayer;
    private final boolean isOver;

    /**
     * Constructor of GameManager
     * @param gameCanvas The GameCanvas it paints to
     */
    public GameManager (GameCanvas gameCanvas) {
        myGameCanvas = gameCanvas;
        isOver = false;
        //mySprites = myFactory.initializeSprites(myGameCanvas.getInitialMapFile());
        myCurrentGameMode = new MapMode(this);
        //myCurrentGameMode.initializeMap();
    }

    public boolean isOver () {
        return isOver;
    }

    public void update () {

    }

    public void paint () {

    }

    @Override
    public void update (Observable arg0, Object arg1) {
        // TODO: Receive notifications from GameModes.
    }
}
