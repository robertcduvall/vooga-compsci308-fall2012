package util.xml;

/**
 * A general exception that represents all possible Java Reflection exceptions.
 * 
 * @author Robert C. Duvall
 */
@SuppressWarnings("serial")
public final class XmlException extends RuntimeException {
    public XmlException (String s) {
        super(s);
    }

    public XmlException (String s, Throwable cause) {
        super(s, cause);
    }
}