package games.wl65game.levels;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.util.List;
import javax.swing.ImageIcon;
import vooga.shooter.level_editor.Level;
import vooga.shooter.gameObjects.Sprite;
import vooga.shooter.gameObjects.Enemy;
import vooga.shooter.gameObjects.intelligence.ChaseAI;
import games.wl65game.Game;
import games.wl65game.Game;

public class Level4 extends Level {

    private static final String ENEMY_IMAGEPATH = "games/wl65game/images/round.png";
    private static final int NUMBER_OF_STAGES = 3;
    private static final int NUMBER_OF_ENEMIES = 3;
    private static final Dimension ENEMY_DIMENSION = new Dimension(100, 100);
    private static final Point ENEMY_VELOCITY = new Point(5, 0);
    private static final int ENEMY_DAMAGE = 1;

    private Game myGame;
    private Level myNextLevel;

    public Level4 (Game game) {
        super();
        myGame = game;
        setNextLevel(new WonGame(myGame));
    }

    public void startLevel () {
        Enemy newEnemy = new Enemy(new Point(100, 50), ENEMY_DIMENSION,
                myGame.getCanvasDimension(), ENEMY_IMAGEPATH , ENEMY_VELOCITY,
                ENEMY_DAMAGE);
        newEnemy.setAI(new ChaseAI(newEnemy,myGame.getPlayer()));
        myGame.addEnemy(newEnemy);

    }

    @Override
    public boolean winningConditionsMet () {
        return myGame.getEnemies().isEmpty();
    }

}
