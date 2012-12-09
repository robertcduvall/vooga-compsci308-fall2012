package games.game.levels;

import games.game.core.IPaintable;
import games.game.core.IUpdatable;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.LinkedList;
import java.util.Queue;
import javax.swing.JPanel;
import util.particleEngine.Explosion;
import util.particleEngine.ParticleSystem;


/**
 * This class manages the different levels the game supports.
 * Depending on which winning or losing conditions are met,
 * this class selects the next level and keeps track of the
 * score.
 * 
 * @author Paul Dannenberg
 * 
 */
public class LevelManager implements IPaintable, IUpdatable {

    private Queue<Level> myLevels;
    private Level myCurrentLevel;
    private Integer myFinalScore;

    /**
     * Creates a new object that will manage the game levels.
     * All these will be painted on the Component, board.
     * 
     * @param board The panel on which all game action will
     *        take place.
     */
    public LevelManager(JPanel board) {
        myLevels = fillLevels(board);
        myCurrentLevel = myLevels.peek();
    }

    /**
     * Adds all new levels. 
     * @return A group of levels.
     */
    private Queue<Level> fillLevels(JPanel board) {
        Queue<Level> levels = new LinkedList<Level>();
        levels.add(new Level(board));
        return levels;
    }

    /**
     * Begins the next level.
     */
    private void startNextLevel() {
        myLevels.poll();
        myCurrentLevel = myLevels.peek();
    }

    /**
     * Determines whether to advance to the next
     * level or to display the game over screen
     * to the user.
     */
    @Override
    public void update() {
        if (myCurrentLevel.isWon()) {
            startNextLevel();
        } else if (myCurrentLevel.isLost()) {
            displayGameOver();
        }
    }

    /**
     * Displays the final score for the user and starts a
     * 'firework' display.
     */
    private void displayGameOver() {
        myFinalScore = myCurrentLevel.getScore();
        ParticleSystem fireWorks = new Explosion(new Point());
        fireWorks.setLoop(true);
    }

    /**
     * Paints the current level.
     */
    @Override
    public void paint(Graphics2D pen) {
        if (myFinalScore == null) {
            myCurrentLevel.paint(pen);
        } else {
            pen.drawString(myFinalScore.toString(), 0, 0);
        }
    }
}
