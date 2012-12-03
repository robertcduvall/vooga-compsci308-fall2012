package util.networking.chat;

/**
 * 
 * @author Connor Gordon
 *
 */

public interface ChatListener {
    
    void handleMessageReceivedEvent(MessageReceivedEvent e);
    
    void handleErrorEvent(ErrorEvent e);
    
}
