package vooga.shooter.level_editor;

import java.awt.Dimension;
import java.awt.Point;
import javax.xml.transform.TransformerException;
import util.xml.XmlUtilities;
import vooga.shooter.gameObjects.Enemy;

/**
 * This is only a temporary test and should probably be
 * removed in the final project or replaced with a more
 * robust JUnit test.
 * 
 * @author Alex
 *
 */
public class TestLevelEditor {
    
    public static void main(String args[]) {
        new TestLevelEditor();
    }
    
    public TestLevelEditor() {
        String project_path = System.getProperty("user.dir");
        String image_path = project_path + "/src/vooga/shooter/images/alien.png";
        String file_path = project_path + "/src/vooga/shooter/levels/level1.xml";
        Level l = new Level(image_path);
        XmlUtilities.write(l.pack(), file_path);
        
        // Point position, Dimension size, Dimension bounds, Image image, int health
        Enemy enemy = new Enemy(
                new Point(0,0),
                new Dimension(10,10),
                new Dimension(100,100),
                image_path,
                new Point(0,0),
                10
                
                );
        
        try {
            System.out.println(XmlUtilities.getXmlAsString(enemy.pack()));
            System.out.println("done");
        }
        catch (TransformerException e) {
            // TODO Auto-generated catch block
            System.err.println("ERROR: " + e.getMessage());
            e.printStackTrace();
        }
        
        Enemy newEnemy = Enemy.unpack(enemy.pack());
        
        try {
            System.out.println(XmlUtilities.getXmlAsString(newEnemy.pack()));
            System.out.println("done");
        }
        catch (TransformerException e) {
            // TODO Auto-generated catch block
            System.err.println("ERROR: " + e.getMessage());
            e.printStackTrace();
        }
        
    }
    
}
