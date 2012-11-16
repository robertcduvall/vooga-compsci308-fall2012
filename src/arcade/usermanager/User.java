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
    private SocialCenter mySocialCenter;

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
        mySocialCenter=SocialCenter.getInstance();
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
    
    protected void setName(String newName) {
        myName=newName;
    }
    
    protected GameData getGameData(String gameName) {
        for(GameData gd:myGameData){
          if( gd.getMyGameName().equals(gameName)){
              return gd;
              
          }
            
        }
        return null;
    }
    
    public void sendMessage(String receiver, String content){
        mySocialCenter.sendMessage(myName, receiver, content);
        
    }
    
    protected List<String> getMyMessage(){
        List<String> myMessage=new ArrayList<String>();
        for(Message m:myMessages){
            myMessage.add(m.getMessage());
            
        }
        
        return myMessage;
    }
    
    protected void updateMyMessage(String sender, String content){
        myMessages.add(new Message(sender,content));
        
    }
    
    protected void updateMyGame(){
        
    }
}
