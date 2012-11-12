package arcade.usermanager;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

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
    private List<String> availableUsers;

    /*
     * initiate user list
     */
    public void updateUser () {
        
        availableUsers=new ArrayList<String>();
        
        File folder = new File("your/path");
        File[] listOfFiles = folder.listFiles();

        for (int i = 0; i < listOfFiles.length; i++) {
        if (listOfFiles[i].isFile()) {
        availableUsers.add(listOfFiles[i].getName());
      }
    }
        

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
