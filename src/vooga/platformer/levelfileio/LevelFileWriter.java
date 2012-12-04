package vooga.platformer.levelfileio;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import util.xml.XmlUtilities;
import vooga.platformer.gameobject.GameObject;
import vooga.platformer.level.condition.Condition;
import vooga.platformer.level.levelplugin.LevelPlugin;


/**
 * A static class responsible for writing level data to an XML file that can
 * parsed by the LevelFileReader for use by the LevelFactory or the level
 * editor.
 * 
 * @author Grant Oakley
 * 
 */
public final class LevelFileWriter {

    /**
     * Method that writes the data describing a platformer level to an XML file
     * so that it can be reconstructed by a level factory or opened again in the
     * editor.
     * 
     * @param filePath file path to write to. Should be an xml file (e.g.
     *        <code>some/path/name.xml</code>. A second file containing the
     *        binary GameObject data will also be constructed based on this
     *        name.
     * @param levelName name of the level to display to the user
     * @param width overall width of the level
     * @param height overall height of the level
     * @param gameObjects Collection of the all the GameObjects that should be
     *        loaded when the user plays or edits the level. One GameObject
     *        <strong>must</strong> of type Player for LevelFactory to be able
     *        to load the level.
     * @param conditions the conditions for winning/losing this level
     * @param plugins plugins this level uses (e.g.
     *        <code>BackgroundPainter</code>)
     * @param cameraType fully-qualified class name of the Camera to use for
     *        this level
     * @param collisionChecker file path to the xml file describing the
     *        CollisionChecker to use for this level
     */
    public static void writeLevel (String filePath, String levelName, int width, int height,
                                   Collection<GameObject> gameObjects,
                                   Collection<Condition> conditions,
                                   Collection<LevelPlugin> plugins, String cameraType,
                                   String collisionChecker) {

        Collection<Object> gameObjectsAsObjects = new ArrayList<Object>(gameObjects);
        Collection<Object> conditionsAsObjects = new ArrayList<Object>(conditions);
        Collection<Object> pluginsAsObjects = new ArrayList<Object>(plugins);

        Document doc = XmlUtilities.makeDocument();
        Element level = doc.createElement(XmlTags.DOCUMENT);
        doc.appendChild(level);

        String serializedGameObjectFilePath = filePath.split("\\.")[0] + "GameObjects.bin";
        String serializedConditionsFilePath = filePath.split("\\.")[0] + "Conditions.bin";
        String serializedPluginsFilePath = filePath.split("\\.")[0] + "Plugins.bin";

        // convert to relative paths
        String base = System.getProperty("user.dir");
        serializedGameObjectFilePath = relativizePath(base, serializedGameObjectFilePath);
        serializedConditionsFilePath = relativizePath(base, serializedConditionsFilePath);
        serializedPluginsFilePath    = relativizePath(base, serializedPluginsFilePath);

        appendXmlElements(levelName, width, height, cameraType, collisionChecker, doc, level,
                          serializedGameObjectFilePath, serializedConditionsFilePath,
                          serializedPluginsFilePath);

        try {
            serializeCollection(gameObjectsAsObjects, doc, level, serializedGameObjectFilePath);
            serializeCollection(conditionsAsObjects, doc, level, serializedConditionsFilePath);
            serializeCollection(pluginsAsObjects, doc, level, serializedPluginsFilePath);
        }
        catch (FileNotFoundException e) {
            throw new LevelFileIOException("File not found", e);
        }
        catch (IOException e) {
            throw new LevelFileIOException("And IO exception occurred", e);
        }

        XmlUtilities.write(doc, filePath);
    }

    private static String relativizePath(String base, String path) {
        return new File(base).toURI().relativize(new File(path).toURI()).getPath();
    }

    private static void appendXmlElements (String levelName, int width, int height,
                                           String cameraType, String collisionChecker,
                                           Document doc, Element level,
                                           String serializedGameObjectPath,
                                           String serializedConditionsPath,
                                           String serializedPluginsPath) {

        XmlUtilities.appendElement(doc, level, XmlTags.LEVEL_NAME, levelName);
        XmlUtilities.appendElement(doc, level, XmlTags.WIDTH, String.valueOf(width));
        XmlUtilities.appendElement(doc, level, XmlTags.HEIGHT, String.valueOf(height));
        XmlUtilities.appendElement(doc, level, XmlTags.COLLISION_CHECKER, collisionChecker);
        XmlUtilities.appendElement(doc, level, XmlTags.CAMERA, cameraType);
        XmlUtilities.appendElement(doc, level, XmlTags.GAMEOBJECT_DATA, serializedGameObjectPath);
        XmlUtilities.appendElement(doc, level, XmlTags.CONDITION, serializedConditionsPath);
        XmlUtilities.appendElement(doc, level, XmlTags.PLUGIN, serializedPluginsPath);

    }

    private static void serializeCollection (Collection<Object> serializableObjects, Document doc,
                                             Element level, String filePath)
                                                                            throws FileNotFoundException,
                                                                            IOException {
        FileOutputStream fos = new FileOutputStream(filePath);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        for (Object g : serializableObjects) {
            oos.writeObject(g);
        }
        oos.close();
    }

}
