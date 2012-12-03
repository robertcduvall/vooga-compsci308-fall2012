package vooga.turnbased.gameobject.mapstrategy;


import vooga.turnbased.gameobject.mapobject.MapObject;


/**
 * Null pattern
 * 
 * @author rex
 * 
 */
public class NullStrategy extends MapStrategy {

    /**
     * constructor
     */
    public NullStrategy () {
        super();
    }

    @Override
    public void performStrategy (MapObject m) {
        // no strategy performed
    }
}
