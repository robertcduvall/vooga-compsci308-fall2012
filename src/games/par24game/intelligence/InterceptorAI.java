package games.par24game.intelligence;

import java.awt.Point;
import util.mathvector.VectorCalculator;
import vooga.shooter.gameObjects.Player;
import vooga.shooter.gameObjects.Sprite;
import vooga.shooter.gameObjects.intelligence.AI;


public class InterceptorAI extends AI {

    public InterceptorAI (Sprite owner, Player thePlayer) {
        super(owner, thePlayer);
    }

    /**
     * Sets the velocity of the its host on an intercept course with the
     * player's last movement.
     */
    @Override
    public void subCalculate (Sprite mySprite, Player myPlayer) {
        Point playerPosition = new Point(myPlayer.getPosition());
        Point playerVelocity = new Point(myPlayer.getVelocity());
        Point myPosition = new Point(mySprite.getPosition());
        Point myNewVelocity = new Point((playerPosition.x - myPosition.x)
                * (1 + playerVelocity.x), (playerPosition.y - myPosition.y)
                * (1 + playerVelocity.y));
        double[] normalized = VectorCalculator.normalizeVector(myNewVelocity);
        double currentMagnitude = VectorCalculator.calculateMagnitude(mySprite
                .getVelocity());
        Point moveVector = new Point(
                (int) Math.round((normalized[0] * currentMagnitude)),
                (int) Math.round((normalized[1] * currentMagnitude)));
        mySprite.setVelocity(moveVector);
    }
}
