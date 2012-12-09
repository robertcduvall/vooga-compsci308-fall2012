package games.wl65game.levels;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.util.List;
import javax.swing.ImageIcon;
import vooga.shooter.level_editor.Level;
import vooga.shooter.gameObjects.Sprite;
import vooga.shooter.gameObjects.Enemy;
import games.wl65game.Game;
import games.wl65game.Game;

public class LostGame extends Level {

    private static final String ENEMY_IMAGEPATH = "games/wl65game/images/lost.png";
    private static final int NUMBER_OF_STAGES = 1;
    private static final int NUMBER_OF_ENEMIES = 1;
    private static final Dimension ENEMY_DIMENSION = new Dimension(150, 150);
    private static final Point ENEMY_VELOCITY = new Point(0, 0);
    private static final int ENEMY_DAMAGE = 1;

    private Game myGame;
    private Level myNextLevel;

    public LostGame (Game Game) {
        super();
        myGame = Game;
        myNextLevel = null;
    }

    public void startLevel () {
        myGame.addEnemy(new Enemy(new Point(myGame.getCanvasDimension().width / 2, myGame
                .getCanvasDimension().height / 2), ENEMY_DIMENSION, myGame.getCanvasDimension(),
                ENEMY_IMAGEPATH, ENEMY_VELOCITY, ENEMY_DAMAGE));
    }

    @Override
    public boolean winningConditionsMet () {
        return false;
    }

}
