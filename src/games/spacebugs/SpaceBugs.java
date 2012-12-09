package games.spacebugs;

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
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.WindowConstants;
import arcade.IArcadeGame;
import arcade.gamemanager.GameSaver;
import util.input.core.KeyboardController;
import util.input.core.MouseController;
import util.particleEngine.Explosion;
import util.particleEngine.ParticleSystem;
import vooga.shooter.gameObjects.Bullet;
import vooga.shooter.gameObjects.Enemy;
import vooga.shooter.gameObjects.Player;
import vooga.shooter.gameObjects.Sprite;
import vooga.shooter.gameplay.inputInitialize.InputTeamSpriteActionAdapter;
import vooga.shooter.graphics.Canvas;
import vooga.shooter.graphics.DrawableComponent;
import vooga.shooter.level_editor.Level;

/**
 * Space Bugs IV
 * 
 * This game uses the framework developed by the Top-Down Shooter team, 
 * with a few changes. I wanted to have the player control two ships
 * at once, which was not a possibility with the current engine. Thus,
 * I had to make a specialized Game class (SpaceBugs). The completed game
 * demonstrates the different AI possibilities in the engine, and
 * implements the Particle Engine effects. It also uses the input team's 
 * code to set up non-standard keyboard buttons as movement and shoot inputs.
 * The game does not currently use levels made through the level editor, as
 * the level editor was unable to successfully create xml files at this time.
 * Once the SpaceBugs class was created to deal with the multiple players,
 * a game took only about 30 minutes to write using our framework.
 * 
 * 
 * @author Stephen Hunt
 * 
 * Based off of Game class by Jesse Starr, Tommy Petrilak, and Stephen Hunt
 *
 */

public class SpaceBugs extends JComponent implements DrawableComponent, IArcadeGame {

    private static final String HIT_BY = "hitby";
    private static final String GAME_NAME = "Space Bugs IV";
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
    private Image myGameImage;
    private JFrame myFrame;
    private KeyboardController myKeyCont;

    /**
     * Game constructor (initializes anything not set in initializeGame())
     */
    public SpaceBugs () {
        super();
    }

    private void initializeGame (Canvas c) {
        myCanvas = c;
        mySprites = new ArrayList<Sprite>();
        myEnemies = new ArrayList<Enemy>();
        myParticleSystems = new ArrayList<ParticleSystem>();
        myPlayerOneStart = new Point(myCanvas.getWidth() / 4, myCanvas.getHeight() - 50);
        myPlayer =
            new Player(myPlayerOneStart, PLAYER_SIZE, new Dimension(myCanvas.getWidth(),
                    myCanvas.getHeight()),
                    PLAYER_IMAGEPATH, new Point(0, 0), PLAYER_HEALTH);

        addSprite(myPlayer);
        myPlayerTwoStart = new Point(myCanvas.getWidth() * 3 / 4, myCanvas.getHeight() - 50);
        myPlayer2 =
            new Player(myPlayerTwoStart, PLAYER_SIZE, new Dimension(myCanvas.getWidth(),
                    myCanvas.getHeight()),
                    PLAYER_IMAGEPATH, new Point(0, 0), PLAYER_HEALTH);

        addSprite(myPlayer2);
        Level myCurrentLevel = new StartUp(this);
        SpaceBugsActionAdapter inputAdapter;
        inputAdapter = new SpaceBugsActionAdapter(myPlayer, myPlayer2);

        myKeyCont = new KeyboardController(this);
        try {
            myKeyCont.setControl(KeyEvent.VK_F, KeyboardController.PRESSED, inputAdapter, "fireShot1");
            myKeyCont.setControl(KeyEvent.VK_W, KeyboardController.PRESSED, inputAdapter, "goUp1");
            myKeyCont.setControl(KeyEvent.VK_S, KeyboardController.PRESSED, inputAdapter, "goDown1");
            myKeyCont.setControl(KeyEvent.VK_A, KeyboardController.PRESSED, inputAdapter, "goLeft1");
            myKeyCont.setControl(KeyEvent.VK_D, KeyboardController.PRESSED, inputAdapter, "goRight1");
            myKeyCont.setControl(KeyEvent.VK_F, KeyboardController.RELEASED, inputAdapter, "stop1");
            myKeyCont.setControl(KeyEvent.VK_W, KeyboardController.RELEASED, inputAdapter, "stop1");
            myKeyCont.setControl(KeyEvent.VK_S, KeyboardController.RELEASED, inputAdapter, "stop1");
            myKeyCont.setControl(KeyEvent.VK_A, KeyboardController.RELEASED, inputAdapter, "stop1");
            myKeyCont.setControl(KeyEvent.VK_D, KeyboardController.RELEASED, inputAdapter, "stop1");
            myKeyCont.setControl(KeyEvent.VK_H, KeyboardController.PRESSED, inputAdapter, "fireShot2");
            myKeyCont.setControl(KeyEvent.VK_I, KeyboardController.PRESSED, inputAdapter, "goUp2");
            myKeyCont.setControl(KeyEvent.VK_K, KeyboardController.PRESSED, inputAdapter, "goDown2");
            myKeyCont.setControl(KeyEvent.VK_J, KeyboardController.PRESSED, inputAdapter, "goLeft2");
            myKeyCont.setControl(KeyEvent.VK_L, KeyboardController.PRESSED, inputAdapter, "goRight2");
            myKeyCont.setControl(KeyEvent.VK_H, KeyboardController.RELEASED, inputAdapter, "stop2");
            myKeyCont.setControl(KeyEvent.VK_I, KeyboardController.RELEASED, inputAdapter, "stop2");
            myKeyCont.setControl(KeyEvent.VK_K, KeyboardController.RELEASED, inputAdapter, "stop2");
            myKeyCont.setControl(KeyEvent.VK_J, KeyboardController.RELEASED, inputAdapter, "stop2");
            myKeyCont.setControl(KeyEvent.VK_L, KeyboardController.RELEASED, inputAdapter, "stop2");
        }
        catch (NoSuchMethodException e) {
        }
        catch (IllegalAccessException e) {
        }
        myCanvas.addKeyListener(myKeyCont);
        startLevel(myCurrentLevel);
    }
    
