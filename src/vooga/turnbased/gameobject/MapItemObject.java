package vooga.turnbased.gameobject;

import java.awt.Image;
import java.awt.Point;
import java.awt.event.KeyEvent;

public class MapItemObject extends MapObject{

	public MapItemObject(int id, Point location, Image mapImage) {
		super(id, location, mapImage);
		
	}

	@Override
	public void handleKeyReleased(KeyEvent e) {
	}

	@Override
	public void handleKeyPressed(KeyEvent e) {
	}

	@Override
	public void update(int delayTime) {
	}
	
	public void interact(MapObject target) {
		if (target instanceof MapPlayerObject) {
			setVisible(false);
		}
	}
}
