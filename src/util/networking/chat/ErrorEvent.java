package util.networking.chat;

import java.util.EventObject;

/**
 *
 * @author Oren Bukspan
 */
public class ErrorEvent extends EventObject {

    private static final long serialVersionUID = -323918138536983045L;
    private String myMessage;
    
    public ErrorEvent(Object source, String message) {
        super(source);
        myMessage = message;
    }
    
    public String getErrorMessage() {
        return myMessage;
    }
    
}
