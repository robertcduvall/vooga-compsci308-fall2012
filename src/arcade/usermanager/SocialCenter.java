package arcade.usermanager;

import java.io.IOException;
import java.util.Map;
import java.util.ResourceBundle;
import twitter4j.auth.AccessToken;
import util.facebook.FacebookTools;
import util.twitter.TwitterTools;
import arcade.usermanager.exception.UserNotExistException;
import arcade.usermanager.exception.ValidationException;
import arcade.utility.FileOperation;


/**
 * social center accommodate all the requests concerning user
 * including basic operations such as log on/register user
 * Send/receive messages between users
 * user game history
 * 
 * @author Difan Zhao
 *         modified by Howard Chung
 * 
 */
public class SocialCenter {

    private static SocialCenter mySocialCenter;
    // private Map<String, User> myAllUser;
    private String myUserBasicFilePath;
    private String myUserMessageFilePath;
    private String myUserGameFilePath;
    private UserXMLReader myXMLReader;
    private UserXMLWriter myXMLWriter;
    // private final String successString = "Successful";
    // private final String passwordDoNotMatch = "password do not mat";
    // private final String userNameExist = "Successful";
    private static ResourceBundle resource;
    private UserManager myUserManager;
    private TwitterTools myTwitterTools;
    private FacebookTools myFacebookTools;

    /**
     * constructor
     */

    public SocialCenter () {
        myXMLReader = new UserXMLReader();
        myXMLWriter = new UserXMLWriter();
        myUserManager = UserManager.getInstance();
        resource = ResourceBundle.getBundle("arcade.usermanager.filePath");
        myUserBasicFilePath = resource.getString("BasicFilePath");
        myUserMessageFilePath = resource.getString("MessageFilePath");
        myUserGameFilePath = resource.getString("GameFilePath");
        myTwitterTools = new TwitterTools();
        myFacebookTools = new FacebookTools();

    }

    /**
     * log on user
     * 
     * @param userName
     * @param password
     * @return operaton status
     * @throws ValidationException
     */

    public boolean logOnUser (String userName, String password) throws ValidationException {
        myUserManager.validateUser(userName, password);

        // set current user

        myUserManager.setCurrentUser(userName);

        return true;
    }

    /**
     * register new user, new user will automatically be log on after
     * registration
     * 
     * @param userName
     * @param password
     * @param firstName
     * @param lastName
     * @return
     * @throws ValidationException
     * @throws IOException
     */

    public boolean registerUser (String userName, String password, String firstName, String lastName)
                                                                                                     throws ValidationException,
                                                                                                     IOException {
        // check validity

        try {
            myUserManager.validateUser(userName, "");
        }

        catch (UserNotExistException e) {

            User newUser =
                    myUserManager
                            .addNewUser(userName, password, "default.jpg", firstName, lastName);
            myUserManager.setCurrentUser(userName);

            return true;
        }

        return false;
    }

    /**
     * delete user profile when user decide to cancel the account
     * 
     * @param userName
     * @param password
     * @return operation status
     * @throws ValidationException
     */

    public boolean deleteUser (String userName, String password) throws ValidationException {
        // check validity
        myUserManager.validateUser(userName, password);

        // valid file
        FileOperation.deleteFile(myUserBasicFilePath + userName + ".xml");
        FileOperation.deleteFile(myUserMessageFilePath + userName + ".xml");
        FileOperation.deleteFile(myUserGameFilePath + userName + ".xml");
        myUserManager.deleteUser(userName);
        return true;
    }

    /**
     * send message between users
     * 
     * @param receiver
     * @param content
     * @return operation status
     */

    public boolean sendMessage (String receiver, String content) {
        String sender = myUserManager.getCurrentUserName();
        myXMLWriter.appendMessage(sender, receiver, content);
        // myUserManager.getUser(receiver).updateMyMessage(sender, content);
        myUserManager.updateMessage(sender, receiver, content);

        return true;
    }

    public boolean sendMessage (String sender, String receiver, String content) {
        myXMLWriter.appendMessage(sender, receiver, content);
        // myUserManager.getUser(receiver).updateMyMessage(sender, content);
        myUserManager.updateMessage(sender, receiver, content);

        return true;
    }

    /**
     * Sends a tweet to Twitter.
     * 
     * @param name
     * @param tweetText
     */
    public boolean sendTweet (String name, String tweetText) {
        try {
            Map<String, AccessToken> myTokens = myUserManager.getTwitterTokens();
            AccessToken at;
            if (!myTokens.keySet().contains(name)) {
                at = myTwitterTools.requestAccessToken();
                if (at == null) { return false; }
                myUserManager.addTwitterToken(name, at);
            }
            else {
                at = myTokens.get(name);
            }
            myTwitterTools.updateStatus(tweetText, at);
            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Sends a post to Facebook.
     * 
     * @param name
     * @param post
     * @return
     */
    public boolean sendPost (String name, String message) {
        try {
            Map<String, com.restfb.FacebookClient.AccessToken> myTokens = myUserManager.getFacebookTokens();
            com.restfb.FacebookClient.AccessToken at;
            if (!myTokens.keySet().contains(name)) {
                at = myFacebookTools.requestAccessToken();
                if (at == null) { return false; }
                myUserManager.addFacebookToken(name, at);
            }
            else {
                at = myTokens.get(name);
            }
            myFacebookTools.publishPost(message, at);
            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
