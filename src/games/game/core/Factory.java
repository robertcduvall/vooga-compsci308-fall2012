package games.game.core;


import games.game.gameobject.DamagableSprite;
import games.game.gameobject.Player;
import games.game.gameobject.RangedWeapon;
import games.game.levels.Level;
import games.game.levels.MovementManager;
import games.game.levels.ProjectileManager;
import games.game.levels.SpawnManager;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import util.input.core.KeyboardController;


/**
 * Creates particular game objects and registers them with the correct
 * classes.
 * 
 * @author Paul Dannenberg
 * 
 */
public class Factory {

    private final String ENEMY_IMAGE_PATH = "/images/mario-reverse-gravity.png";
    private JPanel myBoard;
    private Level myLevel;
    private SpawnManager mySpawnManager;
    private MovementManager myMovementManager;
    private ProjectileManager myProjectileManager;

    /**
     * Creates a new Factory. This factory will create objects
     * for a particular level and instantiate objects that respond
     * to certain input through a Java component, <code>board</code>.
     * 
     * @param board The object that will register the user input for
     *        game objects.
     * @param level The level for which this object will create other
     *        objects.
     */
    public Factory(JPanel board, Level level) {
        myBoard = board;
        myLevel = level;
        createManagers();
    }

    /**
     * Creates the different managers the level will need. Ideally this would
     * be more flexible (as would a lot of this game).
     */
    private void createManagers() {
        mySpawnManager = new SpawnManager(myBoard, myLevel, this);
        myMovementManager = new MovementManager(myBoard, myLevel);
        myProjectileManager = new ProjectileManager(myBoard, myLevel);
    }

    /**
     * Creates the main player and sets up his response to user input.
     */
    public void createPlayer() {
        KeyboardController keyboardController = new KeyboardController(myBoard);
        Player player = new Player(createWeapons());
        initializeInput(player, keyboardController);

    }

    /**
     * Determines how the player's input will influence the game.
     * 
     * @param player The player that the user will control.
     * @param keyboardController The controller that will manager keyboard
     *        input.
     */
    private void initializeInput(Player player,
            KeyboardController keyboardController) {
        try {
            keyboardController.setControl(KeyEvent.VK_SPACE,
                    KeyboardController.PRESSED, player.getClass(), "shoot");
            keyboardController.setControl(KeyEvent.VK_R,
                    KeyboardController.PRESSED, player.getClass(), "reload");
            keyboardController.setControl(KeyEvent.VK_Q,
                    KeyboardController.PRESSED, player.getClass(),
                    "switchWeapon");

            // Die silently.
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates the weapons the player will use.
     * 
     * @return A collection of the weapons available to the player.
     */
    private List<RangedWeapon> createWeapons() {
        RangedWeapon weapon = new RangedWeapon(myProjectileManager);
        List<RangedWeapon> playersWeapons = new ArrayList<RangedWeapon>();
        playersWeapons.add(weapon);
        return playersWeapons;
    }

    /**
     * 
     * @return The managers, created by this factory, that rely on a constant
     *         frame update.
     */
    public Collection<IUpdatable> getUpdatableManagers() {
        Collection<IUpdatable> managers = new ArrayList<IUpdatable>();
        managers.add(mySpawnManager);
        managers.add(myMovementManager);
        return managers;
    }

    /**
     * Reads the enemy's image from a file directory.
     * 
     * @param path The path to the image on the file system.
     * @return The image that will appear on screen to represent
     *         and enemy sprite.
     */
    private Image readImage(String path) {
        ImageIcon enemyImage = new ImageIcon(path);
        return enemyImage.getImage();
    }

    /**
     * Creates a new enemy at a particular location.
     * 
     * @param positionToSpawn The position at which the new
     *        enemy should be created.
     * @param initialHitPoints The inital hit points the enemy
     *        should have.
     * @return A new enemy.
     */
    public DamagableSprite createEnemy(Point positionToSpawn,
            int initialHitPoints) {
        Image enemyImage = readImage(ENEMY_IMAGE_PATH);
        return new DamagableSprite(enemyImage, positionToSpawn,
                initialHitPoints);
    }

}
