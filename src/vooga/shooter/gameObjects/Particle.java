package vooga.shooter.gameObjects;

import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.util.List;


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
    private Image image;
    private Point velocity;
    private Point position;
    private int durationLimit;
    private int durationExisted;

    public void draw (Graphics2D g) {
        // This code has errors. Idk what you were even
        // trying to do so I commented it out.
        // please remove or fix.
        // - Alex
        
//        Composite originalComposite = g.getComposite();
//        g.setPaint(Color.blue);
//        g.fill(blueSquare);
//        g.setComposite(makeComposite(alpha));
//        g.setPaint(Color.red);
//        g.fill(redSquare);
//        g.setComposite(originalComposite);
    }

    public void update () {

    }

    public void delete () {

    }

    public Point getPosition () {
        // TODO: fill in method details
        return null;

    }

    public Point getDirection () {
        // TODO: fill in method details
        return null;

    }

    public Image getImage () {
        // TODO: fill in method details
        return null;
    }

    public List<Sprite> collisions () {
        // TODO: fill in method details
        return null;
    }

    @Override
    public void draw (Graphics g) {
        // TODO Auto-generated method stub

    }

    @Override
    public void setPosition (Point position) {
        // TODO Auto-generated method stub

    }

    @Override
    public void setDirection (Point direction) {
        // TODO Auto-generated method stub

    }

    @Override
    public void setImage (Image image) {
        // TODO Auto-generated method stub

    }
}
