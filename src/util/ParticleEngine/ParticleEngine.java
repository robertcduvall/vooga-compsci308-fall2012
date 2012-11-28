package util.ParticleEngine;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import util.calculator.VectorCalculator;
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
    private static final double DEFAULT_ANGLESPAN = 0;
    private static final int DEFAULT_NUMBEROFDIRECTIONS = 1;

    private int spriteCount;
    private Image spriteImage;
    private Point initialPosition;
    private Point mainVelocity;
    private int variance;
    private int duration;
    private Boolean loop;
    private double angleSpan;

    private List<Particle> particles;
    private boolean ParticlesRotate = false;
    	
    private VectorCalculator vcalculator = new VectorCalculator();
    
    /**
     * Construct the ParticleEngine object using default values
     * 
     * @param particleImage the image to use as the particle
     */
    public ParticleEngine (Image particleImage, Point initialPosition, Boolean loopValue) {
        this(DEFAULT_COUNT, particleImage, initialPosition, DEFAULT_DIRECTION,
                DEFAULT_VARIANCE, DEFAULT_DURATION, DEFAULT_ANGLESPAN, DEFAULT_NUMBEROFDIRECTIONS, loopValue);
    }

    /**
     * Constructor 
     * @param particleImage	Image to be used to visualize the particles in this particle engine
     * @param initialPosition Initial position of the particles in this particle engine
     * @param angleSpan The angle through which the collection of particles are distributed; 
     * e.g. if angleSpan = 360, then the particles are constructed with varying directions 
     * so that they move (more or less) straight outwards in a full circle (sorry if this is confusing)
     * @param numberOfDirections The total number of different directions given to the collection of particles (the different
     * directions will be calculated using the given or default direction, angleSpan, and the numberOfDirections)
     */
    public ParticleEngine (Image particleImage, Point initialPosition, double inputAngleSpan, int numberOfDirections, Boolean loopValue){
    	this(DEFAULT_COUNT, particleImage, initialPosition, DEFAULT_DIRECTION,
                DEFAULT_VARIANCE, DEFAULT_DURATION, inputAngleSpan, numberOfDirections, loopValue);
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
            int tolerance, int length, double inputAngleSpan, int numberOfDirections, Boolean loopValue) {
    	spriteCount = density;
        spriteImage = particleImage;
        initialPosition = position;
        mainVelocity = velocity;
        variance = tolerance;
        duration = length;
        loop = loopValue;
        
        particles = new ArrayList<Particle>();
        
        createParticles(angleSpan, numberOfDirections);
    }
    
    private void createParticles(double inputAngleSpan, int numberOfDirections){
    	Dimension particleSize = new Dimension(spriteImage.getWidth(null),spriteImage.getHeight(null));
    	int numberOfOriginLines = Math.max(1,numberOfDirections-1);
    	double angleInterval = inputAngleSpan/ (double) numberOfOriginLines * Math.PI/180;
    	int approxNumberOfSpritesPerOriginLine = spriteCount/numberOfOriginLines+numberOfOriginLines;
    	double velocityMagnitude = vcalculator.calculateMagnitude(mainVelocity);
    	double velocityAngle = vcalculator.calculateAngle(mainVelocity);
    			
    	for (int i = 0; i < numberOfOriginLines; i++){
    		for (int j = 0; j<approxNumberOfSpritesPerOriginLine; j++){
    			particles.add(new Particle(new Point(initialPosition), particleSize, spriteImage,
    					velocityMagnitude, velocityAngle+angleInterval*i, variance, duration));
    		}
    	}
    	
/*    	for (int i = 0; i < spriteCount; i++) {
        	particles.add(new Particle(new Point(initialPosition), particleSize, spriteImage,
            mainVelocity, variance, duration));
        }*/
    }
    
    public void draw(Graphics g) {
    	for (Particle p: particles){
    		if (p.stillExists())
        		p.draw(g);
    	}
    }

    public void update() {
        ArrayList<Particle> remove = new ArrayList<Particle>();
    	for (Particle p: particles){
    		if (p.stillExists())
    			p.update();
    		else if(loop) {
    		    remove.add(p);
    		}
    	}
    	for(Particle p : remove) {
    	    particles.remove(p);
    	    createParticles(angleSpan,1);
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
    
    public void setLoop(Boolean doLoop)
    {
        loop = doLoop;
    }
    
    public void setParticleRotation(Boolean rotate){
    	ParticlesRotate = rotate;
    	for (Particle p : particles){
    		p.setRotation(ParticlesRotate);
    	}
    }
}
