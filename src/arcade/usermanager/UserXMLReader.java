package arcade.usermanager;

import java.awt.Image;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import util.xml.XmlParser;


/**
 * Reads in user data from an XML file and creates User objects
 * 
 * @author Howard
 *         minor modification by difan
 */
public class UserXMLReader {
    private static ResourceBundle ourResources;
    private String myUserBasicFilePath;
    private String myUserMessageFilePath;
    private String myUserGameFilePath;
    private XmlParser myXmlParser;

    /**
     * Constructs a UserXMLReader.
     */
    public UserXMLReader () {
        ourResources = ResourceBundle.getBundle("arcade.usermanager.filePath");
        myUserBasicFilePath = ourResources.getString("BasicFilePath");
        myUserMessageFilePath = ourResources.getString("MessageFilePath");
        myUserGameFilePath = ourResources.getString("GameFilePath");
    }

    /**
     * Creates a user object from XML data.
     * 
     * @param name name of the user to create object for
     * @return
     */
    public User getUser (String name) {
        name=name+".xml";
        myXmlParser = new XmlParser(new File(myUserBasicFilePath + name));
        Element el = myXmlParser.getDocumentElement();
        String username = myXmlParser.getTextContent(el, "name");
        String password = myXmlParser.getTextContent(el, "password");
        Image picture = myXmlParser.getImageContent(el, "picture");
        int credits = myXmlParser.getIntContent(el, "credits");
        // later, hash basic user info?

        List<Message> messageList = getMessageList(name);
        List<GameData> gameDataList = getGameDataList(name);

        return new User(username, password, picture, credits, messageList,
                gameDataList);

    }

    /**
     * Gets a list of GameData objects
     * 
     * @param name with extension of user file
     * @return
     */
    public List<GameData> getGameDataList (String name) {
        myXmlParser = new XmlParser(new File(myUserGameFilePath + name));
        Element el = myXmlParser.getDocumentElement();
        List<GameData> gameDataList = new ArrayList<GameData>();
        NodeList nl = el.getElementsByTagName("game");
        if (nl != null && nl.getLength() > 0) {
            for (int i = 0; i < nl.getLength(); i++) {
                // get an element
                Element ele = (Element) nl.item(i);
                String gameName = myXmlParser.getTextContent(ele, "name");
                String gameInfo = myXmlParser.getTextContent(ele, "gameinfo");
                String highScore = myXmlParser.getTextContent(ele, "highscore");
                String timesPlayed = myXmlParser.getTextContent(ele, "timesplayed");
                gameDataList.add(new GameData(gameName, gameInfo, highScore,
                        timesPlayed));
            }
        }
        return gameDataList;
    }

    /**
     * Gets a list of Message objects
     * 
     * @param name name with extension of user file
     * @return
     */
    public List<Message> getMessageList (String name) {
        myXmlParser = new XmlParser(new File(myUserMessageFilePath + name));
        Element el = myXmlParser.getDocumentElement();
        List<Message> messageList = new ArrayList<Message>();
        NodeList nl = el.getElementsByTagName("message");
        if (nl != null && nl.getLength() > 0) {
            for (int i = 0; i < nl.getLength(); i++) {
                // get an element
                Element ele = (Element) nl.item(i);
                String sender = myXmlParser.getTextContent(ele, "sender");
                String message = myXmlParser.getTextContent(ele, "content");
                messageList.add(new Message(sender, message));
            }
        }
        return messageList;
    }
}
