package vooga.turnbased.gameobject.mapstrategy;

import vooga.turnbased.gamecore.MapMode;
import vooga.turnbased.gameobject.mapobject.MapObject;
import vooga.turnbased.gameobject.mapobject.MovingMapObject;

/**
 * a strategy to block moving map objects
 * @author Rex
 *
 */
public class BlockStrategy extends MapStrategy {

    public BlockStrategy (MapMode mapMode) {
        super(mapMode);
    }

    @Override
    /**
     * This Strategy is only active when the target is a MovingMapObject
     * perform "block" strategy to stop the target's movement to its tile
     */
    public void performStrategy (MapObject target) {
        super.performStrategy(target);
        if (target instanceof MovingMapObject) {
            ((MovingMapObject) target).setCanMove(false);
        }
    }
}
