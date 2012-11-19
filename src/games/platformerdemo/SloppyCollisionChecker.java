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
        this.addCollisionEvents("games.platformerdemo.Brick", "games.platformerdemo.Enemy", "games.platformerdemo.BrickEnemy");
        
        
        this.addCollisionEvents("games.platformerdemo.Brick", "games.platformerdemo.Player", "games.platformerdemo.BrickMovingObject");
        
        
        this.addCollisionEvents("games.platformerdemo.Enemy", "games.platformerdemo.Player", "games.platformerdemo.EnemyPlayer");
        
        System.out.print("done");
    }

    /*@Override
    public CollisionEvent buildCollisionEvent (GameObject a, GameObject b) {
        String className;
        String nameA;
        String nameB;
        
        if (a.getClass().getCanonicalName().compareTo(b.getClass().getCanonicalName()) < 0) {
            nameA = a.getClass().getCanonicalName();
            nameB = b.getClass().getCanonicalName();
        }
        else {
            nameA = b.getClass().getCanonicalName();
            nameB = a.getClass().getCanonicalName();
        }
        
        if (nameA == "games.platformerdemo.Brick" &&
                nameB == "games.platformerdemo.Enemy") {
            className = "games.platformerdemo.BrickEnemy";
        }
        else if (nameA == "games.platformerdemo.Brick" &&
                nameB == "games.platformerdemo.Player") {
            className = "games.platformerdemo.BrickMovingObject";
        }
        else if (nameA == "games.platformerdemo.Enemy" &&
                nameB == "games.platformerdemo.Player") {
            className = "games.platformerdemo.EnemyPlayer";
        }
        else {
            return null;
        }
        /*if (a instanceof Enemy && b instanceof Brick) {
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
        
        CollisionEvent ce = (CollisionEvent) Reflection.createInstance(className, a, b);
      
        return ce;
    }*/

}
