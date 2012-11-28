package util.particleEngine;

import java.awt.Image;
import java.awt.Point;

import javax.swing.ImageIcon;

import util.calculator.VectorCalculator;

public class AngleDistributionTester extends ParticleEngineTestingUnit{

	private static Image particleImage;
	private static Point position = new Point (300,300);
	private static VectorCalculator vcalculator;
	private static double angleSpan = 90;
	private static int numberOfDirections = 10;
	private static Point velocity = new Point(2,2);
	private static int tolerance = 10;
	private static int length = 55;

	private static int density = 100;
	
	@Override
	protected void setUpParticleEngines() {
		//these 2 lines of code are from David's ParticleTestApplet class
		ImageIcon temp = new ImageIcon(
				AngleDistributionTester.class.getResource("explosion.png"));
		Image particleImage = temp.getImage();

		vcalculator = new VectorCalculator();
		addParticleEngine(density, particleImage, position, velocity, tolerance, length, angleSpan, numberOfDirections, false);

	}
}
