package util.datatable;


/**
 * An unmodifiable row element that only
 * allows user to retrieve entries.
 * @author Lance
 *
 */
public class UnmodifiableRowElement extends RowElement {
    /**
     * Instantiating an unmodifiable row element.
     * @param re
     */
    public UnmodifiableRowElement (RowElement re) {
        super(re);
    }
}
