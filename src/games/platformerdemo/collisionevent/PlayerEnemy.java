package games.platformerdemo.collisionevent;

import vooga.platformer.collision.CollisionEvent;
import vooga.platformer.gameobject.Enemy;
import vooga.platformer.gameobject.Player;
import vooga.platformer.level.Level;
import vooga.platformer.util.enums.Direction;


/**
 * @author Yaqi Zhang
 * 
 */
public class PlayerEnemy extends CollisionEvent {

    public Player myPlayer;
    public Enemy myEnemy;

    public PlayerEnemy (Player a, Enemy b) {
        super(a, b);
        myPlayer = a;
        myEnemy = b;
    }

    public PlayerEnemy (Enemy a, Player b) {
        super(b, a);
        myPlayer = b;
        myEnemy = a;
    }

    @Override
    public void applyCollision (Level level) {
        if (this.direction() == Direction.DOWN) {
            myEnemy.markForRemoval();
        }
        else {
            myPlayer.markForRemoval();
        }
    }

}
