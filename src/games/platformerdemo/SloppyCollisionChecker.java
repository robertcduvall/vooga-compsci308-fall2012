package games.platformerdemo;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import vooga.platformer.collision.BasicCollisionChecker;
import vooga.platformer.collision.CollisionEvent;
import vooga.platformer.gameobject.GameObject;
import util.reflection.*;


/**
 * @author Niel
 * @author Yaqi
 * @author Bruce
 */
public class SloppyCollisionChecker extends BasicCollisionChecker {
    
    public SloppyCollisionChecker() {
        this.addCollisionEvents("vooga.platformer.gameobject.Brick", "vooga.platformer.gameobject.Enemy", "games.platformerdemo.collisionevent.BrickBounceBack");
        this.addCollisionEvents("vooga.platformer.gameobject.Brick", "games.platformerdemo.Bullet", "games.platformerdemo.collisionevent.BrickBounceBack");
        this.addCollisionEvents("vooga.platformer.gameobject.Brick", "vooga.platformer.gameobject.Player", "games.platformerdemo.collisionevent.BrickMovingObject");   
        this.addCollisionEvents("vooga.platformer.gameobject.Enemy", "vooga.platformer.gameobject.Player", "games.platformerdemo.collisionevent.EnemyPlayer");
        this.addCollisionEvents("vooga.platformer.gameobject.Enemy", "games.platformerdemo.Bullet", "vooga.platformer.gameobject.strategy.BulletMovingObject");
    }
}
