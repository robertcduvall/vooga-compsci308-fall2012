package util.networking.chat;

import java.util.List;


/**
 * 
 * The ChatProtocol interface defines what functionality all ChatProtocol
 * subclasses must have. Subclasses must both encrypt client data as well as
 * decrypt Server response
 * 
 * @author Connor Gordon
 * @author Oren Bukspan
 */
public interface ChatProtocol {

    // Method to determine type of Protocol String

    /**
     * Extracts ChatCommand from String formatted according to this protocol
     * @param input String encrypted by this protocol
     * @return ChatCommand of input
     */
    ChatCommand getType (String input);

    // Methods to read from Protocol Strings

    /**
     * Returns name of user from a Protocol-formatted input
     * @param input data passed in, organized by protocol
     * @return name of user
     */
    String getUser (String input);

    /**
     * Returns password of user from a Protocol-formatted input
     * @param input data passed in, previously organized by protocol
     * @return password of user
     */
    String getPassword (String input);

    /**
     * Returns name of message recipient from a Protocol-formatted input
     * @param input data passed in, previously organized by protocol
     * @return name of message recipient
     */
    String getTo (String input);

    /**
     * Returns name of message sender from a Protocol-formatted input
     * @param input data passed in, previously organized by protocol
     * @return name of message sender
     */
    String getFrom (String input);

    /**
     * Returns message associated with error from a Protocol-formatted input
     * @param input data passed in, previously organized by protocol
     * @return message associated with error
     */
    String getErrorMessage (String input);

    /**
     * Returns message body from a Protocol-formatted input
     * @param input data passed in, previously organized by protocol
     * @return message body
     */
    String getBody (String input);

    /**
     * Returns loggedInStatus from a Protocol-formatted input
     * @param input data passed in, previously organized by protocol
     * @return loggedInStatus
     */
    boolean getStatus (String input);

    /**
     * Returns list of users online from a Protocol formatted input
     * @param input data passed in, previously organized by protocol
     * @return list of users online
     */
    List<String> getListUsers (String input);

    // Methods to create Protocol Strings

    /**
     * Create String displaying LoggedIn status, formatted according to protocol
     * @param user name of user
     * @param b login status
     * @return loggedIn status as a string, protocol-formatted
     */
    String createLoggedIn (String user, boolean b);

    /**
     * Create String displaying user addition event, formatted according to protocol
     * @param user name of user
     * @return user addition as a string, protocol-formatted
     */
    String createAddUser (String user);
    
    /**
     * Create String displaying user removal event, formatted according to protocol
     * @param user name of user
     * @return user removal event as a string, protocol-formatted
     */
    String createRemoveUser (String user);

    /**
     * Generate Error Message formatted according to protocol
     * @param message error description
     * @return error message in protocol format
     */
    String createError (String message);

    /**
     * Create String of Message Data, formatted according to protocol
     * @param from message sender
     * @param to message recipient
     * @param body message text
     * @return string of protocol-formatted message data
     */
    String createMessage (String from, String to, String body);

    /**
     * Create String of Users Online, formatted according to protocol
     * @param users List of Users online
     * @return string of protocol-formatted users online
     */
    String createListUsers (List<String> users);

    /**
     * Format login data according to protocol
     * @param user username
     * @param password password of user
     * @return login data encryped as protocol
     */
    String createLogin (String user, String password);

    /**
     * Create String of logout info, formatted according to protocol
     * @param user name of user that is logging out
     * @return logout information as protocol-formatted string
     */
    String createLogout (String user);

    /**
     * Create String of Register info, formatted according to protocol
     * @param user name of user that is registering
     * @param password the password that they select
     * @return
     */
    String createRegister (String user, String password);

    // Protocol-specific port

    /**
     * Returns the appropriate port to open sockets on when using this Protocol
     * 
     * @return port numbers
     */
    int getPort ();

}
