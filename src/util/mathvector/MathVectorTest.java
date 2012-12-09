package util.mathvector;


import java.util.ArrayList;

public class MathVectorTest {

/*	*//**
	 * Returns the angle (in radians) between this MathVector 
	 * and the positive x-axis using the dot product formula:
	 * v1 * v2 = ||v1||*||v2||cos(theta)
	 * where v1 and v2 are vectors, ||v|| is the magnitude 
	 * of vector v, and theta is the angle between v1 and v2.
	 * @return
	 *//*
	protected double calculateAngleInRadians(){
		MathVector positiveXaxis = new MathVector(dimension);
		positiveXaxis.setComponent(0, 1);
		double dotProduct = calculateDotProduct(positiveXaxis);
		double angle = Math.acos(dotProduct / calculateMagnitude());
		return angle;
	}*/
	
	private MathVector[] constructorTest(MathVector[] v){
		MathVector[] vectors = v;
		vectors[0] = new MathVector(9);
		vectors[1] = new MathVector(-1);
		vectors[2] = new MathVector();
		vectors[3] = new MathVector(vectors[0]);
		vectors[4] = new MathVector(new double[]{0.1,7.4});
		//printOutVectors(vectors);
		return v;
	}
	
	private MathVector[] copyVectorTest(MathVector[] v){
		//tests getCopyOfMathVector
		MathVector[] vectors = new MathVector[v.length];
		for (int k = 0; k < vectors.length; k++){
			vectors[k] = v[k].getCopyOfMathVector();
		}
		printOutVectors(vectors);
		
		//tests copyVector
		vectors = new MathVector[10];
		for (int k = 0; k < vectors.length; k++){
			double[] d = new double[k+1];
			for (int j = 0; j < k+1; j++){
				d[j] = j;
			}
			vectors[k] = new MathVector(k+1);
			vectors[k].setVector(d);
		}
		printOutVectors(vectors);
		//dimensionTest(vectors);
		return vectors;
	}
	
	private void dimensionTest(MathVector[] v){
		for (int k = 0; k < v.length; k++){
			System.out.println(v[k].getDimension());
		}
	}
	
	private void printOutVectors(MathVector[] v){
		for (int k = 0; k < v.length; k++){
			System.out.println(v[k]);
			for (int j = 0; j<v[k].getDimension(); j++){
				System.out.print(v[k].getComponent(j));
				System.out.print(" , ");
			}
			System.out.println();
		}
	}
	
	private void printVector(MathVector v){
		if (v == null){
			System.out.println("null");
			return;
		}
		System.out.print("(");
		for (int k = 0; k< v.getDimension(); k++){
			System.out.print(v.getComponent(k)+", ");
		}
		System.out.print(")");
	}
	
	private static ArrayList<MathVector> vectorSet1(){
		ArrayList<MathVector> vectors = new ArrayList<MathVector>();
		vectors.add(new MathVector(new double[]{1,2,3}));
		vectors.add(new MathVector(new double[]{3,2,1}));
		vectors.add(new MathVector(new double[]{2,3}));
		vectors.add(new MathVector(new double[]{2}));
		vectors.add(new MathVector(new double[]{}));
		vectors.add(new MathVector(new double[]{1,2,3,5}));
		return vectors;
	}
	
	private static ArrayList<MathVector> vectorSet2D(){
		ArrayList<MathVector> vectors = new ArrayList<MathVector>();
		vectors.add(new MathVector(new double[2]));
		vectors.add(new MathVector(new double[]{1,0}));
		vectors.add(new MathVector(new double[]{1,1}));
		vectors.add(new MathVector(new double[]{0.1,1}));
		vectors.add(new MathVector(new double[]{0,1}));
		vectors.add(new MathVector(new double[]{-1,1}));
		vectors.add(new MathVector(new double[]{-1,0}));
		vectors.add(new MathVector(new double[]{-1,-1}));
		vectors.add(new MathVector(new double[]{-2,2}));
		vectors.add(new MathVector(new double[]{-1,-4}));
		vectors.add(new MathVector(new double[]{0,-1}));
		vectors.add(new MathVector(new double[]{1,-1}));
		return vectors;
	}
	
