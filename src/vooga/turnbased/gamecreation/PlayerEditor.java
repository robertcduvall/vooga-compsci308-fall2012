package vooga.turnbased.gamecreation;

import java.util.ArrayList;
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
     * @param player Object element to which the mapObject is added
     * @param modes Comma-separated modes this object is in
     * @param mapClass Specific concrete class for the map object
     * @param condition Event for this map object
     * @param x Map x-coordinate
     * @param y Map y-coordinate
     * @param imagePaths Paths to the Map Image (maps <source, direction>)
     */
    public void addMapObject (Element player, String modes, String mapClass, String condition,
            Number x, Number y, Map<String, String> imagePaths) {
        Element object = XmlUtilities.appendElement(myXmlDocument, player, "object");
        addMapObject(modes, mapClass, condition, x, y, imagePaths, object);
    }

    /**
     * Creates a map object and adds it to a sprite.
     * 
     * @param player Object element to which the mapObject is added
     * @param createOn When to create the object
     * @param modes Comma-separated modes this object is in
     * @param mapClass Specific concrete class for the map object
     * @param condition Event for this map object
     * @param x Map x-coordinate
     * @param y Map y-coordinate
     * @param imagePaths Paths to the Map Image (maps <source, direction>)
     */
    public void addMapObject (Element player, String createOn, String modes, String mapClass,
            String condition, Number x, Number y, Map<String, String> imagePaths) {
        Element object = XmlUtilities.appendElement(myXmlDocument, player, "object");
        XmlUtilities.appendElement(myXmlDocument, object, "createOn", createOn);
        addMapObject(modes, mapClass, condition, x, y, imagePaths, object);
    }

    private void addMapObject (String modes, String mapClass, String condition,
            Number x, Number y, Map<String, String> imagePaths, Element object) {
        XmlUtilities.appendElement(myXmlDocument, object, "modes", modes);
        XmlUtilities.appendElement(myXmlDocument, object, "class", mapClass);
        XmlUtilities.appendElement(myXmlDocument, object, "condition", condition);
        Element location = XmlUtilities.appendElement(myXmlDocument, object, "location");
        XmlUtilities.appendElement(myXmlDocument, location, "x", x.toString());
        XmlUtilities.appendElement(myXmlDocument, location, "y", y.toString());
        for (String key : imagePaths.keySet()) {
            Element image = XmlUtilities.appendElement(myXmlDocument, object, "image");
            XmlUtilities.appendElement(myXmlDocument, image, "source", key);
            XmlUtilities.appendElement(myXmlDocument, image, "direction", imagePaths.get(key));
        }
    }

    /**
     * Creates a battle object and adds it to a sprite.
     * 
     * @param s Sprite to which the battleObject is added
     * @param modes Comma-separated modes this object is in
     * @param battleClass Specific concrete class for the battle object
     * @param condition Event for the battle object
     * @param stats map for each battle attribute and value (health, attack, etc.)
     * @param name Name of the battle object (i.e. Pikachu)
     * @param imagePath Path to the Battle Image
     */
    public void addBattleObject (Element s, String modes, String battleClass, String condition,
            Map<String, Number> stats, String name, String[] imagePath) {
        Element object = XmlUtilities.appendElement(myXmlDocument, s, "battle");
        addBattleObject(modes, battleClass, condition, stats, name, imagePath,
                object);
    }

    /**
     * Creates a battle object and adds it to a sprite.
     * 
     * @param s Sprite to which the battleObject is added
     * @param createOn When to create the object
     * @param modes Comma-separated modes this object is in
     * @param battleClass Specific concrete class for the battle object
     * @param condition Event for the battle object
     * @param stats map for each battle attribute and value (health, attack, etc.)
     * @param name Name of the battle object (i.e. Pikachu)
     * @param imagePath Path to the Battle Image
     */
    public void addBattleObject (Element s, String createOn, String modes, String battleClass,
            String condition, Map<String, Number> stats, String name, String[] imagePath) {
        Element object = XmlUtilities.appendElement(myXmlDocument, s, "object");
        XmlUtilities.appendElement(myXmlDocument, object, "createOn", createOn);
        addBattleObject(modes, battleClass, condition, stats, name, imagePath,
                object);
    }

    private void addBattleObject (String modes, String battleClass,
            String condition, Map<String, Number> stats, String name,
            String[] imagePath, Element object) {
        XmlUtilities.appendElement(myXmlDocument, object, "modes", modes);
        XmlUtilities.appendElement(myXmlDocument, object, "class", battleClass);
        XmlUtilities.appendElement(myXmlDocument, object, "condition", condition);
        Element statsElement = XmlUtilities.appendElement(myXmlDocument, object, "stats");
        for (String key : stats.keySet()) {
            XmlUtilities.appendElement(myXmlDocument, statsElement, key,
                    stats.get(key).toString());
        }
        XmlUtilities.appendElement(myXmlDocument, object, "name", name);
        for (String image : imagePath) {
            XmlUtilities.appendElement(myXmlDocument, object, "image", image);
        }
    }

    /**
     * @param s object element for which the battle stats need altering
     * @param stats Map of battle attributes and their values
     * @param name Name of the battle object that stat changes in
     */
    public void modifyBattleStats (Element s, Map<String, Number> stats, String name) {
        List<Element> allObjects = (List<Element>) XmlUtilities.getElements(s, "object");
        List<Element> battleObjects = new ArrayList<Element>();
        for (Element current : allObjects) {
            if (XmlUtilities.getChildContent(current, "modes").contains("battle")) {
                battleObjects.add(current);
            }
        }
        Element statsElement = null;
        for (Element current : battleObjects) {
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
