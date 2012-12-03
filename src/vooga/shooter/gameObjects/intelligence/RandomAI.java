package vooga.shooter.gameObjects.intelligence;

import java.awt.Point;
import vooga.shooter.gameObjects.Player;
import vooga.shooter.gameObjects.Sprite;
import vooga.shooter.gameplay.Game;
import util.mathvector.VectorCalculator;

/**
 * AI that moves randomly.
 * @author Stephen Hunt
 */
public class RandomAI extends AI{

    private int myTimer;
    
    public RandomAI (Sprite owner, Game theGame, Player thePlayer) {
        super(owner, theGame, thePlayer);
        myTimer = 0;
    }

    /**
     * Sets the velocity of the its host to a random vector while
     * changing the speed as little as possible.
     */
    @Override
    public void subCalculate (Sprite mySprite, Player myPlayer, Game myGame) {
        if (myTimer <= 0){
            double currentMagnitude = 
                VectorCalculator.calculateMagnitude(mySprite.getVelocity());
            int newX = (int)(Math.random()*currentMagnitude);
            int newY = (int)(Math.sqrt((currentMagnitude*currentMagnitude) - (newX*newX)));
            mySprite.setVelocity(new Point(newX, newY));
            myTimer = 10;
        }
        myTimer--;
    }

}