    @Override
    public void runGame (String userPreferences, GameSaver s) {
        myFrame = new JFrame(GAME_NAME);
        myFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        myCanvas = new Canvas(this);
        initializeGame(myCanvas);
        myCanvas.start();
        myFrame.getContentPane().add(myCanvas, BorderLayout.CENTER);
        myFrame.pack();
        myFrame.setVisible(true);

    }
    private void startLevel (Level level) {
        myCurrentLevel = level;
        myCurrentLevel.startLevel();
        update();
    }

    @Override
    public String getDescription () {
        return "Two brothers must destroy the bugs... again!";
    }

    @Override
    public String getName () {
        return "Space Bugs IV";
    }

    @Override
    public List<Image> getScreenshots () {
        return null;
    }

    @Override
    public Image getMainImage () {
        return myGameImage;
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

        if (myPlayer.isDead() && myPlayer2.isDead()) {
            myCurrentLevel.setNextLevel(new LoseScreen(this));
            myCurrentLevel = myCurrentLevel.getNextLevel();
            myPlayer.setDead(false);
            startLevel(myCurrentLevel);
        }
        if (myCurrentLevel.winningConditionsMet() && myCurrentLevel.getNextLevel() != null) {
            myCurrentLevel = myCurrentLevel.getNextLevel();
            startLevel(myCurrentLevel);
        }
        if (myCurrentLevel.winningConditionsMet() && myCurrentLevel.getNextLevel() == null) {
            myCurrentLevel.setNextLevel(new WinScreen(this));
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
    
    public Player getPlayer2() {
        return myPlayer2;
    }

        public Dimension getCanvasDimension () {
        return myCanvas.getSize();
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
            myPlayer2.doEvent(Integer.toString(e.getKeyCode()), null);
        }

        /**
         * Checks if any keys are being pressed. If not, sends to key mapper
         * that no keys are currently pressed.
         */
        @Override
        public void keyReleased (KeyEvent e) {
            myPlayer.doEvent(Integer.toString(NO_KEYS_PRESSED), null);
            myPlayer2.doEvent(Integer.toString(NO_KEYS_PRESSED), null);
        }

        @Override
        public void keyTyped (KeyEvent e) {
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

    @Override
    public void setKeyboardListener (KeyboardController key) {
        
    }

    @Override
    public void setMouseListener (MouseController mouseMotion) {
        
    }
}