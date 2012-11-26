package util.ParticleEngine;

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
    private int durationLimit;
    private int durationExisted;
    public Point myPosition;
    public Point myVelocity;
    private int myVariance;
    private double myAngle;
    public double maxDistanceTraveledPerUpdate;
    
    private Random myRandomGenerator;
    private VectorCalculator vcalculator = new VectorCalculator();
    
    private float[] scales = { 1f, 1f, 1f, 0.1f }; // before somebody comments,
                                                   // I know that these are
                                                   // magic numbers, they're
                                                   // just for testing purposes
    private float[] offsets;
    //private BufferedImage myImage;
    private Image myImage;
    
    private static final int oneHundred = 100;
    private static final double radiansPerCircle = 2*Math.PI;
    
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
            Point velocity, int variance, int duration) {
    	declareVariables(position, size, image, variance, duration);
    	myVelocity = velocity;
        setupRadianMode();
    }

    public Particle (Point position, Dimension size, Image image, 
    		Double velocityMagnitude, Double velocityAngle, int variance, int duration){
    	declareVariables(position, size, image, variance, duration);
    	myAngle = velocityAngle;
    	maxDistanceTraveledPerUpdate = velocityMagnitude;
    }
    
    private void declareVariables(Point position, Dimension size, Image image,
            int variance, int duration){
    	myPosition = position;
    	myVariance = variance;
    	durationLimit = duration;
    	durationExisted = 0;
    	myImage = image;
    	
    	myRandomGenerator = new Random();
        offsets = new float[4];
        
/*        myImage = new BufferedImage(size.width, size.height,
                BufferedImage.TYPE_INT_ARGB);
        myImage.createGraphics().drawImage(image, myPosition.x, myPosition.y,
                size.width, size.height, null);*/
    }
    
    /**
     * Stores the angle and magnitude of the velocity vector.
     */
    private void setupRadianMode(){
    	myAngle = vcalculator.calculateAngle(myVelocity);
    	maxDistanceTraveledPerUpdate = Math.max(1, vcalculator.calculateMagnitude(myVelocity));
    }

    
    /**
     * Draws the particle
     * 
     * @param g
     */
    public void draw (Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        RescaleOp rop = new RescaleOp(scales, offsets, null);
        g2d.drawImage(myImage, myPosition.x, myPosition.y, null);
        //g2d.drawImage(myImage, rop, myPosition.x, myPosition.y);
    }

    public void update () {
    	double r = myRandomGenerator.nextInt(2*myVariance+1);
    	double angleVariation = (r-myVariance)/oneHundred;
    	
    	double tempNewAngle = myAngle + radiansPerCircle*angleVariation;
    	int newX = (int) (Math.cos(tempNewAngle)*maxDistanceTraveledPerUpdate);
    	int newY = (int) (Math.sin(tempNewAngle)*maxDistanceTraveledPerUpdate);
        myPosition.x += newX;
        myPosition.y -= newY;
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
