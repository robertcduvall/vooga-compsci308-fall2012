package vooga.platformer.test;

import java.io.File;
import java.util.ArrayList;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import vooga.platformer.gameobject.StaticObject;
import vooga.platformer.gameobject.GameObject;
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
public class LevelFileIOWithSerializationTest {

    private static final String XML_FILE_PATH =
            "src/vooga/platformer/test/testIOWithSerialziation.xml";
    private static final String LEVEL_ID = "Level Name";
    private static final int LEVEL_WIDTH = 20;
    private static final int LEVEL_HEIGHT = 20;
    private static final String TEST_IMAGE = "src/vooga/platformer/test/testImage.png";
    private static final String COLLISION_CHECKER_PATH = "someCollisionCheckerPath";
    private static final String CAMERA_TYPE = "vooga.platformer.someCamera";

    private static final int X_POS = 1;
    private static final int Y_POS = 1;
    private static final int SPRITE_WIDTH = 1;
    private static final int SPRITE_HEIGHT = 1;
    private static final int SPRITE_ID = 0;

    private LevelFileReader lfr;
    private GameObject myStaticObj;
    private ArrayList<GameObject> gameObjects = new ArrayList<GameObject>();
    private ArrayList<Condition> conditions = new ArrayList<Condition>();
    private ArrayList<LevelPlugin> plugins = new ArrayList<LevelPlugin>();

    @Before
    public void setUp () throws Exception {
        
        myStaticObj =
                new StaticObject(X_POS, Y_POS, SPRITE_WIDTH, SPRITE_HEIGHT, SPRITE_ID,
                                 new File(TEST_IMAGE));
        gameObjects.add(myStaticObj);
        conditions.add(new DefeatAllEnemiesCondition());
        plugins.add(new SimpleBackgroundPainter(new File(TEST_IMAGE)));
        
        LevelFileWriter.writeLevel(XML_FILE_PATH, LEVEL_ID, LEVEL_WIDTH, LEVEL_HEIGHT, gameObjects,
                                   conditions, plugins, CAMERA_TYPE, COLLISION_CHECKER_PATH);
        lfr = new LevelFileReader(XML_FILE_PATH);
        Assert.assertNotNull(lfr);
    }

    private GameObject getFirstGameObject () {
        ArrayList<GameObject> go = new ArrayList<GameObject>(lfr.getGameObjects());
        return go.get(0);
    }

    @Test
    public void testgetLevelID () throws Exception {
        Assert.assertEquals(LEVEL_ID, lfr.getLevelName());
    }

    @Test
    public void testGetWidth () throws Exception {
        Assert.assertEquals(LEVEL_WIDTH, lfr.getWidth());
    }

    @Test
    public void testGetHeight () throws Exception {
        Assert.assertEquals(LEVEL_WIDTH, lfr.getHeight());
    }
    
    @Test
    public void testGetCollisionChecker () throws Exception {
        Assert.assertEquals(COLLISION_CHECKER_PATH, lfr.getCollisionCheckerPath());
    }

    @Test
    public void testGetCamera () throws Exception {
        Assert.assertEquals(CAMERA_TYPE, lfr.getCameraType());
    }

    @Test
    public void testGetGameObjects () throws Exception {
        Assert.assertEquals(1, lfr.getGameObjects().size());
    }

    @Test
    public void testGameObjectX () throws Exception {
        Assert.assertEquals(myStaticObj.getX(), getFirstGameObject().getX());
    }
    
    @Test
    public void testGetConditions () {
        Assert.assertNotNull(lfr.getConditions());
    }
    
    @Test
    public void testGetLevelPlugins () {
        Assert.assertNotNull(lfr.getLevelPlugins());
    }

}
