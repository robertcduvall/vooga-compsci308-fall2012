package vooga.shooter.level_editor;

import util.xml.XmlWriter;

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
        XmlWriter.writeXML(l.pack(), file_path);
    }
    
}
