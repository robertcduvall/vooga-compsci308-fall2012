package vooga.platformer.level.condition;

import java.util.List;
import java.util.Map;
import vooga.platformer.gameobject.GameObject;
import vooga.platformer.util.enums.PlayState;

/**
 * Represents a win or loss condition imposed upon the list of GameObjects in the Level.
 * @author Niel Lebeck
 *
 */
public interface Condition {
    
    /**
     * @param objectList The list of GameObjects in the level
     * @return true if the condition is satisfied and false if the condition is not satisfied
     */
    boolean isSatisfied(List<GameObject> objectList);
    
    /**
     * @return The next level to load, if any, when this condition is satisfied. If no subsequent
     * level will be loaded (for instance, if this condition is a loss condition), then the return
     * value does not matter
     */
    String getNextLevelName();
    
    /**
     * @return a PlayState representing the level's status when this condition is satisfied:
     * NEXT_LEVEL if it's a win condition or GAME_OVER if it's a loss condition
     */
    PlayState getStatus();
    
    /**
     * Return a map with information about the param tags (keys) and descriptions of the
     * params (values), just like GameObject.
     * @return the map
     */
    Map<String, String> getConfigStringParams();
}
