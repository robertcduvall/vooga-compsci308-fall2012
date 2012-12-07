package vooga.platformer.level.condition;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import vooga.platformer.gameobject.GameObject;
import vooga.platformer.gameobject.Player;
import vooga.platformer.util.enums.PlayState;


public class NoPlayersRemainLosingCondition implements Condition {

    private static final long serialVersionUID = 1L;
    protected static final String LEVEL_NAME_TAG = "nextlevel";
    private String myNextLevelName;

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
