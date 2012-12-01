package util.networking.chat;
/**
 * An enum representing possible actions that a ChatServer or
 * ChatClient would need to be able to process and support. Each
 * enum is linked to a method name to assist in invoking by reflection.
 * @author Oren Bukspan
 */
public enum ChatCommand {

    //Server Processes
    LOGIN ("processLogin"),
    LOGOUT ("processLogout"),
    REGISTER ("processRegister"),
    
    //Server Processes and Forwards
    MESSAGE ("processMessage"),
    
    //Server Creates
    ERROR ("processError"),
    LOGGED_IN ("processLoggedIn"),
    LIST_USERS ("processUsers"),
    ADD_USER ("processAddUser"),
    REMOVE_USER ("processRemoveUser");
    
    private String myMethodName;
    
    private ChatCommand(String method) {
        myMethodName = method;              
    }
    
    public String getMethodName()  {
        return myMethodName;
    }
}
