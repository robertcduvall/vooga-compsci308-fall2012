
package games.particleEngineMedley;

import java.awt.Image;
import java.util.Random;

import javax.swing.ImageIcon;
import util.mathvector.MathVector2D;
import util.particleEngine.ParticleSystem;

/**
 * 
 * @author Kathleen
 *
 */
public class Bubbles extends ParticleSystem {
	private static final MathVector2D default_Starting_Velocity = new MathVector2D(0,5);
	
	private static int numberOfParticleEngines = 1;
	private static int density = 2;
	private static Image image;
	private static String imageName = "bubble.png";
	private static MathVector2D sourceEndpoint1;
	private static MathVector2D sourceEndpoint2; //equal to bubblePosition if 
										   //point-source model is used
	private static int tolerance = 15;
	private static int length = 100;
	private static double angleSpan = 0;
	private static int numberOfDirections = 0;

	private static Boolean loop = true;
	private static float[] RGBAscales = { 0.2f, 0.8f, 0.4f, 0.4f };
	private static float[] RGBAtolerances = { 0.3f, 0.3f, 0.3f, 0.4f };

	public Bubbles(MathVector2D sourcePosition){
		super(sourcePosition, default_Starting_Velocity);
	}
	
	public Bubbles(MathVector2D sourcePosition, MathVector2D startingVelocity){
		super(sourcePosition, startingVelocity);
		//instantiateSourcePositions(sourcePosition, sourcePosition);
	}

	/**
	 * Bubble spawn sources (i.e. particle engine positions) are positioned 
	 * randomly along the line between sourceEndpoint1 and sourceEndpoint2.
	 * 
	 * This line-source functionality doesn't work at the moment. :\ Sorry!
	 * @param sourceEndpoint1 one endpoint of line along which bubbles spawn
	 * @param sourceEndpoint2 the other endpoint of the line along which bubbles spawn
	 * @param startingVelocity initial velocity of bubbles
	 */
	public Bubbles(MathVector2D sourceEndpoint1, MathVector2D sourceEndpoint2, 
			MathVector2D startingVelocity) {
		super(sourceEndpoint1, startingVelocity);
		instantiateSourcePositions(sourceEndpoint1, sourceEndpoint2);
	}

	private void instantiateSourcePositions(MathVector2D endpoint1, MathVector2D endpoint2){
		sourceEndpoint1 = endpoint1;
		sourceEndpoint2 = endpoint2;
	}

	@Override
	protected void setUpParticleEngines() {
		ImageIcon temp = new ImageIcon(
				Bubbles.class.getResource(imageName));
		image = temp.getImage();
		
		Random randomGenerator = new Random();
		for (int k = 0; k < numberOfParticleEngines; k++){
			addParticleEngine(density, image, getPosition(), getVelocity(), 
					tolerance, length, angleSpan, numberOfDirections, 
					RGBAscales, RGBAtolerances, loop);
		}

	}
	
	private MathVector2D generateRandomSourcePosition(Random r) {
		MathVector2D  returnValue = new MathVector2D();
		double differenceFactor = r.nextDouble();
		for (int k = 0; k < 2 ; k++){
			returnValue.setComponent(k, sourceEndpoint1.getComponent(k)*(1-differenceFactor) 
					+ sourceEndpoint2.getComponent(k)*differenceFactor);
		}
		return returnValue;
	}

}
