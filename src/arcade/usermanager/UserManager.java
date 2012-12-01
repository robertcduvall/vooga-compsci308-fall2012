package arcade.usermanager;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import arcade.utility.FileOperation;


/**
 * manage a list of user
 * 
 * @author difan
 * 
 */

public class UserManager {
    private Map<String, User> myAllUser;
    private static UserManager myUserManager;
    private static String myUserBasicFilePath;
    private static String myUserMessageFilePath;
    private static String myUserGameFilePath;
    private static ResourceBundle resource;
    private UserXMLReader myXMLReader;
    private UserXMLWriter myXMLWriter;
    private final String successString = "Successful";
    private User myCurrentUser;

    public static UserManager getInstance () {
        if (myUserManager == null) {
            myUserManager = new UserManager();
        }

        return myUserManager;
    }

    private UserManager () {

        resource = ResourceBundle.getBundle("arcade.usermanager.filePath");
        myUserBasicFilePath = resource.getString("BasicFilePath");
        myUserMessageFilePath = resource.getString("MessageFilePath");
        myUserGameFilePath = resource.getString("GameFilePath");

        myXMLReader = new UserXMLReader();
        myXMLWriter = new UserXMLWriter();

        myAllUser = new HashMap<String, User>();

        File folder = new File(myUserBasicFilePath);
        File[] listOfFiles = folder.listFiles();

        for (File listOfFile : listOfFiles) {
            if (listOfFile.isFile()) {
                String name = FileOperation.stripExtension(listOfFile.getName());
                User newUser = myXMLReader.getUser(name);

                myAllUser.put(name, newUser);

            }

        }

    }

    protected User getUser (String userName) {
        if (myAllUser.containsKey(userName)) return myAllUser.get(userName);
        return null;

    }

    protected boolean validateUser (String userName, String password) {
        if (!myAllUser.containsKey(userName)) throw new UserNotExistException();
        if (myAllUser.get(userName).getPassword().equals(password)) return true;
        if (myAllUser.containsKey(userName)) throw new PasswordNotMatchException();
        return false;

    }

    protected User addNewUser (String userName, String password, String picture, String firstName,
                               String lastName) throws IOException {
        // write an xml file
        myXMLWriter.makeUserXML(userName, password, picture, firstName, lastName);
        // make new user class
        User newUser = myXMLReader.getUser(userName);
        myAllUser.put(userName, newUser);
        return newUser;

    }

    protected void deleteUser (String userName) {
        myAllUser.remove(userName);

    }

    public User getCurrentUser () {
        return myCurrentUser;
    }

    protected void setCurrentUser (User newUser) {
        myCurrentUser = newUser;

    }
    
    public UserProfile getUserProfile(String userName){
        User user=getUser(userName);
        String name=user.getName();
        String picture=user.getPicture();
        String firstName=user.getFirstName();
        String lastName=user.getLastName();
        return new UserProfile(name,picture,firstName,lastName);
    }
    
    public List<UserProfile> getAllUserProfile(){
        List<UserProfile> userProfileList=new ArrayList<UserProfile>();
        for(String name:myAllUser.keySet()){
            userProfileList.add(getUserProfile(name));
        }
        
        return userProfileList;
        
    }

}
