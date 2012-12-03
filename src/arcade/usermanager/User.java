package arcade.usermanager;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;


/**
 * Represents a User of the arcade.
 * 
 * @author Howard
 *         modified by Difan Zhao, Jei Min Yoo, Robert Bruce
 * 
 * 
 */
public class User {
    private String myName;
    private String myPicture;
    private String myPassword;
    private int myCredits;
    private List<Message> myMessages;
    private List<GameData> myGameData;
    private String myFirstName;
    private String myLastName;
    private boolean myAdminStatus;
    private UserXMLWriter myXmlWriter;

    /**
     * Constructs a new User
     * 
     * @param name
     * @param picture
     */
    public User (String name, String password, String picture, int credits, List<Message> messages,
                 List<GameData> gameData, String firstName, String lastName) {
        myName = name;
        myPassword = password;
        myPicture = picture;
        myCredits = credits;
        myMessages = messages;
        myGameData = gameData;
        myFirstName = firstName;
        myLastName = lastName;
        setMyAdminStatus(false);
        myXmlWriter=new UserXMLWriter();

    }

    protected String getPassword () {
        return myPassword;
    }

    protected String getName () {
        return myName;
    }

    protected String getFirstName () {
        return myFirstName;
    }

    protected String getLastName () {
        return myLastName;
    }

    public String getPicture () {
        return myPicture;
    }

    public void setPicture (String picture) {
        myPicture = picture;
        myXmlWriter.updateUserInfo(myName,"picture",picture);
    }

    
    public GameData getGameData (String gameName) {
        for (GameData gd : myGameData) {
            if (gd.getGameInfo("name").equals(gameName)) return gd;

        }
        return null;
    }

    public List<Message> getMyMessage () {
        return myMessages;
    }

    protected void updateMyMessage (String sender, String content) {
        myMessages.add(new Message(sender, content));

    }

    protected void updateMyGame () {

    }

    public String getFullName () {
        // TODO Auto-generated method stub
        return null;
    }

    public boolean isMyAdminStatus () {
        return myAdminStatus;
    }

    public void setMyAdminStatus (boolean myAdminStatus) {
        this.myAdminStatus = myAdminStatus;
    }
}
