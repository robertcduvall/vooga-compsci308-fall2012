package vooga.shooter.gameObjects;

import java.awt.Color;
import java.awt.Composite;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.util.List;
import vooga.shooter.graphics.Canvas;


/**
 * A particle object is essentially a sprite that also maintains its velocity,
 * direction, and a count of how many cycles it has existed, which it will use
 * to draw the sprite with changing alpha levels so that it will fade away over
 * time
 * 
 * @author David Spruill
 * 
 */
public class Particle extends Sprite {
    private int durationLimit;
    private int durationExisted;

    /**
     * Creates a particle to use in the particle effect implemented by
     * the graphics package.
     * @param position the center position of the image
     * @param size the size of the image to use
     * @param image the image to use
     * @param velocity the velocity of the particle
     */
    public Particle (Point position, Dimension size, Image image, Point velocity) {
        super(position, size, image, velocity);
    }

    public void draw (Graphics2D g) {
        // This code has errors. Idk what you were even
        // trying to do so I commented it out.
        // please remove or fix.
        // - Alex
        
        //i also don't know what this class is supposed
        //to do, but i changed the constructor and
        //some methods (that didn't do anything yet anyway)
        //so that it would work with the Sprite.java class
        // - Jesse
        
//        Composite originalComposite = g.getComposite();
//        g.setPaint(Color.blue);
//        g.fill(blueSquare);
//        g.setComposite(makeComposite(alpha));
//        g.setPaint(Color.red);
//        g.fill(redSquare);
//        g.setComposite(originalComposite);
    }

    public void continueUpdate(Canvas c) {

    }

    public List<Sprite> collisions () {
        return null;
    }

    public void collide (Bullet b) {
        
    }

    public void collide (Player p) {
        
    }

    public void collide (Enemy e) {
        
    }

    /**
     * Returns the type of this sprite.
     * @return "particle"
     */
    public String getType () {
        return "particle";
    }

    public void continuePaint (Graphics pen) {
        
    }
}
