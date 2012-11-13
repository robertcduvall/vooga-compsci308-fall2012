package arcade.usermanager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
 * minor modification by difan
 */
public class UserXMLReader {
    private static Document dom;

    private   void parseXmlFile (String filePath) {
        // get the factory
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            // Using factory get an instance of document builder
            DocumentBuilder db = dbf.newDocumentBuilder();

            // parse using builder to get DOM representation of the XML file

            dom = db.parse(filePath);

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
     * @return
     */
    private User getUser () {
        Element el = dom.getDocumentElement();
        String name = getTextValue(el, "name");
        String password = getTextValue(el, "password");
        String picture = getTextValue(el, "picture");
        
        // provide hashed version in separate file for credits?
        int credits = getIntValue(el, "credits");
        List<Message> messageList = new ArrayList<Message>();
        List<GameData> gameDataList = new ArrayList<GameData>();

        // split into separate method
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
        // split into separate method
        nl = el.getElementsByTagName("game");
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

       return new User(name, password, picture, credits, messageList, gameDataList);

    }

    /**
     * I take a xml element and the tag name, look for the tag and get
     * the text content
     * i.e for <employee><name>John</name></employee> xml snippet if
     * the Element points to employee node and tagName is name I will return
     * John
     * 
     * @param ele
     * @param tagName
     * @return
     */
    private   String getTextValue (Element ele, String tagName) {
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
    private   int getIntValue (Element ele, String tagName) {
        // in production application you would catch the exception
        return Integer.parseInt(getTextValue(ele, tagName));
    }

    public static void main (String[] args) {
        // create an instance
        UserXMLReader dpe = new UserXMLReader();
        // call run example
        dpe.parseXmlFile("src/arcade/database/Howard.xml");
        dpe.getUser();
    }

}