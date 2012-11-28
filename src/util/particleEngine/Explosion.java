package util.particleEngine;

import java.awt.Image;
import java.awt.Point;
import javax.swing.ImageIcon;
import util.calculator.VectorCalculator;


/**
 * Tests an outward-moving particle engine effect.
 * There are 4 "directions of origin" - particles
 * have one of the eight following directions:
 * (-1,-1), (1,1), (-1,1), (1,-1).
 * 
 * @author Kathleen
 *         edited by David Spruill
 * 
 */
public class Explosion extends ParticleSystem {

    private static Point ourPosition = new Point(400, 400);
    private static Point[] ourVelocities = {new Point(-1, 1), new Point(-1, -1), new Point(1, -1),
                                         new Point(1, 1), new Point(-1, 0), new Point(1, 0),
                                         new Point(0, -1), new Point(0, 1) };
    private static int ourTolerance = 30;
    private static int ourLength = 20;

    private static int ourDensityStartNum = 20;

    private VectorCalculator myVcalculator;

    @Override
    protected void setUpParticleEngines () {
        ImageIcon temp = new ImageIcon(Explosion.class.getResource("explosion.png"));
        Image explosionImage = temp.getImage();
        temp = new ImageIcon(Explosion.class.getResource("smokeParticle.png"));
        Image smokeImage = temp.getImage();

        myVcalculator = new VectorCalculator();
        for (int j = 0; j < ourVelocities.length; j++) {
            addParticleEngine(ourDensityStartNum + 20, explosionImage, ourPosition,
                              myVcalculator.scaleVelocity(2, ourVelocities[j]),
                              ourTolerance, ourLength, 0.0, 5, false);
            addParticleEngine(ourDensityStartNum, smokeImage, ourPosition,
                              myVcalculator.scaleVelocity(2, ourVelocities[j]),
                              ourTolerance, ourLength + 15, 0.0, 1, false);
        }

    }

}
