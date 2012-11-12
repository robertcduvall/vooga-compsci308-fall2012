package util.input.input_utils;

/**
 * @author Amay
 * 
 * @param <T>
 * @param <E>
 */
public class BoolTuple<T, E> {

    T elem1;
    E elem2;
    Boolean active;

    public BoolTuple(T elem1, E elem2) {
        this.elem1 = elem1;
        this.elem2 = elem2;
        active = true;
    }

    public BoolTuple(T elem1, E elem2, boolean b) {
        this(elem1, elem2);
        active = b;
    }

    public void activate() {
        active = true;
    }

    public void deactivate() {
        active = false;
    }

    public boolean isActive() {
        return active;
    }

    public T getFirst() {
        return elem1;
    }

    public E getLast() {
        return elem2;
    }

    public void setFirst(T elem) {
        elem1 = elem;
    }

    public void setLast(E elem) {
        elem2 = elem;
    }
}
