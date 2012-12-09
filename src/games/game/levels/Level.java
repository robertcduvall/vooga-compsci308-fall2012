package games.game.levels;

import games.game.conditions.ICondition;
import games.game.core.Factory;
import games.game.core.IPaintable;
import games.game.core.IUpdatable;
import games.game.gameobject.DamagableSprite;
import games.game.gameobject.Sprite;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import javax.swing.JPanel;


/**
 * This class represents a level in the game.
 * 
 * @author Paul Dannenberg
 * 
 */
public class Level implements IPaintable, IUpdatable {

    private static final int SCORE_MULTIPLIER = 2;
    private Collection<ICondition> myWinningConditions, myLosingConditions;
    private int myStartTime;
    private Collection<Sprite> mySprites;
    private Collection<DamagableSprite> myEnemySprites;
    private Factory myFactory;
    private Collection<IUpdatable> myUpdatables;

    /**
     * Creates a new level.
     * 
     * @param board The JPanel on which the level will be drawn.
     */
    public Level(JPanel board) {
        myStartTime = (int) System.currentTimeMillis();
        mySprites = new ArrayList<Sprite>();
        myEnemySprites = new ArrayList<DamagableSprite>();
        setupFinishingConditions();
        setupManagers(board);
    }

    /**
     * Gets the managers to which this level will delegate
     * some of its behavior.
     * 
     * @param board The JPanel on which the level will be drawn.
     */
    private void setupManagers(JPanel board) {
        myFactory = new Factory(board, this);
        myUpdatables = myFactory.getUpdatableManagers();
    }

    /**
     * Adds a sprite to the level.
     * 
     * @param toAdd The sprite to be added.
     */
    public void add(Sprite toAdd) {
        mySprites.add(toAdd);
    }

    /**
     * Removes a sprite from the level.
     * 
     * @param toRemove The sprite to be removed.
     */
    public void remove(Sprite toRemove) {
        mySprites.remove(toRemove);
    }

    /**
     * Returns a collection of this level's sprites that were
     * added through the <code>add (Sprite toAdd)</code> method.
     * 
     * @return An unmodifiable collection of sprites.
     */
    public Collection<Sprite> getSprites() {
        return Collections.unmodifiableCollection(mySprites);
    }

    /**
     * Returns a collection of sprites currently in the level
     * that can be damaged.
     * 
     * @return An unmodifiable collection of damagable sprites.
     */
    public Collection<DamagableSprite> getDamagableSprites() {
        return Collections.unmodifiableCollection(myEnemySprites);
    }

    private void setupFinishingConditions() {
        myWinningConditions = new ArrayList<ICondition>();
        myLosingConditions = new ArrayList<ICondition>();
    }

    /**
     * Checks if the level has been won.
     * 
     * @return True if the level has been successfully
     *         completed by the player. False otherwise.
     */
    public boolean isWon() {
        return isFinished(myWinningConditions);
    }

    /**
     * Checks if the level has been lost.
     * 
     * @return True if the level has been lost. False
     *         otherwise.
     */
    public boolean isLost() {
        return isFinished(myLosingConditions);
    }

    /**
     * Checks to see whether the level is over.
     * 
     * @param endingConditions A group of conditions.
     * @return True if one of the conditions in <code>endingConditions</code>
     *         has been met. False, otherwise.
     */
    private boolean isFinished(Collection<ICondition> endingConditions) {
        for (ICondition endingCondition : endingConditions) {
            if (endingCondition.isMet()) { return true; }
        }
        return false;
    }

    /**
     * Computes the score. This is computed based on how long the player
     * has currently been playing the level.
     * 
     * @return The computed score.
     */
    public int getScore() {
        int computedScore = (int) ((System.currentTimeMillis() - myStartTime) * SCORE_MULTIPLIER);
        return computedScore;
    }

    /**
     * Paints the level's objects and the score.
     */
    @Override
    public void paint(Graphics2D pen) {
        char[] score = Character.toChars(getScore());
        pen.drawChars(score, score.length, 0, 0, 0);
        for (Sprite sprite : mySprites) {
            sprite.paint(pen);
        }
    }

    /**
     * Updates all the managers associated with this level that must be
     * updated during each frame.
     */
    @Override
    public void update() {
        for (IUpdatable updatableManager : myUpdatables) {
            updatableManager.update();
        }
    }
}
