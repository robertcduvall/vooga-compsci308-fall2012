package arcade.gamemanager;

import arcade.IArcadeGame;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;


// TODO getUserPreferences: maybe we should remove this method and let GUI team
// call from SocialCenter?

/**
 * Class of the arcade used to start, end, and maintain the relationship with
 * all games in the arcade.
 * 
 * @author Michael Deng, Patrick Royal, Jei Min Yoo
 * 
 */
public class Game {

    /**
     * Constant string for review.
     */
    private static final String REVIEW = "review";
    /**
     * Constant string for rating.
     */
    private static final String RATING = "rating";
    private GameSaver mySaver;
    private IArcadeGame myGame;
    private Document myDocument;
    private Element myGameElement;
    private List<String> myGameInfoList;
    private DocumentManager myDocumentManager;

    /**
     * Constructor for Game Manager takes in a specific game, so there is a
     * separate manager for each game. The Game Manager also instantiates a Game
     * Saver that can be passed into the games as they run. The Game Saver
     * allows the game to save user preferences and high scores without
     * accessing the rest of the game manager.
     * 
     * @param gameObject
     *        the game to be managed
     * @param doc xml document used to save and load data
     */
    public Game (IArcadeGame gameObject, Document doc) {
        mySaver = new GameSaver(null, gameObject);
        myGame = gameObject;
        myDocumentManager = new XmlManager();
        myDocument = doc;
        myGameElement = getGameElement(doc);
        resetGameInfoList();
    }

    private void resetGameInfoList () {
        myGameInfoList = makeGameInfoList(myGameElement);
    }

    private Element getGameElement (Document doc) {
        Element gameElement = doc.getDocumentElement();
        Collection<Element> games = myDocumentManager.getElements(doc.getDocumentElement(), "name");
        for (Element ele : games) {
            if (ele.getTextContent().equals(myGame.getName())) {
                gameElement = (Element) ele.getParentNode();
            }
        }
        return gameElement;

    }

    private List<String> makeGameInfoList (Element gameElement) {
        Collection<Element> gameInfo = myDocumentManager.getElements(myGameElement);
        Set<String> gameInfoList = new HashSet<String>();
        for (Element ele : gameInfo) {
            gameInfoList.add(ele.getNodeName());
        }
        return new ArrayList<String>(gameInfoList);
    }

    /**
     * returns the list of available information for the game from xml file.
     * 
     * @return list of available information
     */
    public List<String> getGameInfoList () {
        return myGameInfoList;
    }

    /**
     * returns information requested by infoName parameter
     * 
     * @param infoName name of requested information
     * @return requested information in a list form.
     */
    private List<String> getGameInfo (String infoName) {
        List<String> info = new ArrayList<String>();
        if (!myGameInfoList.contains(infoName)) {
            System.out.println("no such information is available!!!");
            info.add("");
            return info;
        }
        Collection<Element> infoList = myDocumentManager.getElements(myGameElement, infoName);
        for (Element ele : infoList) {
            info.add(ele.getTextContent());
        }
        return info;
    }

    /**
     * adds additional game information
     * @param tag tag name of new information
     * @param content new information content
     */
    private void setGameInfo (String tag, String content) {
        myDocumentManager.appendElement(myDocument, myGameElement, tag, content);
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
    }

    /**
     * returns the game's description.
     * 
     * @return game description
     */
    public String getDescription () {
        return myGame.getDescription();
    }

    /**
     * Returns all reviews for the given game, displaying the current user's
     * review first (if it exists).
     */
    public List<String> getReviews () {
        return getGameInfo(REVIEW);
    }

    /**
     * Returns the average of all the ratings for the game.
     */
    public double getAverageRating () {
        double total = 0;
        List<Integer> ratings = getRatings();
        if (ratings.size() == 0) {
            return 0;
        }
        for (Integer rating : ratings) {
            total += rating;
        }
        return total / ratings.size();
    }

    /**
     * Returns all ratings for the given game, displaying the current user's
     * rating first (if it exists).
     */
    public List<Integer> getRatings () {
        List<Integer> ratings = new ArrayList<Integer>();
        String ratingData = getGameInfo(RATING).get(0);
        String[] ratingList = ratingData.split(" ");
        for (String rating : ratingList) {
            if (!rating.isEmpty()) {
                ratings.add(Integer.parseInt(rating));
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
        setGameInfo(REVIEW, review);
        saveChanges();
    }

    private void saveChanges () {
        myDocumentManager.save(myDocument, GameCenter.GAME_XML_FILE);
        resetGameInfoList();
    }

    /**
     * Lets the user choose a rating, and then adds it to the list of ratings
     * for that game.
     * 
     * @param rating
     *        rating to be added
     */
    public void setRating (int rating) {
        String ratingData = getGameInfo(RATING).get(0) + " " + rating;
        ratingData = ratingData.trim();
        Node ratingElement = myGameElement.getElementsByTagName(RATING).item(0);
        if (ratingElement == null) {
            myDocumentManager.appendElement(myDocument, myGameElement, RATING, "");
            ratingElement = myGameElement.getElementsByTagName(RATING).item(0);
        }
        ratingElement.setTextContent(ratingData);
        saveChanges();
    }

    /**
     * Returns the thumbnail image associated with the game.
     */
    public Image getImage () {
        return myGame.getMainImage();
    }

    /**
     * Loads the user's preferences for that particular game from User.xml.
     */
    public String getUserPreferences () {
        // user.getGameData(myGame.getName()).getGameInfo("preferences");

        return "";
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
    public List<String> getTags () {
        String genreData = getGameInfo("tags").get(0);
        List<String> genre = Arrays.asList(genreData.split(" "));
        return genre;
    }
}
