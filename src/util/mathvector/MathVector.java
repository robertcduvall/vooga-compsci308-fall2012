package util.mathvector;

/**
 * This class represents a mathematical vector (as opposed to a more 
 * general vector of objects such as the Vector class defined in the 
 * Java API). Vector component values are stored as doubles.
 * The size of the MathVector is fixed at instantiation. 
 * 
 * @author Kathleen
 *
 */
public class MathVector {

	protected static final int default_Vector_Dimension = 2;
	private double[] myVector;
	private int dimension;
	
	/**
	 * "Instantiates" this MathVector as the vector represented by
	 * the double array v. If an empty array is passed, this 
	 * MathVector is instantiated as the two-dimensional zero 
	 * vector (0.0, 0.0).
	 * @param v
	 */
	public MathVector(double[] v){
		if (v.length < 1){
			myVector = setupZeroVector(default_Vector_Dimension);
		}
		else{
			myVector = setupZeroVector(v.length);
			copyVector(v);
		}
	}
	
	/**
	 * This vector represents an n-dimensional zero vector.
	 * If an invalid int is passed (n<1), then the vector is 
	 * instantiated as the two-dimensional zero vector (0.0, 0.0).
	 * @param n
	 */
	public MathVector(int n){
		if (n < 1){
			dimension = default_Vector_Dimension;
		}
		else{
			dimension = n;
		}
		myVector = setupZeroVector(dimension);
	}
	
	/**
	 * Instantiates this MathVector as the two-dimensional 
	 * zero vector (0.0, 0.0).
	 */
	public MathVector(){
		this(default_Vector_Dimension);
	}

	/**
	 * Instantiates this MathVector as a copy of v 
	 * (i.e. has the same dimension and component values as v).
	 * @param v vector to be copied
	 */
	public MathVector(MathVector v){
		dimension = v.getDimension();
		myVector = v.getCopyOfVector();
	}
	
	/**
	 * Returns an double array of the specified length with
	 * all elements equal to 0.0. 
	 * @param n dimension of vector
	 * @return
	 */
	private double[] setupZeroVector(int n){
		dimension = n;
		double[] zeroVector = new double[n];
		for (int k = 0; k < n; k++){
			zeroVector[k] = 0.0;
		}
		return zeroVector;
	}
	
	/**
	 * Sets the component-values of this vector to the component-values 
	 * of the vector represented by v.
	 * @param v vector 
	 */
	public void copyVector(double[] v){
		if (vectorDimensionsEqual(v)){
			for (int k = 0; k < v.length; k++){
				myVector[k] = v[k];
			}
		}
	}
	
	/**
	 * Returns a new MathVector that is a copy of this MathVector 
	 * (i.e. has the same dimension and component values).
	 * @return
	 */
	public MathVector getCopyOfMathVector(){
		MathVector copy = new MathVector(dimension);
		for (int k = 0; k < dimension; k++){
			copy.setComponent(k, myVector[k]);
		}
		return copy;
	}
	
	/**
	 * Returns a copy of this vector represented as a double array.
	 * @return
	 */
	public double[] getCopyOfVector(){
		double[] copy = new double[dimension];
		for (int k = 0; k < dimension; k++){
			copy[k] = myVector[k];
		}
		return copy;
	}
	
	/**
	 * Returns a MathVector which is the vector sum of this 
	 * vector and vector v. If the dimension of v does not 
	 * equal the dimension of this vector, then null is returned.
	 * @param v vector to be summed with this vector.
	 * @return
	 */
	public MathVector getVectorSumCopy(MathVector v){
		if (!vectorDimensionsEqual(v)){
			return null;
		}
		MathVector vectorSum = new MathVector(this);
		vectorSum.addVector(v);
		return vectorSum;
	}
	
	/**
	 * Adds vector v to this vector. (i.e. Redefines 
	 * (the values of) this vector to be the vector 
	 * sum of itself and v.) Returns true if the 
	 * vector addition is valid. Returns false and does
	 * not add vector v if the dimension of this vector 
	 * and the dimension of vector v are not equal.
	 * @param v vector to be added to this vector
	 * @return
	 */
	public boolean addVector(MathVector v){
		if (!vectorDimensionsEqual(v)){
			return false;
		}
		for (int k = 0; k < dimension; k++){
			myVector[k] += v.getComponent(k);
		}
		return true;
	}
	
