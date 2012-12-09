package games.particleEngineMedley;

import java.awt.Image;
import java.util.Stack;

import javax.swing.ImageIcon;

import util.mathvector.MathVector2D;
import util.particleEngine.ParticleEngine;
import util.particleEngine.ParticleSystem;

public class Waterfall extends ParticleSystem{

	private static final MathVector2D default_Initial_Position = new MathVector2D(400, 0);
	private static final MathVector2D default_Initial_Velocity = new MathVector2D(0,-3);

	private static final MathVector2D mistDirectionLeft = new MathVector2D(-2,0.5);
	private static final MathVector2D mistDirectionRight = new MathVector2D(2,0.5);
	
	private static int numberOfParticleEngines = 2;
	private static int density = 1000;
	private static int tolerance = 10;
	private static int length = 140;
	private static double angleSpan = 10;
	private static int numberOfDirections = 2;

	private static Boolean loop = true;
	private static float[] RGBAscales = { 0.3f, 0.6f, 0.9f, 0.4f };
	private static float[] RGBAtolerances = { 0.2f, 0.2f, 0.2f, 0.4f };
	
	private static float[] whiteWaterRGBAscales = { 1.0f, 1.0f, 1.0f, 0.2f };
	
	private static int mistDensity = 200;
	private static int mistLength = 100;
	private static float[] mistRGBAscales = { 1.0f, 1.0f, 1.0f, 0.4f };
	private static float[] mistRGBAtolerances = { 0.0f, 0.2f, 0.2f, 0.3f };
	
	private static int sourcePointInterval = 7;
	
	public Waterfall (MathVector2D initialPosition, MathVector2D initialVelocity, double numOfSourcePoints) {
		super(initialPosition, initialVelocity);
	}
	
	public Waterfall (){
		super(default_Initial_Position, default_Initial_Velocity);
	}
	
	public Waterfall (MathVector2D initialPosition){
		super(initialPosition,default_Initial_Velocity);
	}
	
	@Override
	protected void setUpParticleEngines() {
		//cyanParticle3.png
		ImageIcon temp = new ImageIcon(
				Waterfall.class.getResource("whiteParticle.png"));
		Image image = temp.getImage();
		for (int k = 0; k < numberOfParticleEngines; k++){
			MathVector2D nextPosition = new MathVector2D(getPosition().getX()
					+k*sourcePointInterval, getPosition().getY());
			addParticleEngine(density, image, nextPosition, getVelocity(), 
					tolerance, length, angleSpan, numberOfDirections, 
					RGBAscales, RGBAtolerances, loop);
			addParticleEngine(density/4, image, nextPosition, getVelocity(), 
					tolerance, length*8/10, angleSpan, numberOfDirections, 
					whiteWaterRGBAscales, mistRGBAtolerances, loop);
		}
		
		/*temp = new ImageIcon(
				Waterfall.class.getResource("particle2.png"));
		image = temp.getImage();*/
		MathVector2D nextPosition = new MathVector2D(getPosition().getX()+5, getPosition().getY()+300);
		addParticleEngine(mistDensity, image, nextPosition, mistDirectionLeft, 
				tolerance*3, mistLength, angleSpan, numberOfDirections, 
				mistRGBAscales, mistRGBAtolerances, loop);
		nextPosition = new MathVector2D(getPosition().getX()-5, getPosition().getY()+300);
		addParticleEngine(mistDensity, image, nextPosition, mistDirectionRight, 
				tolerance*3, mistLength, angleSpan, numberOfDirections, 
				mistRGBAscales, mistRGBAtolerances, loop);
	}
	
}
