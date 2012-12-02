package util.networking.chat;
import java.util.EventObject; 
import util.networking.ClientEvent;


/**
 * 
 * @author Connor Gordon
 *
 */
public class NewMessageEvent extends EventObject implements ClientEvent {
    public NewMessageEvent(Object source){
        super(source);
    }
}
