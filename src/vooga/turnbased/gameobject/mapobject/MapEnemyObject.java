package vooga.turnbased.gameobject.mapobject;

import java.awt.Image;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * A map object that triggers a battle
 * @author Turnbased team
 */
public class MapEnemyObject extends MapObject {
    /**
     * Constructor
     * @param allowableModes 
     * @param condition 
     * @param location 
     * @param mapImage 
     */
    public MapEnemyObject (Set<String> allowableModes, String condition, 
            Point location, Image mapImage) {
        super(allowableModes, condition, location, mapImage);
    }

    @Override
    public void interact (MapObject m) {
        super.interact(m);
        // do battle specific stuff
    }
}
