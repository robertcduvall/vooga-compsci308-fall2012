package vooga.shooter.gameObjects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

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
public class MethodMapper {
    Map<String, List<MethodWrapper>> myMap;

    /**
     * Constructs a new method mapping (one for each sprite).
     * Begins with an empty map. 
     */
    public MethodMapper(){
        myMap = new HashMap<String,List<MethodWrapper>>();
    }

    /**
     * Adds to the map a string and all its associated actions.
     *
     * @param key the string (the key)
     * @param m the list of actions to which the string is
     * mapped
     */
    public void addPair(String key, MethodWrapper...m){
        List<MethodWrapper> mw = new ArrayList<MethodWrapper>();
        for(MethodWrapper me : m){
            mw.add(me);
        }
        myMap.put(key, mw);
    }

    /**
     * Iterate through all actions associated with this
     * string.
     *
     * @param key the string that describes which actions to do
     * @param any damage done to the sprite doing the action
     * @param s any sprite that this sprite collides with
     */
    public void doEvent(String key, int damage, Sprite s)
    {
        for(MethodWrapper m : myMap.get(key)){
            m.doAction(damage, s);
        }
    }
}