	/**
	 * Returns the magnitude of this vector.
	 */
	public double calculateMagnitude(){
		return Math.sqrt(calculateDotProduct(this));
	}
	
	/**
	 * Changes this vector by scaling the components by the constant c.
	 * @param c
	 */
	public MathVector scale(double c){
		for (int k = 0; k < dimension; k++){
			setComponent(k, c*getComponent(k));
		}
		return this;
	}
	
	/**
	 * Returns a new MathVector which is the vector represented by this 
	 * MathVector scaled by the constant c.
	 * @param c
	 * @return
	 */
	public MathVector getScaledCopy(double c){
		MathVector scaledV = new MathVector(this);
		scaledV.scale(c);
		return scaledV;
	}
	
	/**
	 * Normalizes this vector.
	 */
	public MathVector normalize(){
		double magnitude = calculateMagnitude();
		scale(1 / magnitude);
		return this;
	}
	
	/**
	 * Returns a new MathVector that represents this vector normalized.
	 * @return
	 */
	public MathVector getNormalizedCopy(){
		return getScaledCopy(calculateMagnitude());
	}
	
	/**
	 * Returns 0 if the dimensions of this vector and v are not equal. 
	 * Otherwise, returns the dot product of vector v and this vector.
	 * @param v vector
	 * @return
	 */
	public double calculateDotProduct(MathVector v){
		if (!vectorDimensionsEqual(v)){
			return 0;
		}
		double dotProduct = 0;
		for (int k = 0; k < dimension; k++){
			dotProduct += myVector[k]*v.getComponent(k);
		}
		return dotProduct;
	}
	
	/**
	 * Returns the angle (in radians) between this MathVector 
	 * and the MathVector v. Returns a value between 0 and pi 
	 * if the angle can be calculated. Returns -1 if the 
	 * dimension of vector v does not equal the dimension 
	 * of this vector or if one of vector v or this vector 
	 * has magnitude zero (i.e. is the zero-vector.)
	 * @pram v vector
	 * @return
	 */
	public double calculateAngleInRadians(MathVector v){
		if (!vectorDimensionsEqual(v) || v.calculateMagnitude() == 0 || calculateMagnitude() == 0){
			return -1;
		}
		double dotProduct = calculateDotProduct(v);
		double angle = Math.acos(dotProduct / 
				(calculateMagnitude() * v.calculateMagnitude()));
		return angle;
	}
	
	/**
	 * Returns the vector component value at index n. 
	 * If an invalid index number is passed, then returns 0.0.
	 * @param n index of component to be retrieved
	 * @return
	 */
	public double getComponent(int n){
		if (!isValidIndex(n)){
			return 0.0;
		}
		return myVector[n];
	}
	
	/**
	 * Sets the component value at index n to the double d. Returns
	 * false if n is an invalid index (and does not modify the vector) 
	 * and true otherwise.
	 * @param n index
	 * @param d component value
	 */
	public boolean setComponent(int n, double d){
		if (!isValidIndex(n)){
			return false;
		}
		myVector[n] = d;
		return true;
	}
	
	/**
	 * Returns whether the index is within the bounds of 0 (inclusive) 
	 * and the dimension (exclusive) of this vector.
	 * @param n index
	 * @return
	 */
	public boolean isValidIndex(int n){
		return (n >= 0 && n < dimension);
	}
	
	/**
	 * Returns whether the vector represented by v has the 
	 * same dimension as this MathVector.
	 * @param v vector
	 * @return
	 */
	public boolean vectorDimensionsEqual(double[] v){
		return v.length == dimension;
	}
	
	/**
	 * Returns whether MathVector v has the same dimension 
	 * as this MathVector.
	 * @param v vector
	 * @return
	 */
	public boolean vectorDimensionsEqual(MathVector v){
		return v.getDimension() == dimension;
	}
	
	/**
	 * Returns the dimension of this vector.
	 * @return
	 */
	public int getDimension(){
		return dimension;
	}
}
