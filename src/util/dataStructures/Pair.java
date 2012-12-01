package util.dataStructures;

/**
 * This class represents a pair of any two objects.
 *
 * @author Amay
 *
 * @param <A> - One kind of object
 * @param <B> - Another kind of object
 */
public class Pair<A, B> {

    private A myElem1;
    private B myElem2;

    /**
     * Create a new Pair.
     *
     * @param elem1 - Any object
     * @param elem2 - Any object
     */
    public Pair (A elem1, B elem2) {
        this.myElem1 = elem1;
        this.myElem2 = elem2;
    }

    /**
     * Get the first element in the Pair.
     *
     * @return - The first element
     */
    public A getFirst () {
        return myElem1;
    }

    /**
     * Get the last element in the Pair.
     *
     * @return - The last element
     */
    public B getLast () {
        return myElem2;
    }

    /**
     * Change the first element.
     *
     * @param elem - The first element in the pair
     */
    public void setFirst (A elem) {
        myElem1 = elem;
    }

    /**
     * Change the last element.
     *
     * @param elem - The last element in the pair
     */
    public void setLast (B elem) {
        myElem2 = elem;
    }
}
