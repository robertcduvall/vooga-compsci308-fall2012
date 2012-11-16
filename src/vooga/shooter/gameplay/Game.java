package vooga.shooter.gameplay;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import vooga.shooter.gameObjects.Bullet;
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
 * @author Stephen Hunt
 */
public class Game {
    private static final String HIT_BY = "hitby";

    private List<Sprite> mySprites;
    private Player myPlayer;
    private Player myPlayer2;
    private List<Enemy> myEnemies;
    private Level myCurrentLevel;
    private Canvas myCanvas;
    private Image myPlayerImage;
    private ImageIcon myImageIcon;
    private final int myPlayerHealth = 10;
    private final Dimension myPlayerDimension = new Dimension(20, 20);
    private final Point myPlayerOneStart = new Point(400, 300);
    private final Point myPlayerTwoStart = new Point(400, 500);

    private void initializeGame (Canvas c, boolean multiplayer) {
        myImageIcon =
                new ImageIcon(this.getClass().getResource("../vooga/shooter/images/alien.png"));
        myPlayerImage = myImageIcon.getImage();
        myPlayer =
                new Player(myPlayerOneStart, myPlayerDimension,
                           new Dimension(myCanvas.getWidth(), myCanvas.getHeight()), myPlayerImage,
                           myPlayerHealth);
        addSprite(myPlayer);
        if (multiplayer) {
            new Player(myPlayerOneStart, myPlayerDimension, new Dimension(myCanvas.getWidth(),
                                                                          myCanvas.getHeight()),
                       myPlayerImage, myPlayerHealth);
            addSprite(myPlayer2);
        }
        Level firstLevel = new Level1(this);
        myCanvas = c;
        myCanvas.addKeyListener(new KeyboardListener());
        startLevel(firstLevel);
    }

    private void startLevel (Level level) {
        myCurrentLevel = level;
        update();
    }

    /**
     * Updates the sprites on the screen. Also checks
     * for collisions between two sprites (only if
     * both sprites are still alive (i.e. still visible
     * in the game). If there is a collision, this method
     * will tell each sprite that it was hit by a type of
     * the other sprite. Each sprite will then invoke the
     * correct method to deal with that type of collision.
     */
    public void update () {
        for (Sprite s : getSprites()) {
            s.update();
        }

        for (Sprite s1 : getSprites()) {
            for (Sprite s2 : getSprites()) {
                if (s1.getImage() == null || s2.getImage() == null) {
                    continue;
                }

                // list of the two sprites that collide
                // either enemy/player, enemy/enemy, or bullet/sprite
                List<Sprite> collides = collisionCheck(s1, s2);
                if (collides.size() > 0) {
                    String key = HIT_BY + collides.get(1).getType();
                    collides.get(0).doEvent(key, collides.get(1));

                    key = HIT_BY + collides.get(0).getType();
                    collides.get(1).doEvent(key, collides.get(0));
                }
            }
        }
    }

    /**
     * Checks if two sprites are colliding with each other.
     * Or checks if any of the bullets from either collides with
     * the other sprite.
     *
     * @param s1 The first sprite to check.
     * @param s2 The second sprite to check.
     * @return Returns a list of 2 sprites: either (1) the two original
     *         sprites if they are colliding, or (2) a bullet from one sprite,
     *         and the other sprite itself
     */
    List<Sprite> collisionCheck (Sprite s1, Sprite s2) {
        List<Sprite> ret = new ArrayList<Sprite>();

        // get bounds of both sprites
        Rectangle r1 = new Rectangle(new Point(s1.getLeft(), s1.getTop()), s1.getSize());
        Rectangle r2 = new Rectangle(new Point(s2.getLeft(), s2.getTop()), s2.getSize());

        // checks for collision between 1st and 2nd sprite
        if (r1.intersects(r2)) {
            ret.add(s1);
            ret.add(s2);
            return ret;
        }

        // will be bounds for the bullets from sprites
        Rectangle bulletR;

        // checks for bullets from 1st sprite hitting 2nd sprite
        for (Bullet b : s1.getBulletsFired()) {
            bulletR = new Rectangle(new Point(b.getLeft(), b.getTop()), b.getSize());
            if (bulletR.intersects(r2)) {
                ret.add(b);
                ret.add(s2);
                return ret;
            }
        }

        // checks for bullets from 2nd sprite hitting 1st sprite
        for (Bullet b : s2.getBulletsFired()) {
            bulletR = new Rectangle(new Point(b.getLeft(), b.getTop()), b.getSize());
            if (bulletR.intersects(r1)) {
                ret.add(b);
                ret.add(s1);
                return ret;
            }
        }

        return ret;
    }

    /**
     * Paints all still-alive sprites on the screen.
     * Any sprites who have died (e.g. have health < 0)
     * are removed from the game.
     * 
     * @param pen used to draw the images
     */
    public void paint (Graphics pen) {
        List<Sprite> deadSprites = new ArrayList<Sprite>();

        for (Sprite s : getSprites()) {
            if (s.getImage() == null) {
                deadSprites.add(s);
            }
            else {
                s.paint(pen);
            }
        }

        getSprites().removeAll(deadSprites);
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
     * Add an enemy to the list of enemies currently existing in the Game.
     * 
     * @param enemy to be added to list of existing enemies
     */
    public void addEnemy (Enemy enemy) {
        getEnemies().add(enemy);
        getSprites().add(enemy);
    }

    /**
     * Returns a list of all players/enemies in
     * the game.
     * 
     * @return mySprites
     */
    public List<Sprite> getSprites () {
        return mySprites;
    }

    /**
     * @param sprites the new list to set the
     *        current mySprites to
     */
    public void setSprites (List<Sprite> sprites) {
        this.mySprites = sprites;
    }

    /**
     * @return the myEnemies
     */
    public List<Enemy> getEnemies () {
        return myEnemies;
    }

    /**
     * @param enemies the new list of enemies to set
     *        the current list to
     */
    public void setEnemies (List<Enemy> enemies) {
        this.myEnemies = enemies;
    }

    /**
     * Listens for input and sends input to the method mapper.
     * 
     * @author Stephen Hunt
     * 
     */
    private class KeyboardListener implements KeyListener {
        private int myNumKeysPressed;

        public KeyboardListener () {
            super();
            myNumKeysPressed = 0;
        }

        /**
         * Sends info about keys pressed to method mapper.
         */
        @Override
        public void keyPressed (KeyEvent e) {
            myPlayer.doEvent(Integer.toString(e.getKeyCode()), null);
            myNumKeysPressed++;
        }

        /**
         * Checks if any keys are being pressed. If not, sends to key mapper
         * that no keys are currently pressed.
         */
        @Override
        public void keyReleased (KeyEvent e) {
            myNumKeysPressed--;
            if (myNumKeysPressed == 0) {
                myPlayer.doEvent("-1", null);
            }
        }

        @Override
        public void keyTyped (KeyEvent e) {
        }

    }
}
