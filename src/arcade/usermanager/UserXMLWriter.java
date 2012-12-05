package arcade.usermanager;

import java.io.IOException;
import java.util.ResourceBundle;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import util.xml.XmlUtilities;


/**
 * Writes user data to an XML file.
 * 
 * @author Howard, Difan
 */
public class UserXMLWriter {
    private String myUserBasicFilePath;
    private String myUserMessageFilePath;
    private String myUserGameFilePath;
    private ResourceBundle myResource;
    private String myUserImageFilePath;

    /**
     * Constructs a writer.
     */
    public UserXMLWriter () {
        myResource = ResourceBundle.getBundle("arcade.usermanager.filePath");
        myUserBasicFilePath = myResource.getString("BasicFilePath");
        myUserMessageFilePath = myResource.getString("MessageFilePath");
        myUserGameFilePath = myResource.getString("GameFilePath");
        myUserImageFilePath = myResource.getString("ImageFilePath");
    }

    /**
     * initiate a new xml for user
     * 
     * @param userName
     * @param password
     * @param picture
     * @author difan
     * @throws IOException
     */

    public void makeUserXML (String userName, String password, String picture, String firstName,
                             String lastName) throws IOException {

        makeBasicXml(userName, password, picture, firstName, lastName);
        makeMessageXml(userName);
        makeGameXml(userName);
    }

    private void makeBasicXml (String userName, String password, String picture, String firstName,
                               String lastName) throws IOException {
        String basicInfoFilePath = myUserBasicFilePath + userName + ".xml";

        Document doc = XmlUtilities.makeDocument();

        Element rootElement = doc.createElement("user");
        doc.appendChild(rootElement);

        XmlUtilities.appendElement(doc, rootElement, "name", userName);
        XmlUtilities.appendElement(doc, rootElement, "firstname", firstName);
        XmlUtilities.appendElement(doc, rootElement, "lastname", lastName);
        XmlUtilities.appendElement(doc, rootElement, "password", password);
        XmlUtilities.appendElement(doc, rootElement, "picture", myUserImageFilePath + picture);
        XmlUtilities.appendElement(doc, rootElement, "credits", "0");
        XmlUtilities.write(doc, basicInfoFilePath);

    }

    private void makeMessageXml (String userName) throws IOException {
        String messageFilePath = myUserMessageFilePath + userName + ".xml";

        Document doc = XmlUtilities.makeDocument();

        Element rootElement = doc.createElement("messagelist");
        doc.appendChild(rootElement);

        XmlUtilities.write(doc, messageFilePath);

    }

    private void makeGameXml (String userName) throws IOException {
        String gameFilePath = myUserGameFilePath + userName + ".xml";

        Document doc = XmlUtilities.makeDocument();

        Element rootElement = doc.createElement("gamelist");
        doc.appendChild(rootElement);

        XmlUtilities.write(doc, gameFilePath);

    }
    
    public void appendGame(String userName, String name, String score, String times, String info){
        String filePath = myUserGameFilePath + userName + ".xml";

        Document doc = XmlUtilities.makeDocument(filePath);
        Element root = doc.getDocumentElement();
        Element message = XmlUtilities.appendElement(doc, root, "game", "");
        XmlUtilities.appendElement(doc, message, "name", name);
        XmlUtilities.appendElement(doc, message, "highscore", score);
        XmlUtilities.appendElement(doc, message, "timesplayed", times);
        XmlUtilities.appendElement(doc, message, "gameinfo", info);
        XmlUtilities.write(doc, filePath);
        
    }

    protected void appendMessage (String sender, String receiver, String content) {
        String filePath = myUserMessageFilePath + receiver + ".xml";

        Document doc = XmlUtilities.makeDocument(filePath);
        Element root = doc.getDocumentElement();
        Element message = XmlUtilities.appendElement(doc, root, "message", "");
        XmlUtilities.appendElement(doc, message, "sender", sender);
        XmlUtilities.appendElement(doc, message, "content", content);
        XmlUtilities.write(doc, filePath);

    }

    protected void updateUserInfo (String userName, String tagName, String newContent) {
        String filePath = myUserBasicFilePath + userName + ".xml";
        Document doc = XmlUtilities.makeDocument(filePath);
        Element root = doc.getDocumentElement();
        XmlUtilities.replaceAllTagNames(root, tagName, newContent);
        XmlUtilities.write(doc, filePath);

    }

    protected void updateGameInfo (String userName, String gameName, String tagName, String content) {
        String filePath = myUserGameFilePath + userName + ".xml";

        Document doc = XmlUtilities.makeDocument(filePath);
        Element root = doc.getDocumentElement();
        NodeList gameList = root.getElementsByTagName("game");

        if (gameList != null) {
            for (int i = 0; i < gameList.getLength(); i++) {
                Element game = (Element) gameList.item(i);
                String name = XmlUtilities.getChildContent(game, "name");
                if (name.equals(gameName)) {
                    XmlUtilities.replaceAllTagNames(game, tagName, content);
                    XmlUtilities.write(doc, filePath);
                }
            }

        }

    }
}
