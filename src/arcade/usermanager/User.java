package arcade.usermanager;

import java.util.Date;
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

    public String getName () {
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
    
    protected List<GameData> getAllGameData(){
        return myGameData;
    }
    
    

    public List<Message> getMyMessage () {
        return myMessages;
    }

    protected void updateMyMessage (String sender, String content) {
        myMessages.add(new Message(sender, content));

    }
    
    @SuppressWarnings("deprecation")
    protected void updateMyMessage (String sender, String content, String myDate) {
        myMessages.add(new Message(sender, content, new Date(myDate)));

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
