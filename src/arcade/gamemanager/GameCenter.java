package arcade.gamemanager;

import java.awt.Image;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import util.xml.XmlParser;
import arcade.IArcadeGame;
import arcade.usermanager.SocialCenter;
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
    private SocialCenter socialCenter;
    
    public GameCenter() {
        initialize();
    }
    
    /**
     * initializes the class by reading information from game.xml file.
     */
    public void initialize() {
        socialCenter = SocialCenter.getInstance();
        File f = new File("../vooga-compsci308-fall2012/src/arcade/database/game.xml");
        myXmlParser = new XmlParser(f);
        gameNodeList = myXmlParser.getElementsByName(myXmlParser.getDocumentElement(), "game");
    }

    /**
     * General function for getting String lists by passing a string of
     * the tag.
     * 
     * @author Seon Kang
     * 
     * @param tag 
     * 
     * @return A String ArrayList of elements matching tag
     */
    public List<String> getListByTagName(String tag, Element element) {
    	List<String> stringListByTag = new ArrayList<String>();
    	NodeList nodeList = myXmlParser.getElementsByName(element, tag);
    	for (int i = 0; i < nodeList.getLength(); i++) {
    		stringListByTag.add(nodeList.item(i).getTextContent());
    	}
    	return stringListByTag;
    }

    /**
     * Default element is the document.
     * 
     * @param tag
     * @return
     */
    public List<String> getListByTagName(String tag) {
    	return getListByTagName(tag, myXmlParser.getDocumentElement());
    }
    
    
    public List<String> getListOfGames() {
        List<String> gameList = new ArrayList<String>();
        NodeList nList = myXmlParser.getElementsByName(myXmlParser.getDocumentElement(), "name");
        for (int i = 0; i < nList.getLength(); i++) {
            gameList.add(nList.item(i).getTextContent());
        }
        return gameList;
    }
    
    /**
     * A more general way to get game properties.
     * 
     * @auther Seon Kang
     * @param gameName 
     * @param tag 
     * @return
     */
    
    public String getAttributeFromGame(String gameName, String tag) {
    	for (int i = 0; i < gameNodeList.getLength(); i++) {
    		if (gameNodeList.item(i).getTextContent().equals(gameName)) {
    			NodeList attributeList = gameNodeList.item(i).getChildNodes();
    			for (int j = 0; j < attributeList.getLength(); j++) {
    				return myXmlParser.getTextContent((Element) gameNodeList.item(j), 
    						tag);
    			}
    		}
    	}
    	return null;
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
    
    /**
     * Find a list of attributes from gameName.
     * 
     * @auther Seon Kang
     * 
     * @param gameName
     * @param tag 
     * @return
     */
    public List<String> getListOfX(String gameName, String tag) {
    	Element gameAsElement = myXmlParser.getDocumentElement();
    	for (int i = 0; i < gameNodeList.getLength(); i++) {
    		if (tag.equals(gameNodeList.item(i).getTextContent())) {
    			gameAsElement = (Element) gameNodeList.item(i);
    		}
    	}
    	return getListByTagName(tag, gameAsElement);
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
        for (IArcadeGame game : myGames) {
            if (game.getName().equals(gameName)) {
                return game.getMainImage();
            }
        }
        return null;
        
    }
    
    public static void main(String args[]) {
        System.out.println("haha");
        GameCenter gc = new GameCenter();
    }
}
