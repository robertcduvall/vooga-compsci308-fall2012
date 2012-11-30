package games.platformerdemo;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import vooga.platformer.gameobject.MovingObject;
import vooga.platformer.gameobject.strategy.SimpleMoveStrategy;
import vooga.platformer.util.enums.Direction;


/**
 * Bullet can be shot by either a player or an enemy. It kills all alive
 * objects.
 * 
 * @author Yaqi Zhang
 * 
 */
public class Bullet extends MovingObject {
    private static final int VELOCITY = 3;
    private static final int SIZE = 8;

    /**
     * 
     */
    public Bullet () {
        super();
        setVelocity(VELOCITY, 0);
        setSize(SIZE, SIZE);
        String defaultImageName = "src/games/platformerdemo/bullet.png";
        try {
            Image img = ImageIO.read(new File(defaultImageName));
            super.setImage(img);
        }
        catch (IOException e) {
            System.out.println("could not load image " + defaultImageName);
            System.exit(0);
        }
        addStrategy(new SimpleMoveStrategy(this));
    }

    /**
     * @param direction of initial velocity
     */
    public void setDirection (Direction direction) {
        if (direction.equals(Direction.LEFT)) {
            setVelocity(-VELOCITY, 0);
        }
        else {
            setVelocity(VELOCITY, 0);
        }
    }
}
