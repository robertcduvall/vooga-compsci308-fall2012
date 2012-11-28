package vooga.platformer.levelfileio;

/**
 * A general exception that represents all possible exceptions while reading and
 * writing a platformer level data file.
 * 
 * @author Robert C. Duvall
 * @author Grant Oakley (repurposed)
 */
@SuppressWarnings("serial")
public class LevelFileIOException extends RuntimeException {
    /**
     * Creates a new LevelFileIOException with the specified error message.
     * 
     * @param s error message
     */
    public LevelFileIOException (String s) {
        super(s);
    }

    /**
     * Creates a new LevelFileIOException with the specified error message.
     * 
     * @param s error message
     * @param cause Throwable that caused the exception.
     */
    public LevelFileIOException (String s, Throwable cause) {
        super(s, cause);
    }
}
