package games.platformerdemo;

import vooga.platformer.gameobject.MovingObject;
import vooga.platformer.util.enums.Direction;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;


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
