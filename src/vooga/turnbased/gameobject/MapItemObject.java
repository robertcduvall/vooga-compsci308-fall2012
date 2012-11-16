package vooga.turnbased.gameobject;

import java.awt.Image;
import java.awt.Point;

import vooga.turnbased.gamecore.GameManager;
import vooga.turnbased.gamecore.MapMode;

public class MapItemObject extends MapObject{

	public MapItemObject(int id, GameManager.GameEvent event, Point location, Image mapImage, MapMode mapMode) {
		super(id, event, location, mapImage, mapMode);
		
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
