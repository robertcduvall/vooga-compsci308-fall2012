package arcade.gamemanager;

import java.awt.Image;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import util.xml.XmlParser;
import arcade.IArcadeGame;
import arcade.utility.ReadWriter;
//TODO Replace readwriter with other xml reader

/**
 * Class of the arcade used to start, end, and maintain
 * the relationship with all games in the arcade.
 * 
 * @author Michael Deng, Patrick Royal
 * 
 */
public class Game {

    private GameSaver mySaver;
    private IArcadeGame myGame;

    /**
     * Constructor for Game Manager takes in a specific game, so there is a
     * separate manager for each game. The Game Manager also instantiates a Game
     * Saver that can be passed into the games as they run. The Game Saver
     * allows the game to save user preferences and high scores without
     * accessing the rest of the game manager.
     * 
     * @param gameObject the game to be managed
     */
    public Game (IArcadeGame gameObject) {
        mySaver = new GameSaver(null, gameObject);
        myGame = gameObject;
        // TODO
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
     * Loads a sorted list of the user's scores for that particular game from
     * User.xml. The highest score is always the first item on the list, for
     * easy access
     */
    public List<Integer> getUserScores () {
        File f = new File("../vooga-compsci308-fall2012/src/arcade/database/game.xml");
        XmlParser myXmlParser = new XmlParser(f);
        return null;
    }

    /**
     * Loads the user's preferences for that particular game from User.xml.
     */
    public String getUserPreferences () {
        File f = new File("arcade.database/game.xml");
        List<String> tags = new ArrayList<String>();
        tags.add(myGame.getName());
        tags.add("userPreferences");
        return ReadWriter.loadData(f, tags);
    }

    /**
     * Returns all reviews for the given game, displaying the current user's
     * review first (if it exists).
     */
    public List<String> getReviews () {
        File f = new File("arcade.database/game.xml");
        List<String> tags = new ArrayList<String>();
        tags.add(myGame.getName());
        tags.add("review");
        return ReadWriter.search(f, tags);
    }

    /**
     * Returns the average of all the ratings for the game.
     */
    public Integer getAverageRatings () {
        File f = new File("arcade.database/game.xml");
        List<String> tags = new ArrayList<String>();
        tags.add(myGame.getName());
        tags.add("rating");
        Integer scores = 0;
        List<String> ratings = ReadWriter.search(f, tags);
        if (ratings.size() == 0) return null;
        for (String s : ratings) {
            scores += Integer.parseInt(s);
        }
        return scores / ratings.size();
    }

    /**
     * Returns all ratings for the given game, displaying the current user's
     * rating first (if it exists).
     */
    public List<Integer> getRatings () {
        File f = new File("arcade.database/game.xml");
        List<String> tags = new ArrayList<String>();
        tags.add(myGame.getName());
        tags.add("rating");
        List<Integer> scores = new ArrayList<Integer>();
        List<String> ratings = ReadWriter.search(f, tags);
        for (String s : ratings) {
            scores.add(Integer.parseInt(s));
        }
        return scores;
    }

    /**
     * Allows the user to input a new review for the game, overwriting the
     * previous review (if applicable).
     * 
     * @param review text for the review
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
        File f = new File("arcade.database/game.xml");
        List<String> tags = new ArrayList<String>();
        tags.add(myGame.getName());
        tags.add("review");
        if (ReadWriter.search(f, tags).size() == 0) return "";
        return ReadWriter.loadData(f, tags);
    }

    /**
     * Lets the user choose a rating, and then adds it to the list of ratings
     * for that game.
     * 
     * @param rating rating to be added
     */
    public void setRating (int rating) {
        File f = new File("arcade.database/game.xml");
        List<String> tags = new ArrayList<String>();
        tags.add(myGame.getName());
        tags.add("rating");
        ReadWriter.storeData(f, tags, Integer.toString(rating));
    }
    
    public Image getImage() {
        return myGame.getMainImage();
    }
    
    public String getGameName() {
        return myGame.getName();
    }
    
    public List<String> getGenre() {
        // TODO
        return null;
    }
}
