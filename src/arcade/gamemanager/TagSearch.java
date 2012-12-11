package arcade.gamemanager;

import java.util.ArrayList;
import java.util.List;

/**
 * a GameSearcher that searches by tags.
 * @author Jei Min Yoo
 *
 */
public class TagSearch extends GameSearcher {
    private List<Game> myGames;

    public TagSearch (List<Game> games) {
        super(games);
        myGames = games;
    }

    @Override
    public List<String> search (String rating) {
        List<String> gameList = new ArrayList<String>();
        for (Game gm : myGames) {
            if (gm.getTags().contains(rating)) {
                gameList.add(gm.getGameName());
            }
        }
        return gameList;
    }

}
