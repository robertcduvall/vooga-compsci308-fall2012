package vooga.platformer.levelfileio;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Collection;
import java.util.Map;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import util.xml.XmlUtilities;
import vooga.platformer.gameobject.GameObject;
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
     * 
     * @deprecated Using custom exception instead.
     */
    public static final int UNSUCCESSFUL_WRITE = 0;
    /**
     * Integer constant returned by writeLevel if the data file is written
     * successfully.
     * 
     * @deprecated Using custom exception instead.
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
     * 
     * @deprecated Sprites are no longer supported in file writing, only
     *             serializable GameObjects. Use the alternate writeLevel
     *             method.
     */
    public static int writeLevel (String filePath, String levelType, String levelName, int width,
                                  int height, String backgroundImage,
                                  Collection<Sprite> levelObjects, String collisionCheckerType,
                                  String cameraType) {
        Document doc = XmlUtilities.makeDocument();

        Element level = doc.createElement(XmlTags.DOCUMENT);
        doc.appendChild(level);

        level.setAttribute(XmlTags.CLASS_NAME, levelType);
        XmlUtilities.appendElement(doc, level, XmlTags.LEVEL_NAME, levelName);
        XmlUtilities.appendElement(doc, level, XmlTags.WIDTH, String.valueOf(width));
        XmlUtilities.appendElement(doc, level, XmlTags.HEIGHT, String.valueOf(height));
        XmlUtilities.appendElement(doc, level, XmlTags.BACKGROUND_IMAGE, backgroundImage);
        XmlUtilities.appendElement(doc, level, XmlTags.COLLISION_CHECKER, collisionCheckerType);
        XmlUtilities.appendElement(doc, level, XmlTags.CAMERA, cameraType);

        addLevelObjects(levelObjects, doc, level);

        XmlUtilities.write(doc, filePath);

        return SUCCESSFUL_WRITE;
    }

    /**
     * Method that writes the data describing a platformer level to an XML file
     * so that it can be reconstructed by a level factory or opened again in the
     * editor.
     * 
     * @param filePath file path to write to. Should be an xml file. A second
     *        file containing the binary GameObject data will also be
     *        constructed based on this name.
     * @param levelName Name of the level to display to the user
     * @param width overall width of the level
     * @param height overall height of the level
     * @param backgroundImage image to display in the background of the level
     * @param gameObjects a collection of the all the GameObjects that should be
     *        loaded when the user plays or edits the level
     * @param collisionCheckerType fully-qualified class name of the
     *        CollisionChecker to use for
     *        this level
     * @param cameraType fully-qualified class name of the Camera to use for
     *        this level
     */
    public static void writeLevel (String filePath, String levelName, int width, int height,
                                   String backgroundImage, Collection<GameObject> gameObjects,
                                   String collisionCheckerType, String cameraType) {
        Document doc = XmlUtilities.makeDocument();

        Element level = doc.createElement(XmlTags.DOCUMENT);
        doc.appendChild(level);

        XmlUtilities.appendElement(doc, level, XmlTags.LEVEL_NAME, levelName);
        XmlUtilities.appendElement(doc, level, XmlTags.WIDTH, String.valueOf(width));
        XmlUtilities.appendElement(doc, level, XmlTags.HEIGHT, String.valueOf(height));
        XmlUtilities.appendElement(doc, level, XmlTags.BACKGROUND_IMAGE, backgroundImage);
        XmlUtilities.appendElement(doc, level, XmlTags.COLLISION_CHECKER, collisionCheckerType);
        XmlUtilities.appendElement(doc, level, XmlTags.CAMERA, cameraType);

        String serializedGameObjectFilePath = filePath.split("\\.")[0] + "GameObjects.bin";
        XmlUtilities.appendElement(doc, level, XmlTags.GAMEOBJECT_DATA,
                                   serializedGameObjectFilePath);
        try {
            serializeGameObjects(gameObjects, doc, level, serializedGameObjectFilePath);
        }
        catch (FileNotFoundException e) {
            throw new LevelFileIOException("File not found", e);
        }
        catch (IOException e) {
            throw new LevelFileIOException("And IO exception occurred", e);
        }

        XmlUtilities.write(doc, filePath);
    }

    private static void serializeGameObjects (Collection<GameObject> gameObjects, Document doc,
                                              Element level, String filePath)
                                                                             throws FileNotFoundException,
                                                                             IOException {
        FileOutputStream fos = new FileOutputStream(filePath);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        for (GameObject g : gameObjects) {
            oos.writeObject(g);
        }
        oos.close();
    }

    /**
     * Writes sprite data to the xml file.
     * 
     * @param levelObjects sprites to be converted to xml tags
     * @param doc document in which to write this data
     * @param level parent element under which to write sprite data
     * 
     * @deprecated Sprite data is no longer being written to xml file. Instead,
     *             GameObjects are being serialized.
     */
    private static void addLevelObjects (Collection<Sprite> levelObjects, Document doc,
                                         Element level) {
        for (Sprite s : levelObjects) {
            Element spriteElement = doc.createElement(XmlTags.GAMEOBJECT);
            spriteElement.setAttribute(XmlTags.CLASS_NAME, s.getClassName());

            XmlUtilities.appendElement(doc, spriteElement, XmlTags.X, String.valueOf(s.getX()));
            XmlUtilities.appendElement(doc, spriteElement, XmlTags.Y, String.valueOf(s.getY()));
            XmlUtilities.appendElement(doc, spriteElement, XmlTags.WIDTH,
                                       String.valueOf(s.getWidth()));
            XmlUtilities.appendElement(doc, spriteElement, XmlTags.HEIGHT,
                                       String.valueOf(s.getHeight()));
            XmlUtilities.appendElement(doc, spriteElement, XmlTags.ID, s.getID());
            XmlUtilities.appendElement(doc, spriteElement, XmlTags.IMAGE_PATH, s.getImagePath());

            for (Map<String, String> strategy : s.getUpdateStrategies()) {

                String strategyType = "";
                if (strategy.containsKey(XmlTags.CLASS_NAME)) {
                    strategyType = strategy.get(XmlTags.CLASS_NAME);
                    strategy.remove(XmlTags.CLASS_NAME);
                }

                Element strategyElement =
                        XmlUtilities.generateElementFromMap(doc, XmlTags.STRATEGY, strategy);
                strategyElement.setAttribute(XmlTags.CLASS_NAME, strategyType);
                spriteElement.appendChild(strategyElement);
            }

            if (s.getAttributes() != null && s.getAttributes().size() > 0) {
                XmlUtilities.appendMapContents(doc, spriteElement, XmlTags.CONFIG,
                                               s.getAttributes());
            }

            level.appendChild(spriteElement);
        }
    }
}
