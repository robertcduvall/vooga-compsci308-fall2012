package arcade.usermanager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


/**
 * Reads in user data from an XML file and creates User objects
 * 
 * @author Howard
 *         minor modification by difan
 */
public class UserXMLReader {
    private Document myDom;
    private ResourceBundle myResource;
    private String myUserBasicFilePath;
    private String myUserMessageFilePath;
    private String myUserGameFilePath;

    /**
     * Constructs a UserXMLReader.
     */
    public UserXMLReader () {
        myResource = ResourceBundle.getBundle("resources.filePath");
        myUserBasicFilePath = myResource.getString("BasicFilePath");
        myUserMessageFilePath = myResource.getString("MessageFilePath");
        myUserGameFilePath = myResource.getString("GameFilePath");
    }

    /**
     * Parses an XML file into a DOM object
     * 
     * @param filePath
     */
    private void parseXmlFile (String filePath) {
        // get the factory
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            // Using factory get an instance of document builder
            DocumentBuilder db = dbf.newDocumentBuilder();

            // parse using builder to get DOM representation of the XML file
            myDom = db.parse(filePath);
        }
        catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        }
        catch (SAXException se) {
            se.printStackTrace();
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    /**
     * Creates a user object from XML data.
     * 
     * @param name name of the user to create object for
     * @return
     */
    public User getUser (String name) {

        parseXmlFile(myUserBasicFilePath + name + ".xml");
        Element el = myDom.getDocumentElement();
        String username = getTextValue(el, "name");
        String password = getTextValue(el, "password");
        String picture = getTextValue(el, "picture");
        int credits = getIntValue(el, "credits");
        // later, hash basic user info?
        List<Message> messageList = getMessageList(name);
        List<GameData> gameDataList = getGameDataList(name);

        return new User(username, password, picture, credits, messageList, gameDataList);

    }

    /**
     * Gets a list of GameData objects
     * 
     * @param name
     * @return
     */
    public List<GameData> getGameDataList (String name) {
        parseXmlFile(myUserGameFilePath + name + ".xml");
        Element el = myDom.getDocumentElement();
        List<GameData> gameDataList = new ArrayList<GameData>();
        NodeList nl = el.getElementsByTagName("game");
        if (nl != null && nl.getLength() > 0) {
            for (int i = 0; i < nl.getLength(); i++) {
                // get an element
                Element ele = (Element) nl.item(i);
                String gameName = getTextValue(ele, "name");
                String gameInfo = getTextValue(ele, "gameinfo");
                int highScore = getIntValue(ele, "highscore");
                int timesPlayed = getIntValue(ele, "timesplayed");
                gameDataList.add(new GameData(gameName, gameInfo, highScore, timesPlayed));
            }
        }
        return gameDataList;
    }

    /**
     * Gets a list of Message objects
     * 
     * @param name
     * @return
     */
    public List<Message> getMessageList (String name) {
        parseXmlFile(myUserMessageFilePath + name + ".xml");
        Element el = myDom.getDocumentElement();
        List<Message> messageList = new ArrayList<Message>();
        NodeList nl = el.getElementsByTagName("message");
        if (nl != null && nl.getLength() > 0) {
            for (int i = 0; i < nl.getLength(); i++) {
                // get an element
                Element ele = (Element) nl.item(i);
                String sender = getTextValue(ele, "sender");
                String message = getTextValue(ele, "content");
                messageList.add(new Message(sender, message));
            }
        }
        return messageList;
    }

    /**
     * Gets a text value from DOM element
     * 
     * @param ele
     * @param tagName
     * @return
     */
    private String getTextValue (Element ele, String tagName) {
        String textVal = null;
        NodeList nl = ele.getElementsByTagName(tagName);
        if (nl != null && nl.getLength() > 0) {
            Element el = (Element) nl.item(0);
            textVal = el.getFirstChild().getNodeValue();
        }

        return textVal;
    }

    /**
     * Calls getTextValue and returns a int value
     * 
     * @param ele
     * @param tagName
     * @return
     */
    private int getIntValue (Element ele, String tagName) {
        // in production application you would catch the exception
        return Integer.parseInt(getTextValue(ele, tagName));
    }

}
