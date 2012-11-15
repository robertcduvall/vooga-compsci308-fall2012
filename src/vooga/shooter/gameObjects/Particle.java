package vooga.shooter.gameObjects;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;


/**
 * You should not be using this class unless you are either working on the
 * particle engine or are extending the particle engine.
 * 
 * A particle object is essentially a sprite that also maintains its velocity,
 * direction, and a count of how many cycles it has existed, which it will use
 * to draw the sprite with changing alpha levels so that it will fade away over
 * time
 * 
 * @author David Spruill
 * 
 */
public class Particle {
    private int durationLimit;
    private int durationExisted;
    private Point myVelocity;
    private Point myPosition;
    private float[] scales = { 1f, 1f, 1f, 0.1f }; // before somebody comments,
                                                   // I know that these are
                                                   // magic numbers, they're
                                                   // just for testing purposes
    private float[] offsets;
    private BufferedImage myImage;

    /**
     * Creates a particle to use in the particle effect implemented by
     * the graphics package. The image to be drawn for this particular
     * particle is written to the buffer of the bufferedimage that will
     * be used to apply alpha filters to the entire image.
     * 
     * @param position the center position of the image
     * @param size the size of the image to use
     * @param image the image to use
     * @param velocity the velocity of the particle
     */
    public Particle (Point position, Dimension size, Image image,
            Point velocity, int duration) {
        myVelocity = velocity;
        myPosition = position;

        offsets = new float[4];
        durationLimit = duration;
        durationExisted = 0;

        myImage = new BufferedImage(size.width, size.height,
                BufferedImage.TYPE_INT_ARGB);
        myImage.createGraphics().drawImage(image, myPosition.x, myPosition.y,
                size.width, size.height, null);
    }

    /**
     * Draws the particle
     * 
     * @param g
     */
    public void draw (Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        RescaleOp rop = new RescaleOp(scales, offsets, null);

        g2d.drawImage(myImage, rop, myPosition.x, myPosition.y);
    }

    public void update () {
        myPosition.x += myVelocity.x;
        myPosition.y += myVelocity.y;
        durationExisted++;

        // this is the alpha scale
        scales[3] = (durationLimit - durationExisted) / durationLimit;
    }

    /**
     * Tells if the particle still exists
     * 
     * @return if the particle still exists
     */
    public boolean stillExists () {
        return (durationExisted < durationLimit);
    }
}
