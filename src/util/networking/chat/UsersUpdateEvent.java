package util.networking.chat;

import java.util.EventObject;
import java.util.List;

public class UsersUpdateEvent extends EventObject {
    /**
     * 
     */
    private static final long serialVersionUID = 2634932792489861477L;
    List<String> myUsers;
    
    public UsersUpdateEvent(Object source, List<String> users){
        super(source);
        myUsers = users;
    }
    
    public List<String> getUsers(){
        return myUsers;
    }
}
