package vooga.shooter.gameplay;

import arcade.IArcadeGame;
import arcade.gamemanager.GameSaver;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.WindowConstants;
import util.particleEngine.Explosion;
import util.particleEngine.ParticleSystem;
import vooga.shooter.gameObjects.Bullet;
import vooga.shooter.gameObjects.Enemy;
import vooga.shooter.gameObjects.Player;
import vooga.shooter.gameObjects.Sprite;
import vooga.shooter.graphics.Canvas;
import vooga.shooter.graphics.DrawableComponent;
import games.tommygame.levels.Level1;
import games.tommygame.levels.LostGame;
import games.tommygame.levels.WonGame;
import vooga.shooter.level_editor.Level;


/**
 * Initializes the top-down shooter game and owns all sprites and levels
 * initiated throughout the course of the game.
 * 
 * @author Tommy Petrilak
 * @author Stephen Hunt
 * @author Jesse Starr
 */
public class Game implements DrawableComponent, IArcadeGame {

    private static final String HIT_BY = "hitby";
    private static final String GAME_NAME = "Space Invaders";
    private static final String GAME_DESCRIPTION = "Classic top-down shooter game.";
    private static final String GAME_IMAGEPATH = "vooga/shooter/images/background.gif";
    private static final Dimension PLAYER_SIZE = new Dimension(20, 20);
    private static final int PLAYER_HEALTH = 10;
    private static final String PLAYER_IMAGEPATH = "vooga/shooter/images/spaceship.gif";

    private List<ParticleSystem> myParticleSystems;
    private List<Sprite> mySprites;
    private Player myPlayer;
    private Player myPlayer2;
    private List<Enemy> myEnemies;
    private Level myCurrentLevel;
    private Canvas myCanvas;
    private Point myPlayerOneStart;
    private Point myPlayerTwoStart;
    private JFrame myFrame;
    private Image myGameImage;

    /**
     * Game constructor (initializes anything not set in initializeGame())
     */
    public Game () {

        //ImageIcon imageIcon = new ImageIcon(this.getClass().getResource(GAME_IMAGEPATH));
        //myGameImage = imageIcon.getImage();

    }

    private void initializeGame (Canvas c, boolean multiplayer) {
        myCanvas = c;
        mySprites = new ArrayList<Sprite>();
        myEnemies = new ArrayList<Enemy>();
        myParticleSystems = new ArrayList<ParticleSystem>();
        myPlayerOneStart = new Point(myCanvas.getWidth() / 2, myCanvas.getHeight() - 50);
        myPlayer =
                new Player(myPlayerOneStart, PLAYER_SIZE, new Dimension(myCanvas.getWidth(),
                                                                        myCanvas.getHeight()),
                           PLAYER_IMAGEPATH, new Point(0, 0), PLAYER_HEALTH);

        addSprite(myPlayer);

        if (multiplayer) {
            myPlayerTwoStart = new Point(200, 400);
            myPlayer2 =
                    new Player(myPlayerTwoStart, PLAYER_SIZE, new Dimension(myCanvas.getWidth(),
                                                                            myCanvas.getHeight()),
                               PLAYER_IMAGEPATH, new Point(0, 0), PLAYER_HEALTH);

            addSprite(myPlayer2);
        }

        Level myCurrentLevel = new MainScreen(this, new Level1(this));
        myCanvas.addKeyListener(new KeyboardListener());
        startLevel(myCurrentLevel);
    }

