package vooga.turnbased.gameobject;

import java.awt.Image;
import java.awt.Point;
import vooga.turnbased.gamecore.GameManager;
import vooga.turnbased.gamecore.MapMode;


/**
 * This class is a bit stupid
 * 
 * I would agree
 * 
 * @author Come forward if this is yours
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
    public MapTileObject (int id, GameManager.GameEvent event, Point location, Image mapImage,
            MapMode mapMode) {
        super(id, event, location, mapImage, mapMode);
    }

    @Override
    public void update (int delayTime) {
        super.update(delayTime);
    }
}
