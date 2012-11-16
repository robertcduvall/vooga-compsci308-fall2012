package arcade.datatransfer;

import java.awt.Image;
import java.util.List;

/**
 * List of get and set methods for GUI to interface with User model
 * 
 * @author Michael Deng, Robert Bruce, Kannan Raju
 *
 */
public class UserLink {
    /**
     * Should change the user of UserLink to whatever user is successfully logged in.
     * @param username String of the username to be logged in.
     * @param password Array of characters for the attempted password. Must be array.
     * @return True if successful login, false if unsuccessful.
     */
    public boolean loginUser(String username, char[] password){
        // TODO Implement this. Return true if login successful, false if unsuccessful.
        return false;
    }

    public String getUserName() {
        // TODO Auto-generated method stub
        return null;
    }
    
    public Image getUserProfilePicture() {
        // TODO Auto-generated method stub
        return null;
    }
    
    public List<String> getListOfGamesPlayed() {
        // TODO Auto-generated method stub
        return null;
    }
    
    public List<Integer> getListOfScores() {
        // TODO Auto-generated method stub
        return null;
    }
}
