package arcade.usermanager;

/**
 * social center accommodate all the requests concerning user
 * including basic operations such as log on/register user
 * Send/receive messages between users
 * user game history
 * 
 * @author Difan Zhao
 * 
 */

public class SocialCenter {
    private User myCurrentUser;

    /*
     * initiate user list
     */
    public void run () {

    }

    /*
     * return log on status
     */
    public String logOnUser (String username, String password) {

    }

    /*
     * return log on status
     */
    public String registerUser (String username, String password) {

    }

    /*
     * return operation status
     */
    public String deleteUser (String username, String password) {

    }

    /*
     * return current user
     */
    public User getCurrentUser () {

        return myCurrentUser;

    }

    /*
     * edit user info
     */
    public void editCurrentUser () {

    }

    /*
     * return operation status
     */
    public String sendMessage (String sender, String receiver, String content) {

    }

    /*
     * return operation status
     */
    public String receiveMessage (String sender, String receiver, String content) {

    }

    /*
     * return game history for certain game
     */
    public String readGameHistory (String gameName) {

    }

    /*
     * return whether the operation is successful
     */
    public boolean writeGameHistory (String gameName) {

    }

}
