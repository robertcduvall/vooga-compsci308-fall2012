package vooga.turnbased.gameobject;

import java.awt.Point;

import javax.swing.ImageIcon;

public class MovingMapObject extends MapObject {
	
	static private boolean smoothMovingOn;
	
    public MovingMapObject (int id, Point coord) {
        super(id, coord);
        smoothMovingOn = true;
    }
}
