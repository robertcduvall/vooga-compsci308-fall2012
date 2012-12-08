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
    private static final String HEIGHT = "height";
    private static final String WIDTH = "width";
    private static final String CAMERA_DIMENSION = "cameraDimension";
    private static final String FORMAT1 = "\\s";
    private static final String FORMAT2 = "\\s*,\\s*";

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
     * Method that initializes battle, gameover, option and map modes.
     */
    public void initialize () {
        XmlUtilities.appendElement(myXmlDocument, myGameSetupElement, "startMode", "map1");
        Element battleMode = addMode("battle", "vooga.turnbased.gamecore.gamemodes.BattleMode",
                "dobattle");
        XmlUtilities.appendElement(myModeElement, battleMode);
        Element gameOver = addMode("gameOver", "vooga.turnbased.gamecore.gamemodes.GameOverMode",
                "endgame");
        XmlUtilities.appendElement(myModeElement, gameOver);
        Element option = addMode("optionMode", "vooga.turnbased.gamecore.gamemodes.OptionMode",
                "optionstuff");
        XmlUtilities.appendElement(myModeElement, option);
        Element map1 = addMode("map1", "vooga.turnbased.gamecore.gamemodes.MapMode",
                "entermap1");
        XmlUtilities.appendElement(myModeElement, map1);
    }

    public void addStaticSprites (String background, int numMaps) {
        Element sprite = XmlUtilities.appendElement(myXmlDocument, mySpriteElement, "staticSprite");
        XmlUtilities.appendElement(myXmlDocument, sprite,
                "class", "vooga.turnbased.gameobject.mapobject.MapObject");
        XmlUtilities.appendElement(myXmlDocument, sprite, "condition", "NO_ACTION");
        XmlUtilities.appendElement(myXmlDocument, sprite, "image", background);
        String maps = "";
        for (int i = 0; i < numMaps; i++) {
            maps = maps + ", map" + ((Integer)(i+1)).toString();
        }
        maps = maps.substring(2);
        XmlUtilities.appendElement(myXmlDocument, sprite, "modes", maps);
    }

    /**
     * 
     * @param width Describes width of the Map dimension
     * @param height Describes height of the Map dimension
     */
    public void addDimensionTag(String width, String height) {
        addDimension(DIMENSION, width, height);
    }

    /**
     * 
     * @param width New map dimension width
     * @param height New map dimension height
     */
    public void modifyDimensionTag(String width, String height) {
        modifyDimension(DIMENSION, width, height);
    }

    /**
     * 
     * @param width Describes width of the Camera dimension
     * @param height Describes height of the Camera dimension
     */
    public void addCameraDimension (String width, String height) {
        addDimension(CAMERA_DIMENSION, width, height);
    }

    /**
     * 
     * @param width New camera dimension width
     * @param height New camera dimension height
     */
    public void modifyCameraDimension (String width, String height) {
        modifyDimension(CAMERA_DIMENSION, width, height);
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
     * Changes the background image to the image located at the given filepath.
     * @param imagePath Filepath to new image.
     */
    public void modifyBackgroundImage (String imagePath) {
        Element background = XmlUtilities.getElement(myGameSetupElement, BACKGROUND_IMAGE);
        XmlUtilities.setContent(background, imagePath);
    }

    /**
     * 
     * @param name Name of Declared mode
     * @param classMode Class used for the mode
     * @param conditions Either single string value or multiple comma separated strings
     */
    public Element addMode (String name, String classMode, String conditions) {
        Element mode = null;
        if (conditions.contains(",")) {
            conditions.replaceAll(FORMAT1, "");
            String[] newConditions = conditions.split(FORMAT2);
            mode = addMode(name, classMode, newConditions);
        }
        else {
            mode = XmlUtilities.appendElement(myXmlDocument, myModeElement, MODE);
            XmlUtilities.appendElement(myXmlDocument, mode, NAME, name);
            XmlUtilities.appendElement(myXmlDocument, mode, CLASS, classMode);
            XmlUtilities.appendElement(myXmlDocument, mode, CONDITION, conditions);
        }
        return mode;
    }

    /**
     * 
     * @param name Name of Declared mode
     * @param classMode Class used for the mode
     * @param conditions multiple condition tags to be added
     */
    public Element addMode (String name, String classMode, String[] conditions) {
        Element mode = XmlUtilities.appendElement(myXmlDocument, myModeElement, MODE);
        XmlUtilities.appendElement(myXmlDocument, mode, NAME, name);
        XmlUtilities.appendElement(myXmlDocument, mode, CLASS, classMode);
        for (String condition : conditions) {
            XmlUtilities.appendElement(myXmlDocument, mode, CONDITION, condition);
        }
        return mode;
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
     * @param modes The modes that this object is in
     * @param className Specific concrete class for the map object
     * @param condition Condition event for this map object
     * @param x Map x-coordinate
     * @param y Map y-coordinate
     * @param imagePaths Path to the Map Image
     */
    public void addObject (Element s, String createsOn, String modes, String className,
            String condition, String x, String y, String imagePaths, String stats, String name) {
        Element objectElement = XmlUtilities.appendElement(myXmlDocument, s, OBJECT);
        XmlUtilities.appendElement(myXmlDocument, objectElement, CREATE_ON, createsOn);
        XmlUtilities.appendElement(myXmlDocument, objectElement, MODES, modes);
        XmlUtilities.appendElement(myXmlDocument, objectElement, CLASS, className);
        XmlUtilities.appendElement(myXmlDocument, objectElement, CONDITION, condition);
        Element location = XmlUtilities.appendElement(myXmlDocument, objectElement, "location");
        XmlUtilities.appendElement(myXmlDocument, location, "x", x);
        XmlUtilities.appendElement(myXmlDocument, location, "y", y);
        addImagesToXml(objectElement, imagePaths);
        Element statsElement = XmlUtilities.appendElement(myXmlDocument, objectElement, "stats");
        addStatsToXml(myXmlDocument, statsElement, stats);
        XmlUtilities.appendElement(myXmlDocument, objectElement, NAME, name);
    }

    private void addDimension (String tagName, String width, String height) {
        Element dimension = XmlUtilities.appendElement(myXmlDocument, myGameSetupElement, tagName);
        XmlUtilities.appendElement(myXmlDocument, dimension, WIDTH, width);
        XmlUtilities.appendElement(myXmlDocument, dimension, HEIGHT, height);
    }

    private void modifyDimension (String s, String width, String height) {
        Element dimension = XmlUtilities.getElement(myGameSetupElement, s);
        Element w = XmlUtilities.getElement(dimension, WIDTH);
        Element h = XmlUtilities.getElement(dimension, HEIGHT);
        XmlUtilities.setContent(w, width);
        XmlUtilities.setContent(h, height);
    }

    private void addStatsToXml (Document d, Element e, String stats) {
        if (!"".equals(stats)) {
            stats.replaceAll(FORMAT1, "");
            String[] allStats = stats.split(FORMAT2);
            for (String stat : allStats) {
                String[] singleStat = stat.split("\\s*:\\s*");
                XmlUtilities.appendElement(d, e, singleStat[0], singleStat[1]);
            }
        }
    }

    private void addImagesToXml (Element objectElement, String imagePaths) {
        imagePaths.replaceAll(FORMAT1, "");
        String[] allImages = imagePaths.split(FORMAT2);
        for (String image : allImages) {
            XmlUtilities.appendElement(myXmlDocument, objectElement, "image", image);
        }
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
