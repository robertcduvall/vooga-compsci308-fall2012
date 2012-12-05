package util.particleEngine;

import java.awt.Dimension;
import java.awt.Image;

import util.mathvector.MathVector;
import util.mathvector.MathVector2D;

public class GravitationalParticle extends Particle{
	
	MathVector2D acceleration;
	
	public GravitationalParticle (MathVector2D position, Dimension size, Image image,
            double velocityMagnitude, double velocityAngle, int variance,
            int duration, float[] RGBAscales, float[] RGBAtolerances){
		super(position, size, image, velocityMagnitude, velocityAngle, 
				variance, duration, RGBAscales, RGBAtolerances);
		acceleration = new MathVector2D(0, 0.5);
	}
	
	protected void positionUpdate () {
		movePosition((MathVector2D) getMyVelocity());
		changeMyVelocity(acceleration);
	}
	
	private void changeMyVelocity(MathVector2D v){
		MathVector2D newVelocity = new MathVector2D();
		newVelocity.setVector(getMyVelocity().getVectorSumCopy(v));
		setMyVelocity(newVelocity);
	}
}
