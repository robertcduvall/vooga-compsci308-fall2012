package arcade.gamemanager;

import java.util.ArrayList;
import java.util.List;

/**
 * abstract class for game searcher. Game searcher returns list of games at specific requests.
 * @author Jei Min Yoo
 *
 */
public abstract class GameSearcher {

    private List<Game> myGames;
    
    /**
     * constructor for GameSearcher. game searcher has the full list of games.
     * @param games
     */
    public GameSearcher(List<Game> games) {
        myGames = games;
    }
    /**
     * returns the list of all available games.
     * 
     * @return list of all available games
     */
    public List<String> getGameList () {
        List<String> gameList = new ArrayList<String>();
        for (Game game : myGames) {
            gameList.add(game.getGameName());
        }
        return gameList;
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
            if (gm.getTags().contains(tag)) {
                gameList.add(gm.getGameName());
            }
        }
        return gameList;
    }
    
    
}
