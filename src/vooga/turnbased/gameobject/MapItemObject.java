package vooga.turnbased.gameobject;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import vooga.turnbased.gamecore.GameManager;
import vooga.turnbased.gamecore.MapMode;


/**
 * items that could be picked up on the map
 * 
 * @author rex
 * 
 */
public class MapItemObject extends MapObject {

    /**
     * Creates the MapItemObject that will be used in MapMode.
     * @param id Integer ID associated with the MapItemObject.
     * @param event GameEvent that can be passed to GameManager.
     * @param location Location of object on the map.
     * @param mapImage Image of the object.
     * @param mapMode MapMode in which the object exists.
     */
    public MapItemObject (int id, String event, Point location, Image mapImage,
            MapMode mapMode) {
        super(id, event, location, mapImage, mapMode);

    }

    /**
     * Sets action taken when a player object interacts with this item object.
     * @param target MapObject that interacts with item object.
     */
    public void interact (MapObject target) {
        if (target instanceof MapPlayerObject) {
            setVisible(false);
        }
    }

    @Override
    public void paint (Graphics g) {
        int x = (myTileDimensions.width - getImage().getWidth(null)) / 2;
        int y = (myTileDimensions.height - getImage().getHeight(null)) / 2;
        g.drawImage(getImage(), myOffset.x + x, myOffset.y + y, getImage().getWidth(null),
                getImage().getHeight(null), null);
    }
}
