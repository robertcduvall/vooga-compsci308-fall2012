package arcade.usermanager;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.ResourceBundle;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import util.xml.XmlBuilder;
import util.xml.XmlParser;
import util.xml.XmlUtilities;
import util.xml.XmlWriter;
import arcade.usermanager.exception.UserNotExistException;
import arcade.usermanager.exception.ValidationException;
import arcade.utility.FileOperation;


/**
 * social center accommodate all the requests concerning user
 * including basic operations such as log on/register user
 * Send/receive messages between users
 * user game history
 * 
 * @author Difan Zhao
 *         modified by Howard Chung
 *         
 */
public class SocialCenter {

    private static SocialCenter mySocialCenter;
    // private Map<String, User> myAllUser;
    private String myUserBasicFilePath;
    private String myUserMessageFilePath;
    private String myUserGameFilePath;
    private UserXMLReader myXMLReader;
    private UserXMLWriter myXMLWriter;
    // private final String successString = "Successful";
    // private final String passwordDoNotMatch = "password do not mat";
    // private final String userNameExist = "Successful";
    private static ResourceBundle resource;
    private UserManager myUserManager;

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
    public boolean logOnUser (String userName, String password) throws ValidationException {
        myUserManager.validateUser(userName, password);

        // set current user
        
        myUserManager.setCurrentUser(userName);

        return true;
    }

    /*
     * return log on status
     */
    public boolean registerUser (String userName, String password, String firstName, String lastName)
                                                                                                     throws ValidationException, IOException {
        // check validity

        try {
            myUserManager.validateUser(userName, "");
        }

        catch (UserNotExistException e) {

            User newUser =
                    myUserManager
                            .addNewUser(userName, password, "default.jpg", firstName, lastName);
            myUserManager.setCurrentUser(userName);

            return true;
        }
        
        return false;
    }

    /*
     * return operation status
     */
    public boolean deleteUser (String userName, String password) throws ValidationException {
        // check validity
        myUserManager.validateUser(userName, password);

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
    public boolean sendMessage (String receiver, String content) {
        String sender=myUserManager.getCurrentUserName();
        String filePath = myUserMessageFilePath + receiver + ".xml";
        File f = new File(filePath);

        Document doc = XmlUtilities.makeDocument(filePath);
        Element root = doc.getDocumentElement();
        Element message = XmlUtilities.appendElement(doc, root, "message", "");
        XmlUtilities.appendElement(doc, message, "sender", sender);
        XmlUtilities.appendElement(doc, message, "content", content);
        XmlUtilities.write(doc, filePath);
       // myUserManager.getUser(receiver).updateMyMessage(sender, content);
        myUserManager.updateMessage(sender, receiver, content);

        return true;
    }
    
    public boolean sendMessage (String sender, String receiver, String content) {
        String filePath = myUserMessageFilePath + receiver + ".xml";
        File f = new File(filePath);

        Document doc = XmlUtilities.makeDocument(filePath);
        Element root = doc.getDocumentElement();
        Element message = XmlUtilities.appendElement(doc, root, "message", "");
        XmlUtilities.appendElement(doc, message, "sender", sender);
        XmlUtilities.appendElement(doc, message, "content", content);
        XmlUtilities.write(doc, filePath);
       // myUserManager.getUser(receiver).updateMyMessage(sender, content);
        myUserManager.updateMessage(sender, receiver, content);

        return true;
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
