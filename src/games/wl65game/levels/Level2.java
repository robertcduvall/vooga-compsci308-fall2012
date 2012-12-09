package games.wl65game.levels;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.util.List;
import javax.swing.ImageIcon;
import vooga.shooter.level_editor.Level;
import vooga.shooter.gameObjects.Sprite;
import vooga.shooter.gameObjects.Enemy;
import vooga.shooter.gameObjects.intelligence.BounceAI;
import vooga.shooter.gameObjects.intelligence.RandomAI;
import games.wl65game.Game;

public class Level2 extends Level {

    private static final String ENEMY_IMAGEPATH = "games/wl65game/images/lettuce.png";
    private static final int NUMBER_OF_STAGES = 1;
    private static final int NUMBER_OF_ENEMIES = 2;
    private static final Dimension ENEMY_DIMENSION = new Dimension(40, 30);
    private static final Point ENEMY_VELOCITY = new Point(0, 5);
    private static final int ENEMY_DAMAGE = 1;

    private Game myGame;
    private Level myNextLevel;

    public Level2 (Game game) {
        super();
        myGame = game;
        setNextLevel(new Level3(myGame));
    }

    public void startLevel () {
        Enemy newEnemy = new Enemy(new Point(100, 50), ENEMY_DIMENSION,
                myGame.getCanvasDimension(), ENEMY_IMAGEPATH, ENEMY_VELOCITY,
                ENEMY_DAMAGE);
        newEnemy.setAI(new BounceAI(newEnemy, myGame.getPlayer()));
        myGame.addEnemy(newEnemy);
        Enemy newEnemy1 = new Enemy(new Point(200, 150), ENEMY_DIMENSION,
                myGame.getCanvasDimension(), ENEMY_IMAGEPATH, ENEMY_VELOCITY,
                ENEMY_DAMAGE);
        newEnemy1.setAI(new BounceAI(newEnemy1,myGame.getPlayer()));
        myGame.addEnemy(newEnemy1);

    }

    @Override
    public boolean winningConditionsMet () {
        return myGame.getEnemies().isEmpty();
    }
}

