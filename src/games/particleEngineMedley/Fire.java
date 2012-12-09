package games.particleEngineMedley;

import java.awt.Image;

import javax.swing.ImageIcon;

import util.mathvector.MathVector2D;
import util.particleEngine.ParticleSystem;

public class Fire extends ParticleSystem {
	private static final MathVector2D default_Starting_Velocity = new MathVector2D(0,-5);
	private int density = 100;
	private Image image;
	private int tolerance = 15;
	private int length = 100;
	private double angleSpan = 0;
	private int numberOfDirections = 1;
	private float[] RGBAscales = { 1.0f, 0.8f, 0.0f, 0.4f };
	private float[] RGBAtolerances = { 0.2f, 0.2f, 0.2f, 0.4f };
	private boolean loop = true;
	
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
				Fire.class.getResource("orangeParticle.png"));
		image = temp.getImage();
		addParticleEngine(density, image, getPosition(), getVelocity(),
				tolerance, length, angleSpan,
				numberOfDirections, RGBAscales, RGBAtolerances, loop);
	}

}
