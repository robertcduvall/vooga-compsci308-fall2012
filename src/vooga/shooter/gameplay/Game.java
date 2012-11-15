package vooga.shooter.gameplay;

import java.awt.Graphics;
import java.util.List;
import util.input.core.KeyboardController;
import vooga.shooter.gameObjects.Enemy;
import vooga.shooter.gameObjects.Sprite;
import vooga.shooter.graphics.Canvas;
import vooga.shooter.implementation.Level1;
import vooga.shooter.level_editor.Level;


/**
 * Initializes the top-down shooter game and owns all sprites and levels
 * initiated throughout the course of the game.  
 * 
 * @author Tommy Petrilak
 * 
 */
public class Game {
    private List<Sprite> mySprites;
    private List<Enemy> myEnemies;
    private Level myCurrentLevel;
    private Canvas myCanvas;

    private void initializeGame (Canvas c) {
        Level firstLevel = new Level1(this);
        myCanvas = c;
        startLevel(firstLevel);
    }

    private void startLevel (Level level) {
        myCurrentLevel = level;
        update();
    }

    private void update () {
        // will work when Levels contain winning conditions
        // if (myCurrentLevel.winningConditionsMet(this)) {
        //      startLevel(myCurrentLevel.myNextLevel());
        // }
        
        for (Sprite s : getSprites()) {
            s.update(myCanvas);
        }

       
    }

    private void paint (Graphics pen) {
        for (Sprite s : getSprites()) {
            s.paint(pen);
        }

    }

    /**
     * Add a sprite to the list of sprites currently existing in the Game.
     * 
     * @param sprite to be added to list of existing sprites
     */
    public void addSprite (Sprite sprite) {
        getSprites().add(sprite);
    }
    
    /**
     * Add a sprite to the list of sprites currently existing in the Game.
     * 
     * @param sprite to be added to list of existing sprites
     */
    public void addEnemy (Enemy enemy) {
        getEnemies().add(enemy);
        getSprites().add(enemy);
    }

    /**
     * @return the mySprites
     */
    public List<Sprite> getSprites () {
        return mySprites;
    }

    /**
     * @param mySprites the mySprites to set
     */
    public void setSprites (List<Sprite> mySprites) {
        this.mySprites = mySprites;
    }

    /**
     * @return the myEnemies
     */
    public List<Enemy> getEnemies () {
        return myEnemies;
    }

    /**
     * @param myEnemies the myEnemies to set
     */
    public void setEnemies (List<Enemy> myEnemies) {
        this.myEnemies = myEnemies;
    }

}
