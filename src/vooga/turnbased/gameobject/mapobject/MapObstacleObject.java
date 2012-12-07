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
     * @param allowableModes modes that the obstacle could be in
     * @param condition Event condition
     * @param location Location of the obstacle
     * @param mapImage image of the obstacle on the map
     */
    public MapObstacleObject (Set<String> allowableModes, String condition, Point location,
                              Image mapImage) {
        super(allowableModes, condition, location, mapImage);
    }

    @Override
    public void interact (MapObject target) {
        super.interact(target);
        if (target instanceof MapMovingObject) {
            ((MapMovingObject) target).setCanMove(false);
        }
    }
}
