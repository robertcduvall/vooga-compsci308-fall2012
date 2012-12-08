package games.spacebugs;

import vooga.shooter.gameplay.Game;
import vooga.shooter.level_editor.Level;

public class WinScreen extends Level{

    private SpaceBugs myGame;
    private Level myNextLevel;

    public WinScreen (SpaceBugs game) {
        super();
        myGame = game;
        setNextLevel(null);
    }

    public void startLevel () {
           // Display something happy
    }

    @Override
    public boolean winningConditionsMet () {
        return false;
    }
}
