package vooga.turnbased.gameobject;

import java.awt.Image;
import java.awt.Point;

import vooga.turnbased.gamecore.GameManager;
import vooga.turnbased.gamecore.MapMode;

public class MapPlayerObject extends MovingMapObject {

    public MapPlayerObject (int id, GameManager.GameEvent event, Point coord, Image mapImage, MapMode mapMode) {
        super(id, event, coord, mapImage, mapMode);
    }

    @Override
    public void update (int delayTime) {
        super.update(delayTime);
    }
}
