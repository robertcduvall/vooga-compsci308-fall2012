package games.spruillGame.levels;

import java.awt.Dimension;
import java.awt.Point;
import java.util.Random;
import vooga.shooter.gameObjects.Enemy;
import games.spruillGame.Game.Game;


public class LoseGame extends Level {

    private static final String SCREEN_IMAGEPATH = "games/spruillGame/images/LostGame.png";
    private static final Dimension ENEMY_DIMENSION = new Dimension(728, 50);
    private static final int ENEMY_DAMAGE = 100;
    private static final int XOFFSET = -80;
    private static final int YOFFSET = 200;

    private Game myGame;

    /**
     * the first level of the game
     * 
     * @param game
     */
    public LoseGame (Game game) {
        super();
        myGame = game;
        setNextLevel(null);
    }

    public void startLevel () {
        Random rand = new Random();
        myGame.addEnemy(new Enemy(new Point(ENEMY_DIMENSION.width / 2 + XOFFSET,
                ENEMY_DIMENSION.height / 2 + YOFFSET), ENEMY_DIMENSION, myGame
                .getCanvasDimension(), SCREEN_IMAGEPATH, new Point(0, 0),
                ENEMY_DAMAGE));

    }

    @Override
    public boolean winningConditionsMet () {
        return false;
    }

}
