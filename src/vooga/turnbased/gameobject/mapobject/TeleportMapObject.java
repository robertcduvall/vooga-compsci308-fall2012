package vooga.turnbased.gameobject.mapobject;

import java.awt.Image;
import java.awt.Point;
import java.util.Set;

public class TeleportMapObject extends MapObject {
	private Point myDestLocation;
	private String myDestMode;

	public TeleportMapObject(Set<String> allowableModes, String condition,
			Point location, Image mapImage, Point destLocation, String destMode) {
		super(allowableModes, condition, location, mapImage);
		myDestLocation = destLocation;
		myDestMode = destMode;
	}

	@Override
	public void interact(MapObject m) {
		super.interact(m);
		m.setAllowableModes(myDestMode);
		m.setLocation(myDestLocation);
		m.setCanMove(false);
	}
}
