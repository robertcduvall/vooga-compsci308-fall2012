package vooga.platformer.leveleditor.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import vooga.platformer.leveleditor.LevelFileReader;
import vooga.platformer.leveleditor.LevelFileWriter;
import vooga.platformer.leveleditor.Sprite;


/**
 * 
 * @author Grant Oakley
 * 
 */
public class LevelFileIOTest {

    private static final String XML_FILE_PATH = "src/vooga/platformer/leveleditor/test/testIO.xml";
    private static final String LEVEL_ID = "Level Name";
    private static final int LEVEL_WIDTH = 20;
    private static final int LEVEL_HEIGHT = 20;
    private static final String LEVEL_IMAGE = "level.jpg";

    private static final String TYPE = "enemy";
    private static final int X_POS = 1;
    private static final int Y_POS = 1;
    private static final int SPRITE_WIDTH = 1;
    private static final int SPRITE_HEIGHT = 1;
    private static final String SPRITE_IMAGE = "something.jpg";

    private static final String STRATEGY_TYPE_TAG = "type";
    private static final String STRATEGY_TYPE_VALUE = "movement";
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
        strategy.put(STRATEGY_TYPE_TAG, STRATEGY_TYPE_VALUE);
        strategy.put(STRATEGY_PARAM_TAG, STRATEGY_PARAM_VALUE);

        sprite = new Sprite(TYPE, X_POS, Y_POS, SPRITE_WIDTH, SPRITE_HEIGHT, SPRITE_IMAGE);
        sprite.addUpdateStrategy(strategy);
        sprite.addAttribute(SPRITE_ATTR_TAG, SPRITE_ATTR_VALUE);
        sprites.add(sprite);
        LevelFileWriter.writeLevel(XML_FILE_PATH, LEVEL_ID, LEVEL_WIDTH, LEVEL_HEIGHT, LEVEL_IMAGE,
                                   sprites);
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
    public void testGetSprites () throws Exception {
        Assert.assertEquals(sprites.size(), lfr.getSprites().size());
    }

    @Test
    public void testGetSpriteType () throws Exception {
        Assert.assertEquals(TYPE, getFirstSprite().getType());
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
    public void testGetSpriteImage () throws Exception {
        Assert.assertEquals(SPRITE_IMAGE, getFirstSprite().getImagePath());
    }

    @Test
    public void testGetUpdateStrategies () throws Exception {
        Assert.assertNotNull(getFirstSprite().getUpdateStrategies());
    }

    @Test
    public void testGetUpdateStrategyType () throws Exception {
        Assert.assertEquals(STRATEGY_TYPE_VALUE, getFirstStrategy().get(STRATEGY_TYPE_TAG));
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
