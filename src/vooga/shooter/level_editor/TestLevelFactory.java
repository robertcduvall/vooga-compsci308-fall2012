package vooga.shooter.level_editor;

import java.awt.Dimension;
import java.awt.Point;
import javax.xml.transform.TransformerException;
import junit.framework.TestCase;
import util.xml.XmlUtilities;
import vooga.shooter.gameObjects.Enemy;
import vooga.shooter.gameObjects.Player;
import vooga.shooter.gameObjects.intelligence.BounceAI;
import vooga.shooter.gameObjects.intelligence.RandomAI;


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
public class TestLevelFactory extends TestCase {

    public void testLevelStoreAndLoad () {

        // Instantiate a level class and add a single enemy to it...
        String imagePath = "vooga/shooter/images/alien.png";

        Level level = new Level(imagePath);
        
        // create the player
        Player player = new Player(new Point(0, 0), new Dimension(10, 10),
                new Dimension(100, 100), imagePath, new Point(0, 0), 10);
        level.setPlayer(player);
        
        
        // add one enemy
        Enemy enemy1 = new Enemy(new Point(0, 0), new Dimension(10, 10),
                new Dimension(100, 100), imagePath, new Point(0, 0), 10);
        enemy1.setAI(new RandomAI(enemy1, player));
        level.addSprite(enemy1);
        
        // add another enemy
        Enemy enemy2 = new Enemy(new Point(-10, 10), new Dimension(10, 10),
                new Dimension(90, 90), imagePath, new Point(0, 0), 12);
        enemy2.setAI(new BounceAI(enemy2, player));
        level.addSprite(enemy2);
        

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
                    .storeLevel(newLevel));
        }
        catch (TransformerException e) {
            // TODO Auto-generated catch block
            System.err.println("ERROR: " + e.getMessage());
            e.printStackTrace();
        }
        
        System.out.println("original: " + originalString);
        System.out.println("copy: " + newString);

        // make sure neither string is null
        assertTrue("The original level string is not null",
                originalString != null);
        assertTrue("The new level string is not null", newString != null);

        // if the two strings are identical, we can assume LevelFactory stored
        // and loaded the level correctly.
        assertTrue("The new level is an exact duplicate of the original",
                originalString.equals(newString));
    }

}
