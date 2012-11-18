package arcade.usermanager;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import util.xml.XmlBuilder;
import util.xml.XmlWriter;


/**
 * 
 * @author Howard, modified by difan
 * 
 */

public class GameData {
    private String myGameName;
    private String myGameInfo;
    private String myHighScore;
    private String myTimesPlayed;
    private Map<String, String> myPropertyMap;
    private String myFilePath;
    private static ResourceBundle resource;

    public GameData (String name, String gameInfo, String highScore,
            String timesPlayed) {
        myPropertyMap = new HashMap<String, String>();
        myPropertyMap.put(myGameName, name);
        myPropertyMap.put(myGameInfo, gameInfo);
        myPropertyMap.put(myHighScore, highScore);
        myPropertyMap.put(myTimesPlayed, timesPlayed);
        resource = ResourceBundle.getBundle("arcade.usermanager.filePath");
        myFilePath = resource.getString("GameFilePath") + name + ".xml";

    }

    public String getGameInfo (String propertyName) {
        if (myPropertyMap.containsKey(propertyName))
            return myPropertyMap.get(propertyName);

        return "";

    }

    public boolean setGameInfo (String propertyName, String content) {
        if (myPropertyMap.containsKey(propertyName)) {
            myPropertyMap.put(propertyName, content);
            Document doc = XmlBuilder.createDocument(myFilePath);

            Element root = doc.getDocumentElement();
            NodeList children = root.getChildNodes();
            for (int i = 0; i < children.getLength(); i++) {
                Element child = (Element) children.item(i);

                if (XmlParser.getTextContent(child, "name").equals(
                        getGameInfo(getGameInfo("myGameName")))) {
                    XmlBuilder.modifyTag(child, propertyName, content);
                }
            }

            XmlWriter.writeXML(doc, myFilePath);

            return true;
        }

        return false;

    }
    /**
     * Added getters for use in Game model, hope that's okay. 
     *
     * @author Seon Kang
     * @return
     */
    public String getGameInfoKeyString() {
    	return myGameInfo;
    }
    
    public String getHighScoreKeyString() {
    	return myHighScore;
    }
}
