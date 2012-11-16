package vooga.shooter.gameObjects.spriteUtilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import vooga.shooter.gameObjects.Sprite;

/**
 * This class provides a mapping of strings to
 * one or more methods. Each sprite will have an instance
 * of this mapping in order to determine which actions
 * to do based on different inputs. Each sprite will make a map
 * of strings to associated actions, e.g. "goleft" to the action
 * that makes the sprite go left on the screen. The strings that are
 * sent to the sprites will be provided by the Game class.
 *
 * @author Jesse Starr
 *
 */
public class SpriteMethodMap {
    private Map<String, List<SpriteActionInterface>> myMap;

    /**
     * Constructs a new method mapping (one for each sprite).
     * Begins with an empty map.
     */
    public SpriteMethodMap() {
        myMap = new HashMap<String, List<SpriteActionInterface>>();
    }

    /**
     * Adds to the map a string and all its associated actions.
     *
     * @param key the string (the key)
     * @param m the list of actions to which the string is
     * mapped
     */
    public void addPair(String key, SpriteActionInterface...m) {
        List<SpriteActionInterface> methodList =
            new ArrayList<SpriteActionInterface>();
        for (SpriteActionInterface eachm : m) {
            methodList.add(eachm);
        }
        myMap.put(key, methodList);
    }

    /**
     * Iterate through all actions associated with this
     * string.
     *
     * @param key the string that describes which actions to do
     * @param s any sprite that this sprite collides with
     */
    public void doEvent(String key, Sprite s) {
        if (myMap.containsKey(key)) {
            for (SpriteActionInterface m : myMap.get(key)) {
                m.doAction(s);
            }
        }
    }
}