	private ArrayList<MathVector2D> vector2Dset(){
		ArrayList<MathVector2D> vectors = new ArrayList<MathVector2D>();
		vectors.add(new MathVector2D(0,0));
		vectors.add(new MathVector2D(1,1));
		vectors.add(new MathVector2D(0,1));
		vectors.add(new MathVector2D(-1,1));
		vectors.add(new MathVector2D(-1,0));
		vectors.add(new MathVector2D(-1,-1));
		vectors.add(new MathVector2D(0,-1));
		vectors.add(new MathVector2D(1,-1));
		vectors.add(new MathVector2D(10,10));
		vectors.add(new MathVector2D(2,1));
		vectors.add(new MathVector2D(1,2));
		return vectors;
	}
	
	private void rotateAngleTest(ArrayList<MathVector2D> vectors, double angle){
		ArrayList<MathVector2D> v = vectors;
		for (MathVector2D m : v){
			printVector(m.getRotatedCopy(angle));
		}
	}
	
	private void calculateAngle2Dtest(ArrayList<MathVector2D> v){
		ArrayList<MathVector2D> vectors = v;
		for (MathVector2D k : vectors){
			printVector(k);
			System.out.print("Vector "+k.getX()+", "+k.getY());
			System.out.println(" angle: "+k.calculateAngleInRadians()*180/Math.PI);
		}
	}
	
	private void calculateAngleTest(ArrayList<MathVector> v){
		ArrayList<MathVector> vectors = v;
		for (int k = 0; k<vectors.size(); k++){
			for (MathVector j : vectors){
				System.out.print("Angle between ");
				printVector(vectors.get(k));
				System.out.print(" and ");
				printVector(j);
				System.out.println(": "+vectors.get(k).calculateAngleInRadians(j)*180/Math.PI);
			}
		}
	}
	
	private void dotProductTest(ArrayList<MathVector> v){
		ArrayList<MathVector> vectors = v;
		for (int k = 0; k<vectors.size(); k++){
			for (MathVector j : vectors)
				System.out.println(vectors.get(k).calculateDotProduct(j));
		}
		
	}
	
	private void magnitudeAndScale(ArrayList<MathVector> v){
		//also normalize
		ArrayList<MathVector> vectors = v;
		for (int k = 0; k < vectors.size(); k++){
			System.out.println(vectors.get(k).calculateMagnitude());
		}
		for (int k = 0; k < vectors.size(); k++){
			MathVector temp = vectors.get(k).getScaledCopy(2);
			printVector(temp);
		}
		
		for (int k = 0; k< vectors.size(); k++){
			vectors.get(k).normalize();
			printVector(vectors.get(k));
			System.out.println(vectors.get(k).calculateMagnitude());
		}
	}
	
	private void vectorSumTest(ArrayList<MathVector> v){
		ArrayList<MathVector> vectors = v;
		for (int k = 0; k<vectors.size(); k++){
			for (MathVector j : vectors){
				printVector(vectors.get(k).getVectorSumCopy(j));
			}
		}
		
		for (int k = 0; k<vectors.size()-1; k++){
			System.out.println(vectors.get(k).addVector(vectors.get(k+1)));
		}
	}
	
	private void constructorTest2(){
		ArrayList<MathVector> vectors = new ArrayList<MathVector>();
		vectors.add(new MathVector(new double[3]));
		double[] empty = new double[0];
		vectors.add(new MathVector(-10));
		vectors.add(new MathVector(new double[]{0,0,0}));
		for (int k = 0; k<vectors.size();k++){
			printVector(vectors.get(k));
		}
	}
	
	private double[] getAngles(){
	double[] angles = new double[8];
	for (int k = 0; k < 8 ; k++){
		angles[k] = k*45;
	}
	return angles;
	}
	
	private void angleConstructorTest(double[] d){
		for (int k = 0; k < d.length; k++){
			MathVector2D temp = new MathVector2D(d[k]);
			printVector(temp);
			System.out.println();
		}
	}
	
	public static void main(String[] args){
		MathVectorTest test = new MathVectorTest();
		//MathVector[] vectors = new MathVector[5];
		//vectors = test.constructorTest(vectors);
		//test.copyVectorTest(vectors);
		//test.vectorSumTest();
		//test.constructorTest2();
		//test.magnitudeAndScale(test.vectorSet1());
		//test.dotProductTest(test.vectorSet1());
		//test.calculateAngleTest(test.vectorSet2D());
		//test.calculateAngle2Dtest(test.vector2Dset());
		//test.rotateAngleTest(test.vector2Dset(), 90);
		test.angleConstructorTest(test.getAngles());
	}
}
