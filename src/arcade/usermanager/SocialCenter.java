package arcade.usermanager;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import arcade.utility.FileOperation;


/**
 * social center accommodate all the requests concerning user
 * including basic operations such as log on/register user
 * Send/receive messages between users
 * user game history
 * 
 * @author Difan Zhao
 * 
 */

public class SocialCenter {
    private User myCurrentUser;
    private static SocialCenter mySocialCenter;
    private Map<String,User> myAllUser;
    private final String myUserBasicFilePath="src/arcade/database/user";
    private final String myUserMessageFilePath="src/arcade/database/userMessage";
    private final String myUserGameFilePath="src/arcade/database/userGame";
    private UserXMLReader myXMLReader;
    private UserXMLWriter myXMLWriter;
    private final String successString="Successful";
    
    
    public static  SocialCenter getInstance()
    {
            if (mySocialCenter == null)
                    mySocialCenter = new SocialCenter();

            return mySocialCenter;
    }
    

    /*
     * initiate user list
     */
    public SocialCenter () {
        myXMLReader=new UserXMLReader();
        myXMLWriter=new UserXMLWriter();
        
       // availableUserName=new ArrayList<String>();
        myAllUser=new HashMap<String,User>();
        
        File folder = new File(myUserBasicFilePath);
        File[] listOfFiles = folder.listFiles();

        for (int i = 0; i < listOfFiles.length; i++) { 
        if (listOfFiles[i].isFile()) {
            String name=listOfFiles[i].getName();
            User newUser= myXMLReader.initiateUser(name);//user reader to do
            myAllUser.put(name,newUser);
        
    }
        }
        

    }
    
    private  User addNewUser(String userName, String password, String picture){
        //write an xml file
        UserXMLWriter.makeUserXML(userName, password, picture);
        //make new user class
        User newUser= myXMLReader.initiateUser(userName);
        myAllUser.put(userName,newUser);
        return newUser;
       
        
       
    }
    
    private String validateUser(String userName, String password){
        if(!myAllUser.containsKey(userName)) return "Such user does not exist";
        if(!myAllUser.get(userName).getPassword().equals(password)) return "Password is incorrect";
        return successString;
        
    }
    
   
    

    /*
     * 
     * return log on status
     */
    public String logOnUser (String userName, String password) {
        String status=validateUser(userName,password);
        if(!status.equals(successString)) return status;
        //set current user
        myCurrentUser=myAllUser.get(userName);
        
       
        return successString;
    }

    /*
     * return log on status
     */
    public String registerUser (String userName, String password, String picture) {
        //check validity
        if(myAllUser.containsKey(userName)) return "This user already exists";
        
        
      //valid registration
        addNewUser(userName,password,picture);
        myCurrentUser=myAllUser.get(userName);
       
        return successString;
    }

    /*
     * return operation status
     */
    public String deleteUser (String userName, String password) {
      //check validity
        String status=validateUser(userName,password);
        if(!status.equals(successString)) return status;
        
        
        //valid file
        FileOperation.deleteFile(myUserBasicFilePath+userName+".xml");
        FileOperation.deleteFile(myUserMessageFilePath+userName+".xml");
        FileOperation.deleteFile(myUserGameFilePath+userName+".xml");
        myAllUser.remove(userName);
        return successString;
    }

    /*
     * return current user
     */
    public User getCurrentUser() {

        return myCurrentUser;

    }

    /*
     * edit user info
     */
    public void editCurrentUser() {

    }

    /*
     * return operation status
     */
    public String sendMessage(String sender, String receiver, String content) {
        return null;
    }

    /*
     * return operation status
     */
    public String receiveMessage(String sender, String receiver, String content) {
        return null;

    }

    /*
     * return game history for certain game
     */
    public String readGameHistory (String gameName, String tagName) {
        return null;
    }

    /*
     * return whether the operation is successful
     */
    public boolean writeGameHistory (String gameName, String tagName, String content) {
        return false;
    }

}
