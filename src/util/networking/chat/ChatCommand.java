package util.networking.chat;
/**
 * An enum representing possible actions that a ChatServer or
 * ChatClient would need to be able to process and support. Each
 * enum is linked to a method name to assist in invoking by reflection.
 * @author Oren Bukspan
 */
public enum ChatCommand {

    //Miscellaneous
    /**
     * XML format incorrect or tag not recognized.
     */
    UNKNOWN ("processUnknown", "UNKNOWN"),

    //Server Processes
    /**
     * Represents a request to login to the server.
     */
    LOGIN ("processLogin", "LOGIN"),
    /**
     * Represents a request to logout from the server.
     */
    LOGOUT ("processLogout", "LOGOUT"),
    /**
     * Represents a request to register an account on the server.
     */
    REGISTER ("processRegister", "REGISTER"),

    //Server Processes and Forwards
    /**
     * Represents a message sent from one client to the other on 
     * the server.
     */
    MESSAGE ("processMessage", "MESSAGE"),

    //Server Creates
    /**
     * Represents an error generated on the server (sent to the client).
     */
    ERROR ("processError", "ERROR"),
    /**
     * Represents the "logged in" status of a client - whether or not
     * there is a user currently logged in or not.
     */
    LOGGEDIN ("processLoggedIn", "LOGGEDIN"),
    /**
     * Represents a list of users currently online.
     */
    LISTUSERS ("processUsers", "LISTUSERS"),
    /**
     * Represents an addition to the list of users currently online.
     */
    ADDUSER ("processAddUser", "ADDUSER"),
    /**
     * Represents the removal of a user from the list of users currently online.
     */
    REMOVEUSER ("processRemoveUser", "REMOVEUSER");


    private String myMethodName;
    private String myCommandName;

    private ChatCommand(String method, String commandName) {
        myMethodName = method;
        myCommandName = commandName;
    }

    /**
     * For use in reflection, provides a method name that corresponds
     * to any particular ChatCommand.
     * @return The ChatCommand's corresponding method name.
     */
    public String getMethodName()  {
        return myMethodName;
    }

    /**
     * Finds a corresponding ChatCommand from the given String.
     * @param commandName A case-insensitive string to match.
     * @return The ChatCommand whose name matches this String.
     */
    public static ChatCommand getChatCommandFromString(String commandName) {
        String capitalName = commandName.toUpperCase();
        for (ChatCommand c : values()) {
            if (c.myCommandName.equals(capitalName)) {
                return c;
            }
        }
        return null;
    }
}