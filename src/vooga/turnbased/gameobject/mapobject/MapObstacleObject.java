package vooga.turnbased.gameobject.mapobject;

import java.awt.Image;
import java.awt.Point;

import vooga.turnbased.gamecore.MapMode;

/**
 * obstacles that blocks player
 * @author rex
 *
 */
public class MapObstacleObject extends MapObject{

	public MapObstacleObject (int id, String event, Point location, Image mapImage,
            MapMode mapMode) {
        super(id, event, location, mapImage, mapMode);
    }

	@Override
    public void interact (MapObject m) {
    	if (m instanceof MovingMapObject) {
    		((MovingMapObject) m).setCanMove(false);
    	}
    }
}
