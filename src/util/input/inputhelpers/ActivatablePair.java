package util.input.inputhelpers;

/**
 * @author Amay
 * 
 * @param <T>
 * @param <E>
 */
public class ActivatablePair<T, E> {

    private T myElem1;
    private E myElem2;
    private Boolean myActiveState;

    public ActivatablePair (T elem1, E elem2) {
        this.myElem1 = elem1;
        this.myElem2 = elem2;
        myActiveState = true;
    }

    public ActivatablePair (T elem1, E elem2, boolean b) {
        this(elem1, elem2);
        myActiveState = b;
    }

    public void activate () {
        myActiveState = true;
    }

    public void deactivate () {
        myActiveState = false;
    }

    public boolean isActive () {
        return myActiveState;
    }

    public T getFirst () {
        return myElem1;
    }

    public E getLast () {
        return myElem2;
    }

    public void setFirst (T elem) {
        myElem1 = elem;
    }

    public void setLast (E elem) {
        myElem2 = elem;
    }
}
