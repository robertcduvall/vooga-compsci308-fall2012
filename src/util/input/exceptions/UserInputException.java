package util.input.exceptions;

/**
 * A general exception framework for user input.
 * 
 * @author Robert C. Duvall
 *         Modified by Ben Schwab
 */
@SuppressWarnings("serial")
public final class UserInputException extends Exception {
    private final Throwable myCause;

    /**
     * Create exception with given message.
     * 
     * @param message explanation of problem
     * @param values details of problem
     */
    public UserInputException (String message, Object ... values) {
        super(String.format(message, values));
        myCause = this;
    }

    /**
     * Create exception with given message, appending the message provided by
     * the given
     * cause to this message.
     * 
     * @param cause original cause of this exception
     * @param message explanation of problem
     * @param values details of problem
     */
    public UserInputException (Throwable cause, String message, Object ... values) {
        super(String.format(message, values) + "\n" + cause.getMessage());
        myCause = cause;
    }

    /*
     * @see java.lang.Throwable#getCause()
     */
    @Override
    public Throwable getCause () {
        return myCause;
    }
}
