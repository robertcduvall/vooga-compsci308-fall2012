package vooga.platformer.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import vooga.platformer.gameobject.StaticObject;
import vooga.platformer.gameobject.GameObject;
import vooga.platformer.levelfileio.LevelFileReader;
import vooga.platformer.levelfileio.LevelFileWriter;


/**
 * 
 * @author Grant Oakley
 * 
 */
public class LeveFileIOWithSerializationTest {

    private static final String XML_FILE_PATH =
            "src/vooga/platformer/test/testIOWithSerialziation.xml";
    private static final String LEVEL_ID = "Level Name";
    private static final int LEVEL_WIDTH = 20;
    private static final int LEVEL_HEIGHT = 20;
    private static final String TEST_IMAGE = "src/vooga/platformer/test/testImage.jpg";
    private static final String COLLISION_CHECKER_TYPE = "vooga.platformer.someCollisionChecker";
    private static final String CAMERA_TYPE = "vooga.platformer.someCamera";

    private static final int X_POS = 1;
    private static final int Y_POS = 1;
    private static final int SPRITE_WIDTH = 1;
    private static final int SPRITE_HEIGHT = 1;
    private static final String SPRITE_ID = String.valueOf(0);

    private static final String STRATEGY_CLASS = "vooga.platformer.movement";
    private static final String STRATEGY_PARAM_TAG = "distance";
    private static final String STRATEGY_PARAM_VALUE = String.valueOf(10);

    private static final String SPRITE_ATTR_TAG = "hp";
    private static final String SPRITE_ATTR_VALUE = String.valueOf(10);

    private LevelFileReader lfr;
    private GameObject brick;
    private Map<String, String> strategy = new HashMap<String, String>();
    private ArrayList<GameObject> gameObjects = new ArrayList<GameObject>();

    @Before
    public void setUp () throws Exception {
        strategy.put(STRATEGY_PARAM_TAG, STRATEGY_PARAM_VALUE);

        brick =
                new StaticObject("x=" + X_POS + ",y=" + Y_POS + ",width=" + SPRITE_WIDTH + ",height=" +
                          SPRITE_HEIGHT + ",imagePath=" + TEST_IMAGE + ",id=" + SPRITE_ID);
        gameObjects.add(brick);
        LevelFileWriter.writeLevel(XML_FILE_PATH, LEVEL_ID, LEVEL_WIDTH, LEVEL_HEIGHT, TEST_IMAGE,
                                   gameObjects, CAMERA_TYPE);
        lfr = new LevelFileReader(XML_FILE_PATH);
        Assert.assertNotNull(lfr);
    }

    @Test
    public void testgetLevelID () throws Exception {
        Assert.assertEquals(LEVEL_ID, lfr.getLevelID());
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
    public void testGetBackgroundImage () throws Exception {
        Assert.assertNotNull(lfr.getBackgroundImage());
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
        Assert.assertEquals(brick.getX(), getFirstGameObject().getX());
    }

    private GameObject getFirstGameObject () {
        ArrayList<GameObject> go = new ArrayList<GameObject>(lfr.getGameObjects());
        return go.get(0);
    }

    // TODO test update more parameters (update Strategy)
}
