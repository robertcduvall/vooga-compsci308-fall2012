package util.input.tests.android.squareattack;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;


/**
 * 
 * A simple "sprite" type class that has methods that can be mapped via the
 * setControl method in the class that contains the reference to the sprite.
 * 
 */
public class Square {

    private int myWidth = 50;
    private int myHeight = 50;
    private int myX;
    private int myY;
    private Color myColor;
    private boolean moveLeft;
    private boolean moveRight;
    private boolean moveUp;
    private boolean moveDown;
    private int nextXMove;
    private int nextYMove;
    private double myRotation = 0;
    private static final int MOVE_PIXEL = 3;
    private boolean[][] myHitPoints;
    private int lifeLeft;

    // attacker squares
    // attacked square
    // draw box
    // breakable square
    // etc. etc.

    /**
     * Create a new square at start position with the given color.
     * 
     * @param startPosition start position of the square
     * @param color the color of the square
     */
    public Square (Point startPosition, Color color) {
        myX = startPosition.x;
        myY = startPosition.y;
        myColor = color;
        myHitPoints array = new boolean[size][size];
        Arrays.fill(array, Boolean.FALSE);

    }

    /**
     * Draw the square with the graphics object pen.
     * 
     * @param pen
     */
    public void draw (Graphics2D pen) {
        move();
        pen.setColor(myColor);
        pen.fillRect(myX, myY, myWidth, myHeight);
    }

    /*
     * The following are public methods that are called via the input API's
     * reflective code. You specify these in the setControl method. This is an
     * example of how to handle movement that should occur while a key is
     * pressed.
     */
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

    /**
     * Based on the flag variables move the sprite.
     */
    public void move () {
        myX += nextXMove;
        myY += nextYMove;
        nextXMove = 0;
        nextYMove = 0;

        if (moveLeft) {
            myX += -MOVE_PIXEL;
        }
        if (moveRight) {
            myX += MOVE_PIXEL;
        }
        if (moveUp) {
            myY += -MOVE_PIXEL;
        }
        if (moveDown) {
            myY += MOVE_PIXEL;
        }
    }

    /*
     * This method is used with the joystick input option.
     */
    public void setNextMove (int x, int y) {
        nextXMove = x;
        nextYMove = y;

    }

    public void setRotation (double rotation) {
        myRotation = rotation;
    }

}
