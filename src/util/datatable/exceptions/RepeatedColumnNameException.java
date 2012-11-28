package util.datatable.exceptions;




/**
 * Exception thrown for repeated column name.
 * @author Lance
 *
 */
@SuppressWarnings("serial")
public class RepeatedColumnNameException extends
    Exception {
    private static final String DESCRIPTION = " Repeated Column Name ";

    /**
     * @param repeatedColumn - specific column repeated
     */
    public RepeatedColumnNameException(String repeatedColumn) {
        super(DESCRIPTION + repeatedColumn);
    }


}

