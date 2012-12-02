package util.particleEngine;

import java.awt.Image;
import java.awt.Point;
import javax.swing.ImageIcon;
import util.mathvector.*;


public class Trail extends ParticleSystem {

    private static double angleSpan = 90;
    private static int numberOfDirections = 15;
    private static MathVector2D velocity = new MathVector2D(0,0);
    private static int tolerance = 25;
    private static int length = 35;
    private static int density = 450;
    
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
                tolerance, length, angleSpan, numberOfDirections,  RGBAscales, RGBAtolerances, true);

    }
}
