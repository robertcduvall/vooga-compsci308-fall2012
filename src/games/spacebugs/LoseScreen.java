package games.spacebugs;

import vooga.shooter.gameplay.Game;
import vooga.shooter.level_editor.Level;

public class LoseScreen extends Level{

    private SpaceBugs myGame;
    private Level myNextLevel;

    public LoseScreen (SpaceBugs game) {
        super();
        myGame = game;
        setNextLevel(null);
    }

    public void startLevel () {
           // Display something sad
    }

    @Override
    public boolean winningConditionsMet () {
        return false;
    }
}