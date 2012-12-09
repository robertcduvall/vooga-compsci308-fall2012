package games.game.conditions;

/*
 * Maybe should've used a timer here...
 */

/**
 * This class represents a condition on the level. If the time
 * limit exceeds a particular amount the level will be over. The
 * time limit begins from when this object was created.
 * 
 * @author Paul Dannenberg
 * 
 */
public class TimeExpired implements ICondition {

    private static final int TIME_LIMIT = 20000;
    private int myCreationTime;

    /**
     * Creates a new condition.
     */
    public TimeExpired() {
        myCreationTime = (int) System.currentTimeMillis();
    }

    /**
     * Returns true if time has expired. False, otherwise.
     */
    @Override
    public boolean isMet() {
        return System.currentTimeMillis() - myCreationTime > TIME_LIMIT;
    }

}
