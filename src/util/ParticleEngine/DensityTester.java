package util.ParticleEngine;

import java.awt.Image;
import java.awt.Point;

/**
 * Stores a List of ParticleEngine with varying density and all other parameters the same.
 * 
 * @author Kathleen
 *
 */
		
public class DensityTester extends ParticleEngineTestingUnit{
	//Need to be given default values
	private static Image particleImage;
	private static Point position = new Point (100,100);
	private static Point velocity = new Point(0,-1);
    private static int tolerance = 15;
    private static int length = 1000;
	
    private static int densityStartNum = 1000;
    private static int densityEndNum = 100001;
    private static int densityStepCount = 10000;
    
    @Override
	protected void setUpParticleEngines() {
		for (int k = densityStartNum; k<densityEndNum; k+=densityStepCount){
			addParticleEngine(k, particleImage, position, velocity, tolerance, length);
		}
	}

	
}
