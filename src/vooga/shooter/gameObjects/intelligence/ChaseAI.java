package vooga.shooter.gameObjects.intelligence;

import java.awt.Point;
import vooga.shooter.gameObjects.Player;
import vooga.shooter.gameObjects.Sprite;
import vooga.shooter.gameplay.Game;
import util.calculator.VectorCalculator;

/**
 * AI that moves directly towards the player character.
 * @author Stephen Hunt
 */
public class ChaseAI extends AI{

    public ChaseAI (Sprite owner, Game theGame, Player thePlayer) {
        super(owner, theGame, thePlayer);
    }

    /**
     * Sets the velocity of the its host directly towards the player while
     * changing the speed as little as possible.
     */
    @Override
    public void subCalculate (Sprite mySprite, Player myPlayer, Game myGame) {
        Point playerVector = new Point(
                mySprite.getPosition().x - myPlayer.getPosition().x,
                mySprite.getPosition().y - myPlayer.getPosition().y);
        double[] normalized = VectorCalculator.normalizeVector(playerVector);
        double currentMagnitude = 
            VectorCalculator.calculateMagnitude(mySprite.getVelocity());
        Point moveVector = new Point((int)(normalized[0]*currentMagnitude),
                (int)(normalized[1]*(currentMagnitude)));
        mySprite.setVelocity(moveVector);
    }

}
