package games.game.levels;

import games.game.gameobject.DamagableSprite;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;


/**
 * This class managers projectile fire and removes objects
 * if the projectile hit killed/destroyed them.
 * 
 * @author Paul Dannenberg
 * 
 */
public class ProjectileManager {

    private static final int SCORE_MULTIPLIER = 5;
    private int myKilled;
    private Point myCursorPosition;
    private Level myLevel;

    /**
     * Creates a new object that will manage ranged weapon
     * fire.
     * 
     * @param board The panel on which the game is drawn.
     * @param level The level whose sprites this object
     *        will manage.
     */
    public ProjectileManager(JPanel board, Level level) {
        setupInput(board);
        myLevel = level;
    }

    /**
     * Sets up an input listener so the user's aim can be
     * followed. (Unfortunately the input team couldn't
     * handle this case (I think)).
     * 
     * @param board The component which should listen for
     *        mouse movement.
     */
    private void setupInput(JPanel board) {
        board.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                myCursorPosition = new Point(e.getX(), e.getY());
            }
        });
    }

    /**
     * Checks whether a particular shot hits one of the level's
     * targets. If it does, this method deals with the result.
     * 
     * @param damageAmount The amount of damage the shot will
     *        cause if it hits.
     */
    public void simulateShot(int damageAmount) {
        for (DamagableSprite sprite : myLevel.getDamagableSprites()) {
            if (sprite.contains(myCursorPosition)) {
                sprite.damage(damageAmount);
                if (!sprite.isAlive()) {
                    myLevel.remove(sprite);
                }
            }
        }
    }

    /**
     * Computes a component of the total score.
     * 
     * @return The score the user has gained from killing enemies.
     */
    public int getScore() {
        return myKilled * SCORE_MULTIPLIER;
    }

}
