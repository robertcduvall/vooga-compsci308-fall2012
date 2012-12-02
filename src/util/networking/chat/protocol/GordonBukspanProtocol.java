package util.networking.chat.protocol;

import java.io.File;
import org.w3c.dom.Document;
import util.networking.chat.ChatCommand;
import util.networking.chat.ChatProtocol;
import util.xml.XmlUtilities;

/**
 * One example of a ChatProtocol compatible with ChatServer.
 *
 * @author Oren Bukspan
 * @author Connor Gordon
 */
public class GordonBukspanProtocol implements ChatProtocol {

    @Override
    public ChatCommand getType (String input) {
        String commandName;
        File f = new File("", 0);
        Document doc = XmlUtilities.makeDocument();
        
        
        ChatCommand c = ChatCommand.getChatCommandFromString(commandName);
        return ChatCommand.UNKNOWN;
    }

    @Override
    public String getUser (String input) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getPassword (String input) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getTo (String input) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getFrom (String input) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String createLoggedIn (boolean b) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String createAddUser (String user) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String createRemoveUser (String user) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String createError (String string) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String createMessage (String to, String dest, String body) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int getPort () {
        // TODO Auto-generated method stub
        return 0;
    }

}
