package util.networking.chat;
import java.util.EventObject; 
import util.networking.ClientEvent;


/**
 * 
 * @author Connor Gordon
 *
 */
public class MessageReceivedEvent extends EventObject implements ClientEvent {
    private String myRecipient;
    private String mySender;
    private String myMessageBody;
    
    public MessageReceivedEvent(Object source, String to, String from, String body){
        super(source);
        myRecipient = to;
        mySender = from;
        myMessageBody = body;
    }

    public String getRecipient(){
        return myRecipient;
    }
    
    public String getSender(){
        return mySender;
    }
    
    public String getMessageBody(){
        return myMessageBody;
    }
    
    public String getName() {
        return "NewMessage";
    }
}
