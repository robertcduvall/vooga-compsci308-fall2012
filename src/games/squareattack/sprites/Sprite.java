package games.squareattack.sprites;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import util.mathvector.MathVector;
import util.mathvector.MathVector2D;

/**
 * 
 * @author Ben Schwab
 *
 */
public abstract class Sprite {

    private MathVector2D myVelocity;
    private double myX;
    private double myY;
    private Image myImage;
    private int myWidth;
    private int myHeight;

    public Sprite (Dimension boundingBox) {
        myWidth = boundingBox.width;
        myHeight = boundingBox.height;
        myVelocity = new MathVector2D();
    }

    public abstract void paint (Graphics2D pen);

    public void update () {
        myX += myVelocity.getComponent(0);
        myY += myVelocity.getComponent(1);
    }

    public void setVelocity (MathVector2D v) {
        myVelocity = v;
    }

    public void addToVelocity (MathVector2D v) {
        myVelocity.addVector(v);
    }

    public void setCenterLocation (Point location) {
        myX = location.x;
        myY = location.y;
    }

    public Rectangle getBounds () {
        return new Rectangle((int) (myX - myWidth / 2), (int) (myY - myHeight / 2), myWidth,
                             myHeight);
    }

    public boolean intersects (Sprite other) {
        return getBounds().intersects(other.getBounds());

    }
    
    public Point getCenter(){
        return new Point((int)myX, (int)myY);
    }

   

    public void setCorner (Point point) {
        myX = point.x + myWidth / 2;
        myY = point.y +myHeight/2;
        
    }

}
