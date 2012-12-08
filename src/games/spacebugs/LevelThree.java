package games.spacebugs;

import java.awt.Dimension;
import java.awt.Point;
import vooga.shooter.gameObjects.Enemy;
import vooga.shooter.gameObjects.intelligence.ChaseAI;
import vooga.shooter.gameObjects.intelligence.RandomAI;
import vooga.shooter.level_editor.Level;

public class LevelThree extends Level{
    private SpaceBugs myGame;
    
    private static final Dimension ENEMY_DIMENSION = new Dimension(20, 17);
    private static final Point ENEMY_VELOCITY = new Point(0, 5);
    private static final int ENEMY_DAMAGE = 1;

    public LevelThree (SpaceBugs game) {
        super();
        myGame = game;
        setNextLevel(null);
    }

    public void startLevel () {
        String imagePath = "vooga/shooter/images/alien.png";
        Enemy newEnemy = new Enemy(new Point(100, 50), ENEMY_DIMENSION,
                myGame.getCanvasDimension(), imagePath, ENEMY_VELOCITY,
                ENEMY_DAMAGE);
        newEnemy.setAI(new ChaseAI(newEnemy, myGame.getPlayer()));
        myGame.addEnemy(newEnemy);
        Enemy newEnemy2 = new Enemy(new Point(400, 50), ENEMY_DIMENSION,
                myGame.getCanvasDimension(), imagePath, ENEMY_VELOCITY,
                ENEMY_DAMAGE);
        newEnemy2.setAI(new ChaseAI(newEnemy2, myGame.getPlayer2()));
        myGame.addEnemy(newEnemy2);
        Enemy newEnemy3 = new Enemy(new Point(200, 50), ENEMY_DIMENSION,
                myGame.getCanvasDimension(), imagePath, ENEMY_VELOCITY,
                ENEMY_DAMAGE);
        newEnemy3.setAI(new RandomAI(newEnemy3, myGame.getPlayer2()));
        myGame.addEnemy(newEnemy3);
        Enemy newEnemy4 = new Enemy(new Point(300, 50), ENEMY_DIMENSION,
                myGame.getCanvasDimension(), imagePath, ENEMY_VELOCITY,
                ENEMY_DAMAGE);
        newEnemy4.setAI(new RandomAI(newEnemy4, myGame.getPlayer2()));
        myGame.addEnemy(newEnemy4);
    }

    @Override
    public boolean winningConditionsMet () {
        return myGame.getEnemies().isEmpty();
    }
}
