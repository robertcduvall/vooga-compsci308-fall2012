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

    private static final String NAME = "name";
    private static final String CLASS = "class";
    private static final String CONDITION = "condition";
    private static final String OBJECT = "object";
    private static final String MODES = "modes";
    private static final String IMAGE = "image";
    private static final String CREATE_ON = "createOn";

    private Document myXmlDocument;
    private Element myRootElement;
    private String myFileName;

    /**
     * Instantiates a PlayerEditor for modifying an Xml document.
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
     * Creates a PlayerEditor for a new Xml document.
     * 
     * @param fileName File name (with path) of Xml document
     */
    public PlayerEditor(String fileName) {
        myXmlDocument = XmlUtilities.makeDocument();
        myRootElement = myXmlDocument.createElement("player");
        myXmlDocument.appendChild(myRootElement);
        myFileName = fileName;
    }

    /**
     * Creates a map object and adds it to a sprite.
     * 
     * @param createOn When to create the object
     * @param modes Comma-separated modes this object is in
     * @param mapClass Specific concrete class for the map object
     * @param condition Event for this map object
     * @param x Map x-coordinate
     * @param y Map y-coordinate
     * @param imagePaths Paths to the Map Image (maps <source, direction>)
     */
    public void addMapObject (String createOn, String modes, String mapClass,
            String condition, Number x, Number y, Map<String, String> imagePaths) {
        Element object = XmlUtilities.appendElement(myXmlDocument, myRootElement, OBJECT);
        XmlUtilities.appendElement(myXmlDocument, object, MODES, modes);
        XmlUtilities.appendElement(myXmlDocument, object, CLASS, mapClass);
        XmlUtilities.appendElement(myXmlDocument, object, CONDITION, condition);
        Element location = XmlUtilities.appendElement(myXmlDocument, object, "location");
        XmlUtilities.appendElement(myXmlDocument, location, "x", x.toString());
        XmlUtilities.appendElement(myXmlDocument, location, "y", y.toString());
        for (String key : imagePaths.keySet()) {
            Element image = XmlUtilities.appendElement(myXmlDocument, object, IMAGE);
            XmlUtilities.appendElement(myXmlDocument, image, "source", key);
            XmlUtilities.appendElement(myXmlDocument, image, "direction", imagePaths.get(key));
        }
    }

    /**
     * Creates a battle object and adds it to a sprite.
     * 
     * @param createOn When to create the object
     * @param modes Comma-separated modes this object is in
     * @param battleClass Specific concrete class for the battle object
     * @param condition Event for the battle object
     * @param stats map for each battle attribute and value (health, attack, etc.)
     * @param name Name of the battle object (i.e. Pikachu)
     * @param imagePath Path to the Battle Image
     */
    public void addBattleObject (String createOn, String modes, String battleClass,
            String condition, String stats, String name, String imagePath) {
        Element object = XmlUtilities.appendElement(myXmlDocument, myRootElement, OBJECT);
        XmlUtilities.appendElement(myXmlDocument, object, CREATE_ON, createOn);
        XmlUtilities.appendElement(myXmlDocument, object, MODES, modes);
        XmlUtilities.appendElement(myXmlDocument, object, CLASS, battleClass);
        XmlUtilities.appendElement(myXmlDocument, object, CONDITION, condition);
        Element statsElement = XmlUtilities.appendElement(myXmlDocument, object, "stats");
        addStatsToXml(myXmlDocument, statsElement, stats);
        XmlUtilities.appendElement(myXmlDocument, object, NAME, name);
        addImagesToXml(object, imagePath);
    }

    /**
     * @param stats Map of battle attributes and their values
     * @param name Name of the battle object that stat changes in
     */
    public void modifyBattleStats (Map<String, Number> stats, String name) {
        List<Element> allObjects = (List<Element>) XmlUtilities.getElements(myRootElement, OBJECT);
        List<Element> battleObjects = new ArrayList<Element>();
        for (Element current : allObjects) {
            if (XmlUtilities.getChildContent(current, MODES).contains("battle")) {
                battleObjects.add(current);
            }
        }
        Element statsElement = null;
        for (Element current : battleObjects) {
            String currentName = XmlUtilities.getChildContent(current, NAME);
            if (currentName.equals(name)) {
                statsElement = current;
            }
        }
        for (String key : stats.keySet()) {
            Element current = XmlUtilities.getElement(statsElement, key);
            XmlUtilities.setContent(current, stats.get(key).toString());
        }
    }

    private void addStatsToXml (Document d, Element e, String stats) {
        if (!stats.equals("")) {
            stats.replaceAll("\\s", "");
            String[] allStats = stats.split("\\s*,\\s*");
            for (String stat : allStats) {
                String[] singleStat = stat.split("\\s*:\\s*");
                XmlUtilities.appendElement(d, e, singleStat[0], singleStat[1]);
            }
        }
    }

    private void addImagesToXml (Element objectElement, String imagePaths) {
        imagePaths.replaceAll("\\s", "");
        String[] allImages = imagePaths.split("\\s*,\\s*");
        for (String image : allImages) {
            XmlUtilities.appendElement(myXmlDocument, objectElement, "image", image);
        }
    }

    /**
     * Save Xml Document.
     */
    public void saveXmlDocument () {
        XmlUtilities.write(myXmlDocument, myFileName);
    }

    /**
     * get Xml document.
     */
    public Document getXmlDocument () {
        return myXmlDocument;
    }

    /**
     * 
     * @param i Number of maps used for this player.
     */
    public void modifyModes (int i) {
        Element mode = XmlUtilities.getElement(myRootElement, MODES);
        String content = "";
        for (int j = 0; j < i; j++) {
            content = content + ", map" + ((Integer) i).toString();
        }
        content = content.substring(2);
        XmlUtilities.setContent(mode, content);
    }
}
