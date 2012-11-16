package arcade.usermanager;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import util.xml.XmlParser;
import util.xml.XmlWriter;
import arcade.utility.FileOperation;


/**
 * social center accommodate all the requests concerning user
 * including basic operations such as log on/register user
 * Send/receive messages between users
 * user game history
 * 
 * @author Difan Zhao
 *         modified by Howard Chung
 */
public class SocialCenter {
    private User myCurrentUser;
    private static SocialCenter mySocialCenter;
    private Map<String, User> myAllUser;
    private String myUserBasicFilePath;
    private String myUserMessageFilePath;
    private String myUserGameFilePath;
    private UserXMLReader myXMLReader;
    private UserXMLWriter myXMLWriter;
    private final String successString = "Successful";
    private static ResourceBundle resource;

    public static SocialCenter getInstance () {
        if (mySocialCenter == null) mySocialCenter = new SocialCenter();

        return mySocialCenter;
    }

    /*
     * initiate user list
     */
    public SocialCenter () {
        myXMLReader = new UserXMLReader();
        myXMLWriter = new UserXMLWriter();
        resource = ResourceBundle.getBundle("resources.filePath");
        myUserBasicFilePath = resource.getString("BasicFilePath");
        myUserMessageFilePath = resource.getString("MessageFilePath");
        myUserGameFilePath = resource.getString("GameFilePath");

        // availableUserName=new ArrayList<String>();
        myAllUser = new HashMap<String, User>();

        File folder = new File(myUserBasicFilePath);
        File[] listOfFiles = folder.listFiles();

        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                String name = listOfFiles[i].getName();

                User newUser = myXMLReader.getUser(name);
                myAllUser.put(name, newUser);

            }
        }

    }

    private User addNewUser (String userName, String password, String picture) {
        // write an xml file
        myXMLWriter.makeUserXML(userName, password, picture);
        // make new user class
        User newUser = myXMLReader.getUser(userName);
        myAllUser.put(userName, newUser);
        return newUser;

    }

    private String validateUser (String userName, String password) {
        if (!myAllUser.containsKey(userName)) return "Such user does not exist";
        if (!myAllUser.get(userName).getPassword().equals(password))
            return "Password is incorrect";
        return successString;

    }

    /*
     * 
     * return log on status
     */
    public String logOnUser (String userName, String password) {
        String status = validateUser(userName, password);
        if (!status.equals(successString)) return status;
        // set current user
        myCurrentUser = myAllUser.get(userName);

        return successString;
    }

    /*
     * return log on status
     */
    public String registerUser (String userName, String password, String picture) {
        // check validity
        if (myAllUser.containsKey(userName)) return "This user already exists";

        // valid registration
        addNewUser(userName, password, picture);
        myCurrentUser = myAllUser.get(userName);

        return successString;
    }

    /*
     * return operation status
     */
    public String deleteUser (String userName, String password) {
        // check validity
        String status = validateUser(userName, password);
        if (!status.equals(successString)) return status;

        // valid file
        FileOperation.deleteFile(myUserBasicFilePath + userName + ".xml");
        FileOperation.deleteFile(myUserMessageFilePath + userName + ".xml");
        FileOperation.deleteFile(myUserGameFilePath + userName + ".xml");
        myAllUser.remove(userName);
        return successString;
    }

    /*
     * return current user name
     */
    public String getUserName () {

        return myCurrentUser.getName();

    }

    /*
     * edit user name
     */
    public void editUserName (String newName) {
        myCurrentUser.setName(newName);

    }

    /*
     * return operation status
     */
    public String sendMessage (String sender, String receiver, String content) {
        String filePath = "myUserMessageFilePath" + receiver + ".xml";
        File f = new File(filePath);
        XmlParser parser = new XmlParser(f);
        Document doc = parser.getDocument();
        Element root = (Element) parser.getDocumentElement();
        Element message = XmlWriter.appendElement(doc, root, "Message", "");
        XmlWriter.appendElement(doc, message, "receiver", receiver);
        XmlWriter.appendElement(doc, message, "content", content);
        XmlWriter.writeXML(doc, filePath);
        myAllUser.get(receiver).updateMyMessage(sender, content);
        
        return successString;
    }

    /*
     * return operation status
     */
    public List<String> viewMessage (String sender, String receiver, String content) {
        return myCurrentUser.getMyMessage();

    }

    /*
     * return whether the operation is successful
     */

    public boolean writeGameScore (String gameName, int score) {
        myCurrentUser.getGameData(gameName).setMyHighScore(score);

        // write xml
        String filePath = "myUserGameFilePath" + myCurrentUser.getName() + ".xml";
        File f = new File(filePath);
        XmlParser parser = new XmlParser(f);
        Document doc = parser.getDocument();
        Element root = (Element) parser.getDocumentElement();
        NodeList children = root.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            Element child = (Element) children.item(i);
            if (parser.getTextContent(child, "name").equals(gameName)) {
                XmlWriter.modifyTag(child, "highscore", Integer.toString(score));
            }
        }

        XmlWriter.writeXML(doc, filePath);

        return true;

    }

    public boolean writeGameInfo (String gameName, String info) {
        myCurrentUser.getGameData(gameName).setMyGameInfo(info);

        String filePath = "myUserGameFilePath" + myCurrentUser.getName() + ".xml";
        File f = new File(filePath);
        XmlParser parser = new XmlParser(f);
        Document doc = parser.getDocument();
        Element root = (Element) parser.getDocumentElement();
        NodeList children = root.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            Element child = (Element) children.item(i);
            if (parser.getTextContent(child, "name").equals(gameName)) {
                XmlWriter.modifyTag(child, "gameinfo", info);
            }
        }

        XmlWriter.writeXML(doc, filePath);

        return true;

    }

    /*
     * return game history for certain game
     */

    public int readGameScore (String gameName) {
        return myCurrentUser.getGameData(gameName).getMyHighScore();

    }

    public String readGameInfo (String gameName) {
        return myCurrentUser.getGameData(gameName).getMyGameInfo();

    }

}
