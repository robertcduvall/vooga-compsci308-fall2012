package arcade.usermanager;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import util.encrypt.Encrypter;
import arcade.usermanager.exception.PasswordNotMatchException;
import arcade.usermanager.exception.UserNotExistException;
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
    private static String myDatabaseFilePath;
    private static ResourceBundle resource;
    private UserXMLReader myXMLReader;
    private UserXMLWriter myXMLWriter;
    private final String successString = "Successful";
    private User myCurrentUser;
    private Set<String> myAdminHashes;

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
        myDatabaseFilePath = resource.getString("DatabaseFilePath");

        myXMLReader = new UserXMLReader();
        myXMLWriter = new UserXMLWriter();

        myAllUser = new HashMap<String, User>();

        myAdminHashes = new HashSet<String>();
        readAdminFile();

        File folder = new File(myUserBasicFilePath);
        File[] listOfFiles = folder.listFiles();

        for (File listOfFile : listOfFiles) {
            if (listOfFile.isFile()) {
                String name = FileOperation.stripExtension(listOfFile.getName());
                User newUser = myXMLReader.getUser(name);
                newUser.setMyAdminStatus(checkAdminStatus(name));
                myAllUser.put(name, newUser);

            }

        }
    }

    private void readAdminFile () {
        try {
            BufferedReader br =
                    new BufferedReader(new FileReader(myDatabaseFilePath + "admins.txt"));
            String line;
            while ((line = br.readLine()) != null) {
                myAdminHashes.add(line);
            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Checks adminstrator status given a username
     * 
     * @param name
     * @return
     */
    private boolean checkAdminStatus (String name) {
        return myAdminHashes.contains(Encrypter.hashCode(name));
    }

    /**
     * Changes a given user's administrator status and writes the administrator
     * file.
     * 
     * @param name
     * @param adminStatus
     */
    public void changeAdminStatus (String name, boolean adminStatus) {
        myAllUser.get(name).setMyAdminStatus(adminStatus);
        if (adminStatus == true) {
            myAdminHashes.add(Encrypter.hashCode(name));
        }
        else {
            myAdminHashes.remove(Encrypter.hashCode(name));
        }
        writeAdminFile(name);

    }

    /**
     * Writes the file of administrators.
     * 
     * @param name
     */
    private void writeAdminFile (String name) {
        try {
            BufferedWriter bw =
                    new BufferedWriter(new FileWriter(myDatabaseFilePath + "admins.txt"));
            for (String hash : myAdminHashes) {
                bw.write(hash);
                bw.newLine();
                bw.flush();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
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
    public String getCurrentUserName(){
        return myCurrentUser.getName();
    }

    protected void setCurrentUser (String userName) {
        myCurrentUser = getUser(userName);

    }

    public UserProfile getUserProfile (String userName) {
        User user = getUser(userName);
        String name = user.getName();
        String picture = user.getPicture();
        String firstName = user.getFirstName();
        String lastName = user.getLastName();
        return new UserProfile(name, picture, firstName, lastName);
    }

    public List<UserProfile> getAllUserProfile () {
        List<UserProfile> userProfileList = new ArrayList<UserProfile>();
        for (String name : myAllUser.keySet()) {
            userProfileList.add(getUserProfile(name));
        }

        return userProfileList;

    }

    protected void updateMessage (String sender, String receiver, String content) {
        getUser(receiver).updateMyMessage(sender, content);
    }
    
    public GameData getGame(String userName, String gameName){
     return   getUser(userName).getGameData(gameName);
        
    }
    
    public List<Message> getMessage(){
        return myCurrentUser.getMyMessage();
    }

}
