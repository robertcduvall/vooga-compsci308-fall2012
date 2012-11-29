package util.particleEngine;

import java.awt.Image;
import java.awt.Point;

import javax.swing.ImageIcon;

import util.calculator.VectorCalculator;

public class Trail extends ParticleSystem{

	private static Image particleImage;
	private static VectorCalculator vcalculator;
	private static double angleSpan = 90;
	private static int numberOfDirections = 10;
	private static Point velocity = new Point(0,2);
	private static int tolerance = 20;
	private static int length = 50;

	private static int density = 10;
	
	@Override
	protected void setUpParticleEngines() {
		//these 2 lines of code are from David's ParticleTestApplet class
		ImageIcon temp = new ImageIcon(
				Trail.class.getResource("particle.png"));
		Image particleImage = temp.getImage();

		vcalculator = new VectorCalculator();
		addParticleEngine(density, particleImage, position, velocity, tolerance, length, angleSpan, numberOfDirections, true);

	}
}