    private void startLevel (Level level) {
        myCurrentLevel = level;
        myCurrentLevel.startLevel();
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
    @Override
    public void update () {

        if (myPlayer.isDead()) {
            myCurrentLevel.setNextLevel(new LostGame(this));
            myCurrentLevel = myCurrentLevel.getNextLevel();
            myPlayer.setDead(false);
            startLevel(myCurrentLevel);
        }
        if (myCurrentLevel.winningConditionsMet() && myCurrentLevel.getNextLevel() != null) {
            myCurrentLevel = myCurrentLevel.getNextLevel();
            startLevel(myCurrentLevel);
        }
        if (myCurrentLevel.winningConditionsMet() && myCurrentLevel.getNextLevel() == null) {
            myCurrentLevel.setNextLevel(new WonGame(this));
            myCurrentLevel = myCurrentLevel.getNextLevel();
            startLevel(myCurrentLevel);
        }
        for (Sprite s : getSprites()) {
            s.update();
        }
        for (Sprite s1 : getSprites()) {
            for (Sprite s2 : getSprites()) {
                if (s1.getImage() == null || s2.getImage() == null || s1 == s2) {
                    continue;
                }
                // list of the two sprites that collide
                // either enemy/player, enemy/enemy, or bullet/sprite
                List<Sprite> collides = collisionCheck(s1, s2);
                // if there is a collision
                if (collides.size() > 0) {
                    String key = HIT_BY + collides.get(1).getType();
                    collides.get(0).doEvent(key, collides.get(1));
                    myParticleSystems.add(new Explosion(collides.get(0).getPosition()));
                    // might not need this second one if going through
                    // all combinations of sprites anyway
                    key = HIT_BY + collides.get(0).getType();
                    collides.get(1).doEvent(key, collides.get(0));
                }
            }
        }
        Stack<ParticleSystem> remove = new Stack<ParticleSystem>();
        for (ParticleSystem p : myParticleSystems) {
            p.update();
            if (!p.stillExists()) remove.add(p);
        }
        for (ParticleSystem p : remove)
            myParticleSystems.remove(p);

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
    public List<Sprite> collisionCheck (Sprite s1, Sprite s2) {
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
                ret.add(s2);
                ret.add(b);
                return ret;
            }
        }
        // checks for bullets from 2nd sprite hitting 1st sprite
        for (Bullet b : s2.getBulletsFired()) {
            bulletR = new Rectangle(new Point(b.getLeft(), b.getTop()), b.getSize());
            if (bulletR.intersects(r1)) {
                ret.add(s1);
                ret.add(b);
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
    @Override
    public void paint (Graphics pen) {
        List<Sprite> deadSprites = new ArrayList<Sprite>();
        List<Enemy> deadEnemies = new ArrayList<Enemy>();

        for (Sprite s : getSprites()) {
            if (s.getImage() == null) {
                deadSprites.add(s);
            }
            else {
                s.paint(pen);
            }
        }

        for (Enemy e : getEnemies()) {
            if (e.getImage() == null) {
                deadEnemies.add(e);
            }
            else {
                e.paint(pen);
            }
        }
        for (ParticleSystem p : myParticleSystems)
            p.draw((Graphics2D) pen);
        getEnemies().removeAll(deadEnemies);
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
        mySprites = sprites;
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
        myEnemies = enemies;
    }

    public Player getPlayer () {
        return myPlayer;
    }
    /**
     * Listens for input and sends input to the method mapper.
     * 
     * @author Stephen Hunt
     */
    public class KeyboardListener implements KeyListener {
        private static final int NO_KEYS_PRESSED = -1;

        public KeyboardListener () {
            super();
        }

        /**
         * Sends info about keys pressed to method mapper.
         */
        @Override
        public void keyPressed (KeyEvent e) {
            myPlayer.doEvent(Integer.toString(e.getKeyCode()), null);
        }

        /**
         * Checks if any keys are being pressed. If not, sends to key mapper
         * that no keys are currently pressed.
         */
        @Override
        public void keyReleased (KeyEvent e) {
            myPlayer.doEvent(Integer.toString(NO_KEYS_PRESSED), null);
        }

        @Override
        public void keyTyped (KeyEvent e) {
        }

    }

    @Override
    public void setMouseListener (MouseMotionListener m) {

    }

    @Override
    public void setKeyboardListener (KeyListener k) {

    }

    public Dimension getCanvasDimension () {
        return myCanvas.getSize();
    }

    @Override
    public void runGame (String userPreferences, GameSaver s) {
        myFrame = new JFrame(GAME_NAME);
        myFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        myCanvas = new Canvas(this);
        initializeGame(myCanvas, false);
        myCanvas.start();
        myFrame.getContentPane().add(myCanvas, BorderLayout.CENTER);
        myFrame.pack();
        myFrame.setVisible(true);

    }

    @Override
    public List<Image> getScreenshots () {
        return null;
    }

    @Override
    public Image getMainImage () {
        return myGameImage;
    }

    @Override
    public String getDescription () {
        return GAME_DESCRIPTION;
    }

    @Override
    public String getName () {
        return GAME_NAME;
    }
}
