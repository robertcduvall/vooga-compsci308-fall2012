package arcade.datatransfer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import util.encrypt.Encrypter;
import arcade.gamemanager.Game;
import arcade.gamemanager.GameCenter;
import arcade.gui.Arcade;
import arcade.usermanager.EditableUserProfile;
import arcade.usermanager.GameData;
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
        myGameCenter = new GameCenter();
        myUserManager = UserManager.getInstance();
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
     * @param username username of user
     * @param password password of user
     * @param fn first name of user
     * @param ln last name of user
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
        return myUserManager.getAllUserProfile();
    }

    /**
     * This is useful for sizing components.
     * 
     * @return The number of profiles in the system.
     */
    public int getNumUsers () {
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

    public EditableUserProfile getEditableCurrentUser () {
        return myUserManager.getEditableCurrentUser();
    }

    /**
     * We actually need to be able to get the User.
     * Stop changing it so we can't.
     * It's messing things up.
     * <3 Rob
     * 
     * @return The User that is currently logged in.
     */
    public User getCurrentUserDontDeleteThisMethod () {
        return myUserManager.getCurrentUserDontDeleteThisMethod();
    }

    /**
     * Sends a message to a user.
     * 
     * @param
     */
    public boolean sendMessage (String sender, String recipient, String messageContent) {
        return mySocialCenter.sendMessage(sender, recipient, messageContent);
    }

    /**
     * Deletes a user.
     * 
     * @param userName
     * @param password
     * @return
     */
    public boolean deleteUser (String userName, String password) {
        return mySocialCenter.deleteUser(userName, Encrypter.hashCode(password));

    }

    /**
     * Changes a user's admin status.
     * 
     * @param name
     * @param adminStatus
     */
    public void changeAdminStatus (String name, boolean adminStatus) {
        myUserManager.changeAdminStatus(name, adminStatus);
    }

    /**
     * Returns a list of String[] that contain game names and high scores for a
     * user.
     * 
     * @param userName The name of the user in question
     * @return
     */
    public List<String[]> getUserHighScores (String userName) {
        List<String[]> list = new ArrayList<String[]>();
        List<GameData> gameList = myUserManager.getGameList(userName);
        for (GameData gd : gameList) {
            String[] arr = { gd.getGameInfo("name"), gd.getGameInfo("highscore") };
            list.add(arr);
        }
        return list;
    }

    /**
     * Returns a sorted list of String[] that contain user names and high scores
     * for a
     * game.
     * "username - highscore"
     * 
     * @param gameName The name of the game in question
     * @return
     */
    public List<String[]> getGameHighScores (String gameName) {
        List<String[]> list = new ArrayList<String[]>();
        List<UserProfile> profileList = myUserManager.getAllUserProfile();
        for (UserProfile prof : profileList) {
            String userName = prof.getUserName();
            List<String[]> userHighScores = getUserHighScores(userName);
            for (String[] arr : userHighScores) {
                if (arr[0] == gameName) {
                    String[] newarr = { userName, arr[1] };

                    list.add(newarr);
                }
            }
            Collections.sort(list, new Comparator<String[]>() {
                public int compare (String[] x1, String[] x2) {
                    return x1[1].compareTo(x2[1]);
                }
            });
        }

        return list;
    }

    /**
     * Gets the current user's list of messages.
     * 
     * @return
     */
    public List<Message> getMessage () {
        return myUserManager.getMessage();
    }

    /**
     * Sends a tweet to Twitter.
     * 
     * @param name
     * @param tweetText
     */
    public boolean sendTweet (String name, String tweetText) {
        return mySocialCenter.sendTweet(name, tweetText);
    }

    /**
     * Disconnects a user's connection to a Twitter account.
     * 
     * @param name
     */
    public boolean disconnectTwitter (String name) {
        return myUserManager.deleteTwitterAccessToken(name);
    }

    /**
     * Makes a post to Facebook.
     * 
     * @param name
     * @param post
     * @return
     */
    public boolean sendPost (String name, String message) {
        return mySocialCenter.sendPost(name, message);
    }

    /**
     * Disconnects a user's connection to a Facebook account.
     * 
     * @param name
     * @return
     */
    public boolean disconnectFacebook (String name) {
        return myUserManager.deleteFacebookAccessToken(name);
    }

    public boolean sendMessage (String sender, String recipient, String messageContent, Date date) {
        return mySocialCenter.sendMessage(sender, recipient, messageContent, date);
    }
}
