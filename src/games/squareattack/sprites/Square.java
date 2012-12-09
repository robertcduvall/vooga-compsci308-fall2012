package games.squareattack.sprites;

import games.squareattack.objects.ExternalMathVector2D;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import util.mathvector.MathVector;
import util.mathvector.MathVector2D;
import util.particleEngine.ParticleSystem;
import util.particleEngine.Trail;


/**
 * 
 * @author Ben Schwab
 * 
 */
public class Square extends Sprite {

    private SmallSquare[][] mySquares;

    private int numSquares;
    private Color myColor;
    private int myRows;
    private int myColumns;
    private boolean moveLeft;
    private boolean moveRight;
    private boolean moveUp;
    private boolean moveDown;
    private MathVector2D myJSVector = new MathVector2D();
    private boolean needBounceVector;
    private MathVector2D bounceVector = new MathVector2D();
    private double mySpeed = 8;
    private MathVector2D myLastMovementVector = new MathVector2D();
    private List<ExternalMathVector2D> externalForces = new ArrayList<ExternalMathVector2D>();
    private int myMaxSquares;
    private double maxSpeed = 15;
    private double bonusSpeed = 9;

    public Square (Dimension size, Point startLocation, Color color) {
        super(size);
        setCenterLocation(startLocation);
        int width = Math.min(size.height, size.width);
        int height = width;
        myRows = height / SmallSquare.SIZE;
        myColumns = width / SmallSquare.SIZE;
        numSquares = myRows * myColumns;
        mySquares = new SmallSquare[myRows][myColumns];
        myMaxSquares = numSquares;
        myColor = color;
        fillMySquares();

    }

    private void fillMySquares () {

        for (int row = 0; row < myRows; row++) {

            for (int column = 0; column < myColumns; column++) {
                mySquares[row][column] = new SmallSquare(myColor);
            }
        }

    }

    @Override
    public void paint (Graphics2D pen) {

        Rectangle bounds = getBounds();
        pen.setColor(myColor);
        int y = bounds.y;
        for (int row = 0; row < myRows; row++) {
            int x = bounds.x;

            for (int column = 0; column < myColumns; column++) {
                if (mySquares[row][column].isAlive()) {

                    mySquares[row][column].setCorner(new Point(x, y));

                }
                mySquares[row][column].paint(pen);
                x += SmallSquare.SIZE;
            }
            y += SmallSquare.SIZE;
        }

        pen.setColor(Color.WHITE);
        pen.drawRect(bounds.x, bounds.y, bounds.width, bounds.height);

    }

    public void damage () {
        if (numSquares == 0) { return; }
        int randRow = (int) (Math.random() * myRows);
        int randCol = (int) (Math.random() * myColumns);
        // search again
        if (!mySquares[randRow][randCol].isAlive()) {
            damage();
        }
        else {
            mySquares[randRow][randCol].kill();

            numSquares--;
        }

    }

    @Override
    public void update () {
        adjustMaxSpeed();
        myLastMovementVector = new MathVector2D();
        if (moveLeft) {
            myLastMovementVector.addVector(new MathVector2D(-mySpeed, 0));
        }
        if (moveRight) {
            myLastMovementVector.addVector(new MathVector2D(mySpeed, 0));
        }
        if (moveUp) {
            myLastMovementVector.addVector(new MathVector2D(0, -mySpeed));
        }
        if (moveDown) {
            myLastMovementVector.addVector(new MathVector2D(0, mySpeed));
        }
        myLastMovementVector.addVector(myJSVector);
        addExternalForces();
        setVelocity(myLastMovementVector);
        super.update();
    }

    private void adjustMaxSpeed () {
        double factor = (double) numSquares / myMaxSquares;
        double sub = factor * bonusSpeed;
        mySpeed = maxSpeed - sub;

    }

    private void addExternalForces () {
        for (int i = externalForces.size() - 1; i >= 0; i--) {
            ExternalMathVector2D force = externalForces.get(i);
            force.detoriate();
            if (force.calculateMagnitude() > .05) {
                myLastMovementVector.addVector(force);
            }
            else {
                externalForces.remove(force);
            }

        }

    }

    public boolean isAlive () {
        return numSquares > 0;
    }

    public void enableMoveUp () {

        moveUp = true;
    }

    public void disableMoveUp () {
        moveUp = false;
    }

    public void enableMoveDown () {
        moveDown = true;
    }

    public void disableMoveDown () {
        moveDown = false;
    }

    public void enableMoveLeft () {
        moveLeft = true;
    }

    public void disableMoveLeft () {
        moveLeft = false;
    }

    public void enableMoveRight () {
        moveRight = true;
    }

    public void disableMoveRight () {
        moveRight = false;
    }

    public void setMovementVector (MathVector2D vector) {
        myJSVector = (MathVector2D) vector.scale(mySpeed);
    }

    public void addExternalForce (ExternalMathVector2D force) {

        externalForces.add(force);

    }

    public MathVector2D getLastMovementVector () {
        return myLastMovementVector;
    }
    
   

}
