package vooga.platformer.gameobject;

import java.io.File;
import java.io.IOException;


public class LevelGoalZone extends GameObject {

    private static final long serialVersionUID = 1L;
    private String myNextLevel;

    public LevelGoalZone (double inX, double inY, double inWidth, double inHeight, int inId,
                          File defaultImageFile, String nextLevel) throws IOException {
        super(inX, inY, inWidth, inHeight, inId, defaultImageFile);
        myNextLevel = nextLevel;
    }

    public String getNextLevelPath () {
        return myNextLevel;
    }

}
