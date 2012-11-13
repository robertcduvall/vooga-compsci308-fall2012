package games.platformerdemo;

import java.awt.Dimension;
import vooga.platformer.level.Level;
import vooga.platformer.level.LevelFactory;

public class DemoLevelFactory extends LevelFactory {

    @Override
    public Level loadLevel (String levelName) {
        if (levelName.equals("level1")) {
            Level currLevel = new TestLevel(new Dimension(800, 600));
            currLevel.addGameObject(new Player("x=4,y=5"));
            return currLevel;
        }
        else {
            return null;
        }
    }
}
