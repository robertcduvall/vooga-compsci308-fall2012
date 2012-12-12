package vooga.shooter.level_editor;

import java.awt.Dimension;
import java.awt.Point;
import java.io.File;
import java.util.HashMap;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import util.reflection.Reflection;
import util.xml.XmlUtilities;
import vooga.shooter.gameObjects.Enemy;
import vooga.shooter.gameObjects.Player;
import vooga.shooter.gameObjects.Sprite;
import vooga.shooter.gameObjects.intelligence.AI;


/**
 * A Utility class for loading levels from an XML file
 * or storing a Level object into an XML file. It will
 * enocde/decode all the information about the level,
 * including it's enemy sprites, background image, etc.
 * 
 * @author Alex Browne
 * 
 */
public class LevelFactory {

    /**
     * Loads a level from an XML document.
     * 
     * @param xmlDoc the XML document with all the
     *        level information.
     * @return the Level object created from the
     *         xml data.
     */
    public static Level loadLevel (Document xmlDoc) {
        Element root = xmlDoc.getDocumentElement();

        // Retrieve the background image
        String bgImagePath = root.getAttribute("backgroundImage");

        // Instantiate a level class ...
        String className = Level.class.getName();
        Level level = (Level) Reflection.createInstance(className, bgImagePath);

        // All the children are either enemies or the player.
        // We are going to iterate over them...
        Node child = root.getFirstChild();

        // make sure the node can be converted to Element
        while (child.getNodeType() != Node.ELEMENT_NODE) {
            child = child.getNextSibling();
        }

        // Initialize player outside the loop so it has the needed scope.
        Player player = null;

        while (child != null) {

            Element sibling = (Element) child;

            if (sibling.getNodeName() == "Player") {

                // Instantiate a Player from the data in the Element
                player = unpackPlayer(sibling);
                level.setPlayer(player);
                level.addSprite(player);

            }
            else {

                // Instantiate an Enemy from the data in the Element
                Enemy enemy = unpackEnemy(sibling, player);
                level.addSprite(enemy);

            }

            child = sibling.getNextSibling();

        }

        return level;
    }

    /**
     * Loads a level from an XML file.
     * 
     * @param xmlDoc the XML file with all the
     *        level information.
     * @return the Level object created from the
     *         xml data.
     */
    public static Level loadLevel (File xmlFile) {
        return loadLevel(XmlUtilities.makeDocument(xmlFile));
    }

    /**
     * Converts a Level object to its canonical
     * xml representation.
     * 
     * @param level the Level object to be converted
     *        to XML.
     * @return an XML document with all the level
     *         data.
     */
    public static Document storeLevel (Level level) {
        Document doc = XmlUtilities.makeDocument();
        Element root = XmlUtilities.makeElement(doc, "Level",
                "backgroundImage", level.getBackgroundImagePath());
        doc.appendChild(root);

        packPlayer(level.getPlayer(), doc, root);

        for (Sprite sprite : level.getSpriteList()) {
            if (sprite instanceof Enemy) {
                Enemy enemy = (Enemy) sprite;
                if (sprite != null) {
                    packEnemy(enemy, doc, root);
                }
            }
        }

        return doc;
    }

    private static Element packEnemy (Enemy enemy, Document doc, Element root) {

        Element enemyElement = packSprite(enemy, doc, root, "Enemy");

        // convert AI (we'll store the class name)
        XmlUtilities.appendElement(doc, enemyElement, "ai", "name", enemy
                .getAI().getClass().getName());

        return enemyElement;
    }

    private static Enemy unpackEnemy (Element enemyElement, Player player) {

        Enemy enemy = (Enemy) unpackSprite(enemyElement, Enemy.class.getName());

        // parse ai...
        Element aiElement = XmlUtilities.getElement(enemyElement, "ai");
        String aiAsString = XmlUtilities.getAttribute(aiElement, "name");

        // This part might need some work! Trying to figure stuff out.
        AI ai = (AI) Reflection.createInstance(aiAsString, enemy, player);
        enemy.setAI(ai);

        return enemy;
    }

    private static Element packPlayer (Player player, Document doc, Element root) {
        return packSprite(player, doc, root, "Player");
    }

