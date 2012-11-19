package util.input.tests.twocontroller;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;


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

    public Square (Point startPosition, Color color) {
        myX = startPosition.x;
        myY = startPosition.y;
        myColor = color;
    }

    public void draw (Graphics2D pen) {
        move();
        pen.setColor(myColor);
        pen.fillRect(myX, myY, myWidth, myHeight);
    }

    public void toggleMoveLeft () {
        moveLeft = !moveLeft;
    }

    public void toggleMoveRight () {
        moveRight = !moveRight;
    }

    public void toggleMoveUp () {
        moveUp = !moveUp;
    }

    public void toggleMoveDown () {
        moveDown = !moveDown;
    }

    public void move () {
        myX+=nextXMove;
        myY+=nextYMove;
        nextXMove=0;
        nextYMove=0;
        
        if (moveLeft) {
            myX += -3;
        }
        if (moveRight) {
            myX += 3;
        }
        if (moveUp) {
            myY += -3;
        }
        if (moveDown) {
            myY += 3;
        }
    }

    public void shrink () {
        myWidth += -3;
        myHeight += -3;
    }

    public void grow () {
        myWidth += 3;
        myWidth += 3;
    }

    public void setNextMove (int x, int y) {
        nextXMove =x;
        nextYMove =y;
        
    }

}
