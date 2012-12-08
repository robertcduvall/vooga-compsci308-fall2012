package games.spacebugs;

import vooga.shooter.level_editor.Level;
import games.spacebugs.LevelOne;

public class StartUp extends Level {

    private SpaceBugs myGame;

    public StartUp (SpaceBugs game) {
        super();
        myGame = game;
        setNextLevel(new LevelOne(myGame));
    }

    public void startLevel () {
    }

    @Override
    public boolean winningConditionsMet () {
        return myGame.getEnemies().isEmpty();
    }
}
