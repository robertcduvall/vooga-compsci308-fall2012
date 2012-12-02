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

    private static final String DIMENSION = "dimension";
    private static final String BACKGROUND_IMAGE = "backgroundImage";
    private static final String MODE = "mode";
    private static final String NAME = "name";
    private static final String CLASS = "class";
    private static final String CONDITION = "condition";
    private static final String OBJECT = "object";
    private static final String CREATE_ON = "createOn";
    private static final String MODES = "modes";
    private static final String IMAGE = "image";
    private static final String HEIGHT = "height";
    private static final String WIDTH = "width";

    private Document myXmlDocument;
    private Element myRootElement;
    private Element myGameSetupElement;
    private Element myModeElement;
    private Element mySpriteElement;
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
        myRootElement = myXmlDocument.createElement("rpggame");
        myXmlDocument.appendChild(myRootElement);
        myGameSetupElement = XmlUtilities.appendElement(myXmlDocument, myRootElement,
                "gameSetup");
        myRootElement.appendChild(myGameSetupElement);
        myModeElement = XmlUtilities.appendElement(myXmlDocument, myRootElement,
                "modeDeclarations");
        myRootElement.appendChild(myModeElement);
        mySpriteElement = XmlUtilities.appendElement(myXmlDocument, myRootElement,
                "spriteDeclarations");
        myRootElement.appendChild(mySpriteElement);
        myFileName = fileName;
    }

    /**
     * 
     * @param width Describes width of the Map dimension
     * @param height Describes height of the Map dimension
     */
    public void addDimensionTag(Number width, Number height) {
        addDimension(DIMENSION, width, height);
    }

    /**
     * 
     * @param width New map dimension width
     * @param height New map dimension height
     */
    public void modifyDimensionTag(Number width, Number height) {
        modifyDimension(DIMENSION, width, height);
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
        XmlUtilities.appendElement(myXmlDocument, myGameSetupElement,
                BACKGROUND_IMAGE, imagePath);
    }

    /**
     * 
     * @param imagePath New Image Path
     */
    public void modifyBackgroundImage (String imagePath) {
        Element background = XmlUtilities.getElement(myGameSetupElement, BACKGROUND_IMAGE);
        XmlUtilities.setContent(background, imagePath);
    }

    /**
     * Adds the start mode element, which is always map.
     */
    public void addStartMode () {
        XmlUtilities.appendElement(myXmlDocument, myGameSetupElement, "startMode", "map");
    }

    /**
     * 
     * @param name Name of Declared mode
     * @param classMode Class used for the mode
     * @param conditions Either single string value or multiple comma separated strings
     */
    public void addMode (String name, String classMode, String conditions) {
        if (conditions.contains(",")) {
            conditions.replaceAll("\\s", "");
            String[] newConditions = conditions.split("\\s*,\\s*");
            addMode(name, classMode, newConditions);
        }
        else {
            Element mode = XmlUtilities.appendElement(myXmlDocument, myModeElement, MODE);
            XmlUtilities.appendElement(myXmlDocument, mode, NAME, name);
            XmlUtilities.appendElement(myXmlDocument, mode, CLASS, classMode);
            XmlUtilities.appendElement(myXmlDocument, mode, CONDITION, conditions);
        }
    }

    /**
     * 
     * @param name Name of Declared mode
     * @param classMode Class used for the mode
     * @param conditions multiple condition tags to be added
     */
    public void addMode (String name, String classMode, String[] conditions) {
        Element mode = XmlUtilities.appendElement(myXmlDocument, myModeElement, MODE);
        XmlUtilities.appendElement(myXmlDocument, mode, NAME, name);
        XmlUtilities.appendElement(myXmlDocument, mode, CLASS, classMode);
        for (String condition : conditions) {
            XmlUtilities.appendElement(myXmlDocument, mode, CONDITION, condition);
        }
    }

    /**
     * 
     * @return The new Sprite element that was just added to the Xml Document
     */
    public Element addSprite () {
        return XmlUtilities.appendElement(myXmlDocument, mySpriteElement, "sprite");
    }

    /**
     * Creates a map object and adds it to a sprite.
     * 
     * @param s Sprite Element to which the mapObject is added
     * @param createsOn When the object is initiated
     * @param modeds The modes that this object is in
     * @param mapClass Specific concrete class for the map object
     * @param condition Condition event for this map object
     * @param x Map x-coordinate
     * @param y Map y-coordinate
     * @param imagePath Path to the Map Image
     */
    public void addMapObject (Element s, String createsOn, String modeds, String mapClass,
            String condition, Number x, Number y, String[] imagePath) {
        Element mapElement = XmlUtilities.appendElement(myXmlDocument, s, OBJECT);
        XmlUtilities.appendElement(myXmlDocument, mapElement, CREATE_ON, createsOn);
        XmlUtilities.appendElement(myXmlDocument, mapElement, MODES, modeds);
        XmlUtilities.appendElement(myXmlDocument, mapElement, CLASS, mapClass);
        XmlUtilities.appendElement(myXmlDocument, mapElement, CONDITION, condition);
        Element location = XmlUtilities.appendElement(myXmlDocument, mapElement, "location");
        XmlUtilities.appendElement(myXmlDocument, location, "x", x.toString());
        XmlUtilities.appendElement(myXmlDocument, location, "y", y.toString());
        for (String image : imagePath) {
            XmlUtilities.appendElement(myXmlDocument, mapElement, IMAGE, image);
        }
    }

    /**
     * Creates a battle object and adds it to a sprite.
     * 
     * @param s Sprite to which the battleObject is added
     * @param createsOn When the object is initiated
     * @param modes The modes this object is in
     * @param battleClass Specific concrete class for the battle object
     * @param condition Condition event for the battle object
     * @param stats map for each battle attribute and value (health, attack, etc.)
     * @param name Name of the battle object (i.e. Pikachu)
     * @param imagePath Path to the Battle Image
     */
    public void addBattleObject (Element s, String createsOn, String modes, String battleClass,
            String condition, Map<String, Number> stats, String name, String[] imagePath) {
        Element battle = XmlUtilities.appendElement(myXmlDocument, s, OBJECT);
        XmlUtilities.appendElement(myXmlDocument, battle, CREATE_ON, createsOn);
        XmlUtilities.appendElement(myXmlDocument, battle, MODES, modes);
        XmlUtilities.appendElement(myXmlDocument, battle, CLASS, battleClass);
        XmlUtilities.appendElement(myXmlDocument, battle, CONDITION, condition);
        Element statsElement = XmlUtilities.appendElement(myXmlDocument, battle, "stats");
        addStatsMapToXml(statsElement, stats);
        XmlUtilities.appendElement(myXmlDocument, battle, NAME, name);
        for (String image : imagePath) {
            XmlUtilities.appendElement(myXmlDocument, battle, IMAGE, image);
        }
    }

    private void addStatsMapToXml (Element e, Map<String, Number> m) {
        for (String key : m.keySet()) {
            XmlUtilities.appendElement(myXmlDocument, e, key, m.get(key).toString());
        }
    }

    private void addDimension (String tagName, Number width, Number height) {
        Element dimension = XmlUtilities.appendElement(myXmlDocument, myGameSetupElement, tagName);
        XmlUtilities.appendElement(myXmlDocument, dimension, WIDTH, width.toString());
        XmlUtilities.appendElement(myXmlDocument, dimension, HEIGHT, height.toString());
    }

    private void modifyDimension (String s, Number width, Number height) {
        Element dimension = XmlUtilities.getElement(myGameSetupElement, s);
        Element w = XmlUtilities.getElement(dimension, WIDTH);
        Element h = XmlUtilities.getElement(dimension, HEIGHT);
        XmlUtilities.setContent(w, width.toString());
        XmlUtilities.setContent(h, height.toString());
    }

    /**
     * Save this Xml Document.
     */
    public void saveXmlDocument () {
        XmlUtilities.write(myXmlDocument, myFileName);
    }

    /**
     * Retrieve the Xml Document.
     */
    public Document getXmlDocument () {
        return myXmlDocument;
    }
}
