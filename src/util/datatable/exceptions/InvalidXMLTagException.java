package util.datatable.exceptions;

@SuppressWarnings("serial")
public class InvalidXMLTagException extends Exception {
    private final static String DESCRIPTION = " Invalid XML column name ";

    /**
     * @param  invalidColumnName - invalid column name
     */
    public InvalidXMLTagException(String invalidColumnName) {
        super(DESCRIPTION + invalidColumnName);
    }
}
