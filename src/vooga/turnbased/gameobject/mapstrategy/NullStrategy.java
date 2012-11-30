package vooga.turnbased.gameobject.mapstrategy;

import vooga.turnbased.gamecore.gamemodes.MapMode;
import vooga.turnbased.gameobject.mapobject.MapObject;


/**
 * Null pattern
 * 
 * @author rex
 * 
 */
public class NullStrategy extends MapStrategy {

    public NullStrategy (MapMode mapMode) {
        super(mapMode);
    }

    @Override
    public void performStrategy (MapObject m) {
        // no strategy performed
    }
}
