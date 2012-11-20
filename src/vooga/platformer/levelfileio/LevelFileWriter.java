package vooga.platformer.levelfileio;

import java.util.Collection;
import java.util.Map;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import util.xml.XmlUtilities;
import vooga.platformer.leveleditor.Sprite;


/**
 * A static class responsible for writing level data to an XML file that can
 * parsed by the level factory.
 * 
 * @author Grant Oakley
 * 
 */
public final class LevelFileWriter {

    /**
     * Integer constant that is returned by writeLevel if the file data cannot
     * be written successfully.
     */
    public static final int UNSUCCESSFUL_WRITE = 0;
    /**
     * Integer constant returned by writeLevel if the data file is written
     * successfully.
     */
    public static final int SUCCESSFUL_WRITE = 1;

    private LevelFileWriter () {
        /*
         * Empty constructor
         */
    }

    /**
     * Method that writes the data describing a platformer level to an XML file
     * so that it can be reconstructed by a level factory.
     * 
     * @param filePath where the XML should be saved
     * @param levelType specifies which Level subclass to use
     * @param levelName name of the level
     * @param width overall width of the level in pixels
     * @param height overall height of the level in pixels
     * @param backgroundImage path to the image that should be painted to the
     *        level's background
     * @param levelObjects Sprites that populate the level
     * @param collisionCheckerType class name of the CollisionChecker to use for
     *        this level
     * @param cameraType class name of the Camera to use for this level
     * @return an integer constant representing whether the file was written
     *         successfully or not
     */
    public static int writeLevel (String filePath, String levelType, String levelName, int width,
                                  int height, String backgroundImage,
                                  Collection<Sprite> levelObjects, String collisionCheckerType,
                                  String cameraType) {
        Document doc = XmlUtilities.makeDocument();

        Element level = doc.createElement("level");
        doc.appendChild(level);

        level.setAttribute("type", levelType);
        XmlUtilities.appendElement(doc, level, "id", levelName);
        XmlUtilities.appendElement(doc, level, "width", String.valueOf(width));
        XmlUtilities.appendElement(doc, level, "height", String.valueOf(height));
        XmlUtilities.appendElement(doc, level, "backgroundImage", backgroundImage);
        XmlUtilities.appendElement(doc, level, "collisionChecker", collisionCheckerType);
        XmlUtilities.appendElement(doc, level, "camera", cameraType);

        addLevelObjects(levelObjects, doc, level);

        XmlUtilities.write(doc, filePath);

        return SUCCESSFUL_WRITE;
    }

    private static void addLevelObjects (Collection<Sprite> levelObjects, Document doc,
                                         Element level) {
        for (Sprite s : levelObjects) {
            Element spriteElement = doc.createElement("gameObject");
            spriteElement.setAttribute("type", s.getType());

            XmlUtilities.appendElement(doc, spriteElement, "x", String.valueOf(s.getX()));
            XmlUtilities.appendElement(doc, spriteElement, "y", String.valueOf(s.getY()));
            XmlUtilities.appendElement(doc, spriteElement, "width", String.valueOf(s.getWidth()));
            XmlUtilities.appendElement(doc, spriteElement, "height", String.valueOf(s.getHeight()));
            XmlUtilities.appendElement(doc, spriteElement, "imagePath",
                                       String.valueOf(s.getImagePath()));

            if (s.getUpdateStrategies() != null && s.getUpdateStrategies().size() > 0) {
                Element strategiesElement = doc.createElement("strategies");
                for (Map<String, String> strategy : s.getUpdateStrategies()) {

                    String strategyType = "";
                    if (strategy.containsKey("type")) {
                        strategyType = strategy.get("type");
                        strategy.remove("type");
                    }

                    Element strategyElement =
                            XmlUtilities.generateElementFromMap(doc, "strategy", strategy);
                    strategyElement.setAttribute("type", strategyType);
                    strategiesElement.appendChild(strategyElement);
                }
                spriteElement.appendChild(strategiesElement);
            }

            if (s.getAttributes() != null && s.getAttributes().size() > 0) {
                XmlUtilities.appendMapContents(doc, spriteElement, "attr", s.getAttributes());
            }

            level.appendChild(spriteElement);
        }
    }
}
