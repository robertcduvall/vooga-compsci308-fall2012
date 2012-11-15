package vooga.turnbased.gameobject;

import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

public class MapPlayerObject extends MovingMapObject {

    public MapPlayerObject (int id, Point coord, Image mapImage) {
        super(id, coord, mapImage);
    }

    @Override
    public void handleKeyReleased (KeyEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void handleKeyPressed (KeyEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void update (int delayTime) {
        super.update(delayTime);
    }
}
