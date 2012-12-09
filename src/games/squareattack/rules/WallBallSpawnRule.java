package games.squareattack.rules;

import games.squareattack.engine.GameManager;
import games.squareattack.sprites.WallBall;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.List;


/**
 * 
 * @author Ben Schwab
 * 
 */
public class WallBallSpawnRule implements GameRule {

    private Rectangle myBoard;
    private int spawnTime = GameManager.GAME_FPS * 5;
    private int curSpawnCounter;
    private List<WallBall> myWallBalls;

    public WallBallSpawnRule (Rectangle board, List<WallBall> wallBalls) {
        myWallBalls = wallBalls;
        myBoard = board;
    }

    @Override
    public void checkRule () {
        curSpawnCounter++;
        if (curSpawnCounter >= spawnTime) {
            WallBall wB = new WallBall(new Dimension(20, 20));
            wB.setCenterLocation(new Point((int) (Math.random() * myBoard.width + myBoard.x),
                                           (int) (Math.random() * myBoard.height + myBoard.y)));
            myWallBalls.add(wB);
            curSpawnCounter = 0;
        }

    }

}
