package arcade.gamemanager;

import java.awt.Image;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import util.xml.XmlParser;
import arcade.IArcadeGame;
import arcade.utility.ReadWriter;

/**
 * 
 * @author Jei Min Yoo
 *
 */
public class GameCenter {

    private XmlParser myXmlParser;
    private List<IArcadeGame> myGames;
    private NodeList gameNodeList;
    
    public GameCenter() {
        initialize();
    }
    
    /**
     * initializes the class by reading information from game.xml file.
     */
    public void initialize() {
        File f = new File("../vooga-compsci308-fall2012/src/arcade/database/game.xml");
        myXmlParser = new XmlParser(f);
        gameNodeList = myXmlParser.getElementsByName(myXmlParser.getRootElement(), "game");
    }
    
    public List<String> getListOfGames() {
        List<String> gameList = new ArrayList<String>();
        NodeList nList = myXmlParser.getElementsByName(myXmlParser.getRootElement(), "name");
        for (int i = 0; i < nList.getLength(); i++) {
            gameList.add(nList.item(i).getTextContent());
        }
        return gameList;
    }
    
    public String getRating(String gameName) {
        
        for (int i = 0; i < gameNodeList.getLength(); i++) {
            NodeList gameInfoList = gameNodeList.item(i).getChildNodes();
            for (int j = 0; j < gameInfoList.getLength(); j++) {
                if (gameInfoList.item(j).getNodeName().equals(gameName)) {
                  return myXmlParser.getTextContent((Element) gameNodeList.item(i), "rating");  
                }
            }
        }
        
        return "0";
    }
    
    public List<String> getListOfReviews(String gameName) {
        List<String> reviewList = new ArrayList<String>();
        
        return reviewList;
    }
    
    public List<String> getUsersThatPlayedGame(String gameName) {
        List<String> userList = new ArrayList<String>();
        return userList;
    }
    
    public List<Integer> getUserScores(String gameName){
        List<Integer> userList = new ArrayList<Integer>();
        return userList;
    }
    public Image getGameProfilePicture(String gameName) {
        // TODO Auto-generated method stub
        return null;
    }
    
}
