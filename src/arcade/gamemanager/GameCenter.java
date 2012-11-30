package arcade.gamemanager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import util.xml.XmlUtilities;
import arcade.IArcadeGame;


/**
 * This class keeps a list of Games and returns appropriate Games at GUI's
 * request.
 * 
 * @author Jei Min Yoo
 * 
 */
public class GameCenter {

    private List<Game> myGames;
    private String myGameXml = "../vooga-compsci308-fall2012/src/arcade/database/game.xml";

    public GameCenter () {
        initialize();
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
        Document doc = XmlUtilities.makeDocument(myGameXml);
        Collection<Element> games = XmlUtilities.getElements(doc.getDocumentElement());
        for (Element ele : games) {
            String filePath = ele.getElementsByTagName("filepath").item(0).getTextContent();
            try {
                IArcadeGame arcade = (IArcadeGame) Class.forName(filePath).newInstance();
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
            if (gm.getGameName().equals(gameName)) return gm;
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
        List<String> gameList = new ArrayList<String>();
        for (Game gm : myGames) {
            if (gm.getGenre().contains(tag)) {
                gameList.add(gm.getGameName());
            }
        }
        return gameList;
    }

//     public static void main (String args[]) {
//     System.out.println("haha");
//     GameCenter gc = new GameCenter();
//     gc.getGameList();
//     System.out.println(gc.myGames.size());
//     Game rpg = gc.getGame("Turnbased RPG");
//     System.out.println(rpg.getGameInfoList());
//     System.out.println(rpg.getAverageRating());
//     rpg.setReview("yet another review");
//     
//     }
}
