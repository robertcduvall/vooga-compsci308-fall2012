package arcade.usermanager;

import java.util.Date;

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
    private Date myDate;

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
    
    public Message (String sender, String content, Date timeStamp) {
        mySender = sender;
        myContent = content;
        myDate = timeStamp;
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

    /**
     * 
     * @return
     */
    public String getDateString () {
        return myDate.toString();
    }
}
