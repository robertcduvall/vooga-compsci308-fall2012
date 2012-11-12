package arcade.usermanager;

/**
 * Stores message information.
 * 
 * @author Howard
 * 
 */
public class Message {
    private String mySender;
    private String myContent;
/**
 * Constructs a message.
 * @param sender
 * @param content
 */
    public Message (String sender, String content) {
        mySender = sender;
        myContent = content;
    }
}
