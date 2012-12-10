package vooga.shooter.gameplay;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.WindowConstants;
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
import arcade.IArcadeGame;
import arcade.gamemanager.GameSaver;


/**
 * Initializes the top-down shooter game and owns all sprites and levels
 * initiated throughout the course of the game.
 * 
 * @author Tommy Petrilak
 * @author Stephen Hunt
 * @author Jesse Starr
 */
public abstract class AbstractGame extends JComponent implements DrawableComponent, IArcadeGame {

    private static final String HIT_BY = "hitby";
    private static final String GAME_NAME = "Space Invaders";
    private static final String GAME_DESCRIPTION = "Classic top-down shooter game.";
    private static final String GAME_IMAGEPATH = "images/background.gif";
    private static final Dimension PLAYER_SIZE = new Dimension(20, 20);
    private static final int PLAYER_HEALTH = 10;
    private static final String PLAYER_IMAGEPATH = "vooga/shooter/images/spaceship.gif";
    private static final int PLAYER_START_HEIGHT = 50;

    private List<ParticleSystem> myParticleSystems;
    private List<Sprite> mySprites;
    private Player myPlayer;
    private List<Enemy> myEnemies;
    private Level myCurrentLevel;
    private Canvas myCanvas;
    private Point myPlayerOneStart;
    private JFrame myFrame;
    private Image myGameImage;
    private KeyboardController myKeyContr;
    InputTeamSpriteActionAdapter inputAdapter;


    /**
     * Game constructor (initializes anything not set in initializeGame())
     */
    public AbstractGame () {
        ImageIcon imageIcon = new ImageIcon("src/vooga/shooter/images/background.gif");
        //ImageIcon imageIcon = new ImageIcon(this.getClass().getResource(GAME_IMAGEPATH));
        myGameImage = imageIcon.getImage();
    }

    private void initializeGame (Canvas c) {
        myCanvas = c;
        mySprites = new ArrayList<Sprite>();
        myEnemies = new ArrayList<Enemy>();
        myParticleSystems = new ArrayList<ParticleSystem>();
        myPlayerOneStart =
                new Point(myCanvas.getWidth() / 2, myCanvas.getHeight() - PLAYER_START_HEIGHT);
        //myPlayer = Player.getInstance();
        myPlayer =
                new Player(myPlayerOneStart, PLAYER_SIZE, new Dimension(myCanvas.getWidth(),
                                                                        myCanvas.getHeight()),
                           PLAYER_IMAGEPATH, new Point(0, 0), PLAYER_HEALTH);

        addSprite(myPlayer);
        
        createGame();
        setupInput();
        startLevel(myCurrentLevel);
    }
    
    protected abstract void createGame();

    private void startLevel (Level level) {
        myCurrentLevel = level;
        myCurrentLevel.startLevel();
        update();
    }
    
