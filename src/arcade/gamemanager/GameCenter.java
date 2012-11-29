package arcade.gamemanager;

import java.awt.Image;
import java.io.File;
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
import util.xml.XmlParser;
import util.xml.XmlUtilities;
import arcade.IArcadeGame;
import arcade.usermanager.SocialCenter;
import arcade.utility.ReadWriter;


/**
 * This class keeps a list of Games and returns appropriate Games at GUI's request.
 * @author Jei Min Yoo
 * 
 */
public class GameCenter {

    private List<Game> myGames;
    private String myGameXml = "../vooga-compsci308-fall2012/src/arcade/database/game.xml";

    public GameCenter () {
        initialize();
    }
    
    private Document makeDocument(File file) {
        Document doc = null;
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            doc = db.parse(file);
        } catch (IOException e) {
            System.out.println("IOException on file: " + file.toString());
        } catch (ParserConfigurationException e) {
            System.out.println("parser error on file: " + file.toString());
        } catch (SAXException e) {
            System.out.println("SAXException on file: " + file.toString());
        }
        
        return doc;
    }

    /**
     * initializes the class by reading information from game.xml file.
     */
    public void initialize () {
        myGames = new ArrayList<Game>();
        refreshGames();

    }

    /**
     * reads game.xml file and re-instantiates Game objects.
     */
    private void refreshGames () {
        myGames.clear();
        Document doc = makeDocument(new File(myGameXml));
        NodeList nList = doc.getElementsByTagName("filepath");
        for (int i = 0; i < nList.getLength(); i++) {
            String filePath = nList.item(i).getTextContent();
            try {
                IArcadeGame arcade = (IArcadeGame) Class.forName(filePath).newInstance();
                Game game = new Game(arcade);
                myGames.add(game);
            }
            catch (IllegalAccessException e) {
                System.out.println("illegal access: " + filePath);
            }
            catch (InstantiationException e) {
                System.out.println("failed to instantiate class: " + filePath);
            }
            catch (ClassNotFoundException e) {
                System.out.println("class does not exist: " + filePath);
            }
        }
    }
    
    /**
     * returns the list of available games.
     * @return list of available games
     */
    public List<String> getGameList () {
        List<String> gameList = new ArrayList<String>();
        for (Game game : myGames) {
            gameList.add(game.getGameName());
        }
        return gameList;
    }

    /**
     * returns Game object specified by the game's name. If no game is found, returns null.
     * @param gameName name of requested game
     * @return requested Game object or null if no such game is found
     */
    public Game getGame (String gameName) {
        for (Game gm : myGames) {
            if (gm.getGameName().equals(gameName)) { return gm; }
        }
        return null;
    }

    /**
     * returns a list of games that have the tag.
     * @param tag a tag that games have in common
     * @return list of games that have the tag.
     */
    public List<String> getGameListByTagName (String tag) {
        List<String> gameList = new ArrayList<String>();
        for (Game gm : myGames) {
            if (gm.getGenre().contains(tag)) {
                gameList.add(gm.getGameName());
            }
        }
        return gameList;
    }

//     public static void main(String args[]) {
//     System.out.println("haha");
//     GameCenter gc = new GameCenter();
//     List<String> list = gc.getGameList();
//     System.out.println(gc.myGames.size());
//     gc.getGame("Sample1").runGame();
//     }
}
