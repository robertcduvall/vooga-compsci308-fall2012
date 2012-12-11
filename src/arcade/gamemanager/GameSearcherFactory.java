package arcade.gamemanager;

import java.util.ArrayList;
import java.util.List;
import util.reflection.Reflection;

/**
 * a factory class that creates GameSearchers
 * @author Jei Min Yoo
 *
 */
public class GameSearcherFactory {
    private String filePath = "arcade.gamemanager.";
    private List<Game> myGames;

    /**
     * factory needs the list of games to search from.
     * @param games list of games to search from.
     */
    public GameSearcherFactory (List<Game> games) {
        myGames = games;
    }

    /**
     * creates searchers by search type.
     * @param searcherType type of searcher to be created.
     * @return a requested searcher
     */
    public GameSearcher createSearcher (String searcherType) {
        return (GameSearcher) Reflection.createInstance(filePath + searcherType, myGames);

    }
}