package games.game.conditions;

import games.game.levels.Level;




/**
 * This class represents a condition that will cause
 * the level to end if all enemies have been killed.
 * 
 * @author Paul Dannenberg
 * 
 */
public class EnemiesKilled implements ICondition {

    private Level myLevel;

    /**
     * Creates a new condition.
     * 
     * @param level The level for which this is a
     *        condition.
     */
    public EnemiesKilled(Level level) {
        myLevel = level;
    }

    /**
     * Returns true if all sprites that can be damaged
     * are no longer present in the level (i.e. they have
     * died).
     */
    @Override
    public boolean isMet() {
        return myLevel.getDamagableSprites().isEmpty();
    }

}
