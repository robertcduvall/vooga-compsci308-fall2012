package vooga.turnbased.gameobject.mapstrategy;

import java.util.ArrayList;
import java.util.List;
import vooga.turnbased.gamecore.MapMode;
import vooga.turnbased.gameobject.mapobject.MapObject;
import vooga.turnbased.gameobject.mapobject.MapPlayerObject;
import vooga.turnbased.gameobject.mapobject.MovingMapObject;

public class ConversationStrategy extends MapStrategy {

    private static final String MODE_EVENT = "CONVERSATION_START";
    
    public ConversationStrategy (MapMode mapMode) {
        super(mapMode);
        // TODO Auto-generated constructor stub
    }
     
    @Override
    public void performStrategy (MapObject target) {
        super.performStrategy(target);
        //does nothing if it is not a player
        if (target instanceof MapPlayerObject) {

        }
    }

}