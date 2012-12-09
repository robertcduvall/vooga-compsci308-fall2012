package vooga.shooter.gameObjects.intelligence;

import java.awt.Point;
import vooga.shooter.gameObjects.Player;
import vooga.shooter.gameObjects.Sprite;
import util.mathvector.VectorCalculator;

/**
 * AI that moves directly towards the player character.
 * @author Stephen Hunt
 */
public class ChaseAI extends AI{

    public ChaseAI (Sprite owner, Player player) {
        super(owner, player);
    }

    /**
     * Sets the velocity of the its host directly towards the player while
     * changing the speed as little as possible.
     */
    @Override
    public void subCalculate (Sprite mySprite, Player myPlayer) {
        Point playerVector = new Point(
                myPlayer.getPosition().x - mySprite.getPosition().x,
                myPlayer.getPosition().y - mySprite.getPosition().y);
        double[] normalized = VectorCalculator.normalizeVector(playerVector);
        double currentMagnitude = 
            VectorCalculator.calculateMagnitude(mySprite.getVelocity());
        Point moveVector = new Point(
                (int)Math.round((normalized[0]*currentMagnitude)),
                (int)Math.round((normalized[1]*currentMagnitude)));
        mySprite.setVelocity(moveVector);
    }

}
