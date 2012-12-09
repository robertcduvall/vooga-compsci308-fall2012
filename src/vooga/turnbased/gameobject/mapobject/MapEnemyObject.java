package vooga.turnbased.gameobject.mapobject;

import java.awt.Image;
import java.awt.Point;
import java.util.List;
import java.util.Set;


/**
 * A map object that triggers a battle
 * 
 * @author Turnbased team
 */
public class MapEnemyObject extends MapObject {
    /**
     * Constructor
     * 
     * @param allowableModes
     * @param condition
     * @param location
     * @param mapImage
     */
    public MapEnemyObject (Set<String> allowableModes, String condition,
            Point location, Image mapImage) {
        super(allowableModes, condition, location, mapImage);
    }

    public MapEnemyObject (Set<String> allowableModes, String condition,
            Image mapImage, List<String> locationPoint) {
        this(allowableModes, condition, new Point(
                Integer.parseInt(locationPoint.get(0)),
                Integer.parseInt(locationPoint.get(1))), mapImage);
    }

    @Override
    public void interact (MapObject m) {
        super.interact(m);
        // do battle specific stuff
    }
}
