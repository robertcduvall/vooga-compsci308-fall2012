package vooga.platformer.level.condition;

import java.awt.Rectangle;
import java.util.List;
import java.util.Map;
import vooga.platformer.gameobject.GameObject;
import vooga.platformer.gameobject.Player;
import vooga.platformer.util.enums.PlayState;


/**
 * A condition representing the following behavior: move to the next level
 * when the player object reaches the given rectangular zone.
 * 
 * @author Niel Lebeck
 * 
 */
public class PlayerInZoneCondition implements Condition {

    Player pl;
    Rectangle rect;
    String myNextLevelName;
    

    @Override
    public boolean isSatisfied (List<GameObject> objectList) {
        if (pl == null) {
            for (GameObject go : objectList) {
                if (go instanceof Player) {
                    pl = (Player) go;
                }
            }
        }
        
        if (pl.getShape().intersects(rect)) {
            return true;
        }
        
        return false;
    }
    
    /**
     * 
     * @param inLvlName
     * @param zonex x position of top left corner
     * @param zoney y position of top left corner
     * @param zonewidth zone width
     * @param zoneheight zone height
     */
    public PlayerInZoneCondition(String inLvlName, int zonex, int zoney, int zonewidth, int zoneheight) {
        rect = new Rectangle(zonex, zoney, zonewidth, zoneheight);
        myNextLevelName = inLvlName;
    }

    @Override
    public String getNextLevelName () {
        return myNextLevelName;
    }

    @Override
    public PlayState getStatus () {
        return PlayState.NEXT_LEVEL;
    }

    @Override
    public Map<String, String> getConfigStringParams () {
        return null;
    }

}
