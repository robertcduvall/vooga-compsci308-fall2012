package arcade.usermanager;

import java.io.File;
import java.util.List;
import java.util.ResourceBundle;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import util.xml.XmlBuilder;
import util.xml.XmlParser;
import util.xml.XmlUtilities;
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
  //  private User myCurrentUser;
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

//    public static SocialCenter getInstance () {
//        if (mySocialCenter == null) {
//            mySocialCenter = new SocialCenter();
//        }
//
//        return mySocialCenter;
//    }

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
    public void logOnUser (String userName, String password)
            throws Exception {
        String status = myUserManager.validateUser(userName, password);

        if (!status.equals(successString)) throw new Exception(status);
        // set current user
         myUserManager.setCurrentUser(myUserManager.getUser(userName));

       
    }

    /*
     * return log on status
     */
    public void registerUser (String userName, String password,
            String picture) throws Exception {
        // check validity
        if (myUserManager.validateUser(userName, "").equals(
                "This user exists, however password is incorrect"))
            throw new Exception("This user already exists");

        // valid registration
        User newUser = myUserManager.addNewUser(userName, password, picture);
        myUserManager.setCurrentUser(newUser);

       
    }

    /*
     * return operation status
     */
    public void deleteUser (String userName, String password)
            throws Exception {
        // check validity
        String status = myUserManager.validateUser(userName, password);
        if (!status.equals(successString)) throw new Exception(status);

        // valid file
        FileOperation.deleteFile(myUserBasicFilePath + userName + ".xml");
        FileOperation.deleteFile(myUserMessageFilePath + userName + ".xml");
        FileOperation.deleteFile(myUserGameFilePath + userName + ".xml");
        myUserManager.deleteUser(userName);
        
    }

    /*
     * return operation status
     */
    public boolean sendMessage (String sender, String receiver, String content) {
        String filePath = myUserMessageFilePath + receiver + ".xml";
                
        Document doc = XmlUtilities.makeDocument(filePath);
        Element root = doc.getDocumentElement();
        Element message = XmlUtilities.appendElement(doc, root, "message", "");
        XmlUtilities.appendElement(doc, message, "receiver", receiver);
        XmlUtilities.appendElement(doc, message, "content", content);
        XmlUtilities.write(doc, filePath);
        myUserManager.getUser(receiver).updateMyMessage(sender, content);

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
