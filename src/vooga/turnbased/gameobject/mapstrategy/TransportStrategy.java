package vooga.turnbased.gameobject.mapstrategy;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import vooga.turnbased.gamecore.MapMode;
import vooga.turnbased.gameobject.mapobject.MapObject;
import vooga.turnbased.gameobject.mapobject.MapPlayerObject;
import vooga.turnbased.gameobject.mapobject.MovingMapObject;


/**
 * @author rex
 */
public class TransportStrategy extends MapStrategy {

    private static final String MODE_EVENT = "SWITCH_LEVEL";
    private String myMapModeResource;
    private Point myNewPlayerLocation;

    public TransportStrategy (MapMode mapMode, String mapModeResource, Point location) {
        super(mapMode);
        myMapModeResource = mapModeResource;
        myNewPlayerLocation = location;
    }

    /**
     * perform the strategy
     * on an object that should be ported to another location
     * 
     * @param s The MapObject on which the strategy is applied
     */
    @Override
    public void performStrategy (MapObject mapObject) {
        ((MovingMapObject) mapObject).finishMovement();
        ((MovingMapObject) mapObject).setLocation(myNewPlayerLocation);
        getGameManager().setNewMapResources(myMapModeResource);
        List<Integer> involvedSpriteIDs = new ArrayList<Integer>();
        involvedSpriteIDs.add(mapObject.getID());
        getMapMode().flagEvent(MODE_EVENT, involvedSpriteIDs);
    }
}
