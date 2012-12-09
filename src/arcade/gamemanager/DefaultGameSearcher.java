package arcade.gamemanager;

import java.util.ArrayList;
import java.util.List;


public class DefaultGameSearcher extends GameSearcher {
    private List<Game> myGames;
    public DefaultGameSearcher (List<Game> games) {
        super(games);
        myGames = games;
    }
    
    public List<String> getGameListByMinAverageRating(int rating) {
        List<String> gameList = new ArrayList<String>();
        for (Game g : myGames) {
            if (g.getAverageRating()>=rating) {
                gameList.add(g.getGameName());
            }
        }
        return gameList;
    }
    
}
