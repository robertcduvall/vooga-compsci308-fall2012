package arcade.usermanager;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

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
    private List<String> availableUserName;
    private List<User> myAllUser;
    private final String myUserFilePath="src/arcade/database/";
    private UserXMLReader myXMLReader;
    private UserXMLWriter myXMLWriter;
    

    /*
     * initiate user list
     */
    public void initiateUser () {
        myXMLReader=new UserXMLReader();
        myXMLWriter=new UserXMLWriter();
        
        availableUserName=new ArrayList<String>();
        myAllUser=new ArrayList<User>();
        
        File folder = new File(myUserFilePath);
        File[] listOfFiles = folder.listFiles();

        for (int i = 0; i < listOfFiles.length; i++) {
        if (listOfFiles[i].isFile()) {
        availableUserName.add(listOfFiles[i].getName());
      }
        
        for(String name:availableUserName){
            User newUser= myXMLReader.initiateUser(myUserFilePath+name+".xml");
            myAllUser.add(newUser);
        }
    }
        

    }
    
    private void addNewUser(String userName, String password, String picture){
        //write an xml file
        UserXMLWriter.makeUserXML(userName, password, picture);
        availableUserName.add(userName);
        User newUser= myXMLReader.initiateUser(myUserFilePath+userName+".xml");
        myAllUser.add(newUser);
        
    }
    
   
    

    /*
     * return log on status
     */
    public String logOnUser (String username, String password) {
        //set current user
    }

    /*
     * return log on status
     */
    public String registerUser (String username, String password, String picture) {
      //write file
        //initiate class
        //add the the available list
        //set current user

    }

    /*
     * return operation status
     */
    public String deleteUser (String username, String password) {
        //delete from file
        //delete from available list
        //set current user to null

    }

    /*
     * return current user
     */
    public User getCurrentUser () {

        return myCurrentUser;

    }

    /*
     * edit user info
     */
    public void editCurrentUser () {

    }

    /*
     * return operation status
     */
    public String sendMessage (String sender, String receiver, String content) {

    }

    /*
     * return operation status
     */
    public String receiveMessage (String sender, String receiver, String content) {

    }

    /*
     * return game history for certain game
     */
    public String readGameHistory (String gameName, String tagName) {

    }

    /*
     * return whether the operation is successful
     */
    public boolean writeGameHistory (String gameName, String tagName, String content) {

    }

}
