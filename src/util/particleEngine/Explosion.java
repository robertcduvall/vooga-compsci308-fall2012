package util.particleEngine;

import java.awt.Image;
import java.awt.Point;
import javax.swing.ImageIcon;
import util.mathvector.*;


/**
 * Tests an outward-moving particle engine effect. There are 8
 * "directions of origin" - particles have one of the eight following
 * directions:
 * (-1,1), (-1,-1), (1,-1), (1,1), (-1,0), (1,0), (0,-1), (0,1).
 * 
 * @author Kathleen
 *         edited by David Spruill
 * 
 */
public class Explosion extends ParticleSystem {

    public Explosion (Point startingPosition) {
        super(new MathVector2D(startingPosition));
    }

    private static MathVector2D[] velocities = { new MathVector2D(-1, 1),
            new MathVector2D(-1, -1), new MathVector2D(1, -1),
            new MathVector2D(1, 1), new MathVector2D(-Math.sqrt(2), 0),
            new MathVector2D(Math.sqrt(2), 0), new MathVector2D(0, -Math.sqrt(2)),
            new MathVector2D(0, Math.sqrt(2)) };
    
    private static int explosionTolerance = 40;
    private static int explosionLength = 20;
    private static int explosionDensity = 210;
    private static int smokeTolerance = 40;
    private static int smokeLength = 30;
    private static int smokeDensity = 100;
    
    private static float[] RGBAscales = { 3f, 1.8f, 2.4f, 0.4f };
    private static float[] RGBAtolerances = {0.2f, 0.2f, 0.2f, 0.1f};

    @Override
    protected void setUpParticleEngines () {
        ImageIcon temp = new ImageIcon(
                Explosion.class.getResource("orangeParticle.png"));
        Image explosionImage = temp.getImage();
        temp = new ImageIcon(Explosion.class.getResource("smokeParticle.png"));
        Image smokeImage = temp.getImage();

        for (int j = 0; j < velocities.length; j++) {
            addParticleEngine(smokeDensity, smokeImage, position, velocities[j], smokeTolerance, smokeLength, 0.0, 1,
                    false);
            addParticleEngine(explosionDensity, explosionImage, position, velocities[j], explosionTolerance, explosionLength, 0.0, 5, false);
        }

    }

}