package arcade.gamemanager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import arcade.IArcadeGame;
import arcade.utility.ReadWriter;


public class GameCenter {

    /**
     * Returns a list of all games from the xml file.
     */
    public List<String> initialize() {
        File f = new File("arcade.database/game.xml");
        List<String> query = new ArrayList<String>();
        return ReadWriter.search(f, query);
    }
}
