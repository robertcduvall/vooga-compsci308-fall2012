package vooga.platformer.collision;

import java.awt.geom.Rectangle2D;
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
 * @author Bruce, revised by Yaqi
 * 
 * 
 */
public abstract class CollisionChecker {
    private Map<String, HashMap<String, String>> collisionEventsMap = new HashMap<String, HashMap<String, String>>();
    private GameObject myObjA;
    private GameObject myObjB;

    /**
     * This method detects collisions for all the GameObjects within the Level
     * and return
     * a list of CollisionEvents
     * 
     * @param level
     * @return
     */
<<<<<<< HEAD
    public abstract void checkCollisions (Level level);
=======
    public abstract void checkCollisions(Level level);
>>>>>>> dfa01f4364ac425da153d33ce42b92a5417d480b

    /**
     * This method takes two colliding objects and return the corresponding
     * CollsionEvent
     * object handling that specific collision.
     * 
     * @param a
     * @param b
     * @return
     */
    public CollisionEvent buildCollisionEvent (GameObject a, GameObject b) {
        String className;
        myObjA = a;
        myObjB = b;

        if (collisionEventsMap.containsKey(a.getClass().getCanonicalName()) &&
                collisionEventsMap.get(a.getClass().getCanonicalName()).containsKey(b.getClass().getCanonicalName())) {
            className = collisionEventsMap.get(a.getClass().getCanonicalName())
                    .get(b.getClass().getCanonicalName());
<<<<<<< HEAD
        }
        else {
            className = collisionEventsMap.get(b.getClass().getCanonicalName())
                    .get(a.getClass().getCanonicalName());
        }

        CollisionEvent c = null;

        try {
            c = (CollisionEvent) Class.forName(className).getConstructor()
                    .newInstance(a, b);
        }
        catch (NoSuchMethodException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (InstantiationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
=======
        } 
        else if (collisionEventsMap.containsKey(b.getClass().getCanonicalName()) && 
                collisionEventsMap.get(b.getClass().getCanonicalName()).containsKey(a.getClass().getCanonicalName())) {
            className = collisionEventsMap.get(b.getClass().getCanonicalName())
                    .get(a.getClass().getCanonicalName());
        }
        else {
            return null;
>>>>>>> dfa01f4364ac425da153d33ce42b92a5417d480b
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
<<<<<<< HEAD

    /**
     * @author Yaqi Zhang
     */
    public boolean isCollide () {
        if (myObjB.getShape().intersects(myObjA.getShape())) { return true; }
        return false;
    }
=======
   
>>>>>>> dfa01f4364ac425da153d33ce42b92a5417d480b
}
