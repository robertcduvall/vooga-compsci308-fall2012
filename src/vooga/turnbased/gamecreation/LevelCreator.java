package vooga.turnbased.gamecreation;

import java.io.File;
import java.util.List;

/**
 * This class is designed to parse Xml data and create a level of 
 * our game from this information, creating character sprites and 
 * other objects that will either be interacted with or act as obstacles.
 * 
 * @author Mark Hoffman
 *
 */
public class LevelCreator {

    private XmlParser myXmlParser;
    
    public LevelCreator (File file) {
        myXmlParser = new XmlParser(file);
    }
    
    public List parseSprites () {
        
        return null;
    }
    
    private void validateXml () {
        // TODO: check if all required elements of a level are available in Xml
    }
}
