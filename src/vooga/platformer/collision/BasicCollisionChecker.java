package vooga.platformer.collision;

import vooga.platformer.gameobject.GameObject;
import vooga.platformer.level.Level;


/**
 * This class is used for collision detection. It iterates through all the
 * GameObjects
 * in the game level and return a list of CollisionEvent
 * 
 * @author Bruce, revised by Yaqi
 * 
 */
public class BasicCollisionChecker extends CollisionChecker {

    public BasicCollisionChecker (String fileName) {
        super(fileName);
    }

    @Override
    public void checkCollisions (Level level) {
        for (int i = 0; i < level.getObjectList().size(); i++) {
            GameObject a = level.getObjectList().get(i);
            for (int j = i + 1; j < level.getObjectList().size(); j++) {
                GameObject b = level.getObjectList().get(j);
                if (a.getShape().intersects(b.getShape())) {
                    CollisionEvent ce = getCollisionEvent(a, b);
                    if (ce != null) {
                        ce.apply(level, a, b);
                    }
                }
            }
        }
    }
}
