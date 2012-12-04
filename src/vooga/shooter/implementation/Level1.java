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
public class Level1 extends Level {

    private static final int NUMBER_OF_STAGES = 1;
    private static final int NUMBER_OF_ENEMIES = 1;
    private static final Dimension ENEMY_DIMENSION = new Dimension(20, 17);
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
        ImageIcon imageIcon = new ImageIcon(this.getClass().getResource("../images/alien.png"));
        Image enemyImage = imageIcon.getImage();
        for (int i = 0; i < NUMBER_OF_STAGES; i++) {
            for (int j = 0; j < NUMBER_OF_ENEMIES; j++) {
                myGame.addEnemy(new Enemy(new Point(300 + (150 * j), 150 * -i), ENEMY_DIMENSION,
                                          myGame.getCanvasDimension(), enemyImage, ENEMY_VELOCITY,
                                          ENEMY_DAMAGE));
            }
        }

    }

    @Override
    public boolean winningConditionsMet () {
        return myGame.getEnemies().isEmpty();
    }

}
