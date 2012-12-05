package vooga.shooter.gameObjects.intelligence;

import java.awt.Point;
import vooga.shooter.gameObjects.Player;
import vooga.shooter.gameObjects.Sprite;
import util.mathvector.VectorCalculator;

/**
 * AI that bounces back and forth horizontally.
 * @author Stephen Hunt
 */
public class BounceAI extends AI{
    
    private boolean myGoingRight; // true for right, false for left

    public BounceAI (Sprite owner, Player thePlayer) {
        super(owner, thePlayer);
        myGoingRight = false;
    }

    /**
     * Sets the velocity of the its host left until it hits a wall, then right
     * until it hits a wall, etc. while changing the speed as little as possible.
     */
    @Override
    public void subCalculate (Sprite mySprite, Player myPlayer) {
        int currentMagnitude = 
            (int)Math.round(VectorCalculator.calculateMagnitude(mySprite.getVelocity()));
        Point moveVector = new Point(0,0);
        if (myGoingRight) {
            moveVector.x = currentMagnitude;
            if(!mySprite.checkBounds("right")) {
                myGoingRight = false;
            }
        }
        else {
            moveVector.x = (-1)*currentMagnitude;
            if(!mySprite.checkBounds("left")) {
                myGoingRight = true;
            }
        }
        if (myGoingRight) {
            moveVector.x = currentMagnitude;  
        }
        mySprite.setVelocity(moveVector);
    }
}