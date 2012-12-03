package util.networking.chat;
import java.util.EventObject; 


/**
 * 
 * @author Connor Gordon
 *
 */
public class MessageReceivedEvent extends EventObject{

    private static final long serialVersionUID = -5925559604006719359L;
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
}
