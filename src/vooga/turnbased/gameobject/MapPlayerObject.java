package vooga.turnbased.gameobject;

import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

import vooga.turnbased.gamecore.GameManager;

public class MapPlayerObject extends MovingMapObject {

    public MapPlayerObject (int id, GameManager.GameEvent event, Point coord, Image mapImage) {
        super(id, event, coord, mapImage);
    }

    @Override
    public void update (int delayTime) {
        super.update(delayTime);
    }
}
