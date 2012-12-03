package util.networking.chat;
/**
 *
 * @author Oren Bukspan
 * @author Connor Gordon
 */
public abstract class ChatAdapter implements ChatListener {

    @Override
    public void handleMessageReceivedEvent (MessageReceivedEvent e) {
    }

    @Override
    public void handleErrorEvent (ErrorEvent e) {
    }

}
