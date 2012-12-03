package vooga.turnbased.gameobject.mapobject;

import java.awt.Image;
import java.awt.Point;
import java.util.Set;

import vooga.turnbased.gamecore.gamemodes.MapMode;
import vooga.turnbased.gameobject.mapstrategy.BlockStrategy;


/**
 * obstacles that blocks player
 * sorry I have no time to eliminate it
 * because it involves changes in PathFinder
 * 
 * @author rex
 * 
 */
public class MapObstacleObject extends MapObject {

    public MapObstacleObject (Set<String> allowableModes, String condition, Point location, Image mapImage) {
        super(allowableModes, condition, location, mapImage);
        addStrategy(new BlockStrategy());
    }
}
