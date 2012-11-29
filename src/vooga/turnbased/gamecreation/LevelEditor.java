package vooga.turnbased.gamecreation;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import util.xml.XmlUtilities;

/**
 * This class is used to edit or create a new Xml level that 
 * could then be used in game play.
 *
 * @author Mark Hoffman
 */
public class LevelEditor {

    private Document myXmlDocument;
    private String myFileName;
    private Element myRootElement;

    /**
     * Instantiates a LevelEditor for modifying an Xml document.
     *
     * @param xmlDocument Already formed Xml document for modifying
     * @param fileName File name (with path) of Xml document
     */
    public LevelEditor(Document xmlDocument, String fileName) {
        myXmlDocument = xmlDocument;
        myFileName = fileName;
    }

    /**
     * Creates a LevelEditor for a new Xml document.
     */
    public LevelEditor() {
        myXmlDocument = XmlUtilities.makeDocument();
        myRootElement = myXmlDocument.createElement("level");
        myXmlDocument.appendChild(myRootElement);
    }

    /**
     * 
     * @param id The id number for the level Xml
     */
    public void addLevelId (Number id) {
        XmlUtilities.appendElement(myXmlDocument, myRootElement, "levelid", id.toString());
    }

    /**
     * 
     * @param width Describes width of the Map dimension
     * @param height Describes height of the Map dimension
     */
    public void addDimensionTag(Number width, Number height) {
        addDimension("dimension", width, height);
    }

    /**
     * 
     * @param width Describes width of the Camera dimension
     * @param height Describes height of the Camera dimension
     */
    public void addCameraDimension (Number width, Number height) {
        addDimension("camaraDimension", width, height);
    }

    /**
     * 
     * @param imagePath Path to the Background image of the level
     */
    public void addBackgroundImage (String imagePath) {
    }

    /**
     *
     * @param x X-coordinate player entry point
     * @param y Y-coordinate player entry point
     */
    public void addPlayerEntryPoints (Number x, Number y) {
    }

    /**
     * 
     */
    public void addSprite () {
    }

    /**
     * 
     * @param pathName Path from working directory (begin with /src folder)
     */
    public void saveXmlDocument(String pathName) {
        String dir = System.getProperty("user.dir");
        XmlUtilities.write(myXmlDocument, dir + pathName);
    }

    /**
     * 
     * @return Xml Document
     */
    public Document getXmlDocument () {
        return myXmlDocument;
    }

    private void addDimension (String tagName, Number width, Number height) {
        XmlUtilities.appendElement(myXmlDocument, myRootElement, tagName);
        Element dimension = XmlUtilities.getElement(myRootElement, tagName);
        XmlUtilities.appendElement(myXmlDocument, dimension, "width", width.toString());
        XmlUtilities.appendElement(myXmlDocument, dimension, "height", height.toString());
    }

}
