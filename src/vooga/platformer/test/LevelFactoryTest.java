package vooga.platformer.test;

import java.io.File;
import java.util.ArrayList;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import util.reflection.Reflection;
import vooga.platformer.gameobject.GameObject;
import vooga.platformer.gameobject.Player;
import vooga.platformer.gameobject.StaticObject;
import vooga.platformer.level.LevelFactory;
import vooga.platformer.level.condition.Condition;
import vooga.platformer.level.condition.DefeatAllEnemiesCondition;
import vooga.platformer.level.levelplugin.LevelPlugin;
import vooga.platformer.level.levelplugin.SimpleBackgroundPainter;
import vooga.platformer.levelfileio.LevelFileReader;
import vooga.platformer.levelfileio.LevelFileWriter;


/**
 * 
 * @author Grant Oakley
 * 
 */
public class LevelFactoryTest {

    private static final String XML_FILE_PATH =
            "src/vooga/platformer/test/testLevelFactory.xml";
    private static final String LEVEL_ID = "Level Name";
    private static final int LEVEL_WIDTH = 500;
    private static final int LEVEL_HEIGHT = 500;
    private static final String TEST_IMAGE = "src/vooga/platformer/test/testImage.png";
    private static final String COLLISION_CHECKER_PATH = "src/vooga/platformer/collision/collisionEvents.xml";
    private static final String CAMERA_TYPE = "vooga.platformer.someCamera";

    private static final int X_POS = 1;
    private static final int Y_POS = 1;
    private static final int SPRITE_WIDTH = 15;
    private static final int SPRITE_HEIGHT = 15;
    private static final int SPRITE_ID = 0;

    private LevelFileReader lfr;
    private GameObject myPlayer;
    private ArrayList<GameObject> gameObjects = new ArrayList<GameObject>();
    private ArrayList<Condition> conditions = new ArrayList<Condition>();
    private ArrayList<LevelPlugin> plugins = new ArrayList<LevelPlugin>();

    @Before
    public void setUp () throws Exception {
        myPlayer =
                new Player(X_POS, Y_POS, SPRITE_WIDTH, SPRITE_HEIGHT, SPRITE_ID,
                                 new File(TEST_IMAGE));
        gameObjects.add(myPlayer);
        conditions.add(new DefeatAllEnemiesCondition());
        plugins.add(new SimpleBackgroundPainter(new File(TEST_IMAGE)));
        
        LevelFileWriter.writeLevel(XML_FILE_PATH, LEVEL_ID, LEVEL_WIDTH, LEVEL_HEIGHT, gameObjects,
                                   conditions, plugins, CAMERA_TYPE, COLLISION_CHECKER_PATH);
        lfr = new LevelFileReader(XML_FILE_PATH);
        Assert.assertNotNull(lfr);
    }

    @Test
    public void testLevelIsCreated () throws Exception {
        Assert.assertNotNull(LevelFactory.loadLevel(XML_FILE_PATH));
    }
}
