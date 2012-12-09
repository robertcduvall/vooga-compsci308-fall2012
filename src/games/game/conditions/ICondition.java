package games.game.conditions;

/**
 * This interface represents a condition that will
 * terminate a level. This will occur if the <code>
 * isMet()</code> method returns true. This can
 * specify that the user has lost OR that the user
 * has won.
 * 
 * @author Paul Dannenberg
 * 
 */
public interface ICondition {

    /**
     * Determines whether or not a level-ending
     * condition has been met.
     * 
     * @return True if the level should end. False
     *         otherwise.
     */
    boolean isMet();

}
