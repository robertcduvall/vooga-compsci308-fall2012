package vooga.platformer.collision;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import vooga.platformer.gameobject.GameObject;
import vooga.platformer.level.Level;

public class BasicCollisionChecker implements CollisionChecker{

    Map<String, HashMap<String, String>> collisionEventsMap = new HashMap<String, HashMap<String, String>>();
    List<CollisionEvent> collisionEvents = new ArrayList<CollisionEvent>();
    
    @Override
    public Iterable<CollisionEvent> checkCollisions (Level level) {
        for (int i = 0; i < level.getObjectList().size(); i++) {
            GameObject a = level.getObjectList().get(i);
            for (int j = i; j < level.getObjectList().size(); j++) {
                GameObject b = level.getObjectList().get(j);
                
                if (a.getShape().intersects(b.getShape())) {
                    collisionEvents.add(buildCollisionEvent(a, b));
                }
            }
        }
        return collisionEvents;
    }
    
    public CollisionEvent buildCollisionEvent(GameObject a, GameObject b) {
        String className;
             
        if (a.getClass().getCanonicalName().compareTo(b.getClass().getCanonicalName()) == 1) {
            className = collisionEventsMap.get(a.getClass().getCanonicalName()).get(b.getClass().getCanonicalName());
        }
        else {
            className = collisionEventsMap.get(b.getClass().getCanonicalName()).get(a.getClass().getCanonicalName());
        }
        
        CollisionEvent c = null;
        Constructor collisionEventConstructor = null;
        
        try {
            collisionEventConstructor = Class.forName(className).getConstructor();
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
        
    
          
        try {
            c = (CollisionEvent) collisionEventConstructor.newInstance();
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
        }
            
        return c;
    }

}
