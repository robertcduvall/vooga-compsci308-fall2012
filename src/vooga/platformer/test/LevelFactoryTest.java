package vooga.platformer.test;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import vooga.platformer.level.LevelFactory;
import vooga.platformer.leveleditor.LevelFileReader;
import vooga.platformer.leveleditor.LevelFileWriter;
import vooga.platformer.leveleditor.Sprite;


/**
 * 
 * @author Grant Oakley
 * 
 */
public class LevelFactoryTest {

    private static final String XML_FILE_PATH = "src/vooga/platformer/test/testLevelFactory.xml";

    @Test
    public void testLevelIsCreated () throws Exception {
        Assert.assertNotNull(LevelFactory.loadLevel(XML_FILE_PATH));
    }
}
