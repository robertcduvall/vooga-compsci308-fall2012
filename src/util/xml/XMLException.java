package util.xml;

/**
 * A general exception that represents all possible Java Reflection exceptions.
 * 
 * @author Robert C. Duvall
 */
@SuppressWarnings("serial")
public final class XMLException extends RuntimeException {
    public XMLException (String s) {
        super(s);
    }

    public XMLException (String s, Throwable cause) {
        super(s, cause);
    }
}