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
        this.addCollisionEvents("vooga.platformer.gameobject.Brick", "games.platformerdemo.Enemy", "games.platformerdemo.BrickEnemy");
        this.addCollisionEvents("vooga.platformer.gameobject.Brick", "games.platformerdemo.Player", "games.platformerdemo.BrickMovingObject");   
        this.addCollisionEvents("games.platformerdemo.Enemy", "games.platformerdemo.Player", "games.platformerdemo.EnemyPlayer");

    }
}
