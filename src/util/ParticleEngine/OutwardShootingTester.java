package util.ParticleEngine;

import java.awt.Image;
import java.awt.Point;

import javax.swing.ImageIcon;

import util.calculator.VectorCalculator;

/**
 * Tests an outward-moving particle engine effect. There are 4 "directions of origin" - particles have one of the four following directions:
 * (-1,-1), (1,1), (-1,1), (1,-1).
 * 
 * @author Kathleen
 *
 */
public class OutwardShootingTester extends ParticleEngineTestingUnit{

	private static Image particleImage;
	private static Point position = new Point (300,300);
	private static Point velocity = new Point(0,-1);
	private static Point[] velocities = {new Point(-1,1), new Point(-1,-1), new Point(1,-1), new Point(1,1)};
	private static int tolerance = 30;
	private static int length = 1000;

	private static int densityStartNum = 100;
	private static int densityEndNum = 100001;
	private static int densityStepCount = 10000;

	private VectorCalculator vcalculator;
	
	@Override
	protected void setUpParticleEngines() {
		//these 2 lines of code are from David's ParticleTestApplet class
		ImageIcon temp = new ImageIcon(
				OutwardShootingTester.class.getResource("explosion.png"));
		Image particleImage = temp.getImage();
		
		vcalculator = new VectorCalculator();
		for (int j=0; j<velocities.length-3; j++){
			addParticleEngine(densityStartNum, particleImage, position, vcalculator.scaleVelocity(10,velocities[j]), tolerance, length,0.0,1);
		}

	}

}
