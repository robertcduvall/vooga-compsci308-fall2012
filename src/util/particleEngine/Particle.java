package util.particleEngine;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.util.Random;
import util.calculator.VectorCalculator;


/**
 * You should not be using this class unless you are either working on the
 * particle engine or are extending the particle engine.
 * 
 * A particle object is essentially a sprite that also maintains its velocity,
 * direction, and a count of how many cycles it has existed, which it will use
 * to draw the sprite with changing alpha levels so that it will fade away over
 * time
 * 
 * @author David Spruill, Kathleen Lan
 * 
 */
public class Particle {
    private static final int ONEHUNDRED = 100;
    private static final double RADIANSPERCIRCLE = 2 * Math.PI;
    
    /**
     * Position of the particle.
     */
    public Point myPosition;
    /**
     * Velocity of the particle.
     */
    public Point myVelocity;
    /**
     * The maximum distance traveled per update.
     */
    public double myMaxDistanceTraveledPerUpdate;

    private int myDurationLimit;
    private int myDurationExisted;
    private int myVariance;
    // radians
    private float myRotation; 
    // radians/frame
    private float myRotationalVelocity;
    private double myAngle;
    private Random myRandomGenerator;
    private VectorCalculator myVcalculator = new VectorCalculator();
    // These values were found after extensive testing,
    // and scale the sprite's red, green, blue, and alpha values
    private float[] myRGBAscales = {3f, 1.8f, 2.4f, 0.2f };
    private float[] myOffsets;
    private BufferedImage myBufferedImage;


    /**
     * Creates a particle to use in the particle effect implemented by
     * the graphics package. The image to be drawn for this particular
     * particle is written to the buffer of the BufferedImage that will
     * be used to apply alpha filters to the entire image.
     * 
     * @param position the center position of the image
     * @param size the size of the image to use
     * @param image the image to use
     * @param velocity the velocity of the particle
     * @variance how far from original position particle can be
     * @duration how long particle lasts
     */
    public Particle (Point position, Dimension size, Image image, Point velocity, int variance,
                     int duration) {
        declareVariables(position, size, image, variance, duration);
        myVelocity = velocity;
        setupRadianMode();
    }

    /**
     * Another version of the constructor that also allows for initial angles to
     * be set for the particle.
     * 
     * @param position the center position of the image
     * @param size the size of the image to use
     * @param image the image to use
     * @param velocityMagnitude the maximum distance to be traveled per update
     * @param velocityAngle the angle at which this velocity is to be applied
     * @param variance the amount which the angle can vary
     * @param duration the number of cycles this particle will exist before
     *        becoming invisible
     */
    public Particle (Point position, Dimension size, Image image, Double velocityMagnitude,
                     Double velocityAngle, int variance, int duration) {
        declareVariables(position, size, image, variance, duration);
        myAngle = velocityAngle;
        myMaxDistanceTraveledPerUpdate = velocityMagnitude;
    }

    /**
     * Initialize the variables which will define this particle's lifetime
     * 
     * @param position the initial position
     * @param size the size of the particle
     * @param image the image to use
     * @param variance the amount which the angle can vary
     * @param duration the number of cycles this particle will exist before
     *        becoming invisible
     */
    private void declareVariables (Point position, Dimension size, Image image, int variance,
                                   int duration) {
        myPosition = position;
        myVariance = variance;
        myDurationExisted = 0;

        myRandomGenerator = new Random();
        myOffsets = new float[4];

        myDurationLimit = (int) (myRandomGenerator.nextDouble() * duration);
        myRotation = (float) (myRandomGenerator.nextFloat() * RADIANSPERCIRCLE);
        myRotationalVelocity = (float) (myRandomGenerator.nextFloat() * 0.5);
        myBufferedImage = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = (Graphics2D) myBufferedImage.createGraphics();
        g2d.setBackground(new Color(0, 0, 0, 0));
        g2d.drawImage(image, 0, 0, size.width, size.height, null);
    }

    /**
     * Stores the angle and magnitude of the velocity vector.
     */
    private void setupRadianMode () {
        myAngle = myVcalculator.calculateAngle(myVelocity);
        myMaxDistanceTraveledPerUpdate = Math.max(1, myVcalculator.calculateMagnitude(myVelocity));
    }

    /**
     * Draws the particle
     * 
     * @param g the graphics entity to draw to
     */
    public void draw (Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        RescaleOp rop = new RescaleOp(myRGBAscales, myOffsets, null);
        g2d.rotate(myRotation, myPosition.x + myBufferedImage.getWidth() / 2,
                   myPosition.y + myBufferedImage.getHeight() / 2);
        g2d.drawImage(myBufferedImage, rop, myPosition.x, myPosition.y);
        g2d.rotate(-myRotation, myPosition.x + myBufferedImage.getWidth() / 2,
                   myPosition.y + myBufferedImage.getHeight() / 2);
    }

    /**
     * update the particle's position, rotation, velocity, and transparency
     */
    public void update () {
        double r = myRandomGenerator.nextInt(2 * myVariance + 1);
        double angleVariation = (r - myVariance) / ONEHUNDRED;

        double tempNewAngle = myAngle + RADIANSPERCIRCLE * angleVariation;
        int newX = (int) (Math.cos(tempNewAngle) * myMaxDistanceTraveledPerUpdate);
        int newY = (int) (Math.sin(tempNewAngle) * myMaxDistanceTraveledPerUpdate);
        myPosition.x += newX;
        myPosition.y -= newY;
        myDurationExisted++;

        myRotation += myRotationalVelocity;

        // this is the alpha scale
        myRGBAscales[3] = (float) (myDurationLimit - myDurationExisted) / (float) myDurationLimit;
    }

    /**
     * @return if the particle still exists
     */
    public boolean stillExists () {
        return myDurationExisted < myDurationLimit * 0.8f;
    }
}
