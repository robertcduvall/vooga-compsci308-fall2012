package vooga.shooter.level_editor;

/**
 * A general exception that represents all possible exceptions thrown by the Level Editor.
 * 
 * @author Robert Duvall, modified by Zachary Hopping
 */
@SuppressWarnings("serial")
public class LevelEditorException extends RuntimeException{
    private final Throwable myCause;

    /**
     * Create exception with given message.
     * 
     * @param message explanation of problem
     * @param values details of problem
     */
    public LevelEditorException (String message, Object ... values) {
        super(String.format(message, values));
        myCause = this;
    }

    /**
     * Create exception with given message.
     * 
     * @param cause original cause of this exception
     * @param message explanation of problem
     * @param values details of problem
     */
    public LevelEditorException (Throwable cause, String message, Object ... values) {
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
