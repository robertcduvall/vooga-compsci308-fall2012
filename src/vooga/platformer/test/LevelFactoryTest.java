package vooga.platformer.test;

import games.platformerdemo.Enemy;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import util.reflection.Reflection;
import vooga.platformer.level.LevelFactory;
import vooga.platformer.leveleditor.Sprite;
import vooga.platformer.levelfileio.LevelFileReader;
import vooga.platformer.levelfileio.LevelFileWriter;


/**
 * 
 * @author Grant Oakley
 * 
 */
public class LevelFactoryTest {

    private static final String XML_FILE_PATH = "src/vooga/platformer/test/testLevelFactory.xml";

    @Test
    public void testReflection () throws Exception {
        Assert.assertNotNull(Reflection.createInstance("games.platformerdemo.Player",
                                                       "x=1,y=1,width=1,height=1"));
    }

    @Test
    public void testLevelIsCreated () throws Exception {
        Assert.assertNotNull(LevelFactory.loadLevel(XML_FILE_PATH, "games.platformerdemo.Player"));
    }
}
