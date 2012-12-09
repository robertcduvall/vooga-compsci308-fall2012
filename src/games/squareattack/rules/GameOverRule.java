package games.squareattack.rules;

import games.squareattack.engine.GameManager;
import games.squareattack.sprites.Square;


/**
 * 
 * @author Ben Schwab
 * 
 */
public class GameOverRule implements GameRule {

    private Square myDefender;
    private GameManager myManager;

    public GameOverRule (Square defenderSquare, GameManager mangager) {
        myDefender = defenderSquare;
        myManager = mangager;
    }

    @Override
    public void checkRule () {
        if (!myDefender.isAlive()) {
            myManager.gameOver();
        }

    }

}
