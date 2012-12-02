package util.networking.chat.protocol;

import java.util.List;
import util.networking.chat.ChatCommand;
import util.networking.chat.ChatProtocol;

/**
 * 
 * @author Connor Gordon
 * @author Oren Bukspan
 */
public class ProtocolXMPP implements ChatProtocol {

    private static final int PORT = 5222;
    
    @Override
    public ChatCommand getType (String input) {
        // TODO Auto-generated method stub
        return null;
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
    public String getMessage (String input) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<String> getListUsers (String input) {
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
    public String createError (String message) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String createMessage (String from, String to, String body) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String createListUsers (List<String> users) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String createLogin (String user, String password) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String createLogout (String user) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String createRegister (String user, String password) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int getPort () {
        return PORT;
    }
    
}
