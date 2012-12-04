package util.networking.chat;

import java.util.EventObject;


/**
 * ErrorEvents are triggered when data sent to the client is invalid or
 * incompatible (e.g. Username registered is already taken, User already logged
 * in, User not authorized to send message from username, etc.)
 * 
 * @author Oren Bukspan
 */
public class ErrorEvent extends EventObject {

    private static final long serialVersionUID = -323918138536983045L;
    private String myMessage;

    /**
     * Creates new Error Event
     * @param source Object where event is triggered
     * @param message description of reason for error
     */
    public ErrorEvent (Object source, String message) {
        super(source);
        myMessage = message;
    }

    /**
     * Returns the description of error message
     * 
     * @return error message
     */
    public String getErrorMessage () {
        return myMessage;
    }

}
