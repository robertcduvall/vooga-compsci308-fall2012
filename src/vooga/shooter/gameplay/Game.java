package vooga.shooter.gameplay;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.List;
import javax.swing.ImageIcon;
import util.input.core.KeyboardController;
import vooga.shooter.gameObjects.Enemy;
import vooga.shooter.gameObjects.Player;
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
    private Player myPlayer;
    private List<Enemy> myEnemies;
    private Level myCurrentLevel;
    private Canvas myCanvas;
    private Image playerImage;
    private ImageIcon imageIcon;

    private void initializeGame (Canvas c) {
        imageIcon = new ImageIcon(this.getClass().getResource("../vooga/shooter/images/alien.png"));
        playerImage = imageIcon.getImage();
        myPlayer = new Player(new Point(400, 300), new Dimension(20, 20), playerImage, 10);
        addSprite(myPlayer);
        Level firstLevel = new Level1(this);
        myCanvas = c;
        startLevel(firstLevel);
    }

    private void startLevel (Level level) {
        myCurrentLevel = level;
        update();
    }

    public void update () {
        // will work when Levels contain winning conditions
        // if (myCurrentLevel.winningConditionsMet(this)) {
        // startLevel(myCurrentLevel.myNextLevel());
        // }

        for (Sprite s : getSprites()) {
            s.update(myCanvas);
        }
        for (Sprite s1 : getSprites()) {
            for (Sprite s2 : getSprites()) {
                if (collisionCheck(s1,s2)) {
                    s1.collide(s2);
                    s2.collide(s1);
                }
            }
        }
    }
    
    public boolean collisionCheck(Sprite s1, Sprite s2) {
        Rectangle r1 = new Rectangle(s1.getPosition(), s1.getDimension());
        Rectangle r2 = new Rectangle(s2.getPosition(), s2.getDimension());
        return r1.intersects(r2);
    }

    public void paint (Graphics pen) {
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
