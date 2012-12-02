package util.mathVector;

import java.awt.Point;

/**
 * This class does basic vector math, which includes vector addition, calculating the magnitude and direction of a vector, 
 * normalizing a vector, scaling a vector by a constant factor, etc. 
 * 
 * @author Kathleen
 *
 */
public class VectorCalculator {

	/**
	 * Adds two vectors of dimension 2.
	 * @param v1 vector 1
	 * @param v2 vector 2
	 * @return
	 */
	public static Point addVectors(Point v1, Point v2){
		return new Point(v1.x+v2.x,v1.y+v2.y);
	}
	
	/**
	 * Adds two vectors of arbitrary dimension (as long as the dimensions of the vectors are equal).
	 * The vectors are represented as double arrays.
	 * @param v1 vector 1
	 * @param v2 vector 2
	 * @return
	 */
	public static double[] addVectors(double[] v1, double[] v2){
		if (!vectorsDimensionsEqual(v1,v2)){
			return null;
		}
		double[] vectorSum = new double[v1.length];
		for (int k = 0; k < v1.length; k++){
			vectorSum[k] = v1[k]+v2[k];
		}
		return vectorSum;
	}

	/**
	 * Adds together multiple vectors. Returns (0,0) if the Point array is empty.
	 * @param vectors an array of vectors (represented by Point objects) to be added
	 * @return
	 */
	public static Point addVectors(Point[] vectors){
		if (vectors.length == 0){
			return new Point(0,0);
		}
		Point vectorSum = new Point(0,0);
		for (Point v : vectors){
			vectorSum = addVectors(vectorSum,v);
		}
		return vectorSum;
	}
	
	/**
	 * Calculates the dot product of two vectors.
	 * @param v1 vector 1
	 * @param v2 vector 2
	 * @return
	 */
	public static int calculateDotProduct(Point v1, Point v2){
		return v1.x*v2.x+v1.y*v2.y;
	}
	
	/**
	 * Calculates the dot product of two vectors of arbitrary 
	 * dimension which are represented as double arrays 
	 * (as long as the two vectors are of the same dimension, of course).
	 * Returns the dot product as a double.
	 * @param v1 vector 1
	 * @param v2 vector 2
	 * @return
	 */
	public static double calculateDotProduct(double[] v1, double[] v2){
		if (!vectorsDimensionsEqual(v1,v2)){
			return -1;
		}
		double dotProduct = 0;
		for (int k = 0; k < v1.length; k++){
			dotProduct += v1[k]*v2[k];
		}
		return dotProduct;
	}
	
    /**
     * Normalizes the vector v (which is represented as a Point). Returns a double array 
     * rather than a Point to preserve some accuracy, 
     * as Points only store x and y values as int variables.
     * @param v vector to be normalized
     */
    public static double[] normalizeVector(Point v){
    	double magnitude = calculateMagnitude(v);
    	double[] normalizedVector = {((double) v.x)/magnitude, ((double) v.y)/magnitude};
    	return normalizedVector;
    }
    
    /**
     * Calculates the magnitude of vector v (represented as a Point) as a double.
     * @param v vector for which you wish to calculate the magnitude
     * @return
     */
    public static double calculateMagnitude(Point v){
    	return Math.sqrt(v.x*v.x+v.y*v.y);
    }
    
    /**
     * Calculates the magnitude of vector v (represented as a double array) as a double.
     * @param v vector for which you wish to calculate the magnitude.
     * @return
     */
    public static double calculateMagnitude(double[] v){
    	return Math.sqrt(calculateDotProduct(v,v));
    }
    
    /**
     * Calculates the angle (in radians, from 0 (inclusive) to 2pi (noninclusive)) from the 
     * positive x-axis to the 2-dimensional vector v. Returns -1 if v is not an array 
     * of length 2.
     * @param v vector
     * @return
     */
    public static double calculateAngleInRadians(double[] v){
    	if (v.length != 2){
    		return -1;
    	}
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
     * Calculates the angle (in radians, from 0 (inclusive) to 2pi (noninclusive)) from the 
     * positive x-axis to the 2-dimensional vector v.
     * @param v vector
     * @return
     */
    public static double calculateAngleInRadians(Point v){
    	return calculateAngleInRadians(normalizeVector(v));
    }
    
    /**
     * Returns vector p multiplied by the scalar factor n.
     * @param n scale factor
     * @param p vector to be scaled
     * @return
     */
	public static Point scaleVector(int n, Point p){
		return new Point(p.x*n, p.y*n);
	}
	
	/**
	 * Compares the dimensions of two vectors (represented as double[] objects) 
	 * and returns whether the dimensions are equal.
	 * @param v1 vector 1
	 * @param v2 vector 2
	 * @return
	 */
	public static boolean vectorsDimensionsEqual(double[] v1, double[] v2){
		return v1.length == v2.length;
	}
}
