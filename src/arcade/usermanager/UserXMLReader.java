package arcade.usermanager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import util.xml.XmlUtilities;


/**
 * Reads in user data from an XML file and creates User objects
 * 
 * @author Howard
 *         minor modification by difan
 */
public class UserXMLReader {
    private static ResourceBundle ourResources;
    private String myXMLExtension = ".xml";
    private String myNameTag = "name";
    private String myUserMessageFilePath;
    private String myUserGameFilePath;
    private String myUserBasicFilePath;

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
        String extendedName = name + myXMLExtension;
        Document doc = XmlUtilities.makeDocument(myUserBasicFilePath + extendedName);
        Element el = doc.getDocumentElement();
        String username = XmlUtilities.getChildContent(el, myNameTag);
        String password = XmlUtilities.getChildContent(el, "password");
        String picture = XmlUtilities.getChildContent(el, "picture");
        int credits = XmlUtilities.getChildContentAsInt(el, "credits");
        String firstName = XmlUtilities.getChildContent(el, "firstname");
        String lastName = XmlUtilities.getChildContent(el, "lastname");

        List<Message> messageList = getMessageList(extendedName);
        List<GameData> gameDataList = getGameDataList(extendedName);
        return new User(username, password, picture, credits, messageList, gameDataList, firstName,
                        lastName);

    }

    /**
     * Gets a list of GameData objects
     * 
     * @param name with extension of user file
     * @return
     */
    public List<GameData> getGameDataList (String name) {
        Document doc = XmlUtilities.makeDocument(myUserGameFilePath + name);
        Element el = doc.getDocumentElement();
        List<GameData> gameDataList = new ArrayList<GameData>();
        NodeList nl = el.getElementsByTagName("game");
        if (nl != null && nl.getLength() > 0) {
            for (int i = 0; i < nl.getLength(); i++) {
                // get an element
                Element ele = (Element) nl.item(i);
                String gameName = XmlUtilities.getChildContent(ele, myNameTag);
                String gameInfo = XmlUtilities.getChildContent(ele, "gameinfo");
                String highScore = XmlUtilities.getChildContent(ele, "highscore");
                String timesPlayed = XmlUtilities.getChildContent(ele, "timesplayed");
                gameDataList.add(new GameData(gameName, gameInfo, highScore, timesPlayed));
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
        Document doc = XmlUtilities.makeDocument(myUserMessageFilePath + name);
        Element el = doc.getDocumentElement();
        List<Message> messageList = new ArrayList<Message>();
        NodeList nl = el.getElementsByTagName("message");
        if (nl != null && nl.getLength() > 0) {
            for (int i = 0; i < nl.getLength(); i++) {
                // get an element
                Element ele = (Element) nl.item(i);
                String sender = XmlUtilities.getChildContent(ele, "sender");
                String message = XmlUtilities.getChildContent(ele, "content");
                SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
                Date date;
                try {
                    date = dateFormat.parse(XmlUtilities.getChildContent(ele, "date"));
                    messageList.add(new Message(sender, message, date));
                }
                catch (ParseException e) {
                    e.printStackTrace();
                }

            }
        }
        return messageList;
    }

    /**
     * Gets one particular tag of user information
     * 
     * @param userName
     * @param tagname
     * @return
     */
    // public String getUserInfo (String userName, String tagname) {
    // userName = userName + ourXMLExtension;
    // Document doc = XmlUtilities.makeDocument(myUserBasicFilePath + userName);
    // Element el = doc.getDocumentElement();
    // String content = XmlUtilities.getChildContent(el, tagname);
    // return content;
    //
    // }
}
