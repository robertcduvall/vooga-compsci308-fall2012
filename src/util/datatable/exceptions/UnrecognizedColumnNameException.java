package util.datatable.exceptions;

/**
 * Unrecognized column name.
 * @author Lance
 *
 */
@SuppressWarnings("serial")
public class UnrecognizedColumnNameException extends Exception {
    private static final String DESCRIPTION = " column does not exist";

    /**
     * @param unmatchedCol - specific column
     */
    public UnrecognizedColumnNameException(String unmatchedCol) {
        super(unmatchedCol + DESCRIPTION);
    }
}
