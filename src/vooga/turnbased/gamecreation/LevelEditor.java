package vooga.turnbased.gamecreation;

import java.util.Map;
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
        XmlUtilities.appendElement(myXmlDocument, myRootElement,
                "backgroundImage", imagePath);
    }

    /**
     *
     * @param x X-coordinate player entry point
     * @param y Y-coordinate player entry point
     */
    public void addPlayerEntryPoints (Number x, Number y) {
        String tagName = "player_entry_point";
        XmlUtilities.appendElement(myXmlDocument, myRootElement, tagName);
        Element dimension = XmlUtilities.getElement(myRootElement, tagName);
        XmlUtilities.appendElement(myXmlDocument, dimension, "x", x.toString());
        XmlUtilities.appendElement(myXmlDocument, dimension, "y", y.toString());
    }

    /**
     * 
     * @return The new Sprite element that was just added to the Xml Document
     */
    public Element addSprite () {
        return XmlUtilities.appendElement(myXmlDocument, myRootElement, "sprite");
    }

    /**
     * Creates a map object and adds it to a sprite.
     * 
     * @param s Sprite Element to which the mapObject is added
     * @param mapClass Specific concrete class for the map object
     * @param event Event for this map object
     * @param x Map x-coordinate
     * @param y Map y-coordinate
     * @param imagePath Path to the Map Image
     */
    public void addMapObject (Element s, String mapClass, String event, Number x, Number y,
            String imagePath) {
        Element map = XmlUtilities.appendElement(myXmlDocument, s, "map");
        XmlUtilities.appendElement(myXmlDocument, map, "class", mapClass);
        XmlUtilities.appendElement(myXmlDocument, map, "event", event);
        Element location = XmlUtilities.appendElement(myXmlDocument, map, "location");
        XmlUtilities.appendElement(myXmlDocument, location, "x", x.toString());
        XmlUtilities.appendElement(myXmlDocument, location, "y", y.toString());
        XmlUtilities.appendElement(myXmlDocument, map, "image", imagePath);
    }

    /**
     * Creates a battle object and adds it to a sprite.
     * 
     * @param s Sprite to which the battleObject is added
     * @param battleClass Specific concrete class for the battle object
     * @param event Event for the battle object
     * @param stats map for each battle attribute and value (health, attack, etc.)
     * @param name Name of the battle object (i.e. Pikachu)
     * @param imagePath Path to the Battle Image
     */
    public void addBattleObject (Element s, String battleClass, String event,
            Map<String, Number> stats, String name, String imagePath) {
        Element battle = XmlUtilities.appendElement(myXmlDocument, s, "battle");
        XmlUtilities.appendElement(myXmlDocument, battle, "class", battleClass);
        XmlUtilities.appendElement(myXmlDocument, battle, "event", event);
        Element statsElement = XmlUtilities.appendElement(myXmlDocument, battle, "stats");
        addStatsMapToXml(statsElement, stats);
        XmlUtilities.appendElement(myXmlDocument, battle, "name", name);
        XmlUtilities.appendElement(myXmlDocument, battle, "image", imagePath);
    }

    private void addStatsMapToXml (Element e, Map<String, Number> m) {
        for (String key : m.keySet()) {
            XmlUtilities.appendElement(myXmlDocument, e, key, m.get(key).toString());
        }
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
