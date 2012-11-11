package vooga.platformer.leveleditor.test;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import vooga.platformer.leveleditor.LevelFileReader;

/**
 * @author Zach Michaelov
 */
public class LevelFileReaderTest {

    private LevelFileReader lfr;

    @Before
    public void setUp() throws Exception {
        lfr = new LevelFileReader("src/vooga/platformer/data/test.xml");
        Assert.assertNotNull(lfr);
    }

    @Test
    public void testGetLevelID() throws Exception {
        Assert.assertEquals(1234, lfr.getLevelID());
    }

    @Test
    public void testGetWidth() throws Exception {
        Assert.assertEquals(3, lfr.getWidth());
    }

    @Test
    public void testGetHeight() throws Exception {
        Assert.assertEquals(5, lfr.getHeight());
    }

    @Test
    public void testGetBackgroundImage() throws Exception {
        Assert.assertNotNull(lfr.getBackgroundImage());
    }
}
