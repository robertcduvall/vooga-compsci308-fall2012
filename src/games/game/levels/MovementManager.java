package games.game.levels;

import games.game.core.IUpdatable;
import games.game.gameobject.DamagableSprite;
import games.game.gameobject.Sprite;
import java.awt.Point;
import java.util.Collection;
import java.util.Random;
import javax.swing.JPanel;


/**
 * This class is responsible for moving enemy sprites
 * around a particular level. It does this by randomizing
 * there motion. The movement speed during any given
 * frame is gaussianly distributed.
 * 
 * @author Paul Dannenberg
 * 
 */
public class MovementManager implements IUpdatable {

    private JPanel myBoard;
    private Level myLevel;

    /**
     * Creates a new object to take charge of moving sprites
     * around the level.
     * 
     * @param board The board on which the sprites will be painted.
     * @param level The level for which this object will manage
     *        movement.
     */
    public MovementManager(JPanel board, Level level) {
        myBoard = board;
        myLevel = level;
    }

    /**
     * Randomly moves a group of sprites around the level.
     * 
     * @param spritesToMove A collection of sprites. Each one of these
     *        will have their movement during a particular frame determined
     *        at random.
     */
    private void moveSprites(Collection<DamagableSprite> spritesToMove) {
        for (DamagableSprite sprite : spritesToMove) {
            Point movementAmount = determineMovement(sprite);
            sprite.move(movementAmount);
        }
    }

    /**
     * Determines the motion of a particular sprite during a given frame.
     * 
     * @param sprite The sprite to be moved.
     * @return The vector representing the movement of the sprite.
     */
    private Point determineMovement(DamagableSprite sprite) {
        Random generator = new Random();
        Point movementQuantity = new Point();
        while (movementQuantity.distance(0, 0) != 0
                && isWithinBoundaries(myBoard, sprite, movementQuantity))
            movementQuantity.setLocation(generator.nextGaussian(),
                    generator.nextGaussian());
        return movementQuantity;
    }

    /**
     * Checks whether the next movement of a sprite will keep the sprite
     * within the boundary determined by the JPanel on which the game
     * is displayed.
     * 
     * @param board The container on which the game is being displayed.
     * @param sprite The sprite to be moved.
     * @param potentialMovement The potential next movement of the sprite.
     * @return True if the the sprite's potential movement will keep it
     *         within the display boundary of the JPanel. False, if the
     *         potential
     *         movement vector will take it outside the boundaries of the
     *         display.
     */
    private boolean isWithinBoundaries(JPanel board, Sprite sprite,
            Point potentialMovement) {
        return board.contains(sprite.getLocation().x + potentialMovement.x,
                sprite.getLocation().y + potentialMovement.y);
    }

    /**
     * Requests a new group of sprites to move. These sprites are sprites
     * that are still present in the level.
     */
    @Override
    public void update() {
        moveSprites(myLevel.getDamagableSprites());
    }

}
