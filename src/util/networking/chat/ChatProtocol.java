package util.networking.chat;

/**
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
    
    
    
    //Methods to create Protocol Strings

    String createLoggedIn (boolean b);

    String createAddUser (String user);
    
    String createRemoveUser (String user);

    String createError (String string);
    
    String createMessage (String to, String dest, String body);
    
    
    
    //Protocol-specific port
    
    int getPort();
    
}
