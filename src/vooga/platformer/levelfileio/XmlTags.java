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

    public static final String DOCUMENT = "level";

    public static final String GAMEOBJECT = "gameObject";

    public static final String LEVEL_NAME = "levelName";

    public static final String CAMERA = "camera";

    public static final String COLLISION_CHECKER = "collisionChecker";

    public static final String BACKGROUND_IMAGE = "backgroundImage";

    public static final String X = "x";

    public static final String Y = "y";

    public static final String WIDTH = "width";

    public static final String HEIGHT = "height";

    public static final String IMAGE_PATH = "imagePath";

    public static final String STRATEGY = "strategy";

    public static final String CLASS_NAME = "class";

    public static final String CONFIG = "config";

    public static final String ID = "id";

    // Tags for collision checker xml file

    public static final String GAMEOBJECT_DATA = "gameObjectData";

    public static final String COLLISIONEVENT = "collisionEvent";

    public static final String COLLISIONEVENTCLASS = "collisionEventClass";

    public static final String GAMEOBJECTA = "gameObjectA";

    public static final String GAMEOBJECTB = "gameObjectB";

}
