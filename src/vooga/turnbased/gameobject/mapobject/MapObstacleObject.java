package vooga.turnbased.gameobject.mapobject;

import java.awt.Image;
import java.awt.Point;

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

    public MapObstacleObject (String condition, Point location, Image mapImage) {
        super(condition, location, mapImage);
        addStrategy(new BlockStrategy());
    }
}
