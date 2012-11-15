/**
 * Abstract class that represent a mode in a game,
 * such as map mode where players walk around, or battle mode where players
 * fight against enemies
 * 
 * @author rex, Volodymyr
 */
package vooga.turnbased.gamecore;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;

import vooga.turnbased.gameobject.MapObject;


public abstract class GameMode extends Observable {

    private final GameManager myGameManager;

    private Map<Point, List<MapObject>> myObjects;
    
    /**
     * Constructor
     * 
     * @param gm The GameManager which receive information about how sprites
     *        interact
     */
    public GameMode (GameManager gm) {
        myGameManager = gm;
        //myGameManager.getModesSprites(MapObject.class);
        myObjects = new HashMap<Point, List<MapObject>>();
        this.addObserver(myGameManager);
    }

    /**
     * Notify GameManager about the events that happened
     * 
     * @param event Event to be handled by the GameManager
     */
    private void notifyGameManager (Object event) {
        setChanged();
        notifyObservers(event);
    }

    /**
     * Test Test Test
     * 
     * @param args
     */
    public static void main (String[] args) {
        // TODO Auto-generated method stub

    }

    protected GameManager getGM () {
        return myGameManager;
    }
    
    public Map<Point, List<MapObject>> getObjects() {
    	return myObjects;
    }
    /**
     * Each game mode should paint everything that should be currently displayed
     * @param g
     * @param canvasWidth
     * @param canvasHeight
     */
    public abstract void paint(Graphics g);
    
    public abstract void update ();

    public abstract void handleKeyPressed (KeyEvent e);

    public abstract void handleKeyReleased (KeyEvent e);
}
