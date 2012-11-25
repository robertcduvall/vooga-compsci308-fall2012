package util.ParticleEngine;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import vooga.shooter.gameObjects.Sprite;

/**
 * This class enables the game creators to create, draw, and update a particle
 * engine that maintains many different sprites and randomly animates them so
 * that the creators can use more realistic animations for explosions or
 * rockets, etc.
 * 
 * To use this you simply need to create an object of this type and have it
 * update and draw.
 * 
 * @author David Spruill, modest contributions from Kathleen Lan
 */
public class ParticleEngine {
    private static final int DEFAULT_COUNT = 1;
    private static final Point DEFAULT_DIRECTION = new Point(0, 10);
    private static final int DEFAULT_VARIANCE = 15; //i.e. 15%
    private static final int DEFAULT_DURATION = 10000;

    private int spriteCount;
    private Image spriteImage;
    private Point initialPosition;
    private Point mainVelocity;
    private int variance;
    private int duration;

    private List<Particle> particles;

    /**
     * Construct the ParticleEngine object using default values
     * 
     * @param particleImage the image to use as the particle
     */
    public ParticleEngine (Image particleImage, Point initialPosition) {
        this(DEFAULT_COUNT, particleImage, initialPosition, DEFAULT_DIRECTION,
                DEFAULT_VARIANCE, DEFAULT_DURATION);
    }

    /**
     * Constructs the ParticleEngine object with custom values
     * 
     * @param density defines the number of particles in the engine
     * @param particleImage the image to use for the particles
     * @param direction the general direction in which the particles will
     *        travel, (0,0) will travel in all directions
     * @param tolerance (%) how much the particles can vary from the given direction
     * @param length how long the particles will exist before being reset
     */
    public ParticleEngine(int density, Image particleImage, Point position, Point velocity,
            int tolerance, int length) {
        spriteCount = density;
        spriteImage = particleImage;
        initialPosition = position;
        mainVelocity = velocity;
        variance = tolerance;
        duration = length;
        
        particles = new ArrayList<Particle>();
        createParticles();
    }

    private void createParticles(){
    	Dimension particleSize = new Dimension(spriteImage.getWidth(null),spriteImage.getHeight(null));
    	for (int i = 0; i < spriteCount; i++) {
        	particles.add(new Particle(new Point(initialPosition), particleSize, spriteImage,
            mainVelocity, variance, duration));
        }
    }
    
    public void draw(Graphics g) {
    	for (Particle p: particles){
    		if (p.stillExists())
        		p.draw(g);
    	}
    }

    public void update() {
    	for (Particle p: particles){
    		if (p.stillExists())
    			p.update();
    	}
    }

    public void setDuration(int length) {
        duration = length;
    }

    public void setDensity(int density) {
        spriteCount = density;
    }

    public void setVelocity(Point v) {
        mainVelocity = v;
    }
    
}
