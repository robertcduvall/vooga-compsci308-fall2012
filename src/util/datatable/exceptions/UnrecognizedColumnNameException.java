package util.datatable.exceptions;

public class UnrecognizedColumnNameException extends Exception {
    private final static String DESCRIPTION=" column does not exist";
    
    
    public UnrecognizedColumnNameException(String unmatchedCol){
        super(unmatchedCol+ DESCRIPTION);
    }
}
