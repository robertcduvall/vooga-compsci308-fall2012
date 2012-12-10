package games.particleEngineMedley;

import java.awt.Image;

import javax.swing.ImageIcon;

import util.mathvector.MathVector2D;
import util.particleEngine.ParticleSystem;

/**
 * This implementation of ParticleSystem demonstrates the use of
 * particles as the "end-product" in the animation (compared to
 * explosions and fires which use large collections of particles to
 * create a larger effect).
 * 
 * @author Kathleen
 *
 */
public class Snow extends ParticleSystem{

	private static final MathVector2D default_Starting_Velocity = new MathVector2D(0, -4);
	private static final MathVector2D startDistributionPosition = new MathVector2D(5, 5);
	private static int numberOfParticleEngines = 15;
	private static int density = 1;
	private static Image image;
	private static String imageName = "whiteParticle.png";
	private static int tolerance = 0;
	private static int length = 200;
	private static double angleSpan = 0;
	private static int numberOfDirections = 1;
	private static int numberOfSizes = 3;

	private static Boolean loop = true;
	private static float[] RGBAscales = { 1.0f, 1.0f, 1.0f, 0.4f };
	private static float[] RGBAtolerances = { 0.1f, 0.1f, 0.1f, 0.4f };

	public Snow(MathVector2D position){
		super(position, default_Starting_Velocity);
	}

	public Snow(MathVector2D position, MathVector2D velocity){
		super(position, velocity);
	}

	protected void setUpParticleEngines() {
		ImageIcon temp = new ImageIcon(
				Snow.class.getResource(imageName));
		image = temp.getImage();
		MathVector2D interval = new MathVector2D(getPosition().getX() / numberOfParticleEngines, 0);
		MathVector2D nextPosition = new MathVector2D(startDistributionPosition);
		nextPosition.setComponent(1, getPosition().getY());
		
		for (int j = 0; j < numberOfSizes; j++){
			for (int k = 0; k < numberOfParticleEngines; k++){
				addParticleEngine(density, image, nextPosition, getVelocity(), 
						tolerance, length, angleSpan, numberOfDirections, 
						RGBAscales, RGBAtolerances, loop);
				nextPosition = new MathVector2D(nextPosition.getVectorSumCopy(interval).getMathVector2D());
			}
		}
	}
}
