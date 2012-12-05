package vooga.turnbased.gameobject.mapobject;

import java.awt.Image;
import java.awt.Point;
import java.util.Set;


/**
 * obstacles that blocks player
 * sorry I have no time to eliminate it
 * because it involves changes in PathFinder
 * 
 * @author rex
 * 
 */
public class MapObstacleObject extends MapObject {
    /**
     * Constructor
     * 
     * @param allowableModes
     * @param condition
     * @param location
     * @param mapImage
     */
    public MapObstacleObject (Set<String> allowableModes, String condition, Point location,
                              Image mapImage) {
        super(allowableModes, condition, location, mapImage);
    }

    public void interact (MapObject target) {
        super.interact(target);
        if (target instanceof MovingMapObject) {
            ((MovingMapObject) target).setCanMove(false);
        }
    }
}
