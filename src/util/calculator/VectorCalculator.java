package util.calculator;

import java.awt.Point;

/**
 * This class does basic vector math, which includes calculating the magnitude and direction of a vector, normalizing a vector,
 * scaling a vector by a constant factor, etc. 
 * 
 * @author Kathleen
 *
 */
public class VectorCalculator {

    /**
     * Normalizes the vector v (which is represented as a Point). Returns an array rather than a Point to preserve some accuracy, 
     * as Points only store x and y values as int variables.
     */
    public double[] normalizeVector(Point v){
    	double magnitude = calculateMagnitude(v);
    	double[] normalizedVector = {((double) v.x)/magnitude, ((double) v.y)/magnitude};
    	return normalizedVector;
    }
    
    /**
     * Calculates the magnitude of vector v.
     * @param v vector
     * @return
     */
    public double calculateMagnitude(Point v){
    	return Math.sqrt(v.x*v.x+v.y*v.y);
    }
    
    /**
     * Calculates the angle (in radians) from the positive x-axis to the vector v.
     * @param v vector
     * @return
     */
    public double calculateAngle(double[] v){
    	double myAngle;
    	if (v[0] == 0) {
			myAngle = Math.PI/2;
		}
    	else {
			myAngle = Math.atan(v[1]/v[0]);
		}
    	if (v[0] < 0 || (v[0]==0 && v[1]<0)) {
			myAngle += Math.PI;
		}
    	return myAngle;
    }
    
    /**
     * Calculates the angle (in radians) from the positive x-axis to the vector v.
     * @param v vector
     * @return
     */
    public double calculateAngle(Point v){
    	return calculateAngle(normalizeVector(v));
    }
    
    /**
     * Returns a vector multiplied by the scalar factor n.
     * @param n scale factor
     * @param p vector to be scaled
     * @return
     */
	public Point scaleVelocity(int n, Point p){
		return new Point(p.x*n, p.y*n);
	}
}
