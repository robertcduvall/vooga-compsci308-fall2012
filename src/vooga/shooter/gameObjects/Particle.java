package vooga.shooter.gameObjects;

import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;


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

    public void draw(Graphics2D g) {
        Composite originalComposite = g.getComposite();
        g.setPaint(Color.blue);
        g.fill(blueSquare);
        g.setComposite(makeComposite(alpha));
        g.setPaint(Color.red);
        g.fill(redSquare);
        g.setComposite(originalComposite);
    }

    @Override
    public void update() {

    }

    @Override
    public void delete() {

    }

    @Override
    public Point getPosition() {

    }

    public void setPosition(Point position) {

    }

    @Override
    public Point getDirection() {

    }

    public void setDirection(Point direction) {

    }

    @Override
    public Image getImage() {

    }

    public void setImage(Image image) {

    }

    @Override
    public List<Sprite> collisions() {

    }
}
