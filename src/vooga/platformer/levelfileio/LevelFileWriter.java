package vooga.platformer.levelfileio;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
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
 * parsed by the level factory.
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
     * @param filePath file path to write to. Should be an xml file. A second
     *        file containing the binary GameObject data will also be
     *        constructed based on this name.
     * @param levelName Name of the level to display to the user
     * @param width overall width of the level
     * @param height overall height of the level
     * @param backgroundImage image to display in the background of the level
     * @param gameObjects Collection of the all the GameObjects that should be
     *        loaded when the user plays or edits the level. One GameObject
     *        <strong>must</strong> of type Player for LevelFactory to be able
     *        to load the level.
     * @param conditions the conditions for winning/losing this level
     * @param plugins plugins this level uses
     * @param cameraType fully-qualified class name of the Camera to use for
     *        this level
     * @param collisionChecker file path to the xml file describing the
     *        CollisionChecker to use for this level
     */
    public static void writeLevel (String filePath, String levelName, int width, int height,
                                   String backgroundImage, Collection<GameObject> gameObjects,
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

        appendXmlElements(levelName, width, height, backgroundImage, cameraType, collisionChecker,
                          doc, level, serializedGameObjectFilePath, serializedConditionsFilePath,
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

    private static void appendXmlElements (String levelName, int width, int height,
                                           String backgroundImage, String cameraType,
                                           String collisionChecker, Document doc, Element level,
                                           String serializedGameObjectFilePath,
                                           String serializedConditionsFilePath,
                                           String serializedPluginsFilePath) {
        
        XmlUtilities.appendElement(doc, level, XmlTags.LEVEL_NAME, levelName);
        XmlUtilities.appendElement(doc, level, XmlTags.WIDTH, String.valueOf(width));
        XmlUtilities.appendElement(doc, level, XmlTags.HEIGHT, String.valueOf(height));
        XmlUtilities.appendElement(doc, level, XmlTags.COLLISION_CHECKER, collisionChecker);
        XmlUtilities.appendElement(doc, level, XmlTags.CAMERA, cameraType);

        XmlUtilities.appendElement(doc, level, XmlTags.GAMEOBJECT_DATA,
                                   serializedGameObjectFilePath);
        XmlUtilities.appendElement(doc, level, XmlTags.CONDITION, serializedConditionsFilePath);
        XmlUtilities.appendElement(doc, level, XmlTags.PLUGIN, serializedPluginsFilePath);
        
    }

    private static void serializeCollection (Collection<Object> gameObjects, Document doc,
                                             Element level, String filePath)
                                                                            throws FileNotFoundException,
                                                                            IOException {
        FileOutputStream fos = new FileOutputStream(filePath);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        for (Object g : gameObjects) {
            oos.writeObject(g);
        }
        oos.close();
    }

}
