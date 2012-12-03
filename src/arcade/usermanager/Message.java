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
     * @param sender The user who sent the message
     * @param content The content of the message
     */
    public Message (String sender, String content) {
        mySender = sender;
        myContent = content;
    }

    /**
     * Gets the sender of the message.
     * 
     * @return
     */
    public String getSender () {
        return mySender;
    }

    /**
     * Gets the content of the message.
     * 
     * @return
     */
    public String getMessage () {
        return myContent;
    }
}
