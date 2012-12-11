package arcade.usermanager;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import util.xml.XmlBuilder;
import util.xml.XmlParser;
import util.xml.XmlUtilities;
import util.xml.XmlWriter;


/**
 * 
 * @author Howard, modified by difan
 * 
 */

public class GameData {
    private String myGameName = "name";
    private String myGameInfo = "gameinfo";
    private String myHighScore = "highscore";
    private String myTimesPlayed = "timesplayed";
    private Map<String, String> myPropertyMap;
    private String myFilePath;
    private static ResourceBundle resource;
    private UserXMLWriter myXmlWriter;

    public GameData (String name, String gameInfo, String highScore, String timesPlayed) {
        myPropertyMap = new HashMap<String, String>();
        myPropertyMap.put(myGameName, name);
        myPropertyMap.put(myGameInfo, gameInfo);
        myPropertyMap.put(myHighScore, highScore);
        myPropertyMap.put(myTimesPlayed, timesPlayed);
        resource = ResourceBundle.getBundle("arcade.usermanager.filePath");
        myFilePath = resource.getString("GameFilePath") + name + ".xml";
        myXmlWriter = new UserXMLWriter();

    }

    // public boolean addGame(String gameName){
    //
    // if (!myPropertyMap.containsKey(propertyName)){}
    // else
    // }

    public String getGameInfo (String propertyName) {
        if (myPropertyMap.containsKey(propertyName)) return myPropertyMap.get(propertyName);

        return "";

    }

    /**
     * Sets a certain property of a user's GameInfo.
     * 
     * @param userName
     * @param propertyName
     * @param content
     * @return
     */
    public boolean setGameInfo (String userName, String propertyName, String content) {
        if ("highscore".equals(propertyName)) {
            if (Integer.parseInt(myPropertyMap.get(myHighScore)) > Integer.parseInt(content)) { 
                return false; }
        }
        
       if (myPropertyMap.containsKey(propertyName)) {

            myPropertyMap.put(propertyName, content);
            myXmlWriter.updateGameInfo(userName, getGameInfo("name"), propertyName, content);

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
    public String getGameInfoKeyString () {
        return myGameInfo;
    }

    public String getHighScoreKeyString () {
        return myHighScore;
    }
}