    private void setupInput() {
        inputAdapter = new InputTeamSpriteActionAdapter(myPlayer);

        myKeyContr = new KeyboardController(this);
        try {
            myKeyContr.setControl(KeyEvent.VK_SPACE, KeyboardController.PRESSED, inputAdapter, "fireShot");
            myKeyContr.setControl(KeyEvent.VK_UP, KeyboardController.PRESSED, inputAdapter, "goUp");
            myKeyContr.setControl(KeyEvent.VK_DOWN, KeyboardController.PRESSED, inputAdapter, "goDown");
            myKeyContr.setControl(KeyEvent.VK_LEFT, KeyboardController.PRESSED, inputAdapter, "goLeft");
            myKeyContr.setControl(KeyEvent.VK_RIGHT, KeyboardController.PRESSED, inputAdapter, "goRight");
            myKeyContr.setControl(KeyEvent.VK_SPACE, KeyboardController.RELEASED, inputAdapter, "stop");
            myKeyContr.setControl(KeyEvent.VK_UP, KeyboardController.RELEASED, inputAdapter, "stop");
            myKeyContr.setControl(KeyEvent.VK_DOWN, KeyboardController.RELEASED, inputAdapter, "stop");
            myKeyContr.setControl(KeyEvent.VK_LEFT, KeyboardController.RELEASED, inputAdapter, "stop");
            myKeyContr.setControl(KeyEvent.VK_RIGHT, KeyboardController.RELEASED, inputAdapter, "stop");

        }
        catch (NoSuchMethodException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        myCanvas.addKeyListener(myKeyContr);
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
        for (Sprite sprite1 : getSprites()) {
            for (Sprite sprite2 : getSprites()) {
                if (sprite1.getImage() == null || sprite2.getImage() == null || sprite1 == sprite2) {
                    continue;
                }
                List<Sprite> collisions = collisionCheck(sprite1, sprite2);
                if (collisions.size() > 0) {
                    String key = HIT_BY + collisions.get(1).getType();
                    collisions.get(0).doEvent(key, collisions.get(1));
                    myParticleSystems.add(new Explosion(collisions.get(0).getPosition()));
                }
            }
        }
        Stack<ParticleSystem> pSystemToRemove = new Stack<ParticleSystem>();
        for (ParticleSystem p : myParticleSystems) {
            p.update();
            if (!p.stillExists()) {
                pSystemToRemove.add(p);
            }
        }
        for (ParticleSystem p : pSystemToRemove) {
            myParticleSystems.remove(p);
        }
    }

    /**
     * Checks if two sprites are colliding with each other.
     * Or checks if any of the bullets from either collides with
     * the other sprite.
     * 
     * @param sprite1 The first sprite to check.
     * @param sprite2 The second sprite to check.
     * @return Returns a list of 2 sprites: either (1) the two original
     *         sprites if they are colliding, or (2) a bullet from one sprite,
     *         and the other sprite itself
     */

    private List<Sprite> collisionCheck (Sprite sprite1, Sprite sprite2) {
        List<Sprite> collidedSprites = new ArrayList<Sprite>();
        Rectangle sprite1Edges = new Rectangle(new Point(sprite1.getLeft(), sprite1.getTop()), sprite1.getSize());
        Rectangle sprite2Edges = new Rectangle(new Point(sprite2.getLeft(), sprite2.getTop()), sprite2.getSize());

        // checks for collision between 1st and 2nd sprite
        if (sprite1Edges.intersects(sprite2Edges)) {
            collidedSprites.add(sprite1);
            collidedSprites.add(sprite2);
            return collidedSprites;
        }
        Rectangle bulletEdges;
        // checks for bullets from 1st sprite hitting 2nd sprite
        for (Bullet bullet : sprite1.getBulletsFired()) {
            bulletEdges = new Rectangle(new Point(bullet.getLeft(), bullet.getTop()), bullet.getSize());
            if (bulletEdges.intersects(sprite2Edges)) {
                collidedSprites.add(sprite2);
                collidedSprites.add(bullet);
                return collidedSprites;
            }
        }
        for (Bullet bullet : sprite2.getBulletsFired()) {
            bulletEdges = new Rectangle(new Point(bullet.getLeft(), bullet.getTop()), bullet.getSize());
            if (bulletEdges.intersects(sprite1Edges)) {
                collidedSprites.add(sprite1);
                collidedSprites.add(bullet);
                return collidedSprites;
            }
        }
        return collidedSprites;
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
        myCurrentLevel.paintSprites(pen, 0, 0);
        
        myCurrentLevel.paintBackground(pen);
        
        for (ParticleSystem p : myParticleSystems) {
            p.draw((Graphics2D) pen);
        }
        
        removeDeadSprites();
    }
    
    private void removeDeadSprites(){
        Set<Sprite> toRemove = new HashSet<Sprite>();
        for(Sprite s : mySprites){
            if(s.getImage() == null){
                toRemove.add(s);
            }
        }
        mySprites.removeAll(toRemove);
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

    /**
     * 
     * @return dimension of the playable game area
     */

    public Dimension getCanvasDimension () {
        return myCanvas.getSize();
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

    @Override
    public void setMouseListener (MouseController mouseMotion) {
        //This is where you'll be given the mouse controller
    }
    
    @Override
    public void setKeyboardListener (KeyboardController k) {
        //This is where you'll be given the keyboard controller
    }

    public Player getPlayer () {
        return myPlayer;
    }
    
    protected void setMyCurrentLevel(Level nextLevel) {
        myCurrentLevel = nextLevel;
    }

}
