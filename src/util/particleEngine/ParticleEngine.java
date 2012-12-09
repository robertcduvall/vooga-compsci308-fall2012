package util.particleEngine;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import util.mathvector.*;


/**
 * This class enables the game creators to create, draw, and update a particle
 * engine that maintains many different sprites and randomly animates them so
 * that the creators can use more realistic animations for explosions or
 * rockets, etc.
 * 
 * To use this you simply need to create an object of this type and have it
 * update and draw.
 * 
 * @author David Spruill, Kathleen Lan
 */
public class ParticleEngine {
    private static final int DEFAULT_COUNT = 1;
    private static final int DEFAULT_VARIANCE = 15; // i.e. 15%
    private static final int DEFAULT_DURATION = 10000;
    private static final double DEFAULT_ANGLESPAN = 0;
    private static final int DEFAULT_NUMBEROFDIRECTIONS = 1;

    private int spriteCount;
    private Image spriteImage;
    private MathVector2D initialPosition;
    private MathVector2D mainVelocity;
    private int variance;
    private int duration;
    private Boolean loop;
    private double angleSpan;
    
    private Dimension particleSize;
    
    private float[] myRGBAscales;
    private float[] myRGBAtolerances;

    private List<Particle> particles;

    /**
     * Constructs the ParticleEngine object with custom values
     * 
     * @param density defines the number of particles in the engine
     * @param particleImage the image to use for the particles
     * @param direction the general direction in which the particles will
     *        travel, (0,0) will travel in all directions
     * @param tolerance (%) how much the particles can vary from the given
     *        direction
     * @param length how long the particles will exist before being reset
     */
    protected ParticleEngine (int density, Image particleImage,
            MathVector2D position, MathVector2D velocity, int tolerance,
            int length, double inputAngleSpan, int numberOfDirections,
            float[] RGBAscales, float[] RGBAtolerances, Boolean loopValue) {
        myRGBAscales = RGBAscales;
        myRGBAtolerances = RGBAtolerances;
        spriteCount = density;
        spriteImage = particleImage;
        initialPosition = position;
        mainVelocity = velocity;
        variance = tolerance;
        duration = length;
        loop = loopValue;
        particleSize = new Dimension(spriteImage.getWidth(null),
                spriteImage.getHeight(null));
        
        particles = new ArrayList<Particle>();

        createParticles(inputAngleSpan, numberOfDirections);
    }

    protected void createParticles (double inputAngleSpan, int numberOfDirections) {
        int numberOfOriginLines = Math.max(1, numberOfDirections - 1);
        int approxNumberOfSpritesPerOriginLine = Math.max(1, spriteCount
                / numberOfOriginLines);
        double angleInterval = inputAngleSpan / (double) numberOfOriginLines;
        MathVector2D nextVelocity;
        for (int i = 0; i < numberOfOriginLines; i++) {
            for (int j = 0; j < approxNumberOfSpritesPerOriginLine; j++) {
            	nextVelocity = new MathVector2D(mainVelocity);
        		nextVelocity.rotate(i*angleInterval);
            	createParticle(nextVelocity);
            }
        }
    }

	private void createParticle(MathVector2D velocity) {
		Particle temp = new Particle(new MathVector2D(initialPosition), 
				particleSize, spriteImage, velocity, variance, duration, 
				myRGBAscales, myRGBAtolerances);
		particles.add(temp);
		
	}

    protected void draw (Graphics g) {
        for (Particle p : particles) {
            if (p.stillExists()) p.draw(g);
        }
    }

    protected void update () {
        ArrayList<Particle> remove = new ArrayList<Particle>();
        for (Particle p : particles) {
            if (!p.stillExists()) {
                remove.add(p);
            }
            else {
                p.update();
            }
        }
        for (Particle p : remove) {
            if (loop) createParticle(new MathVector2D(p.getMyVelocity()));
            particles.remove(p);
        }
    }
    
    protected void addParticle (Particle p){
    	particles.add(p);
    }
    
    protected void setDuration (int length) {
        duration = length;
    }

    protected void setDensity (int density) {
        spriteCount = density;
    }

    protected void setVelocity (MathVector2D v) {
        mainVelocity = v;
    }

    protected void setLoop (Boolean doLoop) {
        loop = doLoop;
    }

    protected Boolean stillExists () {
        return (particles.size() > 0);
    }

    protected void setStartingPosition (MathVector2D position) {
        initialPosition = position;
    }

    protected void moveStartingPositionByVector (MathVector2D movementVector){
    	initialPosition.addVector(movementVector);
    }
    
    protected MathVector2D getMainVelocity(){
    	return mainVelocity;
    }
    
    protected Image getImage(){
    	return spriteImage;
    }
    
    protected MathVector2D getInitialPosition () {
        return initialPosition;
    }

    protected int getSpriteCount () {
        return particles.size();
    }
    
    protected int getVariance () {
    	return variance;
    }
    
    protected int getDuration () {
    	return duration;
    }

    protected float[] getRGBAscales(){
    	return myRGBAscales;
    }
    
    protected float[] getRGBAtolerances(){
    	return myRGBAtolerances;
    }
}
