package vooga.platformer.levelfileio;

/**
 * An interface containing the string constants that are used to read and write
 * the platformer level data XML files. The values of these constants can be
 * altered to use different tag names in the files used to store level data.
 * 
 * @author Grant Oakley
 * 
 */
public interface XmlTags {

    // Tags for level file

    /**
     * Document tag for all level files.
     */
    String DOCUMENT = "level";

    /**
     * Tag deliminating the level's name.
     */
    String LEVEL_NAME = "levelName";

    /**
     * Tag deliminating the type of camera to use for this level.
     */
    String CAMERA = "camera";

    /**
     * Tag deliminating the path to the collision checker to use for this level.
     */
    String COLLISION_CHECKER = "collisionChecker";

    /**
     * Tag deliminating the width of the level.
     */
    String WIDTH = "width";

    /**
     * Tag deliminating the height of the level.
     */
    String HEIGHT = "height";

    /**
     * Attribute name for a fully-qualified class name.
     */
    String CLASS_NAME = "class";

    /**
     * Tag deliminating the path to the binary file containing the level's
     * GameObjects.
     */
    String GAMEOBJECT_DATA = "gameObjectData";

    /**
     * Tag deliminating the path to the binary file containing the level's
     * Conditions.
     */
    String CONDITION_DATA = "conditionData";

    /**
     * Tag deliminating the path to the binary file containing the level's
     * Plugins.
     */
    String PLUGIN_DATA = "pluginData";

    // Tags for collision checker xml file

    /**
     * Tag deliminating an individual collisionEvent.
     */
    String COLLISIONEVENT = "collisionEvent";

    /**
     * Tag deliminating the fully-qualified class name for a concrete
     * CollisionEvent.
     */
    String COLLISIONEVENTCLASS = "collisionEventClass";

    /**
     * Tag deliminating the first colliding object.
     */
    String GAMEOBJECTA = "gameObjectA";

    /**
     * Tag deliminating the second colliding object.
     */
    String GAMEOBJECTB = "gameObjectB";

}
