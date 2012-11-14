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
import java.util.Observable;
import java.util.Observer;
import vooga.turnbased.gamecreation.Factory;
import vooga.turnbased.gameobject.MapObject;
import vooga.turnbased.gui.GameCanvas;


public class GameManager implements Observer {

    private final GameCanvas myGameCanvas;
    private GameMode myCurrentGameMode;
    private GameMode myPausedMapMode;
    private Factory myFactory;
    private MapObject myPlayer;
    private final boolean isOver;

    /**
     * Constructor of GameManager
     * 
     * @param gameCanvas The GameCanvas it paints to
     */
    public GameManager (GameCanvas gameCanvas) {
        myGameCanvas = gameCanvas;
        isOver = false;
        // mySprites =
        // myFactory.initializeSprites(myGameCanvas.getInitialMapFile());
        myCurrentGameMode = new MapMode(this);
        // myCurrentGameMode.initializeMap();
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
     * @param g the Graphics object of the offScreenImage
     */
    public void paint (Graphics g) {
        myCurrentGameMode.paint(g);
    }


    @Override
    public void update (Observable arg0, Object arg1) {

    }

    public void handleKeyPressed (KeyEvent e) {
        myCurrentGameMode.handleKeyPressed(e);
    }

    public void handleKeyReleased (KeyEvent e) {
        myCurrentGameMode.handleKeyReleased(e);
    }

    public Dimension getCanvasDimension () {
        return myGameCanvas.getSize();
    }
    
    public int getDelayTime() {
    	return myGameCanvas.getDelayTime();
    }
}
