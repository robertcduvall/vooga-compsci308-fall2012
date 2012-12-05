package util.particleEngine;

import java.awt.Image;
import java.awt.Point;

import javax.swing.ImageIcon;

import util.mathvector.MathVector2D;

public class Fireworks extends ParticleSystem{

    private static int myTolerance = 0;
    private static int myLength = 50;
    private static int myDensity = 70;
    private static int numDirections = 11;
    private static boolean loop = false;
    private static double angleSpan = 90;
    
    //RGBA magic numbers borrowed from Explosion class
    private static float[] RGBAscales = { 3.0f, 1.2f, 1.2f, 0.4f };
    private static float[] RGBAtolerances = { 0.2f, 0.4f, 0.4f, 0.1f };
    
	public Fireworks(MathVector2D startingPosition) {
		super(startingPosition, new MathVector2D(0,10));
	}
	
	public Fireworks(Point startingPosition){
		this(new MathVector2D(startingPosition));
	}

	@Override
	protected void setUpParticleEngines() {
		//image borrowed from Explosion class
		ImageIcon temp = new ImageIcon(
                Explosion.class.getResource("orangeParticle.png"));
		Image myImage = temp.getImage();
		addParticleEngine(myDensity, myImage, position, getVelocity(), 
				myTolerance, myLength, angleSpan, numDirections, RGBAscales, RGBAtolerances, loop);
	}

    /**
     * Adds a ParticleEngine to the List myParticleEngines.
     * 
     * @param density defines the number of particles in the engine
     * @param particleImage the image to use for the particles
     * @param direction the general direction in which the particles will
     *        travel, (0,0) will travel in all directions
     * @param tolerance how much the particles can vary from the given direction
     * @param length how long the particles will exist before being reset
     */
    protected void addParticleEngine (int density, Image particleImage,
            MathVector2D position, MathVector2D velocity, int tolerance, int length,
            double angleSpan, int numberOfDirections, float[] RGBAscales, float[] RGBAtolerances, Boolean loop) {
        ParticleEngine newParticleEngine = new GravitationalParticleEngine(density, particleImage,
                position, velocity, tolerance, length, angleSpan,
                numberOfDirections, RGBAscales, RGBAtolerances, loop);
    	addParticleEngine(newParticleEngine);
    }
}
