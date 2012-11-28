package util.ParticleEngine;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.geom.AffineTransform;
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
    private double maxDistanceTraveledPerUpdate;
    
    private boolean rotate = true;
    private double rotationAngle = 0;
    private double dtheta = 0.1;
    		
    private Random myRandomGenerator;
    private VectorCalculator vcalculator = new VectorCalculator();
    
    private float[] scales = { 1f, 1f, 1f, 0.1f }; // before somebody comments,
                                                   // I know that these are
                                                   // magic numbers, they're
                                                   // just for testing purposes
    private float[] offsets;
    private BufferedImage myBufferedImage;
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
    	durationExisted = 0;
    	myImage = image;
    	
    	myRandomGenerator = new Random();
        offsets = new float[4];

        durationLimit = (int)(myRandomGenerator.nextDouble()*duration);
        
        myBufferedImage = new BufferedImage(size.width, size.height,
                BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = (Graphics2D) myBufferedImage.createGraphics();
        g2d.setBackground(new Color(0,0,0,0));
        g2d.drawImage(image, 0, 0,size.width, size.height, null);
        for (int y = 0; y < myBufferedImage.getHeight(); ++y) {
            for (int x = 0; x < myBufferedImage.getWidth(); ++x) {
                 int argb = myBufferedImage.getRGB(x, y);
                 if ((argb & 0x00FFFFFF) == 0x00000000)
                 {
                     myBufferedImage.setRGB(x, y, 0);
                 }
            }
        }
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
        //g2d.drawImage(myImage, myPosition.x, myPosition.y, null);
        if (rotate){
        	rotateImage(g2d);
        }
        g2d.drawImage(myBufferedImage, rop, myPosition.x, myPosition.y);
    }

    private void rotateImage(Graphics2D pen){
    	//code borrowed from Professor Duvall's Bouncer class
        // save current state of the graphics area
        AffineTransform old = new AffineTransform(pen.getTransform());
        // move graphics area to center of this shape
        pen.translate(myPosition.x, myPosition.y);
        // rotate area about this shape
        pen.rotate(rotationAngle);
        rotationAngle += dtheta;
        // move graphics area back to original position
        pen.translate(-myPosition.x, -myPosition.y);
        // draw as usual (i.e., rotated)
        pen.drawImage(myImage,
                      getLeft(), getTop(), 
                      getWidth(), getHeight(),
                      null);
        // restore graphics area to its old state, our changes have no lasting effects
        pen.setTransform(old);
    }
	
    public void update () {
    	double r = myRandomGenerator.nextInt(2*myVariance+1);
    	double k = myRandomGenerator.nextDouble();
    	double angleVariation = (r-myVariance)/oneHundred;
    	
    	double tempNewAngle = myAngle + radiansPerCircle*angleVariation;
    	int newX = (int) (Math.cos(tempNewAngle)*maxDistanceTraveledPerUpdate * k);
    	int newY = (int) (Math.sin(tempNewAngle)*maxDistanceTraveledPerUpdate * k);
        myPosition.x += newX;
        myPosition.y -= newY;
        durationExisted++;

        // this is the alpha scale
        scales[3] = (float)(durationLimit - durationExisted) / (float)durationLimit;
    }
    
    /**
     * Tells if the particle still exists
     * 
     * @return if the particle still exists
     */
    public boolean stillExists () {
        return (durationExisted < durationLimit*0.8f);
    }
    
    private int getLeft(){
    	return myPosition.x-getWidth()/2;
    }
    
    private int getTop(){
    	return myPosition.y-getHeight()/2;
    }
    
	private int getWidth(){
		return myBufferedImage.getWidth();
	}
	
	private int getHeight(){
		return myBufferedImage.getHeight();
	}
	
	public void setRotation(Boolean r){
		rotate = r;
	}
}
