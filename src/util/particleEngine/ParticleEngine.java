package util.particleEngine;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import util.calculator.VectorCalculator;


// import vooga.shooter.gameObjects.Sprite;

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
    private static final int DEFAULT_VARIANCE = 15;
    // i.e. 15%
    private static final int DEFAULT_DURATION = 10000;
    private static final double DEFAULT_ANGLESPAN = 0;
    private static final int DEFAULT_NUMBEROFDIRECTIONS = 1;
/**
 * Inital position of the particle
 */
    public Point myInitialPosition;

    private int mySpriteCount;
    private Image mySpriteImage;
    private Point myMainVelocity;
    private int myVariance;
    private int myDuration;
    private Boolean myLoop;
    private double myAngleSpan;

    private List<Particle> myParticles;

    private VectorCalculator myVcalculator = new VectorCalculator();

    /**
     * Construct the ParticleEngine object using default values
     * 
     * @param particleImage the image to use as the particle
     * @param loopValue whether the "animation" should be done again
     * @param initialPosition Initial position of the particles in this particle
     *        engine
     */
    public ParticleEngine (Image particleImage, Point initialPosition, Boolean loopValue) {
        this(DEFAULT_COUNT, particleImage, initialPosition, DEFAULT_DIRECTION, DEFAULT_VARIANCE,
             DEFAULT_DURATION, DEFAULT_ANGLESPAN, DEFAULT_NUMBEROFDIRECTIONS, loopValue);
    }

    /**
     * Constructor
     * 
     * @param particleImage Image to be used to visualize the particles in this
     *        particle engine
     * @param initialPosition Initial position of the particles in this particle
     *        engine
     * @param inputAngleSpan The angle through which the collection of particles
     *        are
     *        distributed;
     *        e.g. if angleSpan = 360, then the particles are constructed with
     *        varying directions
     *        so that they move (more or less) straight outwards in a full
     *        circle (sorry if this is confusing)
     * @param numberOfDirections The total number of different directions given
     *        to the collection of particles (the different
     *        directions will be calculated using the given or default
     *        direction, angleSpan, and the numberOfDirections)
     * @param loopValue whether the "animation" should be done again
     */
    public ParticleEngine (Image particleImage, Point initialPosition, double inputAngleSpan,
                           int numberOfDirections, Boolean loopValue) {
        this(DEFAULT_COUNT, particleImage, initialPosition, DEFAULT_DIRECTION, DEFAULT_VARIANCE,
             DEFAULT_DURATION, inputAngleSpan, numberOfDirections, loopValue);
    }

    /**
     * Constructs the ParticleEngine object with custom values
     * 
     * @param position position of the particle
     * @param density defines the number of particles in the engine
     * @param particleImage the image to use for the particles
     *        travel, (0,0) will travel in all directions
     * @param tolerance (%) how much the particles can vary from the given
     *        direction
     * @param length how long the particles will exist before being reset
     * @param loopValue whether the "animation" should be done again
     * @param numberOfDirections direction particles should move in
     * @param velocity the velocity of the particle
     * @param inputAngleSpan how far away from original trajectory the particles
     *        can be
     */
    public ParticleEngine (int density, Image particleImage, Point position, Point velocity,
                           int tolerance, int length, double inputAngleSpan,
                           int numberOfDirections, Boolean loopValue) {
        mySpriteCount = density;
        mySpriteImage = particleImage;
        myInitialPosition = position;
        myMainVelocity = velocity;
        myVariance = tolerance;
        myDuration = length;
        myLoop = loopValue;

        myParticles = new ArrayList<Particle>();

        createParticles(myAngleSpan, numberOfDirections);
    }

    private void createParticles (double inputAngleSpan, int numberOfDirections) {
        int numberOfOriginLines = Math.max(1, numberOfDirections - 1);
        int approxNumberOfSpritesPerOriginLine =
                mySpriteCount / numberOfOriginLines + numberOfOriginLines;

        for (int i = 0; i < numberOfOriginLines; i++) {
            for (int j = 0; j < approxNumberOfSpritesPerOriginLine; j++) {
                createParticle(inputAngleSpan, numberOfOriginLines, i);
            }
        }
    }

    /**
     * @param inputAngleSpan
     * @param numberOfOriginLines
     * @param i
     */
    private void createParticle (double inputAngleSpan, int numberOfOriginLines, int i) {
        Dimension particleSize =
                new Dimension(mySpriteImage.getWidth(null), mySpriteImage.getHeight(null));
        double angleInterval = inputAngleSpan / (double) numberOfOriginLines * Math.PI / 180;
        double velocityMagnitude = myVcalculator.calculateMagnitude(myMainVelocity);
        double velocityAngle = myVcalculator.calculateAngle(myMainVelocity);
        myParticles.add(new Particle(new Point(myInitialPosition), particleSize, mySpriteImage,
                                     velocityMagnitude, velocityAngle + angleInterval * i,
                                     myVariance, myDuration));
    }

    /**
     * Draw method for particle engine. Draws all the particles in list
     * particles.
     * 
     * @param g graphcis for drawing
     */
    public void draw (Graphics g) {
        for (Particle p : myParticles) {
            if (p.stillExists()) {
                p.draw(g);
            }
        }
    }

    /**
     * Update method for particle engine. Also checks to see if particles need
     * to be looped again.
     */
    public void update () {
        ArrayList<Particle> remove = new ArrayList<Particle>();
        for (Particle p : myParticles) {
            if (p.stillExists()) {
                p.update();
            }
            else if (myLoop) {
                remove.add(p);
            }
        }
        for (Particle p : remove) {
            myParticles.remove(p);
            createParticle(myAngleSpan, 1, 0);
        }
    }

    /**
     * Sets how long the particle engine runs
     * 
     * @param length length of time
     */
    public void setDuration (int length) {
        myDuration = length;
    }

    /**
     * Sets density for the particle
     * 
     * @param density the amount of particles
     */
    public void setDensity (int density) {
        mySpriteCount = density;
    }

    /**
     * Sets the velocity
     * 
     * @param v point velocity for the particle
     */
    public void setVelocity (Point v) {
        myMainVelocity = v;
    }

    /**
     * Sets boolean for looping the particle engine.
     * 
     * @param doLoop information to set looping
     */
    public void setLoop (Boolean doLoop) {
        myLoop = doLoop;
    }

}
