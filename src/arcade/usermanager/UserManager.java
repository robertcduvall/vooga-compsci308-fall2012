package arcade.usermanager;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;


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
                String name = listOfFile.getName();
                User newUser = myXMLReader.getUser(name);
                
                myAllUser.put(name, newUser);

            }
            
        }
        
       

    }

    protected User getUser (String userName) {
        if (myAllUser.containsKey(userName)) return myAllUser.get(userName);
        return null;

    }

    protected String validateUser (String userName, String password) {
        if (!myAllUser.containsKey(userName))
            return "Such user does not exist";
        if (myAllUser.get(userName).getPassword().equals(password))
            return successString;
        if (myAllUser.containsKey(userName))
            return "This user exists, however password is incorrect";
        return "";

    }

    protected User addNewUser (String userName, String password, String picture) {
        // write an xml file
        myXMLWriter.makeUserXML(userName, password, picture);
        // make new user class
        User newUser = myXMLReader.getUser(userName);
        myAllUser.put(userName, newUser);
        return newUser;

    }

    protected void deleteUser (String userName) {
        myAllUser.remove(userName);

    }

}
