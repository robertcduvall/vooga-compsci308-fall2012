package vooga.platformer.collision;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import util.reflection.Reflection;
import vooga.platformer.gameobject.GameObject;
import vooga.platformer.level.Level;


/**
 * CollisionChecker is used to scan the screen and return a list of
 * CollisionEvents
 * for each collision detected on the screen
 * 
 * @author Bruce
 * 
 */
public abstract class CollisionChecker {
    private Map<String, HashMap<String, String>> collisionEventsMap = new HashMap<String, HashMap<String, String>>();

    /**
     * This method detects collisions for all the GameObjects within the Level
     * and return
     * a list of CollisionEvents
     * 
     * @param level
     * @return
     */
    public abstract void checkCollisions(Level level);

    /**
     * This method takes two colliding objects and return the corresponding
     * CollsionEvent
     * object handling that specific collision.
     * 
     * @param a
     * @param b
     * @return
     */
    public CollisionEvent buildCollisionEvent(GameObject a, GameObject b) {
        String className;

        if (collisionEventsMap.containsKey(a.getClass().getCanonicalName()) &&
                collisionEventsMap.get(a.getClass().getCanonicalName()).containsKey(b.getClass().getCanonicalName())) {
            className = collisionEventsMap.get(a.getClass().getCanonicalName())
                    .get(b.getClass().getCanonicalName());
        } 
        else if (collisionEventsMap.containsKey(b.getClass().getCanonicalName()) && 
                collisionEventsMap.get(b.getClass().getCanonicalName()).containsKey(a.getClass().getCanonicalName())) {
            className = collisionEventsMap.get(b.getClass().getCanonicalName())
                    .get(a.getClass().getCanonicalName());
        }
        else {
            return null;
        }
        
        
        if (className != null) {
            CollisionEvent ce = (CollisionEvent) Reflection.createInstance(className, a, b);
            return ce;
        }
        else {
            return null;
        }
    }
    
    public void addCollisionEvents(String a, String b, String ab) {
        if (!collisionEventsMap.containsKey(a)) {
            collisionEventsMap.put(a, new HashMap<String, String>());
        }
        collisionEventsMap.get(a).put(b, ab);
    }
   
}