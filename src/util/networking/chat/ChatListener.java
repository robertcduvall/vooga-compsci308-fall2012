package util.networking.chat;

/**
 * The ChatListener interface handles all Events that can be triggered in the ChatClient
 * @author Connor Gordon
 *
 */
public interface ChatListener {
    
    /**
     * Takes the MessageReceivedEvent triggered and handles it appropriately.
     * @param e MessageReceivedEvent passed in
     */
    void handleMessageReceivedEvent(MessageReceivedEvent e);
    
    /**
     * Takes the ErrorEvent triggered and handles it appropriately.
     * @param e ErrorEventEvent passed in
     */
    void handleErrorEvent(ErrorEvent e);
    
    /**
     * Takes the UsersUpdateEvent triggered and handles it appropriately.
     * @param e UsersUpdateEvent passed in
     */
    void handleUsersUpdateEvent(UsersUpdateEvent e);
}
