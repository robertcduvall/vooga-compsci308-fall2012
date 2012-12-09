package games.particleEngineMedley;

import java.awt.Image;

import javax.swing.ImageIcon;

import util.mathvector.MathVector2D;
import util.particleEngine.ParticleSystem;

public class Snow extends ParticleSystem{

	private static final MathVector2D default_Starting_Velocity = new MathVector2D(0, -4);
	private static final MathVector2D startDistributionPosition = new MathVector2D(5, 5);
	private static int numberOfParticleEngines = 10;
	private static int density = 1;
	private static Image image;
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
		//whiteParticle.png
		ImageIcon temp = new ImageIcon(
				Snow.class.getResource("whiteParticle.png"));
		image = temp.getImage();
		MathVector2D interval = new MathVector2D(getPosition().getX() / numberOfParticleEngines, 0);
		MathVector2D nextPosition = new MathVector2D(startDistributionPosition);
		nextPosition.setComponent(1, getPosition().getY());
		//FIX SIZING
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
