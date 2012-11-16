package arcade.usermanager;

/**
 * Stores message information.
 * 
 * @author Howard
 *      modifed by difan zhao
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
    public Message(String sender, String content) {
        mySender = sender;
        myContent = content;
    }
    
    public String getMessage(){
        return "From "+mySender+":"+"\n"+myContent;
    }
}
