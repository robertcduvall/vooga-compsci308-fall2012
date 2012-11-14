package games.platformerdemo;

import vooga.platformer.collision.BasicCollisionChecker;
import vooga.platformer.collision.CollisionEvent;
import vooga.platformer.gameobject.GameObject;
import vooga.platformer.level.Level;

public class SloppyCollisionChecker extends BasicCollisionChecker {

    @Override
    public CollisionEvent buildCollisionEvent(GameObject a, GameObject b) {
        if (a instanceof Enemy && b instanceof Brick) {
            return new BrickEnemy((Enemy)a, (Brick)b);
        }
        else if (a instanceof MovingObject && b instanceof Brick) {
            return new BrickMovingObject((MovingObject)a, (Brick)b);
        }
        else {
            return null;
        }
    }

}
