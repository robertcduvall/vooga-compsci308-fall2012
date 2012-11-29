package games.platformerdemo;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import vooga.platformer.gameobject.MovingObject;


/**
 * Bullet can be shot by either a player or an enemy. It kills all alive
 * objects.
 * 
 * @author Yaqi Zhang
 * 
 */
public class Bullet extends MovingObject {
    private static final int VELOCITY = 10;

    public Bullet (String configString) {
        super(configString);
        setVelocity(VELOCITY, 0);
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

    public void setDirection (String direction) {
        if (direction == "left") {
            setVelocity(-VELOCITY,0);
        }else{
            setVelocity(VELOCITY, 0);
        }
    }
}
