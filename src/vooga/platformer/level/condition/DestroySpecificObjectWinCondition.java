package vooga.platformer.level.condition;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import util.configstring.ConfigStringParser;
import vooga.platformer.gameobject.GameObject;
import vooga.platformer.util.GameObjectFinder;
import vooga.platformer.util.enums.PlayState;

/**
 * A condition where the user wins the game when a specific GameObject (specified
 * by ID) no longer exists.
 * @author Niel Lebeck
 *
 */
public class DestroySpecificObjectWinCondition implements Condition {

    protected static final String LEVEL_NAME_TAG = "nextlevel";
    protected static final String OBJECT_TAG = "object";
    
    private String myNextLevelName;
    private int myObjectId;
    
    /**
     * 
     */
    public DestroySpecificObjectWinCondition() {
        
    }

    /**
     * 
     * @param configString config string
     */
    public DestroySpecificObjectWinCondition(String configString) {
        Map<String, String> configStringMap = ConfigStringParser.parseConfigString(configString);
        myNextLevelName = configStringMap.get(LEVEL_NAME_TAG);
        myObjectId = Integer.parseInt(configStringMap.get(OBJECT_TAG));
    }
    
    @Override
    public boolean isSatisfied (List<GameObject> objectList) {
        GameObject go = GameObjectFinder.findGameObject(objectList, myObjectId);
        return go == null;
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
        Map<String, String> configMap = new HashMap<String, String>();
        configMap.put(LEVEL_NAME_TAG, "the next level that the player goes to upon winning");
        configMap.put(OBJECT_TAG, "the object that must disappear for the player to win");
        return configMap;
    }

}
