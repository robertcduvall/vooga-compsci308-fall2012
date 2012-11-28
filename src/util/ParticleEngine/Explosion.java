package util.particleEngine;

import java.awt.Image;
import java.awt.Point;
import javax.swing.ImageIcon;
import util.calculator.VectorCalculator;



/**
 * Tests an outward-moving particle engine effect. There are 4 "directions of origin" - particles have one of the eight following directions:
 * (-1,-1), (1,1), (-1,1), (1,-1).
 * 
 * @author Kathleen
 *      edited by David Spruill
 *
 */
public class Explosion extends ParticleSystem{

	private static Point position = new Point (400,400);
	private static Point[] velocities = {new Point(-1,1), new Point(-1,-1), new Point(1,-1), new Point(1,1), new Point(-1,0), new Point(1,0), new Point(0,-1), new Point(0,1)};
	private static int tolerance = 30;
	private static int length = 20;

	private static int densityStartNum = 20;

	private VectorCalculator vcalculator;
	
	@Override
	protected void setUpParticleEngines() {
		ImageIcon temp = new ImageIcon(
				Explosion.class.getResource("explosion.png"));
		Image explosionImage = temp.getImage();
                temp = new ImageIcon(
                        Explosion.class.getResource("smokeParticle.png"));
                Image smokeImage = temp.getImage();
		
		vcalculator = new VectorCalculator();
		for (int j=0; j<velocities.length; j++){
			addParticleEngine(densityStartNum+20, explosionImage, position, vcalculator.scaleVelocity(2,velocities[j]), tolerance, length,0.0,5,false);
			addParticleEngine(densityStartNum, smokeImage, position, vcalculator.scaleVelocity(2,velocities[j]), tolerance, length+15,0.0,1,false);
		}

	}

}