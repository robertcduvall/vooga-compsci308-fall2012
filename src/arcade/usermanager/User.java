package arcade.usermanager;

import java.util.ArrayList;
import java.util.List;


/**
 * Represents a User of the arcade.
 * 
 * @author Howard
 * 
 */
public class User {
    private String myName;
    private String myPicture;
    private int myCredits;
    private List<Message> myMessages;
    private List<GameData> myGameData;

    /**
     * Constructs a new User
     * 
     * @param name
     * @param picture
     */
    public User (String name, String picture) {
        myName = name;
        myPicture = picture;
        myCredits = 0;
        myMessages = new ArrayList<Message>();
        myGameData = new ArrayList<GameData>();
    }

    public User (String name, String picture, int credits, List<Message> messages, List<GameData> gameData) {
        myName = name;
        myPicture = picture;
        myCredits = credits;
        myMessages = messages;
        myGameData = gameData;
    }

    public String getName () {
        return myName;
    }

    public String getPicture () {
        return myPicture;
    }
}
