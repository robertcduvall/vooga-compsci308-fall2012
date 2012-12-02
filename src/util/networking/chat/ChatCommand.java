package util.networking.chat;
/**
 * An enum representing possible actions that a ChatServer or
 * ChatClient would need to be able to process and support. Each
 * enum is linked to a method name to assist in invoking by reflection.
 * @author Oren Bukspan
 */
public enum ChatCommand {

    //Miscellaneous
    UNKNOWN ("processUnkown", "UNKNOWN"),
    
    //Server Processes
    LOGIN ("processLogin", "LOGIN"),
    LOGOUT ("processLogout", "LOGOUT"),
    REGISTER ("processRegister", "REGISTER"),
    
    //Server Processes and Forwards
    MESSAGE ("processMessage", "MESSAGE"),
    
    //Server Creates
    ERROR ("processError", "ERROR"),
    LOGGEDIN ("processLoggedIn", "LOGGEDIN"),
    LISTUSERS ("processUsers", "LISTUSERS"),
    ADDUSER ("processAddUser", "ADDUSER"),
    REMOVEUSER ("processRemoveUser", "REMOVEUSER");
    

    private String myMethodName;
    
    private ChatCommand(String method, String commandName) {
        myMethodName = method;              
    }
    
    public String getMethodName()  {
        return myMethodName;
    }
    
    private String getCommandName()  {
        return myMethodName;
    }
    
    public static ChatCommand getChatCommandFromString(String commandName) {
        for (ChatCommand c : values()) {
            if (c.getCommandName().equals(commandName)) {
                return c;
            }
        }
        return null;
    }
}
