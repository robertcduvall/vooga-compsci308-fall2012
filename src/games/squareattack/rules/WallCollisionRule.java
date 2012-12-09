package games.squareattack.rules;

import games.squareattack.objects.WallBuilder;
import games.squareattack.sprites.Square;
import java.util.ArrayList;
import java.util.List;


/**
 * 
 * @author Ben Schwab
 * 
 */
public class WallCollisionRule implements GameRule {

    private WallBuilder myWallBuilder;
    private List<Square> myEnemies;

    public WallCollisionRule (WallBuilder wallBuilder, List<Square> enemySquares) {
        myWallBuilder = wallBuilder;
        myEnemies = enemySquares;
    }

    @Override
    public void checkRule () {
        for (Square s : myEnemies) {
            myWallBuilder.collide(s);
        }

    }

}
