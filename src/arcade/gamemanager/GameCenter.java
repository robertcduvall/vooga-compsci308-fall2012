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
    private GameSearcher mySearcher;

    /**
     * Constructor for GameCenter.
     */
    public GameCenter () {
        initialize();
    }

    /**
     * initializes the class by reading information from game.xml file.
     */
    public void initialize () {
        myGames = new ArrayList<Game>();
        mySearcher = new DefaultGameSearcher(myGames);
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
        return mySearcher.getGameList();
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

    /**
     * returns a list of games that have the tag.
     * 
     * @param tag a tag that games have in common
     * @return list of games that have the tag.
     */
    public List<String> getGameListByTagName (String tag) {
        return mySearcher.getGameListByTagName(tag);
    }

//     public static void main (String args[]) {
//     System.out.println("haha");
//     GameCenter gc = new GameCenter();
//     gc.getGameList();
//     System.out.println(gc.myGames.size());
//     Game rpg = gc.getGame("Turnbased RPG");
//     System.out.println(rpg.getGameInfoList());
//     System.out.println(rpg.getAverageRating());
//     rpg.getReviews();
//     System.out.println(gc.getGameListByTagName("shooter"));
//     }
}
