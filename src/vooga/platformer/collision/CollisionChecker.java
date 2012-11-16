package vooga.platformer.collision;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
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

        if (a.getClass().getCanonicalName()
                .compareTo(b.getClass().getCanonicalName()) < 0) {
            className = collisionEventsMap.get(a.getClass().getCanonicalName())
                    .get(b.getClass().getCanonicalName());
        } else {
            className = collisionEventsMap.get(b.getClass().getCanonicalName())
                    .get(a.getClass().getCanonicalName());
        }

        CollisionEvent c = null;

        try {
            c = (CollisionEvent) Class.forName(className).getConstructor()
                    .newInstance(a, b);
        } catch (NoSuchMethodException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InstantiationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return c;
    }
    
}
