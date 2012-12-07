package vooga.platformer.level.condition;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import vooga.platformer.gameobject.GameObject;
import vooga.platformer.gameobject.Player;
import vooga.platformer.util.enums.PlayState;


/**
 * Adding this Condition to the Level will cause the Level to check if any
 * Player instances remain in level. If none remain (i.e., the Player has died),
 * the user is taken to the specified level.
 * 
 * @author Grant Oakley (modified)
 * 
 */
public class NoPlayersRemainLosingCondition implements Condition {
    protected static final String LEVEL_NAME_TAG = "nextlevel";

    private static final long serialVersionUID = 1L;
    private String myNextLevelName;

    /**
     * @param nextLevelName level the user should be taken to when this
     *        condition is satisified
     */
    public NoPlayersRemainLosingCondition (String nextLevelName) {
        myNextLevelName = nextLevelName;
    }

    @Override
    public boolean isSatisfied (List<GameObject> objectList) {
        for (GameObject g : objectList) {
            if (g instanceof Player) { return false; }
        }
        return true;
    }

    @Override
    public String getNextLevelName () {
        return myNextLevelName;
    }

    @Override
    public PlayState getStatus () {
        return PlayState.GAME_OVER;
    }

    @Override
    public Map<String, String> getConfigStringParams () {
        Map<String, String> configMap = new HashMap<String, String>();
        configMap.put(LEVEL_NAME_TAG, "the next level that the player goes to upon winning");
        return configMap;
    }

}
