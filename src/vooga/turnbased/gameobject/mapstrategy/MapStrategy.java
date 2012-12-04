package vooga.turnbased.gameobject.mapstrategy;


import vooga.turnbased.gameobject.mapobject.MapObject;

/**
 * abstract strategy for MapObjects
 * @author rex
 *
 */
public abstract class MapStrategy {

    private boolean myIsActive;
    private boolean myIsDisplayable;
    private String myDisplayMessage;

    /**
     * constructor
     */
    public MapStrategy() {
        myIsActive = true;
        myIsDisplayable = false;
        myDisplayMessage = null;
    }

    /**
     * perform strategy!
     * @param target The MapObject which is the target of this strategy.
     */
    public void performStrategy (MapObject target) {
        if (!myIsActive) {
            return;
        }
    }
    /**
     * 
     * @return Returns true if this strategy is displayable
     */
    public boolean isDisplayable() {
        return myIsDisplayable;
    }
    /**
     * Lets you set whether this strategy is displayable.
     * @param isDisplayable Pass true if you want this strategy to be displayable
     */
    protected void setDisplayable(boolean isDisplayable) {
        myIsDisplayable = isDisplayable;
    }
    /**
     * Retrieves the display message of this strategy
     * @return String of the message
     */
    public String getDisplayMessage() {
        return myDisplayMessage;
    }

    protected void setDisplayMessage(String message) {
        myDisplayMessage = message;
    }

//    public MapMode getMapMode() {
//        return myMapMode;
//    }
// 
//    public GameManager getGameManager() {
//        return myMapMode.getGameManager();
//    }
}
