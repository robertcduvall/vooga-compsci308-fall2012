package util.networking.chat;

import java.util.Collections;
import java.util.EventObject;
import java.util.List;

/**
 * The UsersUpdateEvent is an event that is triggered when a change is made to the buddy list
 * {either a user, outside of the client, signs on/off} 
 * @author Connor Gordon
 *
 */
public class UsersUpdateEvent extends EventObject {

    private static final long serialVersionUID = 2634932792489861477L;
    private List<String> myUsers;

    /**
     * @param source Object where the event is being triggered
     * @param users new updated list of users online
     */
    public UsersUpdateEvent (Object source, List<String> users) {
        super(source);
        myUsers = users;
    }

    /**
     * Returns current lists of users online
     * 
     * @return buddy list
     */
    public List<String> getUsers () {
        return Collections.unmodifiableList(myUsers);
    }
}
