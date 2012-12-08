package games.squareattack.sprites;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import util.mathvector.MathVector2D;


/**
 * 
 * @author Ben Schwab
 * 
 */
public class SmallSquare extends Sprite {
    public static final int SIZE = 10;
    private Color myColor;
    private Point myCorner;
    private boolean myIsAlive;
    private int mySize;

    public SmallSquare (Color color) {
        super(new Dimension(SIZE, SIZE));
        myIsAlive = true;
        mySize = SIZE;

    }

    public void setCorner (Point corner) {
        myCorner = corner;
        setCenterLocation(myCorner);
    }

    @Override
    public void paint (Graphics2D pen) {
        pen.setColor(myColor);
        pen.fillRect(myCorner.x, myCorner.y, mySize, mySize);
        update();
    }

    public boolean isAlive () {
        return myIsAlive;
    }

    @Override
    public void update () {
        if (!myIsAlive) {
            setCorner(getCenter());
            super.update();
        }

    }

    public void kill () {
        myIsAlive = false;
        setVelocity(new MathVector2D(Math.random() * 20 - 10, Math.random() * 20 - 10));
    }

    public void setSize (int size) {
        mySize = size;
    }

}
