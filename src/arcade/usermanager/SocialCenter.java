package arcade.usermanager;

import java.io.File;
import java.util.List;
import java.util.ResourceBundle;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import util.xml.XmlBuilder;
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
 *         TODO:
 *         Allow user to change profile picture
 */
public class SocialCenter {
    private User myCurrentUser;
    private static SocialCenter mySocialCenter;
    // private Map<String, User> myAllUser;
    private String myUserBasicFilePath;
    private String myUserMessageFilePath;
    private String myUserGameFilePath;
    private UserXMLReader myXMLReader;
    private UserXMLWriter myXMLWriter;
    private final String successString = "Successful";
    private static ResourceBundle resource;
    private UserManager myUserManager;

    public static SocialCenter getInstance () {
        if (mySocialCenter == null) {
            mySocialCenter = new SocialCenter();
        }

        return mySocialCenter;
    }

    /*
     * initiate user list
     */
    public SocialCenter () {
        myXMLReader = new UserXMLReader();
        myXMLWriter = new UserXMLWriter();
        myUserManager = UserManager.getInstance();
        resource = ResourceBundle.getBundle("arcade.usermanager.filePath");
        myUserBasicFilePath = resource.getString("BasicFilePath");
        myUserMessageFilePath = resource.getString("MessageFilePath");
        myUserGameFilePath = resource.getString("GameFilePath");

    }

    /*
     * 
     * return log on status
     */
    public boolean logOnUser (String userName, String password)
            throws Exception {
        String status = myUserManager.validateUser(userName, password);

        if (!status.equals(successString)) throw new Exception(status);
        // set current user
        myCurrentUser = myUserManager.getUser(userName);

        return true;
    }

    /*
     * return log on status
     */
    public boolean registerUser (String userName, String password) throws Exception {
        // check validity
        if (myUserManager.validateUser(userName, "").equals(
                "This user exists, however password is incorrect"))
            throw new Exception("This user already exists");

        // valid registration
        myCurrentUser = myUserManager.addNewUser(userName, password, "default.jpg");

        return true;
    }

    /*
     * return operation status
     */
    public boolean deleteUser (String userName, String password)
            throws Exception {
        // check validity
        String status = myUserManager.validateUser(userName, password);
        if (!status.equals(successString)) throw new Exception(status);

        // valid file
        FileOperation.deleteFile(myUserBasicFilePath + userName + ".xml");
        FileOperation.deleteFile(myUserMessageFilePath + userName + ".xml");
        FileOperation.deleteFile(myUserGameFilePath + userName + ".xml");
        myUserManager.deleteUser(userName);
        return true;
    }

    /*
     * return operation status
     */
    public boolean sendMessage (String sender, String receiver, String content) {
        String filePath = myUserMessageFilePath + receiver + ".xml";
        File f = new File(filePath);
        XmlParser parser = new XmlParser(f);
        Document doc = parser.getDocument();
        Element root = parser.getDocumentElement();
        Element message = XmlBuilder.appendElement(doc, root, "message", "");
        XmlBuilder.appendElement(doc, message, "receiver", receiver);
        XmlBuilder.appendElement(doc, message, "content", content);
        XmlWriter.writeXML(doc, filePath);
        myUserManager.getUser(receiver).updateMyMessage(sender, content);

        return true;
    }

    /*
     * return operation status
     */
    public List<String> viewMessage (String sender, String receiver,
            String content) {
        return myCurrentUser.getMyMessage();

    }
    
    public GameData getGame(String gameName){
        return myCurrentUser.getGameData(gameName);
    }

    //
    // /*
    // * return whether the operation is successful
    // */
    //
    // public boolean writeGameScore (String gameName, int score) {
    // myCurrentUser.getGameData(gameName).setMyHighScore(score);
    //
    // // write xml
    // String filePath = "myUserGameFilePath" + myCurrentUser.getName() +
    // ".xml";
    // File f = new File(filePath);
    // XmlParser parser = new XmlParser(f);
    // Document doc = parser.getDocument();
    // Element root = (Element) parser.getDocumentElement();
    // NodeList children = root.getChildNodes();
    // for (int i = 0; i < children.getLength(); i++) {
    // Element child = (Element) children.item(i);
    // if (parser.getTextContent(child, "name").equals(gameName)) {
    // XmlBuilder.modifyTag(child, "highscore", Integer.toString(score));
    // }
    // }
    //
    // XmlWriter.writeXML(doc, filePath);
    //
    // return true;
    //
    // }
    //
    // public boolean writeGameInfo (String gameName, String info) {
    // myCurrentUser.getGameData(gameName).setMyGameInfo(info);
    //
    // String filePath = "myUserGameFilePath" + myCurrentUser.getName() +
    // ".xml";
    // File f = new File(filePath);
    // XmlParser parser = new XmlParser(f);
    // Document doc = parser.getDocument();
    // Element root = (Element) parser.getDocumentElement();
    // NodeList children = root.getChildNodes();
    // for (int i = 0; i < children.getLength(); i++) {
    // Element child = (Element) children.item(i);
    // if (parser.getTextContent(child, "name").equals(gameName)) {
    // XmlBuilder.modifyTag(child, "gameinfo", info);
    // }
    // }
    //
    // XmlWriter.writeXML(doc, filePath);
    //
    // return true;
    //
    // }
    //
    // /*
    // * return game history for certain game
    // */
    //
    // public int readGameScore (String gameName) {
    // return myCurrentUser.getGameData(gameName).getMyHighScore();
    //
    // }
    //
    // public String readGameInfo (String gameName) {
    // return myCurrentUser.getGameData(gameName).getMyGameInfo();
    //
    // }
    //
    //

}
