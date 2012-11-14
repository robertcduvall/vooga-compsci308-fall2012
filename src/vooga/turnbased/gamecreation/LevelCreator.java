package vooga.turnbased.gamecreation;

import java.awt.Dimension;
import java.awt.Image;
import java.io.File;
import java.util.List;
import vooga.turnbased.sprites.Sprite;

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

    /**
     *
     * @param file XML file used to create the level, the constructor
     * parameters may change in the future.
     */
    public LevelCreator (File file) {
        myXmlParser = new XmlParser(file);
        validateXml();
    }

    /**
     *
     * @return
     */
    public Dimension parseDimension () {
        return null;
    }

    /**
     *
     * @return
     */
    public Image parseBackgroundImage () {
        return null;
    }

    /**
     *
     * @return
     */
    public List<Sprite> parseSprites () {
        return null;
    }

    /**
     * Used to check for all required elements of the XML file.
     */
    private void validateXml () {
        // TODO: check if all required elements of a level are available in Xml
    }
}
