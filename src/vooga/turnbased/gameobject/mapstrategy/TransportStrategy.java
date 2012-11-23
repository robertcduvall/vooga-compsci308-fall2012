package vooga.turnbased.gameobject.mapstrategy;

import java.awt.Point;
import vooga.turnbased.gamecore.MapMode;
import vooga.turnbased.gameobject.mapobject.MapObject;

/**
 * @author rex
 */
public class TransportStrategy extends MapStrategy{
    
    private String myMapModeResource;

    public TransportStrategy(MapMode mapMode, String mapModeResource, Point location) {
        super(mapMode);
        myMapModeResource = mapModeResource;
    }
    
    @Override
    public void performStrategy(MapObject s) {
        changeGameLevel();
    }
    
    private void changeGameLevel() {
        getMapMode().getGameManager().initializeGameLevel(myMapModeResource);
    }
}
