package vooga.platformer.leveleditor.test;

import java.util.ArrayList;
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

    private LevelFileReader lfr;
    private ArrayList<Sprite> sprites = new ArrayList<Sprite>();

    @Before
    public void setUp () throws Exception {
        sprites.add(new Sprite(TYPE, X_POS, Y_POS, SPRITE_WIDTH, SPRITE_HEIGHT, SPRITE_IMAGE));
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
}
