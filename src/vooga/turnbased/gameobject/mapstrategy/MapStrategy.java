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
    private boolean myIsDisplayable;
    private String myDisplayMessage;
    
    /**
     * constructor
     * @param mapMode MapMode instance in which sprites info is stored
     */
    public MapStrategy(MapMode mapMode) {
        myMapMode = mapMode;
        myIsActive = true;
        myIsDisplayable = false;
        myDisplayMessage = null;
    }
    
    /**
     * perform strategy!
     */
    public void performStrategy (MapObject target) {
        if (!myIsActive) {
            return;
        }
    }
    
    public boolean isDisplayable() {
        return myIsDisplayable;
    }
    
    protected void setDisplayable(boolean isDisplayable) {
        myIsDisplayable = isDisplayable;
    }
    
    public String getDisplayMessage() {
        return myDisplayMessage;
    }
    
    protected void setDisplayMessage(String message) {
        myDisplayMessage = message;
    }
    
    public MapMode getMapMode() {
        return myMapMode;
    }
    
    public GameManager getGameManager() {
        return myMapMode.getGameManager();
    }
}
