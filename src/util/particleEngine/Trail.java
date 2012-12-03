package util.particleEngine;

import java.awt.Image;
import java.awt.Point;
import javax.swing.ImageIcon;
import util.mathvector.*;


public class Trail extends ParticleSystem {

    /**
     * These following constants define the particleEngine
     * 
     * tolerance - the variation allowed in the particles' velocity
     * length - the average time a particle exists before fading away
     * numDirections - the number of directions that particles start out at.
     *                 These are in reference to the origin point of the effect
     * density - the number of particles to be created for each particleEngine
     * velocity - the starting velocity for the particles.
     * loop - whether or not the particleEngines will continuously loop
     * angleSpan - quite literally the span of angles that the particles can move on.
     */
    private static double angleSpan = 90;
    private static int numDirections = 15;
    private static MathVector2D velocity = new MathVector2D(0,0);
    private static int tolerance = 25;
    private static int length = 35;
    private static int density = 450;
    private static boolean loop = true;
    
    // Each particle is drawn with a RGBA scaling factor that is chosen randomly
    // from this scale +/- these tolerances
    private static float[] RGBAscales = { 0f, 1.8f, 2.4f, 0.4f };
    private static float[] RGBAtolerances = {0f, 2.2f, 2.2f, 0.3f};

    public Trail (Point startingPosition) {
        super(new MathVector2D(startingPosition));
    }

    @Override
    protected void setUpParticleEngines () {
        ImageIcon temp = new ImageIcon(Trail.class.getResource("particle.png"));
        Image particleImage = temp.getImage();

        addParticleEngine(density, particleImage, position, velocity,
                tolerance, length, angleSpan, numDirections,  RGBAscales, RGBAtolerances, loop);

    }
}
