package util.particleEngine;

import java.awt.Image;
import java.awt.Point;
import javax.swing.ImageIcon;
import util.calculator.VectorCalculator;


/**
 * 
 * @author David Spruill
 * 
 */
public class Trail extends ParticleSystem {

    private static Image ourParticleImage;
    private static Point ourPosition = new Point(300, 300);
    private static VectorCalculator ourVcalculator;
    private static double ourAngleSpan = 90;
    private static int ourNumberOfDirections = 10;
    private static Point ourVelocity = new Point(0, 2);
    private static int ourTolerance = 20;
    private static int ourLength = 50;

    private static int ourDensity = 10;

    @Override
    protected void setUpParticleEngines () {
        // these 2 lines of code are from David's ParticleTestApplet class
        ImageIcon temp = new ImageIcon(Trail.class.getResource("particle.png"));
        Image particleImage = temp.getImage();

        ourVcalculator = new VectorCalculator();
        addParticleEngine(ourDensity, particleImage, ourPosition, ourVelocity, 
                          ourTolerance, ourLength, ourAngleSpan, ourNumberOfDirections, true);

    }
}
