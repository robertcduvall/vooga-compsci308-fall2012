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
public class LevelEditor extends Editor {

    private Document myXmlDocument;
    private Element myRootElement;
    private String myFileName;

    /**
     * Instantiates a LevelEditor for modifying an Xml document.
     *
     * @param xmlDocument Already formed Xml document for modifying
     * @param fileName File name (with path) of Xml document
     */
    public LevelEditor(Document xmlDocument, String fileName) {
        super(xmlDocument);
        myXmlDocument = xmlDocument;
        myFileName = fileName;
    }

    /**
     * Creates a LevelEditor for a new Xml document.
     * 
     * @param fileName File name (with path) of Xml document
     */
    public LevelEditor(String fileName) {
        myXmlDocument = XmlUtilities.makeDocument();
        myRootElement = myXmlDocument.createElement("level");
        myXmlDocument.appendChild(myRootElement);
        myFileName = fileName;
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
     * @param id The id number to replace the former id
     */
    public void modifyLevelId (Number id) {
        Element levelId = XmlUtilities.getElement(myRootElement, "levelid");
        XmlUtilities.setContent(levelId, id.toString());
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
     * @param width New map dimension width
     * @param height New map dimension height
     */
    public void modifyDimensionTag(Number width, Number height) {
        modifyDimension("dimension", width, height);
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
     * @param width New camera dimension width
     * @param height New camera dimension height
     */
    public void modifyCameraDimension (Number width, Number height) {
        modifyDimension("cameraDimension", width, height);
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
     * @param imagePath New Image Path
     */
    public void modifyBackgroundImage (String imagePath) {
        Element background = XmlUtilities.getElement(myRootElement, "backgroundImage");
        XmlUtilities.setContent(background, imagePath);
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
     * @param x New x-coordinate for player entry
     * @param y New y-coordinate for player entry
     */
    public void modifyPlayerEntryPoints (Number x, Number y) {
        Element playerEntry = XmlUtilities.getElement(myRootElement, "player_entry_point");
        Element xElement = XmlUtilities.getElement(playerEntry, "x");
        Element yElement = XmlUtilities.getElement(playerEntry, "y");
        XmlUtilities.setContent(xElement, x.toString());
        XmlUtilities.setContent(yElement, y.toString());
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

    private void addDimension (String tagName, Number width, Number height) {
        Element dimension = XmlUtilities.appendElement(myXmlDocument, myRootElement, tagName);
        XmlUtilities.appendElement(myXmlDocument, dimension, "width", width.toString());
        XmlUtilities.appendElement(myXmlDocument, dimension, "height", height.toString());
    }

    private void modifyDimension (String s, Number width, Number height) {
        Element dimension = XmlUtilities.getElement(myRootElement, s);
        Element w = XmlUtilities.getElement(dimension, "width");
        Element h = XmlUtilities.getElement(dimension, "height");
        XmlUtilities.setContent(w, width.toString());
        XmlUtilities.setContent(h, height.toString());
    }

    public void saveXmlDocument () {
        XmlUtilities.write(myXmlDocument, myFileName);
    }

    public Document getXmlDocument () {
        return myXmlDocument;
    }
}
