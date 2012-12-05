package util.mathvector;

import java.awt.Point;

public class MathVector2D extends MathVector{

	/**
	 * Our original motivation for implementing this class is to create a representation of 
	 * a two-dimensional vector that has more functionality and allows for more mathematical 
	 * accuracy than the Point object, which strictly uses ints. Sometimes, accuracy is 
	 * lost through mathematical calculations involving ints, and as such, doubles are
	 * preferred for preserving accuracy. 
	 * 
	 * A specific example of when such accuracy is important is in the movement 
	 * algorithms for the Particles in the ParticleEngine. Having accurate calculations
	 * allows for better-looking particle movement.
	 * 
	 *@author Kathleen 
	 */
	
        public static final int X = 0;
        public static final int Y = 1;
    
	double[] myVector;
	
	/**
	 * This MathVector2D object represents the two-dimensional 
	 * vector (a,b).
	 * @param a
	 * @param b
	 */
	public MathVector2D(double a, double b){
		super(new double[]{a,b});
	}
	
	public MathVector2D(Point a) {
	    super(new double[]{a.x,a.y});
	}
	
	public MathVector2D(MathVector2D v){
		super(v);
	}

	/**
	 * Instantiates this MathVector2D object as the two-dimensional 
	 * zero vector (0,0).
	 */
	public MathVector2D(){
		super(default_Vector_Dimension);
	}
	
	/**
	 * Calculates the angle between the positive x-axis and this 
	 * vector in radians. Returns a value between 0 (inclusive) and 
	 * 2pi (exclusive).
	 * @return
	 */
	public double calculateAngleInRadians(){
    	double myAngle;
    	if (getComponent(0) == 0.0) {
			myAngle = Math.PI / 2;
		}
    	else {
			myAngle = Math.atan(getComponent(1) / getComponent(0));
		}
    	if (getComponent(0) < 0 || (getComponent(0) == 0 && getComponent(1) < 0)) {
			myAngle += Math.PI;
		}
    	if (getComponent(1) < 0 && getComponent(0) > 0){
    		myAngle += 2*Math.PI;
    	}
    	return myAngle;
	}
	
	public double getX(){
		return getComponent(X);
	}
	
	public double getY(){
		return getComponent(Y);
	}
}
