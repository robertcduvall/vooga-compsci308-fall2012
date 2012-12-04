package vooga.turnbased.gameobject.mapobject;

import java.awt.Image;
import java.awt.Point;
import java.util.Set;


/**
 * Teleport map object that automatically teleports a moving map object to other
 * locations
 * 
 * @author rex
 * 
 */
public class TeleportMapObject extends MapObject {
    private Point myDestLocation;
    private String myDestMode;

    /**
     * constructor
     * 
     * @param allowableModes modes this object could potentially be in.
     * @param condition The event that will be triggered upon interaction.
     * @param location location of the MapObject on the map.
     * @param mapImage Image of the object represented on the map.
     * @param destLocation Teleport destination.
     * @param destMode The MapMode where objects will be teleported to (do not
     *        have to be the same map).
     */
    public TeleportMapObject (Set<String> allowableModes, String condition, Point location,
                              Image mapImage, Point destLocation, String destMode) {
        super(allowableModes, condition, location, mapImage);
        myDestLocation = destLocation;
        myDestMode = destMode;
    }

    @Override
    public void interact (MapObject target) {
        super.interact(target);
        target.setAllowableModes(myDestMode);
        target.setLocation(myDestLocation);
        // By definition, the map object must be a moving map object in order to
        // be teleported
        ((MovingMapObject) target).setCanMove(false);
    }
}
