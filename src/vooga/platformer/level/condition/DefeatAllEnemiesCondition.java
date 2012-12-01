package vooga.platformer.level.condition;

import java.util.HashMap;
import java.util.Map;
import vooga.platformer.gameobject.GameObject;
import vooga.platformer.util.ConfigStringParser;
import vooga.platformer.util.enums.PlayState;

/**
 * A win condition that moves to the next level if all enemies are defeated. This
 * code will be broken until someone creates an Enemy class in the GameObject package.
 * @author Niel Lebeck
 *
 */
public class DefeatAllEnemiesCondition implements Condition {
    
    protected static final String LEVEL_NAME_TAG = "nextlevel";
    
    private String myNextLevelName;
    
    /**
     * 
     */
    public DefeatAllEnemiesCondition() {
        
    }
    
    /**
     * 
     * @param configString config string
     */
    public DefeatAllEnemiesCondition(String configString) {
        Map<String, String> configStringMap = ConfigStringParser.parseConfigString(configString);
        myNextLevelName = configStringMap.get(LEVEL_NAME_TAG);
    }
    
    @Override
    public boolean isSatisfied (Iterable<GameObject> objectList) {
        int numEnemies = 0;
        for (GameObject go : objectList) {
            //if (go instanceof Enemy) {
            //    numEnemies++;
            //}
        }
        return numEnemies <= 0;
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
        return configMap;
    }

}
