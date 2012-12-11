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
     * searches list of games by specific criteria. 
     * @param criteria
     * @return list of games searched by criteria
     */
    public abstract List<String> search(String criteria);
    
    
}
