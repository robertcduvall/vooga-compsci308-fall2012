package util.particleEngine;

import java.awt.Image;
import java.awt.Point;
import javax.swing.ImageIcon;
import util.mathvector.*;


public class Trail extends ParticleSystem {

    private final double angleSpan = 90;
    private final int numberOfDirections = 10;
    private static MathVector2D velocity = new MathVector2D(0,2);
    private final int tolerance = 20;
    private final int length = 65;

    private static int density = 1750;

    public Trail (Point startingPosition) {
        super(new MathVector2D(startingPosition));
    }

    @Override
    protected void setUpParticleEngines () {
        ImageIcon temp = new ImageIcon(Trail.class.getResource("orangeParticle.png"));
        Image particleImage = temp.getImage();

        addParticleEngine(density, particleImage, position, velocity,
                tolerance, length, angleSpan, numberOfDirections, true);

    }
}