    private static Player unpackPlayer (Element playerElement) {
        return (Player) unpackSprite(playerElement, Player.class.getName());
    }

    private static Element packSprite (Sprite sprite, Document doc,
            Element root, String tag) {

        Element spriteElement = XmlUtilities.appendElement(doc, root, tag);

        // convert position...
        double pointX = sprite.getPosition().getX();
        double pointY = sprite.getPosition().getY();
        HashMap<String, String> positionMap = new HashMap<String, String>();
        positionMap.put("x", Integer.toString((int) pointX));
        positionMap.put("y", Integer.toString((int) pointY));
        XmlUtilities.appendElement(doc, spriteElement, "position", positionMap);

        // convert size...
        double sizeWidth = sprite.getSize().getWidth();
        double sizeHeight = sprite.getSize().getHeight();
        HashMap<String, String> sizeMap = new HashMap<String, String>();
        sizeMap.put("width", Integer.toString((int) sizeWidth));
        sizeMap.put("height", Integer.toString((int) sizeHeight));
        XmlUtilities.appendElement(doc, spriteElement, "size", sizeMap);

        // convert bounds...
        double boundsWidth = sprite.getBounds().getWidth();
        double boundsHeight = sprite.getBounds().getHeight();
        HashMap<String, String> boundsMap = new HashMap<String, String>();
        boundsMap.put("width", Integer.toString((int) boundsWidth));
        boundsMap.put("height", Integer.toString((int) boundsHeight));
        XmlUtilities.appendElement(doc, spriteElement, "bounds", boundsMap);

        // convert image...
        XmlUtilities.appendElement(doc, spriteElement, "image", "path", sprite
                .getImagePath().split("src/")[1]);

        // convert velocity...
        double velX = sprite.getVelocity().getX();
        double velY = sprite.getVelocity().getY();
        HashMap<String, String> velMap = new HashMap<String, String>();
        velMap.put("x", Integer.toString((int) velX));
        velMap.put("y", Integer.toString((int) velY));
        XmlUtilities.appendElement(doc, spriteElement, "velocity", velMap);

        // convert health...
        XmlUtilities.appendElement(doc, spriteElement, "health", "value",
                Integer.toString(sprite.getHealth()));

        return spriteElement;
    }

    private static Sprite unpackSprite (Element spriteElement, String type) {

        // parse position...
        Element positionElement = XmlUtilities.getElement(spriteElement,
                "position");
        int pointX = XmlUtilities.getAttributeAsInt(positionElement, "x");
        int pointY = XmlUtilities.getAttributeAsInt(positionElement, "y");
        Point position = new Point(pointX, pointY);

        // parse size...
        Element sizeElement = XmlUtilities.getElement(spriteElement, "size");
        int sizeWidth = XmlUtilities.getAttributeAsInt(sizeElement, "width");
        int sizeHeight = XmlUtilities.getAttributeAsInt(sizeElement, "height");
        Dimension size = new Dimension(sizeWidth, sizeHeight);

        // parse bounds...
        Element boundsElement = XmlUtilities
                .getElement(spriteElement, "bounds");
        int boundsWidth = XmlUtilities
                .getAttributeAsInt(boundsElement, "width");
        int boundsHeight = XmlUtilities.getAttributeAsInt(boundsElement,
                "height");
        Dimension bounds = new Dimension(boundsWidth, boundsHeight);

        // parse image...
        Element imageElement = XmlUtilities.getElement(spriteElement, "image");
        String imagePath = imageElement.getAttribute("path");

        // parse velocity...
        Element velElement = XmlUtilities.getElement(spriteElement, "velocity");
        int velX = XmlUtilities.getAttributeAsInt(velElement, "x");
        int velY = XmlUtilities.getAttributeAsInt(velElement, "y");
        Point velocity = new Point(velX, velY);

        // parse health...
        Element healthElement = XmlUtilities
                .getElement(spriteElement, "health");
        int health = XmlUtilities.getAttributeAsInt(healthElement, "value");

        // This part might need some work! Trying to figure stuff out.
        Sprite sprite = (Sprite) Reflection.createInstance(type, position,
                size, bounds, imagePath, velocity, health);

        return sprite;
    }

}
