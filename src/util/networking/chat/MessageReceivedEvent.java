package util.networking.chat;

import java.util.EventObject;


/**
 * The MessageReceivedEvent is triggered when the Client receives a new message
 * from the server.
 * This was implemented so that the GUI would not have to constantly poll the
 * client for messages received
 * 
 * @author Connor Gordon
 */
public class MessageReceivedEvent extends EventObject {

    private static final long serialVersionUID = -5925559604006719359L;
    private String myRecipient;
    private String mySender;
    private String myMessageBody;

    /**
     * @param source location where event is triggered
     * @param to message destination
     * @param from message sender
     * @param body body of message
     */
    public MessageReceivedEvent (Object source, String to, String from, String body) {
        super(source);
        myRecipient = to;
        mySender = from;
        myMessageBody = body;
    }

    /**
     * Returns the name of the Message Recipient
     * 
     * @return name of recipient
     */
    public String getRecipient () {
        return myRecipient;
    }

    /**
     * Returns the name of the user who sent the message
     * 
     * @return name of the sender
     */
    public String getSender () {
        return mySender;
    }

    /**
     * Returns the message text
     * 
     * @return message text
     */
    public String getMessageBody () {
        return myMessageBody;
    }
}
