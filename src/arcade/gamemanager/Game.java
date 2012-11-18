package arcade.gamemanager;

import arcade.IArcadeGame;
import arcade.utility.ReadWriter;
import java.awt.Image;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import util.xml.XmlBuilder;
import util.xml.XmlParser;


// TODO Replace readwriter with other xml reader

/**
 * Class of the arcade used to start, end, and maintain the relationship with
 * all games in the arcade.
 * 
 * @author Michael Deng, Patrick Royal, Seon Kang
 * 
 */
public class Game {

    private GameSaver mySaver;
    private IArcadeGame myGame;
    private Node myGameNode;
    private XmlParser myXmlParser;
    private XmlBuilder myXmlBuilder;

    /**
     * Constructor for Game Manager takes in a specific game, so there is a
     * separate manager for each game. The Game Manager also instantiates a Game
     * Saver that can be passed into the games as they run. The Game Saver
     * allows the game to save user preferences and high scores without
     * accessing the rest of the game manager.
     * 
     * @param gameObject
     *        the game to be managed
     */
    public Game (IArcadeGame gameObject) {
        mySaver = new GameSaver(null, gameObject);
        myGame = gameObject;
        File f = new File(
                "../vooga-compsci308-fall2012/src/arcade/database/game.xml");
        XmlParser myXmlParser = new XmlParser(f);
        myXmlBuilder = new XmlBuilder(f);
        NodeList allGames = myXmlParser.getElementsByName(
                myXmlParser.getDocumentElement(), "game");
        for (int i = 0; i < allGames.getLength(); i++) {
            if (allGames.item(i).getTextContent().equals(myGame.getName()))
                myGameNode = allGames.item(i);
        }
    }

    /**
     * Constructor for Game Manager that takes in a game name as a string, then
     * searches for that game. This allows the GUI to specify a string rather
     * than an object, making searching for games easier. The Game Manager also
     * instantiates a Game Saver that can be passed into the games as they run.
     * The Game Saver allows the game to save user preferences and high scores
     * without accessing the rest of the game manager.
     */
    public void runGame () {
        myGame.runGame(getUserPreferences(), mySaver);
        // TODO
    }

    /**
     * Loads the user's preferences for that particular game from User.xml.
     */
    public String getUserPreferences () {
        NodeList gameInfo = myGameNode.getChildNodes();
        for (int i = 0; i < gameInfo.getLength(); i++) {
            if ("preferences".equals(gameInfo.item(i))) {
                return gameInfo.item(
                    i).getTextContent();
            }
        }
        return null;
    }

    /**
     * Returns all reviews for the given game, displaying the current user's
     * review first (if it exists).
     */
    public List<String> getReviews () {
        NodeList gameInfo = myGameNode.getChildNodes();
        List<String> reviews = new ArrayList<String>();
        for (int i = 0; i < gameInfo.getLength(); i++) {
            if ("review".equals(gameInfo.item(i))) {
                reviews.add(gameInfo.item(i).getTextContent());
            }
        }
        return reviews;
    }

    /**
     * Returns the average of all the ratings for the game.
     */
    public Integer getAverageRatings () {
        List<Integer> ratings = getRatings();
        int average = 0;
        for (int i = 0; i < ratings.size(); i++) {
            average += ratings.get(i);
        }
        return average / ratings.size();
    }

    /**
     * Returns all ratings for the given game, displaying the current user's
     * rating first (if it exists).
     */
    public List<Integer> getRatings () {
        NodeList gameInfo = myGameNode.getChildNodes();
        List<Integer> ratings = new ArrayList<Integer>();
        for (int i = 0; i < gameInfo.getLength(); i++) {
            if ("rating".equals(gameInfo.item(i))) {
                ratings.add(Integer.parseInt(gameInfo.item(i).getTextContent()));
            }
        }
        return ratings;
    }

    /**
     * Allows the user to input a new review for the game, overwriting the
     * previous review (if applicable).
     * 
     * @param review
     *        text for the review
     */
    public void setReview (String review) {
        File f = new File("arcade.database/game.xml");
        List<String> tags = new ArrayList<String>();
        tags.add(myGame.getName());
        tags.add("review");
        ReadWriter.storeData(f, tags, review);
    }

    /**
     * Returns the review for the game, if it exists.
     */
    public String getReview () {
        NodeList gameInfo = myGameNode.getChildNodes();
        for (int i = 0; i < gameInfo.getLength(); i++) {
            if ("review".equals(gameInfo.item(i))) { return gameInfo.item(i)
                    .getTextContent(); }
        }
        return null;
    }

    /**
     * Lets the user choose a rating, and then adds it to the list of ratings
     * for that game.
     * 
     * @param rating
     *        rating to be added
     */
    public void setRating (int rating) {
        File f = new File("arcade.database/game.xml");
        List<String> tags = new ArrayList<String>();
        tags.add(myGame.getName());
        tags.add("rating");
        ReadWriter.storeData(f, tags, Integer.toString(rating));
    }

    /**
     * Returns the thumbnail image associated with the game.
     */
    public Image getImage () {
        return myGame.getMainImage();
    }

    /**
     * Returns the name of the game.
     */
    public String getGameName () {
        return myGame.getName();
    }

    /**
     * Returns the genre of the game for the purposes of sorting.
     */
    public String getGenre () {
        NodeList gameInfo = myGameNode.getChildNodes();
        for (int i = 0; i < gameInfo.getLength(); i++) {
            if ("name".equals(gameInfo.item(i))) { return gameInfo.item(i)
                    .getTextContent(); }
        }
        return null;
    }
}
