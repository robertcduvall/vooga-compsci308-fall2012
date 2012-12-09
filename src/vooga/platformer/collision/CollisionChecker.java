package vooga.platformer.collision;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import util.reflection.Reflection;
import util.xml.XmlUtilities;
import vooga.platformer.gameobject.GameObject;
import vooga.platformer.level.Level;
import vooga.platformer.levelfileio.LevelFileIOException;
import vooga.platformer.levelfileio.XmlTags;


/**
 * CollisionChecker is used to scan the screen and return a list of
 * CollisionEvents
 * for each collision detected on the screen
 * 
 * @author Bruce
 * 
 */
public abstract class CollisionChecker {

    private Map<String, HashMap<String, CollisionEvent>> collisionEventsMap =
            new HashMap<String, HashMap<String, CollisionEvent>>();

    public CollisionChecker (String fileName) {
        Document myDocument = XmlUtilities.makeDocument(new File(fileName));
        NodeList collisionEventNodes = myDocument.getElementsByTagName(XmlTags.COLLISIONEVENT);

        for (int i = 0; i < collisionEventNodes.getLength(); i++) {
            Node collisionEventNode = collisionEventNodes.item(i);
            Element collisionEventElement = (Element) collisionEventNode;

            String objectAName =
                    XmlUtilities.getChildContent(collisionEventElement, XmlTags.GAMEOBJECTA)
                            .toString();
            String objectBName =
                    XmlUtilities.getChildContent(collisionEventElement, XmlTags.GAMEOBJECTB)
                            .toString();
            String collisionEventName =
                    XmlUtilities
                            .getChildContent(collisionEventElement, XmlTags.COLLISIONEVENTCLASS)
                            .toString();

            Class typeA = null;
            Class typeB = null;
            try {
                typeA = Class.forName(objectAName);
                typeB = Class.forName(objectBName);
            }
            catch (ClassNotFoundException e) {
                throw new LevelFileIOException("CollisionChecker: class not found", e);
            }

            CollisionEvent collisionEventAB =
                    (CollisionEvent) Reflection.createInstance(collisionEventName, typeA, typeB);
            this.addCollisionEvents(objectAName, objectBName, collisionEventAB);
        }
    }
    /**
     * This method detects collisions for all the GameObjects within the Level
     * and return
     * a list of CollisionEvents
     * 
     * @param level
     * @return
     */
    public abstract void checkCollisions (Level level);

    /**
     * This method takes two colliding objects and return the corresponding
     * CollsionEvent
     * object handling that specific collision.
     * 
     * @param objectA
     * @param objectB
     * @return
     */
    public CollisionEvent getCollisionEvent (GameObject objectA, GameObject objectB) {
        if (collisionEventsMap.containsKey(objectA.getClass().getCanonicalName())
                && collisionEventsMap.get(objectA.getClass().getCanonicalName())
                        .containsKey(objectB.getClass().getCanonicalName())) {
            return collisionEventsMap.get(objectA.getClass().getCanonicalName())
                    .get(objectB.getClass().getCanonicalName());
        }
        else if (collisionEventsMap
                .containsKey(objectB.getClass().getCanonicalName())
                && collisionEventsMap.get(objectB.getClass().getCanonicalName())
                        .containsKey(objectA.getClass().getCanonicalName())) {
            return collisionEventsMap.get(objectB.getClass().getCanonicalName())
                    .get(objectA.getClass().getCanonicalName());
        }
        else {
            return null;
        }
    }

    public void addCollisionEvents (String nameA, String nameB, CollisionEvent collisionEventAB) {
        if (!collisionEventsMap.containsKey(nameA)) {
            collisionEventsMap.put(nameA, new HashMap<String, CollisionEvent>());
        }
        collisionEventsMap.get(nameA).put(nameB, collisionEventAB);
    }
}