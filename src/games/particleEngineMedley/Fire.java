package games.particleEngineMedley;

import java.awt.Image;

import javax.swing.ImageIcon;

import util.mathvector.MathVector2D;
import util.particleEngine.ParticleSystem;

/**
 * 
 * @author Kathleen
 *
 */
public class Fire extends ParticleSystem {
	private static final MathVector2D default_Starting_Velocity = new MathVector2D(0,5);
	private static int density = 200;
	private static Image image;
	private static String imageName = "orangeParticle.png";
	private static int tolerance = 15;
	private static int length = 30;
	private static double angleSpan = 20;
	private static int numberOfDirections = 5;
	private static float[] RGBAscales = { 1.0f, 0.8f, 0.0f, 0.4f };
	private static float[] RGBAtolerances = { 0.2f, 0.2f, 0.2f, 0.4f };
	private static boolean loop = true;
	
	public Fire(MathVector2D position){
		super(position, default_Starting_Velocity);
	}
	
	public Fire(MathVector2D position, MathVector2D velocity) {
		super(position, velocity);
	}

	@Override
	protected void setUpParticleEngines() {
		// image retrieval probably needs to be refactored
		ImageIcon temp = new ImageIcon(
				Fire.class.getResource(imageName));
		image = temp.getImage();
		addParticleEngine(density, image, getPosition(), getVelocity(),
				tolerance, length, angleSpan, 
				numberOfDirections, RGBAscales, 
				RGBAtolerances, loop);
	}

}
