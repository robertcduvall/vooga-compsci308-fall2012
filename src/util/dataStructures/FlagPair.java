package util.dataStructures;

/**
 * This class represents a pair of any two objects.
 * Deactivating a FlagPair makes it unmodifiable.
 *
 * @author Amay
 *
 * @param <T> - One kind of object
 * @param <E> - Another kind of object
 */
public class FlagPair<T, E> {

    private T myElem1;
    private E myElem2;
    private Boolean myActiveState;

    /**
     * Create a new FlagPair.
     * BY default this FlagPair is active.
     *
     * @param elem1 - Any object
     * @param elem2 - Any object
     */
    public FlagPair (T elem1, E elem2) {
        this.myElem1 = elem1;
        this.myElem2 = elem2;
        myActiveState = true;
    }

    /**
     * Create a new FlagPair with desired active state.
     *
     * @param elem1 - Any object
     * @param elem2 - Any object
     * @param active - State of FlagPair
     */
    public FlagPair (T elem1, E elem2, boolean active) {
        this(elem1, elem2);
        myActiveState = active;
    }

    /**
     * Activate this FlagPair to make it modifiable.
     *
     */
    public void activate () {
        myActiveState = true;
    }

    /**
     * Deactivate this FlagPair to make it unmodifiable.
     *
     */
    public void deactivate () {
        myActiveState = false;
    }

    /**
     * Check the flag of this FlagPair.
     *
     * @return - The flag
     */
    public boolean isActive () {
        return myActiveState;
    }

    /**
     * Get the first element in the FlagPair.
     *
     * @return - The first element
     */
    public T getFirst () {
        return myElem1;
    }

    /**
     * Get the last element in the FlagPair.
     *
     * @return - The last element
     */
    public E getLast () {
        return myElem2;
    }

    /**
     * If modifiable, change the first element.
     *
     * @param elem - The first element in the pair
     */
    public void setFirst (T elem) {
        if (isActive()) {
            myElem1 = elem;
        }
    }

    /**
     * If modifiable, change the last element.
     *
     * @param elem - The last element in the pair
     */
    public void setLast (E elem) {
        if (isActive()) {
            myElem2 = elem;
        }
    }
}
