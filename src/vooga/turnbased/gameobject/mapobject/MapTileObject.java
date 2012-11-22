package vooga.turnbased.gameobject.mapobject;

import java.awt.Image;
import java.awt.Point;
import vooga.turnbased.gamecore.MapMode;

/**
 * This class WILL no longer BE stupid, HOPEFULLY
 * 
 */

public class MapTileObject extends MapObject {

    /**
     * Creates the MapTileObject that will be used in MapMode.
     * 
     * @param id Integer ID associated with the MapTileObject.
     * @param event GameEvent that can be passed to GameManager.
     * @param location Location of object on the map.
     * @param mapImage Image of the object.
     * @param mapMode MapMode in which the object exists.
     */
    public MapTileObject (int id, String event, Point location, Image mapImage,
            MapMode mapMode) {
        super(id, event, location, mapImage, mapMode);
    }

    @Override
    public void update () {
        super.update();
    }
}
