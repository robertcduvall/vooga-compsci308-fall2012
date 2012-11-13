package games.platformerdemo;

import java.awt.Dimension;
import vooga.platformer.level.Level;
import vooga.platformer.level.LevelFactory;

public class DemoLevelFactory extends LevelFactory {

    @Override
    public Level loadLevel (String levelName) {
        if (levelName.equals("level1")) {
            return new TestLevel(new Dimension(800, 600));
        }
        else {
            return null;
        }
    }
}
