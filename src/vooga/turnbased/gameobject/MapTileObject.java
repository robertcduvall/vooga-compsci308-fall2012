package vooga.turnbased.gameobject;

import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

import vooga.turnbased.gamecore.GameManager;

/**
 * This class is a bit stupid
 */
public class MapTileObject extends MapObject {

    public MapTileObject(int id, GameManager.GameEvent event, Point location, Image mapImage) {
        super(id, event, location, mapImage);
    }
    
    @Override 
    public void update(int delayTime) {
        // tile behavior on update
    }
}
