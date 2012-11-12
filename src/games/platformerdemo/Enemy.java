package games.platformerdemo;

<<<<<<< HEAD
import vooga.platformer.gameobject.GameObject;


public class Enemy extends GameObject {

=======
public class Enemy extends MovingObject {
    /**
     * @param configString
     */
    public Enemy (String configString) {
        super(configString, "enemy");
        addStrategy(new SimpleMoveStrategy(this));
        addStrategy(new GravityStrategy(this));
    }
>>>>>>> f5741ba1b396d7e73c1993f7f217c5c9362159a8
}
