package util.input.inputhelpers;

import util.dataStructures.Pair;

/**
 * This class is a pair with the additional property of having
 * a boolean state.
 *
 * @author Amay
 *
 * @param <A> - One kind of object
 * @param <B> - Another kind of object
 */
public class FlagPair<A, B> extends Pair<A, B>{ 
    
    private Boolean myActiveState;

    /**
     * Create a new FlagPair.
     * By default the boolean state is true.
     *
     * @param elem1 - Any object
     * @param elem2 - Any object
     */
    public FlagPair (A elem1, B elem2) {
        super(elem1, elem2);
        myActiveState = true;
    }

    /**
     * Create a new FlagPair with desired boolean state.
     *
     * @param elem1 - Any object
     * @param elem2 - Any object
     * @param active - Boolean state
     */
    public FlagPair (A elem1, B elem2, boolean active) {
        super(elem1, elem2);
        myActiveState = active;
    }

    /**
     * Set boolean state to true.
     *
     */
    public void activate () {
        myActiveState = true;
    }

    /**
     * Set boolean state to false.
     *
     */
    public void deactivate () {
        myActiveState = false;
    }

    /**
     * Check the boolean state of this FlagPair.
     *
     * @return - boolean state
     */
    public boolean isActive () {
        return myActiveState;
    }
}
