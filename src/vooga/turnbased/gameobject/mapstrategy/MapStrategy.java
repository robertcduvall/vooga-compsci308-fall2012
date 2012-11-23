package vooga.turnbased.gameobject.mapstrategy;

import vooga.turnbased.gamecore.GameManager;
import vooga.turnbased.gamecore.MapMode;
import vooga.turnbased.gameobject.mapobject.MapObject;

/**
 * abstract strategy for MapObjects
 * @author rex
 *
 */
public abstract class MapStrategy {

    private MapMode myMapMode;
    private boolean myIsActive;
    
    /**
     * constructor
     * @param mapMode MapMode instance in which sprites info is stored
     */
    public MapStrategy(MapMode mapMode) {
        myMapMode = mapMode;
        myIsActive = true;
    }
    
    /**
     * perform strategy!
     */
    public void performStrategy (MapObject m) {
        if (!myIsActive) {
            return;
        }
    }
    
    public MapMode getMapMode() {
        return myMapMode;
    }
    
    public GameManager getGameManager() {
        return myMapMode.getGameManager();
    }
}
