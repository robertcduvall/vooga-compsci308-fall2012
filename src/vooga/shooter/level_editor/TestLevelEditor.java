package vooga.shooter.level_editor;

import java.awt.Dimension;
import java.awt.Point;
import javax.xml.transform.TransformerException;
import junit.framework.TestCase;
import util.xml.XmlUtilities;
import vooga.shooter.gameObjects.Enemy;


/**
 * Tests the loadLevel and storeLevel functions
 * of LevelFactory. The basic process is:
 * 
 * 1) make a level
 * 2) convert it to xml
 * 3) use the xml to make a new level (a copy)
 * 4) compare the new level (the copy) to the original
 * 
 * @author Alex Browne
 * 
 */
public class TestLevelEditor extends TestCase {

    public void testLevelStoreAndLoad () {

        // Instantiate a level class and add a single enemy to it...
        String projectPath = System.getProperty("user.dir");
        String imagePath = projectPath + "/src/vooga/shooter/images/alien.png";

        Level level = new Level(imagePath);
        Enemy enemy = new Enemy(new Point(0, 0), new Dimension(10, 10),
                new Dimension(100, 100), imagePath, new Point(0, 0), 10);
        level.addSprite(enemy);

        // Convert the level to an xml string using LevelFactory
        String originalString = null;
        try {
            originalString = XmlUtilities.getXmlAsString(LevelFactory
                    .storeLevel(level));
        }
        catch (TransformerException e) {
            // TODO Auto-generated catch block
            System.err.println("ERROR: " + e.getMessage());
            e.printStackTrace();
        }

        // Make a copy of the level by loading the xml data in LevelFactory
        Level newLevel = LevelFactory.loadLevel(LevelFactory.storeLevel(level));

        // Convert the copy to an xml string
        String newString = null;
        try {
            newString = XmlUtilities.getXmlAsString(LevelFactory
                    .storeLevel(level));
        }
        catch (TransformerException e) {
            // TODO Auto-generated catch block
            System.err.println("ERROR: " + e.getMessage());
            e.printStackTrace();
        }

        // make sure neither string is null
        assertTrue("The original level string is not null",
                originalString != null);
        assertTrue("The new level string is not null", newString != null);

        // if the two strings are identical, we can assume LevelFactory stored
        // and loaded the level correctly.
        assertEquals("The new level is an exact duplicate of the original",
                originalString, newString);

    }

}
