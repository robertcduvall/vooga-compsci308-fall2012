package games.platformerdemo;

<<<<<<< HEAD
import java.awt.Graphics;
import java.awt.Image;
import util.camera.Camera;
import vooga.platformer.gameobject.GameObject;


public class Player extends GameObject {

    public Player(String configString) {
        super(configString);
    }

    @Override
    public void paint(Graphics pen, Camera cam) {

    }

    @Override
    public Image getCurrentImage() {
        // TODO Auto-generated method stub
        return null;
    }

=======
/**
 * @author Yaqi Zhang
 *
 */
public class Player extends MovingObject {
    /**
     * @param configString
     */
    public Player (String configString) {
        super(configString, "player");
        addStrategy(new PlayerMoveStrategy(this));
        addStrategy(new GravityStrategy(this));
    }
>>>>>>> f5741ba1b396d7e73c1993f7f217c5c9362159a8
}
