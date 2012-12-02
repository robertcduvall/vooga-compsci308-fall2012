package util.mathVector;

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
}
