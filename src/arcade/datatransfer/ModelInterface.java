package arcade.datatransfer;

import java.io.IOException;
import java.util.List;
import util.encrypt.Encrypter;
import arcade.gamemanager.Game;
import arcade.gamemanager.GameCenter;
import arcade.gui.Arcade;
import arcade.usermanager.Message;
import arcade.usermanager.SocialCenter;
import arcade.usermanager.User;
import arcade.usermanager.UserManager;
import arcade.usermanager.UserProfile;
import arcade.usermanager.exception.ValidationException;


/**
 * Methods that facilitate exchange of data between
 * the view and models of the Arcade
 * 
 * @author Michael Deng
 * 
 */
public class ModelInterface {

    private Arcade myArcade;
    private GameCenter myGameCenter;
    private SocialCenter mySocialCenter;
    private UserManager myUserManager;

    /**
     * 
     * @param a
     */
    public ModelInterface (Arcade a) {
        myArcade = a;
        mySocialCenter = new SocialCenter();
        myGameCenter = new GameCenter(); // GameCenter is currently broken.
        myUserManager=UserManager.getInstance();
    }

    // #############################################################
    // To be Implemented by the Game team
    // #############################################################

    /**
     * returns the list of available games.
     * 
     * @return list of available games
     */
    public List<String> getGameList () {
        return myGameCenter.getGameList();
    }

    /**
     * returns Game object specified by the game's name. If no game is found,
     * returns null.
     * 
     * @param gameName name of requested game
     * @return requested Game object or null if no such game is found
     */
    public Game getGame (String gameName) {
        return myGameCenter.getGame(gameName);
    }

    /**
     * returns a list of games that have the tag.
     * 
     * @param tag a tag that games have in common
     * @return list of games that have the tag.
     */
    public List<String> getGameListByTagName (String tag) {
        return myGameCenter.getGameListByTagName(tag);
    }

    // #############################################################
    // To be Implemented by the User team
    // #############################################################

    /**
     * Should change the user of UserLink to whatever user is successfully
     * logged in.
     * 
     * @param username String of the username to be logged in.
     * @param password string password
     * @return True if successful login, false if unsuccessful.
     */
    public boolean executeLogin (String username, String password) {
        // TODO Implement this. Return true if login successful, false if
        // unsuccessful.
        /*
         * // test code
         * if (username.equals("mdeng1990") && password.equals("pswd")) { return
         * true; }
         * 
         * return false;
         */
        try {
            return mySocialCenter.logOnUser(username, Encrypter.hashCode(password));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * method creates a new user.
     * if the request is trying to create a user that ALREADY EXISTS, this
     * method
     * should return false, and the new user should not be created
     * 
     * if the new user is created successfully, the new user is automatically
     * logged in (do not need to go through login screen).
     * 
     * @param username
     * @param password
     * @param fn
     * @param ln
     * @return true if successful, false if unsuccessful
     */
    public boolean executeNewUser (String username, String password, String fn, String ln) {

        // test code
        // if (username.equals("mdeng1990")) { return false; }
        try {
            return mySocialCenter.registerUser(username, Encrypter.hashCode(password), fn, ln);
        }
        catch (ValidationException e) {
            return false;
        }
        catch (IOException e) {
            return false;
        }
    }

    /**
     * 
     * This is for listing all the users on the UserListMainPane.
     * 
     * @return A list of all the users in the system.
     */
    public List<UserProfile> getAllUsers () {
        return  myUserManager.getAllUserProfile();
    }
    /**
     * This is useful for sizing components.
     * @return The number of profiles in the system.
     */
    public int getNumUsers() {
        return myUserManager.getAllUserProfile().size();
    }

    /**
     * 
     * 
     * @param username The username of the User we want to access.
     * @return The User class with the corresponding username.
     */
    public UserProfile getUser (String username) {
        return myUserManager.getUserProfile(username);
    }

    /**
     * @return get an editable user class for the current user
     */

    public User getEditableCurrentUser(){
        return myUserManager.getCurrentUser();

    }

    /**
     * 
     */
    public boolean sendMessage(String sender, String recipient, String messageContent) {
        return mySocialCenter.sendMessage(sender, recipient, messageContent);
    }

    /**
     * delete user profile
     * @param userName
     * @param password
     * @return
     */

    public boolean deleteUser(String userName, String password){
        return   mySocialCenter.deleteUser(userName, password);

    }

    public void changeAdminStatus (String name, boolean adminStatus) {
        myUserManager.changeAdminStatus(name, adminStatus);
    }
    
    public void sendMessage(String receiver, String content){
        mySocialCenter.sendMessage(receiver, content);
    }
    
    public List<Message> getMessage(){
        return myUserManager.getMessage();
    }
}
