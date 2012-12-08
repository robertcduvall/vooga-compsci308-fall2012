package vooga.shooter.implementation;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.util.List;
import javax.swing.ImageIcon;
import vooga.shooter.level_editor.Level;
import vooga.shooter.gameObjects.Sprite;
import vooga.shooter.gameObjects.Enemy;
import vooga.shooter.gameplay.Game;


/**
 * First level (initializes enemies, sets winning conditions)
 * 
 * @author Tommy Petrilak
 * 
 */
public class MainScreen extends Level {

    private static final int NUMBER_OF_STAGES = 1;
    private static final int NUMBER_OF_ENEMIES = 2;
    private static final Dimension ENEMY_DIMENSION = new Dimension(150, 150);
    private static final Point ENEMY_VELOCITY = new Point(0, 0);
    private static final int ENEMY_DAMAGE = 1;

    private Game myGame;
    private Level myNextLevel;

    public MainScreen (Game game) {
        super();
        myGame = game;
        setNextLevel(new Level1(myGame));
    }

    public void startLevel () {
        String imagePath = "vooga/shooter/images/background.gif";
        myGame.addEnemy(new Enemy(new Point(300, 200), ENEMY_DIMENSION,
                                  myGame.getCanvasDimension(), imagePath, ENEMY_VELOCITY,
                                  ENEMY_DAMAGE));
    }

    @Override
    public boolean winningConditionsMet () {
        return myGame.getEnemies().isEmpty();
    }
}
