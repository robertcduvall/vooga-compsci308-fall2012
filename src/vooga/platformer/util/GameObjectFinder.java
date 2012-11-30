package vooga.platformer.util;
import java.util.List;
import vooga.platformer.gameobject.GameObject;

/**
 * A utility class that searches for GameObjects by ID in a list, potentially taking
 * advantage of the fact that the list is sorted.
 * @author Niel
 *
 */
public final class GameObjectFinder {
    
    private GameObjectFinder() {
        
    }
    
    /**
     * Slow linear search for unsorted lists.
     * @param objCollection unsorted list
     * @param id ID
     * @return the GameObject, or null if there isn't one with that ID
     */
    public static GameObject findGameObject(Iterable<GameObject> objCollection, int id) {
        for (GameObject go : objCollection) {
            if (go.getId() == id) { return go; }
        }
        return null;
    }
}
