package games.AdventureRPG;

import java.awt.Image;
import java.util.List;

import vooga.turnbased.gui.GameWindow;
import arcade.IArcadeGame;
import arcade.gamemanager.GameSaver;

/**
 * 
 * @author Michael Elgart
 *
 * To Professor Duvall:
 * This game was built to show off the dynamic abilities of the BattleMode for the RPG game.
 * It was expressly not built to show up different pictures as there aren't many, my apologies. 
 * You will notice that the XML file is very similar to the Demo game already made, with the
 * only important changes being images and the Stats passed into BattleObjects.  It also has
 * its own class that implements BattleObject, called AdventureMob.  And that's it! With 
 * only minor changes to the XML file, and with the implementation of BattleObject, an 
 * entirely new set of game mechanics has been constructed. This does not rely on anything
 * else being changed.  And for further interest, if you look at Jenni's game, she has 
 * implemented her own BattleObject and then added new stats to the XML and yet our Battle
 * mechanics are completely different, but still built using the same parent BattleObject 
 * class and the identical game framework.  Pretty awesome!
 */
public class AdventureGame implements IArcadeGame {
	private final String myFilePath = "src/games/AdventureRPG/AdventureRPGMap.xml";
    private static final int WIDTH = 600;
    private static final int HEIGHT = 450;
    
    @Override
    public void runGame (String userPreferences, GameSaver s) {
        GameWindow myGameWindow = new GameWindow("AdventureLand", "GameSetting", WIDTH, HEIGHT, myFilePath);

    }

    @Override
    public List<Image> getScreenshots () {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Image getMainImage () {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getDescription () {
        return "An adventure RPG that demonstrates the flexibility of the RPG's framework battle aspects";
    }

    @Override
    public String getName () {
        return "Adventure Land";
    }

}
