package vooga.platformer.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import vooga.platformer.leveleditor.Sprite;
import vooga.platformer.levelfileio.LevelFileReader;
import vooga.platformer.levelfileio.LevelFileWriter;
import vooga.platformer.levelfileio.XmlTags;


/**
 * 
 * @author Grant Oakley
 * 
 */
public class LevelFileIOTest {

    private static final String XML_FILE_PATH = "src/vooga/platformer/test/testIO.xml";
    private static final String LEVEL_TYPE = "vooga.platformer.someGameObjectClass";
    private static final String LEVEL_ID = "Level Name";
    private static final int LEVEL_WIDTH = 20;
    private static final int LEVEL_HEIGHT = 20;
    private static final String TEST_IMAGE = "src/vooga/platformer/test/testImage.jpg";
    private static final String COLLISION_CHECKER_TYPE = "vooga.platformer.someCollisionChecker";
    private static final String CAMERA_TYPE = "vooga.platformer.someCamera";

    private static final String TYPE = "vooga.platformer.enemy";
    private static final int X_POS = 1;
    private static final int Y_POS = 1;
    private static final int SPRITE_WIDTH = 1;
    private static final int SPRITE_HEIGHT = 1;
    private static final String SPRITE_ID = "myId";

    private static final String STRATEGY_CLASS = "vooga.platformer.movement";
    private static final String STRATEGY_PARAM_TAG = "distance";
    private static final String STRATEGY_PARAM_VALUE = String.valueOf(10);

    private static final String SPRITE_ATTR_TAG = "hp";
    private static final String SPRITE_ATTR_VALUE = String.valueOf(10);

    private LevelFileReader lfr;
    private Sprite sprite;
    private Map<String, String> strategy = new HashMap<String, String>();
    private ArrayList<Sprite> sprites = new ArrayList<Sprite>();

    @Before
    public void setUp () throws Exception {
        strategy.put(STRATEGY_PARAM_TAG, STRATEGY_PARAM_VALUE);

        sprite = new Sprite(TYPE, X_POS, Y_POS, SPRITE_WIDTH, SPRITE_HEIGHT, SPRITE_ID, TEST_IMAGE);
        sprite.addUpdateStrategy(STRATEGY_CLASS, strategy);
        sprite.addAttribute(SPRITE_ATTR_TAG, SPRITE_ATTR_VALUE);
        sprites.add(sprite);
        LevelFileWriter.writeLevel(XML_FILE_PATH, LEVEL_TYPE, LEVEL_ID, LEVEL_WIDTH, LEVEL_HEIGHT,
                                   TEST_IMAGE, sprites, COLLISION_CHECKER_TYPE, CAMERA_TYPE);
        lfr = new LevelFileReader(XML_FILE_PATH);
        Assert.assertNotNull(lfr);
    }

    @Test
    public void testGetLevelType () throws Exception {
        Assert.assertEquals(LEVEL_TYPE, lfr.getLevelType());
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
    public void testGetCollisionChecker () throws Exception {
        Assert.assertEquals(COLLISION_CHECKER_TYPE, lfr.getCollisionCheckerType());
    }

    @Test
    public void testGetCamera () throws Exception {
        Assert.assertEquals(CAMERA_TYPE, lfr.getCameraType());
    }

    @Test
    public void testGetSprites () throws Exception {
        Assert.assertEquals(sprites.size(), lfr.getSprites().size());
    }

    @Test
    public void testGetSpriteType () throws Exception {
        Assert.assertEquals(TYPE, getFirstSprite().getClassName());
    }

    @Test
    public void testGetSpriteX () throws Exception {
        Assert.assertEquals(X_POS, getFirstSprite().getX());
    }

    @Test
    public void testGetSpriteY () throws Exception {
        Assert.assertEquals(Y_POS, getFirstSprite().getY());
    }

    @Test
    public void testGetSpriteWidth () throws Exception {
        Assert.assertEquals(SPRITE_WIDTH, getFirstSprite().getWidth());
    }

    @Test
    public void testGetSpriteHeight () throws Exception {
        Assert.assertEquals(SPRITE_HEIGHT, getFirstSprite().getHeight());
    }
    
    @Test
    public void testGetSpriteID () throws Exception {
        Assert.assertEquals(SPRITE_ID, getFirstSprite().getID());
    }

    @Test
    public void testGetSpriteImage () throws Exception {
        Assert.assertEquals(TEST_IMAGE, getFirstSprite().getImagePath());
    }

    @Test
    public void testGetUpdateStrategies () throws Exception {
        Assert.assertNotNull(getFirstSprite().getUpdateStrategies());
    }

    @Test
    public void testGetUpdateStrategyType () throws Exception {
        // TODO type became class. Rewrite test.
        Assert.assertEquals(STRATEGY_CLASS, getFirstStrategy().get(XmlTags.CLASS_NAME));
    }

    @Test
    public void testGetUpdateStrategyParam () throws Exception {
        Assert.assertEquals(STRATEGY_PARAM_VALUE, getFirstStrategy().get(STRATEGY_PARAM_TAG));
    }

    @Test
    public void testGetSpriteAttr () throws Exception {
        Assert.assertEquals(SPRITE_ATTR_VALUE, getAttributes().get(SPRITE_ATTR_TAG));
    }

    private Sprite getFirstSprite () {
        return lfr.getSprites().iterator().next();
    }

    private Map<String, String> getFirstStrategy () {
        return getFirstSprite().getUpdateStrategies().iterator().next();
    }

    private Map<String, String> getAttributes () {
        return getFirstSprite().getAttributes();
    }
}
