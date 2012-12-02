package vooga.turnbased.gamecreation;

import java.util.List;
import java.util.Map;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import util.xml.XmlUtilities;

/**
 * This class is used to edit or create a new Xml player document that 
 * could then be used in game play.
 *
 * @author Mark Hoffman
 */
public class PlayerEditor extends Editor {
    private Document myXmlDocument;
    private Element myRootElement;
    private String myFileName;

    /**
     * Instantiates a LevelEditor for modifying an Xml document.
     *
     * @param xmlDocument Already formed Xml document for modifying
     * @param fileName File name (with path) of Xml document
     */
    public PlayerEditor(Document xmlDocument, String fileName) {
        super(xmlDocument);
        myXmlDocument = xmlDocument;
        myFileName = fileName;
    }

    /**
     * Creates a LevelEditor for a new Xml document.
     * 
     * @param fileName File name (with path) of Xml document
     */
    public PlayerEditor(String fileName) {
        myXmlDocument = XmlUtilities.makeDocument();
        myRootElement = myXmlDocument.createElement("players");
        myXmlDocument.appendChild(myRootElement);
        myFileName = fileName;
    }

    /**
     * 
     * @return The new Player element that was just added to the Xml Document
     */
    public Element addPlayer () {
        return XmlUtilities.appendElement(myXmlDocument, myRootElement, "player");
    }

    /**
     * Creates a map object and adds it to a sprite.
     * 
     * @param s Sprite Element to which the mapObject is added
     * @param mapClass Specific concrete class for the map object
     * @param event Event for this map object
     * @param x Map x-coordinate
     * @param y Map y-coordinate
     * @param imagePaths Paths to the Map Image (maps <source, direction>)
     */
    public void addPlayerMapObject (Element s, String mapClass, String event, Number x, Number y,
            Map<String, String> imagePaths) {
        Element map = XmlUtilities.appendElement(myXmlDocument, s, "map");
        XmlUtilities.appendElement(myXmlDocument, map, "class", mapClass);
        XmlUtilities.appendElement(myXmlDocument, map, "event", event);
        Element location = XmlUtilities.appendElement(myXmlDocument, map, "location");
        XmlUtilities.appendElement(myXmlDocument, location, "x", x.toString());
        XmlUtilities.appendElement(myXmlDocument, location, "y", y.toString());
        for (String key : imagePaths.keySet()) {
            Element image = XmlUtilities.appendElement(myXmlDocument, map, "image");
            XmlUtilities.appendElement(myXmlDocument, image, "source", key);
            XmlUtilities.appendElement(myXmlDocument, image, "direction", imagePaths.get(key));
        }
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
        for (String key : stats.keySet()) {
            XmlUtilities.appendElement(myXmlDocument, statsElement, key,
                    stats.get(key).toString());
        }
        XmlUtilities.appendElement(myXmlDocument, battle, "name", name);
        XmlUtilities.appendElement(myXmlDocument, battle, "image", imagePath);
    }

    /**
     * @param s sprite element for which the battle stats need altering
     * @param stats Map of battle attributes and their values
     * @param name Name of the battle object that stat changes in
     */
    public void modifyBattleStats (Element s, Map<String, Number> stats, String name) {
        List<Element> battle = (List<Element>) XmlUtilities.getElements(s, "battle");
        Element statsElement = null;
        for (Element current : battle) {
            String currentName = XmlUtilities.getChildContent(current, "name");
            if (currentName.equals(name)) {
                statsElement = current;
            }
        }
        for (String key : stats.keySet()) {
            Element current = XmlUtilities.getElement(statsElement, key);
            XmlUtilities.setContent(current, stats.get(key).toString());
        }
    }

    public void saveXmlDocument () {
        XmlUtilities.write(myXmlDocument, myFileName);
    }

    public Document getXmlDocument () {
        return myXmlDocument;
    }
}
