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
            new MathVector2D(1, 1), new MathVector2D(-1, 0),
            new MathVector2D(1, 0), new MathVector2D(0, -1),
            new MathVector2D(0, 1) };
    private final MathVector2D velocity = new MathVector2D(0, 0);
    private static int tolerance = 30;
    private static int length = 20;

    private static int densityStartNum = 70;

    @Override
    protected void setUpParticleEngines () {
        ImageIcon temp = new ImageIcon(
                Explosion.class.getResource("orangeParticle.png"));
        Image explosionImage = temp.getImage();
        temp = new ImageIcon(Explosion.class.getResource("smokeParticle.png"));
        Image smokeImage = temp.getImage();

        for (int j = 0; j < velocities.length; j++) {
            addParticleEngine(densityStartNum + 20, explosionImage, position, velocities[j], tolerance, length, 0.0, 5, false);
            addParticleEngine(densityStartNum - 40, smokeImage, position, velocities[j], tolerance, length + 15, 0.0, 1,
                    false);
        }

    }

}