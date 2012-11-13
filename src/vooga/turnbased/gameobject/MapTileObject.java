package vooga.turnbased.gameobject;

import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

public class MapTileObject extends MapObject {

    public MapTileObject(int id, Point location, Image mapImage, Rectangle camera) {
        super(id, location, mapImage, camera);
    }
    
    @Override 
    public void update() {
        // tile behaviour on update
    }
    
    @Override
    public void paint() {
        // tile painting
    }
    
    @Override
    public void handleKeyPressed(KeyEvent e) {
        // tile behaviour on keyPress
    }
    
    @Override
    public void handleKeyReleased(KeyEvent e) {
        // tile behaviour on keyReleased
    }

}
