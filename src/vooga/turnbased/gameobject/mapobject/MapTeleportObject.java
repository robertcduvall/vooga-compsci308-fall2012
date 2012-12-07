package vooga.turnbased.gameobject.mapobject;

import java.awt.Image;
import java.awt.Point;
import java.util.Set;

public class MapTeleportObject extends MapObject {
    Point myDestination;

    public MapTeleportObject (Set<String> allowableModes, String condition, Point location,
                              Image mapImage, Point destination) {
        super(allowableModes, condition, location, mapImage);
        myDestination = destination;
    }

    /**
     * Sets action taken when a player object interacts with this item object.
     * 
     * @param target MapObject that interacts with item object.
     */
    @Override
    public void interact (MapObject target) {
        super.interact(target);
        target.setLocation(myDestination);
    }
}
