package games.par24game.intelligence;

import java.awt.Point;
import util.mathvector.VectorCalculator;
import vooga.shooter.gameObjects.Player;
import vooga.shooter.gameObjects.Sprite;
import vooga.shooter.gameObjects.intelligence.AI;


/**
 * This AI implementation causes the enemy ship to go not where the player is,
 * but where they will be. It plots a course to try to intercept the player,
 * assuming that they continue in a straight line. Depending on player speed vs.
 * AI speed, it may be impossible to escape.
 * 
 * @author Patrick Royal
 * 
 */
public class InterceptorAI extends AI {

    /**
     * Constructor for InterceptorAI
     * @param owner the sprite to whom this movement will be applied
     * @param thePlayer the human player
     */
    public InterceptorAI (Sprite owner, Player thePlayer) {
        super(owner, thePlayer);
    }

    /**
     * Sets the velocity of the its host on an intercept course with the
     * player's last movement.
     * @param mySprite the sprite to whom this movement will be applied
     * @param myPlayer the human player
     */
    @Override
    public void subCalculate (Sprite mySprite, Player myPlayer) {
        Point playerPosition = new Point(myPlayer.getPosition());
        Point playerVelocity = new Point(myPlayer.getVelocity());
        Point myPosition = new Point(mySprite.getPosition());
        //First, we apply a version of the chase algorithm, telling the enemy to move
        //directly towards the player
        Point myNewVelocity = new Point(playerPosition.x - myPosition.x,
                playerPosition.y - myPosition.y);
        double[] normalized = VectorCalculator.normalizeVector(myNewVelocity);
        double currentMagnitude = VectorCalculator.calculateMagnitude(
                mySprite.getVelocity());
        myNewVelocity = new Point(
                (int) Math.round(normalized[0] * currentMagnitude),
                (int) Math.round(normalized[1] * currentMagnitude));
        //Now, we add the player's velocity to the enemy's velocity, so that the enemy
        //will now move towards where the player will be.
        myNewVelocity.x = Math.max(myNewVelocity.x, myNewVelocity.x + playerVelocity.x);
        myNewVelocity.y = Math.max(myNewVelocity.y, myNewVelocity.y + playerVelocity.y);
        normalized = VectorCalculator.normalizeVector(myNewVelocity);
        currentMagnitude = VectorCalculator.calculateMagnitude(
                mySprite.getVelocity());
        myNewVelocity = new Point(
                (int) Math.round(normalized[0] * currentMagnitude),
                (int) Math.round(normalized[1] * currentMagnitude));
        mySprite.setVelocity(myNewVelocity);
    }
}
