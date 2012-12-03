package arcade.usermanager;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import twitter4j.auth.AccessToken;
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
    private static String myTwitterFilePath;
    private static ResourceBundle resource;
    private UserXMLReader myXMLReader;
    private UserXMLWriter myXMLWriter;
    private User myCurrentUser;
    private Set<String> myAdminHashes;
    private Map<String, AccessToken> myTwitterTokens;

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
        myTwitterFilePath = resource.getString("TwitterFilePath");

        myXMLReader = new UserXMLReader();
        myXMLWriter = new UserXMLWriter();

        myAllUser = new HashMap<String, User>();

        readAdminFile();
        loadTwitterTokens();
        loadUserData();
    }

    private void loadUserData () {
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

    private void loadTwitterTokens () {
        myTwitterTokens = new HashMap<String, AccessToken>();
        File folder = new File(myTwitterFilePath);
        File[] listOfFiles = folder.listFiles();
        for (File fi : listOfFiles) {
            if (fi.isFile()) {
                FileInputStream f_in;
                try {
                    f_in = new FileInputStream(fi);
                    ObjectInputStream obj_in = new ObjectInputStream(f_in);
                    Object obj = obj_in.readObject();
                    myTwitterTokens.put(FileOperation.stripExtension(fi.getName()),
                                        (AccessToken) obj);
                }
                catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                catch (ClassNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        }
    }

    /**
     * Reads the admins.txt file.
     */
    private void readAdminFile () {
        myAdminHashes = new HashSet<String>();
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
     * file, then reloads it.
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
        readAdminFile();

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
        if (myAllUser.containsKey(userName)) { return myAllUser.get(userName); }
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

    /**
     * 
     * @return modifable user class
     */
    public User getCurrentUser () {
        return myCurrentUser;
    }
    /**
     * 
     * @return modifable user class
     */
    public String getCurrentUserName () {
        return myCurrentUser.getName();
    }

    protected void setCurrentUser (String userName) {
        myCurrentUser = getUser(userName);

    }
    
    /**
     * 
     * @param userName
     * @return un modifable user profile
     */

    public UserProfile getUserProfile (String userName) {
        User user = getUser(userName);
        String name = user.getName();
        String picture = user.getPicture();
        String firstName = user.getFirstName();
        String lastName = user.getLastName();
        return new UserProfile(name, picture, firstName, lastName);
    }
    
    /**
     * 
     * @return a list of unmodifable user profile
     */

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

    public GameData getGame (String userName, String gameName) {
        return getUser(userName).getGameData(gameName);

    }

    /**
     * 
     * @return current user's message
     */
    public List<Message> getMessage () {
        return myCurrentUser.getMyMessage();
    }

    public Map<String, AccessToken> getTwitterTokens () {
        return myTwitterTokens;
    }

    /**
     * Writes a serialized twitter token to file and reloads tokens.
     * 
     * @param name Arcade username of the user
     * @param at AccessToken to save
     */
    public void addTwitterToken (String name, AccessToken at) {
        FileOutputStream f_out;
        try {
            f_out = new FileOutputStream(myTwitterFilePath + name + ".at");
            ObjectOutputStream obj_out = new ObjectOutputStream(f_out);
            obj_out.writeObject(at);
        }
        catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        loadTwitterTokens();

    }

    /**
     * Deletes a user's access token.
     * 
     * @param name
     */
    public boolean deleteAccessToken (String name) {
        FileOperation.deleteFile(myTwitterFilePath + name + ".at");
        myTwitterTokens.remove(name);
        return true;
    }
}
