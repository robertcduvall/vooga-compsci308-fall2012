package util.particleEngine;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.util.Random;
import util.mathvector.*;


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
    private int durationLimit;
    private int durationExisted;
    public MathVector2D myPosition;
    public MathVector2D myVelocity;
    private int myVariance;
    private float myRotation; // radians
    private float myRotationalVelocity; // radians/frame
    private double myAngle;
    public double maxDistanceTraveledPerUpdate;

    private Random myRandomGenerator;

    // These values were found after extensive testing,
    // and scale the sprite's red, green, blue, and alpha values
    private float[] myRGBAscales;
    private float[] myRGBAtolerances;

    private float[] offsets;
    private BufferedImage myBufferedImage;

    private static final int oneHundred = 100;
    private static final double radiansPerCircle = 2 * Math.PI;

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
     * @param RGBAtolerance
     * @param myRGBAscales
     */
    protected Particle (MathVector2D position, Dimension size, Image image,
            Double velocityMagnitude, Double velocityAngle, int variance,
            int duration, float[] RGBAscales, float[] RGBAtolerances) {
        myRGBAscales = RGBAscales.clone();
        myRGBAtolerances = RGBAtolerances;
        declareVariables(position, size, image, variance, duration);
        myAngle = velocityAngle;
        maxDistanceTraveledPerUpdate = velocityMagnitude;
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
    private void declareVariables (MathVector2D position, Dimension size,
            Image image, int variance, int duration) {
        myPosition = position;
        myVariance = variance;
        durationExisted = 0;

        myRandomGenerator = new Random();
        offsets = new float[4];

        durationLimit = (int) (myRandomGenerator.nextDouble() * duration);
        myRotation = (float) (myRandomGenerator.nextFloat() * radiansPerCircle);
        myRotationalVelocity = (float) (myRandomGenerator.nextFloat() * 0.5);
        myBufferedImage = new BufferedImage(size.width, size.height,
                BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = (Graphics2D) myBufferedImage.createGraphics();
        g2d.setBackground(new Color(0, 0, 0, 0));
        g2d.drawImage(image, 0, 0, size.width, size.height, null);
        setRGBscales();
    }

    private void setRGBscales () {
        for(int i=0; i<myRGBAscales.length;i++)
        {
            myRGBAscales[i]+=(myRandomGenerator.nextFloat()*2-1)*myRGBAtolerances[i];
        }
        
    }

    /**
     * Draws the particle
     * 
     * @param g the graphics entity to draw to
     */
    protected void draw (Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        RescaleOp rop = new RescaleOp(myRGBAscales, offsets, null);
        g2d.rotate(
                myRotation,
                myPosition.getComponent(MathVector2D.X)
                        + myBufferedImage.getWidth() / 2,
                myPosition.getComponent(MathVector2D.Y)
                        + myBufferedImage.getHeight() / 2);
        g2d.drawImage(myBufferedImage, rop,
                (int) myPosition.getComponent(MathVector2D.X),
                (int) myPosition.getComponent(MathVector2D.Y));
        g2d.rotate(
                -myRotation,
                myPosition.getComponent(MathVector2D.X)
                        + myBufferedImage.getWidth() / 2,
                myPosition.getComponent(MathVector2D.Y)
                        + myBufferedImage.getHeight() / 2);
    }

    /**
     * update the particle's position, rotation, velocity, and transparency
     */
    protected void update () {
        double r = myRandomGenerator.nextInt(2 * myVariance + 1);
        double angleVariation = (r - myVariance) / oneHundred;

        double tempNewAngle = myAngle + radiansPerCircle * angleVariation;
        int newX = (int) (Math.cos(tempNewAngle) * maxDistanceTraveledPerUpdate);
        int newY = (int) (Math.sin(tempNewAngle) * maxDistanceTraveledPerUpdate);
        myPosition.setComponent(MathVector2D.X,
                myPosition.getComponent(MathVector2D.X) + newX);
        myPosition.setComponent(MathVector2D.Y,
                myPosition.getComponent(MathVector2D.Y) + newY);
        durationExisted++;

        myRotation += myRotationalVelocity;

        calculateAlphachanges();
    }

    private void calculateAlphachanges () {
        // this is the alpha scale
        myRGBAscales[3] = (float) (durationLimit - durationExisted)
                / (float) durationLimit;
    }

    /**
     * @return if the particle still exists
     */
    protected boolean stillExists () {
        return (durationExisted < durationLimit * 0.8f);
    }
}
