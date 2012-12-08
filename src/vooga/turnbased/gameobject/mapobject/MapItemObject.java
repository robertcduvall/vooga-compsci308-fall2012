package vooga.turnbased.gameobject.mapobject;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.util.List;
import java.util.Set;
import vooga.turnbased.gui.GameWindow;


/**
 * items that could be picked up on the map
 * 
 * @author rex
 * 
 */
public class MapItemObject extends MapObject {
    private static final double SIZE_RELATIVE_TO_TILE = 0.34;
    private boolean myPickupSoundSwitch;

    /**
     * Creates the MapItemObject that will be used in MapMode.
     *
     * @param allowableModes 
     * @param condition GameEvent that can be passed to GameManager.
     * @param location Location of object on the map.
     * @param mapImage Image of the object.
     */
    public MapItemObject (Set<String> allowableModes, String condition,
            Point location, Image mapImage) {
        super(allowableModes, condition, location, mapImage);
        myPickupSoundSwitch = true;

    }
    
    public MapItemObject (Set<String> allowableModes, String condition, Image mapImage, List<String> locationPoint) {
    	this(allowableModes, condition, new Point(Integer.parseInt(locationPoint.get(0)), Integer.parseInt(locationPoint.get(1))), mapImage);
    }

    /**
     * Sets action taken when a player object interacts with this item object.
     * 
     * @param target MapObject that interacts with item object.
     */
    public void interact (MapObject target) {
        super.interact(target);
        if (target instanceof MapPlayerObject) {
            if (myPickupSoundSwitch) {
                GameWindow.playSound("ItemPickupSound");
            }
            setVisible(false);
        }
    }

    @Override
    public void paint (Graphics g) {
        paintInProportion(g, getOffset(), getTileDimension(),
                SIZE_RELATIVE_TO_TILE);
    }

    public void mute () {
        myPickupSoundSwitch = false;
    }
}
