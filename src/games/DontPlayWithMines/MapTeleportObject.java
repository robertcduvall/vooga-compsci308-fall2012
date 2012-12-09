
package games.DontPlayWithMines;

import java.awt.Image;
import java.awt.Point;
import java.util.List;
import java.util.Set;

import vooga.turnbased.gameobject.mapobject.MapMovingObject;
import vooga.turnbased.gameobject.mapobject.MapObject;
import vooga.turnbased.gui.GameWindow;


/**
 * Teleport a MapMovingObject to new location and mode
 * 
 * @author David Howdyshell
 * 
 */
public class MapTeleportObject extends MapObject {
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
    public MapTeleportObject (Set<String> allowableModes, String condition, Point location,
                              Image mapImage, Point destLocation, String destMode) {
        super(allowableModes, condition, location, mapImage);
        myDestLocation = destLocation;
        myDestMode = destMode;
    }
    
    /**
     * 
     * @param allowableModes
     * @param condition
     * @param image
     * @param paramStrings - locationX, locationY, destLocationX, destLocationY, destMode
     */
    public MapTeleportObject(Set<String> allowableModes, String condition, Image image, List<String> paramStrings) {
    	super(allowableModes, condition, new Point(Integer.parseInt(paramStrings.get(0)),Integer.parseInt(paramStrings.get(1))), image);
    	myDestLocation = new Point(Integer.parseInt(paramStrings.get(2)),Integer.parseInt(paramStrings.get(3)));
    	myDestMode = paramStrings.get(4);
    }

    @Override
    public void interact (MapObject target) {
        super.interact(target);
        GameWindow.playSound("TeleportSound");
        target.setAllowableModes(myDestMode);
        target.setLocation(myDestLocation);
        ((MapMovingObject) target).setCanMove(false);
    }
}
