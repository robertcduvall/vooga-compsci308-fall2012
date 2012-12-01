package games.platformerdemo.collisionevent;

import games.platformerdemo.Enemy;
import games.platformerdemo.Player;
import vooga.platformer.collision.CollisionEvent;
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
        super (b,a);
        myPlayer = b;
        myEnemy = a;
    }

    @Override
    public void applyCollision (Level level) {
        if (this.direction() == Direction.DOWN) {
            myPlayer.markForRemoval();
        }
        else if (this.direction() == Direction.UP) {
            myEnemy.markForRemoval();
        }
        if (this.direction() == Direction.RIGHT) {
            myPlayer.markForRemoval();
        }
        else if (this.direction() == Direction.LEFT) {
            myPlayer.markForRemoval();
        }
    }

}
