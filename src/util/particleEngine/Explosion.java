package util.particleEngine;

import java.awt.Image;
import java.awt.Point;
import javax.swing.ImageIcon;
import util.mathvector.*;


/**
 * This implementation of ParticleSystem provides for an explosion effect by
 * creating/animating/drawing a large number of reddish orange sprites (there is
 * some intended variation in color). This system has two types sprites that
 * move radially outward from it's point of instantiation: explosion particles
 * and smoke particles. The explosion particles are brighter in color and have
 * less transparency than the smoke particles, but do not last as long on the
 * screen - creating an effect of smoke lingering after an explosion.
 * 
 * @author David Spruill
 *         Started by Kathleen
 * 
 */
public class Explosion extends ParticleSystem {

    /**
     * Constructs an explosion with a starting position
     * 
     * @param startingPosition the position at which to start the explosion
     */
    public Explosion (Point startingPosition) {
        super(new MathVector2D(startingPosition));
    }

    // The different velocities that the explosion can take
    private static MathVector2D[] velocities = { new MathVector2D(-2, 2),
            new MathVector2D(-2, -2), new MathVector2D(2, -2),
            new MathVector2D(2, 2), new MathVector2D(-Math.sqrt(8), 0),
            new MathVector2D(Math.sqrt(8), 0),
            new MathVector2D(0, -Math.sqrt(8)),
            new MathVector2D(0, Math.sqrt(8)) };

    // These following constants define this particular system
    private static int explosionTolerance = 50;
    private static int explosionLength = 30;
    private static int explosionDensity = 30;
    private static int explosionNumDirections = 10;
    private static int smokeTolerance = 50;
    private static int smokeLength = 50;
    private static int smokeDensity = 70;
    private static int smokeNumDirections = 1;

    // Each particle is drawn with a RGBA scaling factor that is chosen randomly
    // from this scale +/- these tolerances
    private static float[] RGBAscales = { 3f, 1.2f, 1.2f, 0.4f };
    private static float[] RGBAtolerances = { 0.2f, 0.2f, 0.2f, 0.1f };

    @Override
    protected void setUpParticleEngines () {
        ImageIcon temp = new ImageIcon(
                Explosion.class.getResource("orangeParticle.png"));
        Image explosionImage = temp.getImage();
        temp = new ImageIcon(Explosion.class.getResource("smokeParticle.png"));
        Image smokeImage = temp.getImage();

        for (int j = 0; j < velocities.length; j++) {
            addParticleEngine(smokeDensity, smokeImage, position,
                    velocities[j], smokeTolerance, smokeLength, 0.0,
                    smokeNumDirections, RGBAscales, RGBAtolerances, false);
            addParticleEngine(explosionDensity, explosionImage, position,
                    velocities[j], explosionTolerance, explosionLength, 0.0,
                    explosionNumDirections, RGBAscales, RGBAtolerances, false);
        }

    }

}