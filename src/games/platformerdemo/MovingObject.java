package games.platformerdemo;

import java.awt.Image;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import vooga.platformer.gameobject.GameObject;


/**
 * @author Yaqi Zhang
 *
 */
public class MovingObject extends GameObject {

    private Image myImg;
    private Point2D myVelocity;
    private boolean onGround = false;
    /**
     * @param configString String to parse parameters of this player
     * @param type of this object starting with lower case, eg. player, brick.
     */
    public MovingObject (String configString, String type) {
        super(configString);
        myVelocity = new Point2D.Double(0, 0);
        try {
            myImg = ImageIO.read(new File("src/games/platformerdemo/" + type
                    + ".png"));
        }
        catch (IOException e) {
            System.out.println("no file find");
            e.printStackTrace();
        }
    }
    /**
     * @return the velocity of the player
     */
    public Point2D getVelocity () {
        return myVelocity;
    }

    /**
     * @param x horizontal velocity
     * @param y vertical velocity
     */
    public void setVelocity (double x, double y) {
        myVelocity = new Point2D.Double(x, y);
    }
    @Override
    public Image getCurrentImage () {
        return myImg;
    }
    
    /**
     * set status of this moving object to be on the ground
     */
    public void setOnGround() {
        onGround = true;
    }
    
    /**
     * set status of this moving object to be not on the ground
     */
    public void setNotOnGround() {
        onGround = false;
    }
    
    /**
     * @return whether this MovingObject is on a brick
     */
    public boolean isOnGround() {
        return onGround;
    }
}
