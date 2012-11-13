/**
 * Manage Game Sprites, and change the game modes when certain events are triggered
 * Check gameover conditions and goals of games
 * @author rex, Vo
 */
package vooga.turnbased.gamecore;

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
    
    /**
     * paint the images to the buffer
     * @param g the Graphics object of the offScreenImage
     * @param width Width of the image
     * @param height Height of the image
     */
    public void paintImage(Graphics g, int width, int height) {
    	myCurrentGameMode.paint(g, width, height);
    }

    public void paint () {
        myCurrentGameMode.paint(myGameCanvas.getGraphics(), myGameCanvas.getWidth(), 
                myGameCanvas.getHeight());
    }

//    public void startBattle(Enemy e) {
//        // "pause" the current MapMode and switch to BattleMode with the given enemy
//        myPausedMapMode = myCurrentGameMode;
//        myCurrentGameMode = new BattleMode(this, e);
//    }
    
    public void backToMap() {
        myCurrentGameMode = myPausedMapMode;
    }
    
    @Override
    public void update (Observable arg0, Object arg1) {
        
    }

    public void notifyKeyEvent (KeyEvent e) {
        myCurrentGameMode.handleKeyEvent(e);
    }
}
