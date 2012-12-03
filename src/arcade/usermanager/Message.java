package arcade.usermanager;

/**
 * Stores message information.
 * 
 * @author Howard
 *         modifed by difan zhao
 *         re-modified by Robert Bruce
 * 
 */
public class Message {
    private String mySender;
    private String myContent;

    /**
     * Constructs a message.
     * 
     * @param sender
     * @param content
     */
    public Message (String sender, String content) {
        mySender = sender;
        myContent = content;
    }
    
    public String getSender() {
        return mySender;
    }

    public String getMessage () {
        return myContent;
    }
}
