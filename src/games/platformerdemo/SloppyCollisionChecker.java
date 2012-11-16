package games.platformerdemo;

import vooga.platformer.collision.BasicCollisionChecker;
import vooga.platformer.collision.CollisionEvent;
import vooga.platformer.gameobject.GameObject;


/**
 * @author Niel
 * @author Yaqi
 */
public class SloppyCollisionChecker extends BasicCollisionChecker {

    @Override
    public CollisionEvent buildCollisionEvent (GameObject a, GameObject b) {
        if (a instanceof Enemy && b instanceof Brick) {
            return new BrickEnemy((Enemy) a, (Brick) b);
        }
        else if (a instanceof MovingObject && b instanceof Brick) {
            return new BrickMovingObject((MovingObject) a, (Brick) b);
        }
        else if (a instanceof Player && b instanceof Enemy){
            return new PlayerEnemy((Player) a, (Enemy) b);
        }
        else if(a instanceof Enemy && b instanceof Player){
            return new PlayerEnemy((Enemy) a, (Player) b);
        }
        else {
            return null;
        }
    }

}
