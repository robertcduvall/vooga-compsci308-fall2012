package arcade.gamemanager;

import arcade.IArcadeGame;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import util.xml.XmlUtilities;


/**
 * This class keeps a list of Games and returns appropriate Games at GUI's
 * request.
 * 
 * @author Jei Min Yoo, Patrick Royal
 * 
 */
public class GameCenter {

    /**
     * File path where game data is stored and retrieved.
     */
    public static final String GAME_XML_FILE = "src/arcade/database/game.xml";
    private List<Game> myGames;
    private GameSearcherFactory mySearcherFactory;

    /**
     * Constructor for GameCenter.
     */
    public GameCenter () {
        initialize();
    }

    /**
     * sets user for all games.
     * @param userName
     */
    public void setUser(String userName) {
        for (Game g : myGames) {
            g.setUser(userName);
        }
    }
    /**
     * initializes the class by reading information from game.xml file.
     */
    public void initialize () {
        myGames = new ArrayList<Game>();
        mySearcherFactory = new GameSearcherFactory(myGames);
        refreshGames();

    }

    /**
     * reads game.xml file and re-instantiates Game objects.
     */
    private void refreshGames () {
        myGames.clear();
        Document doc = XmlUtilities.makeDocument(GAME_XML_FILE);
        Collection<Element> games = XmlUtilities.getElements(
                doc.getDocumentElement());
        for (Element ele : games) {
            String filePath = ele.getElementsByTagName(
                    "filepath").item(0).getTextContent();
            try {
                IArcadeGame arcade = (IArcadeGame)
                        Class.forName(filePath).newInstance();
                Game game = new Game(arcade, doc);
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
     * 
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
     * returns Game object specified by the game's name. If no game is found,
     * returns null.
     * 
     * @param gameName name of requested game
     * @return requested Game object or null if no such game is found
     */
    public Game getGame (String gameName) {
        for (Game gm : myGames) {
            if (gm.getGameName().equals(gameName)) {
                return gm;
            }
        }
        return null;
    }

    public List<String> getGameListByTagName(String tag) {
        return searchGames("TagSearch",tag);
    }
    /**
     * returns a list of games by a specific search criteria
     * @param searchType type of search
     * @param criteria input regarding type of search
     * @return
     */
    public List<String> searchGames(String searchType, String criteria) {
        GameSearcher mySearcher = mySearcherFactory.createSearcher(searchType);
        return mySearcher.search(criteria);
    }
}
