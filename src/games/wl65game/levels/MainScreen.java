package games.wl65game.levels;

import java.awt.Dimension;
import java.awt.Point;
import vooga.shooter.gameObjects.Enemy;
import vooga.shooter.level_editor.Level;
import games.wl65game.levels.Level1;
import games.wl65game.Game;

public class MainScreen extends Level {

    private static final int NUMBER_OF_STAGES = 1;
    private static final int NUMBER_OF_ENEMIES = 2;
    private static final Dimension ENEMY_DIMENSION = new Dimension(150, 150);
    private static final Point ENEMY_VELOCITY = new Point(0, 0);
    private static final int ENEMY_DAMAGE = 1;

    private Game myGame;
    private Level myNextLevel;

    public MainScreen (Game game, Level nextLevel) {
        super();
        myGame = game;
        setNextLevel(nextLevel);
    }

    public void startLevel () {
        String imagePath = "games/wl65game/images/title.png";
        myGame.addEnemy(new Enemy(new Point(300, 200), ENEMY_DIMENSION,
                                  myGame.getCanvasDimension(), imagePath, ENEMY_VELOCITY,
                                  ENEMY_DAMAGE));
    }

    @Override
    public boolean winningConditionsMet () {
        return myGame.getEnemies().isEmpty();
    }
}
