package games.wl65game.levels;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.util.List;
import javax.swing.ImageIcon;
import vooga.shooter.level_editor.Level;
import vooga.shooter.gameObjects.Sprite;
import vooga.shooter.gameObjects.Enemy;
import games.wl65game.Game;

public class Level1 extends Level {

    private static final String ENEMY_IMAGEPATH = "games/wl65game/images/onion.png";
    private static final int NUMBER_OF_STAGES = 1;
    private static final int NUMBER_OF_ENEMIES = 1;
    private static final Dimension ENEMY_DIMENSION = new Dimension(40, 30);
    private static final Point ENEMY_VELOCITY = new Point(0, 5);
    private static final int ENEMY_DAMAGE = 1;

    private Game myGame;
    private Level myNextLevel;

    public Level1 (Game game) {
        super();
        myGame = game;
        setNextLevel(new Level2(myGame));
    }

    public void startLevel () {
        for (int i = 0; i < NUMBER_OF_STAGES; i++) {
            for (int j = 0; j < NUMBER_OF_ENEMIES; j++) {
                myGame.addEnemy(new Enemy(new Point(300 + (150 * j), 150 * -i), ENEMY_DIMENSION,
                                          myGame.getCanvasDimension(), ENEMY_IMAGEPATH, ENEMY_VELOCITY,
                                          ENEMY_DAMAGE));
            }
        }

    }

    @Override
    public boolean winningConditionsMet () {
        return myGame.getEnemies().isEmpty();
    }

}
