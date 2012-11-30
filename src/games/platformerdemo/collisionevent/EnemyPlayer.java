package games.platformerdemo.collisionevent;

import vooga.platformer.collision.CollisionEvent;
import vooga.platformer.gameobject.Enemy;
import vooga.platformer.gameobject.GameObject;
import vooga.platformer.gameobject.Player;
import vooga.platformer.level.Level;
import vooga.platformer.util.enums.Direction;

public class EnemyPlayer extends CollisionEvent{
    private Player myPlayer;
    private Enemy myEnemy;

    public EnemyPlayer (Enemy a, Player b) {
        super(a, b);
        myPlayer = (Player) this.b();
        myEnemy = (Enemy) this.a();
    }
    
    public EnemyPlayer (Player a, Enemy b) {
        this(b,a);
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
