package vooga.turnbased.gameobject.mapstrategy;

import java.awt.Point;
import vooga.turnbased.gamecore.MapMode;
import vooga.turnbased.gameobject.mapobject.MapObject;
import vooga.turnbased.sprites.Sprite;

/**
 * @author rex
 */
public class TransportStrategy extends MapStrategy{
    
    private String myMapModeResource;
    private Point myNewPlayerLocation;

    public TransportStrategy(MapMode mapMode, String mapModeResource, Point location) {
        super(mapMode);
        myMapModeResource = mapModeResource;
        myNewPlayerLocation = location;
    }
    
    /**
     * perform the strategy
     * on an object that should be ported to another location
     * @param s The MapObject on which the strategy is applied 
     */
    @Override
    public void performStrategy(MapObject s) {
        Sprite movingSprite = getGameManager().findSpriteWithID(s.getID());
        getGameManager().initializeGameLevel(myMapModeResource);
    }
}
