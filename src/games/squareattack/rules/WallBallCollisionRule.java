package games.squareattack.rules;

import games.squareattack.gui.PowerBar;
import games.squareattack.sprites.Square;
import games.squareattack.sprites.WallBall;
import java.util.List;


/**
 * 
 * @author Ben Schwab
 * 
 */
public class WallBallCollisionRule implements GameRule {

    private List<Square> myAttackers;
    private Square myDefender;
    private List<WallBall> myWallBalls;
    private PowerBar myPowerBar;

    public WallBallCollisionRule (List<Square> attackers, Square defender,
                                  List<WallBall> wallBalls, PowerBar power) {
        myAttackers = attackers;
        myDefender = defender;
        myWallBalls = wallBalls;
        myPowerBar = power;
    }

    @Override
    public void checkRule () {

        for (int i = myWallBalls.size() - 1; i >= 0; i--) {
            WallBall curBall = myWallBalls.get(i);
            for (Square attacker : myAttackers) {
                if (attacker.intersects(curBall)) {
                    myWallBalls.remove(curBall);
                }
            }
            if (myDefender.intersects(curBall)) {
                myWallBalls.remove(curBall);
                myPowerBar.addPower(20);
            }
        }

    }

}
