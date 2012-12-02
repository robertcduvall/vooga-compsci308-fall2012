package util.particleEngine;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
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

    private List<Particle> particles;

    /**
     * Construct the ParticleEngine object using default values
     * 
     * @param particleImage the image to use as the particle
     */
    protected ParticleEngine (Image particleImage,
            MathVector2D initialPosition, MathVector2D inputDirection,
            Boolean loopValue) {
        this(DEFAULT_COUNT, particleImage, initialPosition, inputDirection,
                DEFAULT_VARIANCE, DEFAULT_DURATION, DEFAULT_ANGLESPAN,
                DEFAULT_NUMBEROFDIRECTIONS, loopValue);
    }

    /**
     * Constructor
     * 
     * @param particleImage Image to be used to visualize the particles in this
     *        particle engine
     * @param initialPosition Initial position of the particles in this particle
     *        engine
     * @param angleSpan The angle through which the collection of particles are
     *        distributed;
     *        e.g. if angleSpan = 360, then the particles are constructed with
     *        varying directions
     *        so that they move (more or less) straight outwards in a full
     *        circle (sorry if this is confusing)
     * @param numberOfDirections The total number of different directions given
     *        to the collection of particles (the different
     *        directions will be calculated using the given or default
     *        direction, angleSpan, and the numberOfDirections)
     */
    protected ParticleEngine (Image particleImage,
            MathVector2D initialPosition, MathVector2D inputDirection, double inputAngleSpan,
            int numberOfDirections, Boolean loopValue) {
        this(DEFAULT_COUNT, particleImage, initialPosition, inputDirection,
                DEFAULT_VARIANCE, DEFAULT_DURATION, inputAngleSpan,
                numberOfDirections, loopValue);
    }

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
            Boolean loopValue) {
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

    private void createParticles (double inputAngleSpan, int numberOfDirections) {
        int numberOfOriginLines = Math.max(1, numberOfDirections - 1);
        int approxNumberOfSpritesPerOriginLine = spriteCount
                / numberOfOriginLines + numberOfOriginLines;

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
    private void createParticle (double inputAngleSpan,
            int numberOfOriginLines, int i) {
        Dimension particleSize = new Dimension(spriteImage.getWidth(null),
                spriteImage.getHeight(null));
        double angleInterval = inputAngleSpan / (double) numberOfOriginLines
                * Math.PI / 180;
        double velocityMagnitude = mainVelocity.calculateMagnitude();
        double velocityAngle = mainVelocity.calculateAngleInRadians();

        MathVector2D startingPosition = new MathVector2D();
        startingPosition.setComponent(MathVector2D.X, initialPosition.getComponent(MathVector2D.X));
        startingPosition.setComponent(MathVector2D.Y, initialPosition.getComponent(MathVector2D.Y));

        particles.add(new Particle(startingPosition, particleSize, spriteImage,
                velocityMagnitude, velocityAngle + angleInterval * i, variance,
                duration));
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
            particles.remove(p);
            if (loop) createParticle(angleSpan, 1, 0);
        }
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

    protected MathVector2D getStartingPosition () {
        return initialPosition;
    }

}
