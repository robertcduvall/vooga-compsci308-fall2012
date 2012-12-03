package vooga.platformer.gameobject;

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

    /**
     * 
     */
    public Bullet(double inX, double inY, double inWidth, double inHeight, int inId,
            File defaultImageFile, int xVelocity, int yVelocity) throws IOException {
        super(inX, inY, inWidth, inHeight, inId, defaultImageFile);
        setVelocity(xVelocity, yVelocity);
        setSize(inWidth, inHeight);
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
            setVelocity(-getVelocity().getX(), 0);
        }
        else if (direction.equals(Direction.RIGHT)) {
            setVelocity(getVelocity().getX(), 0);
        }
    }
}
