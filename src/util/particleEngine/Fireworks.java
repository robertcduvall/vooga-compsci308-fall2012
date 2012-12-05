package util.particleEngine;

import java.awt.Image;

import util.mathvector.MathVector2D;

public class Fireworks extends ParticleSystem{

	public Fireworks(MathVector2D startingPosition) {
		super(startingPosition);
	}

	@Override
	protected void setUpParticleEngines() {
		
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
