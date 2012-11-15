package vooga.turnbased.gamecreation;

import java.awt.Dimension;
import java.awt.Image;
import java.io.File;
import java.util.List;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import util.xml.XmlParser;
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
    private Element myDocumentElement;

    /**
     *
     * @param file XML file used to create the level, the constructor
     * parameters may change in the future.
     */
    public LevelCreator (File file) {
        myXmlParser = new XmlParser(file);
        validateXml();
        myDocumentElement = myXmlParser.getDocumentElement();
    }

    /**
     *
     * @return The Dimension of the Level
     */
    public Dimension parseDimension () {
        NodeList dimensionList = myXmlParser.getElementsByName(
                myDocumentElement, "dimension");
        Element dimension = (Element) dimensionList.item(0);
        int width = myXmlParser.getIntContent(dimension, "width");
        int height = myXmlParser.getIntContent(dimension, "height");
        return new Dimension(width, height);
    }

    /**
     *
     * @return Background Image of the Level
     */
    public Image parseBackgroundImage () {
        return myXmlParser.getImageContent(
                myDocumentElement, "backgroundImage");
    }

    /**
     *
     * @return List of Sprites in the Level
     */
    public List<Sprite> parseSprites () {
        return null;
    }

    /**
     * 
     * @return The Document Element from the Xml file
     */
    public Element getDocumentElement () {
        return myDocumentElement;
    }

    /**
     * Used to check for all required elements of the XML file.
     */
    private void validateXml () {
        // Empty until decided what is required of Xml
    }
}
