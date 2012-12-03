package util.networking.chat;

import java.util.List;


/**
 * 
 * Javadoc comment.
 * 
 * @author Connor Gordon
 * @author Oren Bukspan
 */
public interface ChatProtocol {

    //Method to determine type of Protocol String
    
    ChatCommand getType (String input);
    
    
    
    //Methods to read from Protocol Strings

    String getUser (String input);

    String getPassword (String input);
    
    String getTo(String input);
    
    String getFrom(String input);
    
    String getErrorMessage(String input);
    
    String getBody(String input);
    
    boolean getStatus(String input);
    
    List<String> getListUsers(String input);
    
    
    //Methods to create Protocol Strings

    String createLoggedIn (String user, boolean b);

    String createAddUser (String user);
    
    String createRemoveUser (String user);

    String createError (String message);
    
    String createMessage (String from, String to, String body);
    
    String createListUsers(List<String> users);
    
    String createLogin(String user, String password);
    
    String createLogout(String user);
    
    String createRegister(String user, String password);
    
    
    //Protocol-specific port
    
    int getPort();
    
}
