package vooga.turnbased.gameobject;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;

import vooga.turnbased.gamecore.GameManager;
import vooga.turnbased.gamecore.MapMode;

/**
 * items that could be picked up on the map
 * @author rex
 *
 */
public class MapItemObject extends MapObject{

	public MapItemObject(int id, GameManager.GameEvent event, Point location, Image mapImage, MapMode mapMode) {
		super(id, event, location, mapImage, mapMode);
		
	}

	public void interact(MapObject target) {
		if (target instanceof MapPlayerObject) {
			setVisible(false);
		}
	}
	
	@Override
	public void paint(Graphics g) {
		int x = (myTileDimensions.width - getImage().getWidth(null)) / 2;
		int y = (myTileDimensions.height - getImage().getHeight(null)) / 2;
		g.drawImage(getImage(), myOffset.x + x, myOffset.y + y, getImage().getWidth(null),
				getImage().getHeight(null), null);
	}
}
