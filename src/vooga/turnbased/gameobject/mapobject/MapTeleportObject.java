package vooga.turnbased.gameobject.mapobject;

import java.awt.Image;
import java.awt.Point;
import java.util.List;
import java.util.Set;
import vooga.turnbased.gui.GameWindow;

public class MapTeleportObject extends MapObject {
    // Point myDestination; // would need to complicate xml for this

    public MapTeleportObject (Set<String> allowableModes, String condition, Point location,
                              Image mapImage) {
        super(allowableModes, condition, location, mapImage);
        // myDestination = destination;
    }
    
    public MapTeleportObject (Set<String> allowableModes, String condition, Image mapImage, List<String> locationPoint) {
    	this(allowableModes, condition, new Point(Integer.parseInt(locationPoint.get(0)), Integer.parseInt(locationPoint.get(1))), mapImage);
    }

    /**
     * Sets action taken when a player object interacts with this item object.
     * 
     * @param target MapObject that interacts with item object.
     */
    @Override
    public void interact (MapObject target) {
        super.interact(target);
        // target.setLocation(myDestination); 
        GameWindow.playSound("TeleportSound");
    }
}
