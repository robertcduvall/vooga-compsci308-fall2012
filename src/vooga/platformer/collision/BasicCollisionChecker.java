package vooga.platformer.collision;

import java.util.ArrayList;
import java.util.List;
import vooga.platformer.gameobject.GameObject;
import vooga.platformer.level.Level;

/**
 * This class is used for collision detection. It iterates through all the GameObjects
 * in the game level and return a list of CollisionEvent
 * @author Bruce
 *
 */
public class BasicCollisionChecker extends CollisionChecker{

    
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
}
