package arcade.usermanager;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;


/**
 * Represents a User of the arcade.
 * 
 * @author Howard
 *         modified by Difan Zhao, Jei Min Yoo
 * 
 * 
 */
public class User {
    private String myName;
    private Image myPicture;
    private String myPassword;
    private int myCredits;
    private List<Message> myMessages;
    private List<GameData> myGameData;
  

    /**
     * Constructs a new User
     * 
     * @param name
     * @param picture
     */
    public User (String name, String password, Image picture, int credits,
            List<Message> messages, List<GameData> gameData) {
        myName = name;
        myPassword = password;
        myPicture = picture;
        myCredits = credits;
        myMessages = messages;
        myGameData = gameData;
        
    }

    protected String getPassword () {
        return myPassword;
    }

    protected String getName () {
        return myName;
    }

    public Image getPicture () {
        return myPicture;
    }

    protected void setName (String newName) {
        myName = newName;
    }

    protected GameData getGameData (String gameName) {
        for (GameData gd : myGameData) {
            if (gd.getGameInfo("myGameName").equals(gameName)) return gd;

        }
        return null;
    }

    

    protected List<String> getMyMessage () {
        List<String> myMessage = new ArrayList<String>();
        for (Message m : myMessages) {
            myMessage.add(m.getMessage());

        }

        return myMessage;
    }

    protected void updateMyMessage (String sender, String content) {
        myMessages.add(new Message(sender, content));

    }

    protected void updateMyGame () {

    }
}
