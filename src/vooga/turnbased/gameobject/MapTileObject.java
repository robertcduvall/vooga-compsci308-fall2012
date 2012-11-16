package vooga.turnbased.gameobject;

import java.awt.Image;
import java.awt.Point;

import vooga.turnbased.gamecore.GameManager;
import vooga.turnbased.gamecore.MapMode;

/**
 * This class is a bit stupid
 */
public class MapTileObject extends MapObject {

    public MapTileObject(int id, GameManager.GameEvent event, Point location, Image mapImage, MapMode mapMode) {
        super(id, event, location, mapImage, mapMode);
    }
    
    @Override 
    public void update(int delayTime) {
        // tile behavior on update
    }
}
