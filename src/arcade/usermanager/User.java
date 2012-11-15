package arcade.usermanager;

import java.util.ArrayList;
import java.util.List;


/**
 * Represents a User of the arcade.
 * 
 * @author Howard
        modified by Difan Zhao, Jei Min Yoo

 * 
 */
public class User {
    private String myName;
    private String myPicture;
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
    public User (String name, String password, String picture, int credits, List<Message> messages,
                 List<GameData> gameData) {
        myName = name;
        myPassword = password;
        myPicture = picture;
        myCredits = credits;
        myMessages = messages;
        myGameData = gameData;
    }
    protected String getPassword(){
        return myPicture;
    }
    protected String getName() {
        return myName;
    }

    public String getPicture() {
        return myPicture;
    }
    
    public List<GameData> getGameData() {
        return myGameData;
    }
}
