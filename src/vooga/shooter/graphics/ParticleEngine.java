package vooga.shooter.graphics;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;

/**
 * This class enables the game creators to create, draw, and update a particle
 * engine that maintains many different sprites and randomly animates them so
 * that the creators can use more realistic animations for explosions or
 * rockets, etc.
 * 
 * To use this you simply need to create an object of this type and have it
 * update and draw.
 * 
 * @author David Spruill
 */
public class ParticleEngine {
    private static final int DEFAULT_COUNT = 100;
    private static final Point DEFAULT_DIRECTION = new Point(0, 0);
    private static final int DEFAULT_VARIANCE = 100;
    private static final int DEFAULT_DURATION = 100;

    private int spriteCount;
    private Image spriteImage;
    private Point mainDirection;
    private int variance;
    private int duration;

    private List<Sprite> particles;

    /**
     * Construct the ParticleEngine object using default values
     * 
     * @param particleImage the image to use as the particle
     */
    public ParticleEngine(Image particleImage) {
        ParticleEngine(DEFAULT_COUNT, particleImage, DEFAULT_DIRECTION,
                DEFAULT_VARIANCE, DEFAULT_DURATION);
    }

    /**
     * Constructs the ParticleEngine object with custom values
     * 
     * @param density defines the number of particles in the engine
     * @param particleImage the image to use for the particles
     * @param direction the general direction in which the particles will
     *        travel, (0,0) will travel in all directions
     * @param tolerance how much the particles can vary from the given direction
     * @param length how long the particles will exist before being reset
     */
    public ParticleEngine(int density, Image particleImage, Point direction,
            int tolerance, int length) {
        spriteCount = density;
        spriteImage = particleImage;
        mainDirection = direction;
        variance = tolerance;
        duration = length;

        for (int i = 0; i < spriteCount; i++) {
        }
    }

    public void draw(Graphics g) {

    }

    public void update() {

    }

    public void setDuration(int length) {
        duration = length;
    }

    public void setDensity(int density) {
        spriteCount = density;
    }

    public void setDirection(Point direction) {
        mainDirection = direction;
    }

    public void setLength(int length) {
        duration = length;
    }
}
