package games.particleEngineMedley;

import java.awt.Image;

import javax.swing.ImageIcon;

import util.mathvector.MathVector2D;
import util.particleEngine.ParticleSystem;

/**
 * This implementation of ParticleSystem is a crude attempt at creating a sunlight effect. Ideally,
 * the particles would be smaller and more dense so that the light looks like a single band rather 
 * than an obvious collection of small particles.
 * 
 * @author Kathleen
 *
 */
public class StreamingSunlight extends ParticleSystem{
	private static final MathVector2D default_Starting_Velocity = new MathVector2D(-3, -3);

	private static int numberOfParticleEngines = 1;
	private static int density = 4000;
	private static Image image;
	private static String imageName = "whiteParticle.png";
	private static int tolerance = 0;
	private static int length = 1000;
	private static double angleSpan = 10;
	private static int numberOfDirections = 100;

	private static Boolean loop = true;
	private static float[] RGBAscales = { 0.9f, 0.9f, 0.7f, 0.0f };
	private static float[] RGBAtolerances = { 0.0f, 0.0f, 0.0f, 0.4f };

	public StreamingSunlight(MathVector2D position){
		super(position, default_Starting_Velocity);
	}
	
	public StreamingSunlight(MathVector2D position, MathVector2D velocity){
		super(position, velocity);
	}
	
	protected void setUpParticleEngines() {
		ImageIcon temp = new ImageIcon(
				StreamingSunlight.class.getResource(imageName));
		image = temp.getImage();
		for (int k = 0; k < numberOfParticleEngines; k++){
			addParticleEngine(density, image, getPosition(), getVelocity(), 
					tolerance, length, angleSpan, numberOfDirections, 
					RGBAscales, RGBAtolerances, loop);
		}
	}
}
