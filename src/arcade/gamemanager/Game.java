package arcade.gamemanager;

import java.awt.Image;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import util.xml.XmlUtilities;
import arcade.IArcadeGame;


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

    private GameSaver mySaver;
    private IArcadeGame myGame;
    private Document myDocument;
    private Element myGameElement;
    private List<String> myGameInfoList;
    private String myGameXml = "../vooga-compsci308-fall2012/src/arcade/database/game.xml";

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
        resetGameInfoSource();
    }

    private void resetGameInfoSource () {
        myDocument = XmlUtilities.makeDocument(myGameXml);
        myGameElement = getGameElement(myDocument);
        myGameInfoList = makeGameInfoList(myGameElement);
    }

    private Element getGameElement (Document doc) {
        Element gameElement = doc.getDocumentElement();
        Collection<Element> games = XmlUtilities.getElements(doc.getDocumentElement(), "name");
        for (Element ele : games) {
            if (ele.getTextContent().equals(myGame.getName())) {
                gameElement = (Element) ele.getParentNode();
            }
        }
        return gameElement;

    }

    private List<String> makeGameInfoList (Element gameElement) {
        Collection<Element> gameInfo = XmlUtilities.getElements(myGameElement);
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
    public List<String> getGameInfo (String infoName) {
        List<String> info = new ArrayList<String>();
        if (!myGameInfoList.contains(infoName)) {
            System.out.println("no such information is available!!!");
            return info;
        }
        Collection<Element> infoList = XmlUtilities.getElements(myGameElement, infoName);
        for (Element ele : infoList) {
            info.add(ele.getTextContent());
        }
        return info;
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
        return getGameInfo("review");
    }

    /**
     * Returns the average of all the ratings for the game.
     */
    public double getAverageRating () {
        double total = 0;
        List<Integer> ratings = getRatings();
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
        String ratingData = getGameInfo("rating").get(0);
        String[] ratingList = ratingData.split(" ");
        List<Integer> ratings = new ArrayList<Integer>();
        for (String rating : ratingList) {
            ratings.add(Integer.parseInt(rating));
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
        XmlUtilities.appendElement(myDocument, myGameElement, "review", review);
        saveChanges();
    }

    private void saveChanges () {
        myDocument.normalizeDocument();
        XmlUtilities.write(myDocument, myGameXml);
        resetGameInfoSource();
    }

    /**
     * Lets the user choose a rating, and then adds it to the list of ratings
     * for that game.
     * 
     * @param rating
     *        rating to be added
     */
    public void setRating (int rating) {
        String ratingData = getGameInfo("rating").get(0) + " " + rating;
        ratingData.trim();
        Node ratingElement = myGameElement.getElementsByTagName("rating").item(0);
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
    public String getGenre () {
        NodeList gameInfo = myGameElement.getChildNodes();
        for (int i = 0; i < gameInfo.getLength(); i++) {
            if ("name".equals(gameInfo.item(i))) return gameInfo.item(i).getTextContent();
        }
        return null;
    }
}
