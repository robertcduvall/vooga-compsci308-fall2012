package vooga.platformer.level.condition;

import java.util.List;
import java.util.Map;
import vooga.platformer.gameobject.GameObject;
import vooga.platformer.gameobject.LevelGoalZone;
import vooga.platformer.gameobject.Player;
import vooga.platformer.util.enums.PlayState;


/**
 * A condition representing the following behavior: move to the next level
 * when the player object reaches the given rectangular zone.
 * 
 * @author Niel Lebeck
 * @author Grant Oakley
 * 
 */
public class PlayerInZoneCondition implements Condition {

    private static final long serialVersionUID = 1L;

    private String myNextLevelName;

    /**
     * Instantiating this class and adding it as a level condition will cause
     * for collisions between and Player instance and any PlayerInZone instance
     * to be checked. If such a collision occurs, the NextLevelName will be set
     * to the level specified by the collided LevelGoalZone object. Until such a
     * collision occurs, the result of getNextLevelName will be null.
     */
    public PlayerInZoneCondition () {
        /*
         * Empty constructor
         */
    }

    @Override
    public boolean isSatisfied (List<GameObject> objectList) {
        for (GameObject lg : objectList) {
            if (lg instanceof LevelGoalZone) {
                for (GameObject p : objectList) {
                    if (p instanceof Player) {
                        myNextLevelName = ((LevelGoalZone) lg).getNextLevelPath();
                        return lg.getShape().intersects(p.getShape());
                    }
                }
            }
        }
        return false;
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
