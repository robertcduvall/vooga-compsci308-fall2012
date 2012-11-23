package vooga.turnbased.gameobject.mapstrategy;

import java.awt.Point;
import vooga.turnbased.gamecore.MapMode;
import vooga.turnbased.gameobject.mapobject.MapObject;

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
    
    @Override
    public void performStrategy(MapObject s) {
        changeGameLevel();
    }
    
    private void changeGameLevel() {
        getMapMode().getGameManager().initializeGameLevel(myMapModeResource);
    }
}
