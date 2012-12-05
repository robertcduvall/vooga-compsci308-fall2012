package util.particleEngine;

import java.awt.Dimension;
import java.awt.Image;

import util.mathvector.MathVector2D;

public class GravitationalParticleEngine extends ParticleEngine {

	public GravitationalParticleEngine(int density, Image particleImage,
            MathVector2D position, MathVector2D velocity, int tolerance,
            int length, double inputAngleSpan, int numberOfDirections,
            float[] RGBAscales, float[] RGBAtolerances, Boolean loopValue){
		
		super (density, particleImage, position, velocity, tolerance,
            length, inputAngleSpan, numberOfDirections,
            RGBAscales, RGBAtolerances, loopValue);
	}
	
    protected void createParticle (double inputAngleSpan,
    		int numberOfOriginLines, int i) {
    	Image myImage = getImage();
        Dimension particleSize = new Dimension(myImage.getWidth(null),
                myImage.getHeight(null));
        MathVector2D startingPosition = new MathVector2D(getInitialPosition());
        double velocityMagnitude = getMainVelocity().calculateMagnitude();
        double velocityAngle = getMainVelocity().calculateAngleInRadians();
        
        Particle newParticle = new GravitationalParticle(startingPosition, particleSize, myImage,
                velocityMagnitude, velocityAngle, getVariance(),
                getDuration(), getRGBAscales(), getRGBAtolerances());
        addParticle(newParticle);
    }
}
