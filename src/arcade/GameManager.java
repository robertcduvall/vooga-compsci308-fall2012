package arcade;

/**
 * Class of the arcade used to start, end, and maintain
 * the relationship with all games in the arcade.
 * 
 * @author Michael Deng
 * 
 */
public class GameManager {

    /**
     * Constructor for Game Manager takes in a specific game, so there is a
     * separate manager for each game. The Game Manager also instantiates a Game
     * Saver that can be passed into the games as they run. The Game Saver
     * allows the game to save user preferences and high scores without
     * accessing the rest of the game manager.
     * 
     * @param gameObject the game to be managed
     */
    public GameManager (Game gameObject) {
        // TODO
    }

    /**
     * Constructor for Game Manager that takes in a game name as a string, then
     * searches for that game. This allows the GUI to specify a string rather
     * than an object, making searching for games easier. The Game Manager also
     * instantiates a Game Saver that can be passed into the games as they run.
     * The Game Saver allows the game to save user preferences and high scores
     * without accessing the rest of the game manager.
     * 
     * @param gameName name of the game to be managed
     */
    public GameManager (String gameName) {
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
        // TODO
    }

    /**
     * Loads a sorted list of the user’s scores for that particular game from
     * User.xml. The highest score is always the first item on the list, for
     * easy access
     */
    public List<Integer> getUserScores () {
        // TODO
    }

    /**
     * Loads the user’s preferences for that particular game from User.xml.
     */
    public String getUserPreferences () {
        // TODO
    }

    /**
     * Returns all reviews for the given game, displaying the current user’s
     * review first (if it exists).
     */
    public String getReviews () {
        // TODO
    }

    /**
     * Returns all reviews for the given game, displaying the current user’s
     * review first (if it exists).
     */
    public int getAverageRatings () {
        // TODO
    }

    /**
     * Returns all reviews for the given game, displaying the current user’s
     * review first (if it exists).
     */
    public List<Integer> getRatings () {
        // TODO
    }

    /**
     * Checks if the game has a rating on file for the given user. If not, it
     * then calls addReview. If so, it then calls editReview.
     * 
     * @param review text for the review
     */
    public void setReview (String review) {
        // TODO
    }

    /**
     * Lets the user input a new review, and then adds it to the review list for
     * that game.
     * void editReview(String tag).
     * 
     * @param review text for the review
     */
    public void addReview (String review) {
        // TODO
    }

    /**
     * Gets the user’s review for the game, lets the user modify it, and then
     * replaces it in the review list for that game.
     * 
     * @param tag location of the previous review in the xml file
     * @param review text for the review
     */
    public void editReview (String tag, String review) {
        // TODO
    }

    /**
     * Checks if the game has a rating on file for the given user. If not, it
     * then calls addRating. If so, it then calls editRating.
     * 
     * @param rating rating to be added
     */
    public void setRating (int rating) {
        // TODO
    }

    /**
     * Lets the user choose a rating, and then adds it to the list of ratings
     * for that game.
     * 
     * @param rating rating to be added
     */
    public void addRating (int rating) {
        // TODO
    }

    /**
     * Gets the user’s rating for the game, lets the user modify it, and then
     * replaces it in the ratings list for that game.
     * 
     * @param tag location of the previous rating
     * @param rating rating to be added
     */
    public void setRating (String tag, int rating) {
        // TODO
    }
}
