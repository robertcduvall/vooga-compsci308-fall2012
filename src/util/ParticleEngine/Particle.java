package util.ParticleEngine;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.util.Random;


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
    private Point myPosition;
    private Point myVelocity;
    private int myVariance;
    private double myAngle;
    private double maxDistanceTraveledPerUpdate;
    
    private Random myRandomGenerator = new Random();
    
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
        myVelocity = velocity;
        myPosition = position;
        myVariance = variance;
        setupRadianMode();
        
        offsets = new float[4];
        durationLimit = duration;
        durationExisted = 0;

        myImage = image;
/*        myImage = new BufferedImage(size.width, size.height,
                BufferedImage.TYPE_INT_ARGB);
        myImage.createGraphics().drawImage(image, myPosition.x, myPosition.y,
                size.width, size.height, null);*/
    }

    /**
     * Stores the angle and magnitude of the velocty vector.
     */
    private void setupRadianMode(){
    	double[] normalizedVelocity = normalizeVector(myVelocity);
    	//System.out.println("x "+normalizedVelocity[0]);
    	//System.out.println("y "+normalizedVelocity[1]);
    	
    	if (normalizedVelocity[0] == 0) {
			myAngle = Math.PI/2;
		}
    	else {
			myAngle = Math.atan(normalizedVelocity[1]/normalizedVelocity[0]);
		}
    	if (myVelocity.y < 0) {
			myAngle += Math.PI;
		}
    	//System.out.println("My Angle "+myAngle);
    	maxDistanceTraveledPerUpdate = Math.max(calculateMagnitude(myVelocity),1);
    	//System.out.println("Max distance" +maxDistanceTraveledPerUpdate);
    }
    
    /**
     * Normalizes the vector v (which is represented as a Point).
     */
    private double[] normalizeVector(Point v){
    	double magnitude = Math.sqrt(v.x*v.x+v.y*v.y);
    	//System.out.println("Magnitude "+magnitude);
    	double[] normalizedVector = {((double) v.x)/magnitude, ((double) v.y)/magnitude};
    	return normalizedVector;
    }
  
    /**
     * Returns the magnitude of the vector represented by Point p. 
     */
    private double calculateMagnitude(Point p){
    	return Math.sqrt(p.x*p.x+p.y*p.y);
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
    	//System.out.println("Angle variation: "+angleVariation);
    	
    	double tempNewAngle = myAngle + radiansPerCircle*angleVariation;
    	//System.out.println("New Angle: "+tempNewAngle);
    	int newX = (int) (Math.cos(tempNewAngle)*maxDistanceTraveledPerUpdate);
    	int newY = (int) (Math.sin(tempNewAngle)*maxDistanceTraveledPerUpdate);
    	//System.out.println("X-Movement: "+newX);
    	//System.out.println("Y-Movement: "+newY);
        myPosition.x += newX;
        myPosition.y += newY;
        durationExisted++;

        // this is the alpha scale
        scales[3] = (durationLimit - durationExisted) / durationLimit;
        //System.out.println("("+myPosition.x+", "+myPosition.y+")");
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
