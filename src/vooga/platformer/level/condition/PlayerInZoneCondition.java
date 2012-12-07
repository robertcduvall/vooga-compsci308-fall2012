package vooga.platformer.level.condition;

import java.awt.Rectangle;
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

    private Player pl;
    private Rectangle rect;
    private String myNextLevelName;

    public PlayerInZoneCondition () {
        /*
         * Empty constructor
         */
    }

    /**
     * 
     * @param inLvlName
     * @param zonex x position of top left corner
     * @param zoney y position of top left corner
     * @param zonewidth zone width
     * @param zoneheight zone height
     * 
     * @deprecated Use default constructor in conjunction with a LevelGoalZone
     *             GameObject
     */
    public PlayerInZoneCondition (String inLvlName, int zonex, int zoney, int zonewidth,
                                  int zoneheight) {
        rect = new Rectangle(zonex, zoney, zonewidth, zoneheight);
        myNextLevelName = inLvlName;
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
