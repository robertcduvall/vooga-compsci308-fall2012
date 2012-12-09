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
import vooga.shooter.gameObjects.Sprite;
import vooga.shooter.gameObjects.intelligence.AI;

/**
 * TODO: Add javadoc comments
 * 
 * @author Alex Browne
 *
 */
public class LevelFactory {

    public static Level loadLevel (Document xmlDoc) {
         Element root = xmlDoc.getDocumentElement();
         
         // Retrieve the background image
         String bgImagePath = root.getAttribute("backgroundImage");
         
         // TODO: if necessary, retrieve other properties of the level.
         
         // Instantiate a level class ...
         String className = Level.class.getName();
         Level level = (Level) Reflection.createInstance(className, bgImagePath);
         
         // All the children are enemies. We are going to iterate over them
         Node child = root.getFirstChild();
         
         // make sure the node can be converted to Element
         while (child.getNodeType() != Node.ELEMENT_NODE) {
             child = child.getNextSibling();
         }
         Element sibling = (Element) child;
         
         while (sibling != null) {
             
             // make sure the node can be converted to Element
             if (sibling.getNodeType() != Node.ELEMENT_NODE) continue;
             
             // Instantiate an enemy from the data in the Element
             Enemy enemy = unpackEnemy(sibling);
             level.addSprite(enemy);
             sibling = (Element) sibling.getNextSibling();
         }
         
         return level;
    }

    public static Level loadLevel (File xmlFile) {
        return loadLevel(XmlUtilities.makeDocument(xmlFile));
    }

    public static Document storeLevel (Level level) {
        Document doc = XmlUtilities.makeDocument();
        Element root = XmlUtilities.makeElement(doc, "Level",
                "backgroundImage", level.getBackgroundImagePath());
        doc.appendChild(root);

        for (Sprite sprite : level.getSpriteList()) {
            if (sprite.getClass().getName() == Enemy.class.getName()) {
                Enemy enemy = (Enemy) sprite;
                packEnemy(enemy, doc, root);
            }
        }

        return doc;
    }

    private static Element packEnemy (Enemy enemy, Document doc, Element root) {

        Element enemyElement = XmlUtilities.appendElement(doc, root, "Enemy");

        // convert position...
        double pointX = enemy.getPosition().getX();
        double pointY = enemy.getPosition().getY();
        HashMap<String, String> positionMap = new HashMap<String, String>();
        positionMap.put("x", Integer.toString((int) pointX));
        positionMap.put("y", Integer.toString((int) pointY));
        XmlUtilities.appendElement(doc, enemyElement, "position", positionMap);

        // convert size...
        double sizeWidth = enemy.getSize().getWidth();
        double sizeHeight = enemy.getSize().getHeight();
        HashMap<String, String> sizeMap = new HashMap<String, String>();
        sizeMap.put("width", Integer.toString((int) sizeWidth));
        sizeMap.put("height", Integer.toString((int) sizeHeight));
        XmlUtilities.appendElement(doc, enemyElement, "size", sizeMap);

        // convert bounds...
        double boundsWidth = enemy.getBounds().getWidth();
        double boundsHeight = enemy.getBounds().getHeight();
        HashMap<String, String> boundsMap = new HashMap<String, String>();
        boundsMap.put("width", Integer.toString((int) boundsWidth));
        boundsMap.put("height", Integer.toString((int) boundsHeight));
        XmlUtilities.appendElement(doc, enemyElement, "bounds", boundsMap);

        // convert image...
        XmlUtilities.appendElement(doc, enemyElement, "image", "path",
                enemy.getImagePath());

        // convert velocity...
        double velX = enemy.getVelocity().getX();
        double velY = enemy.getVelocity().getY();
        HashMap<String, String> velMap = new HashMap<String, String>();
        velMap.put("x", Integer.toString((int) velX));
        velMap.put("y", Integer.toString((int) velY));
        XmlUtilities.appendElement(doc, enemyElement, "velocity", velMap);

        // convert health...
        XmlUtilities.appendElement(doc, enemyElement, "health", "value",
                Integer.toString(enemy.getHealth()));
        
        // convert AI (we'll store the class name)
        XmlUtilities.appendElement(doc, enemyElement, "ai", "name", enemy.getAI().getClass().getName());

        return enemyElement;
    }

    private static Enemy unpackEnemy (Element enemyElement) {

        // parse position...
        Element positionElement = XmlUtilities.getElement(enemyElement, "position");
        int pointX = XmlUtilities.getAttributeAsInt(positionElement, "x");
        int pointY = XmlUtilities.getAttributeAsInt(positionElement, "y");
        Point position = new Point(pointX, pointY);

        // parse size...
        Element sizeElement = XmlUtilities.getElement(enemyElement, "size");
        int sizeWidth = XmlUtilities.getAttributeAsInt(sizeElement, "width");
        int sizeHeight = XmlUtilities.getAttributeAsInt(sizeElement, "height");
        Dimension size = new Dimension(sizeWidth, sizeHeight);

        // parse bounds...
        Element boundsElement = XmlUtilities.getElement(enemyElement, "bounds");
        int boundsWidth = XmlUtilities
                .getAttributeAsInt(boundsElement, "width");
        int boundsHeight = XmlUtilities.getAttributeAsInt(boundsElement,
                "height");
        Dimension bounds = new Dimension(boundsWidth, boundsHeight);

        // parse image...
        Element imageElement = XmlUtilities.getElement(enemyElement, "image");
        String imagePath = imageElement.getAttribute("path");

        // parse velocity...
        Element velElement = XmlUtilities.getElement(enemyElement, "velocity");
        int velX = XmlUtilities.getAttributeAsInt(velElement, "x");
        int velY = XmlUtilities.getAttributeAsInt(velElement, "y");
        Point velocity = new Point(velX, velY);

        // parse health...
        Element healthElement = XmlUtilities.getElement(enemyElement, "health");
        int health = XmlUtilities.getAttributeAsInt(healthElement, "value");
        
        // parse ai...
        Element aiElement = XmlUtilities.getElement(enemyElement, "ai");
        String aiAsString = XmlUtilities.getAttribute(aiElement, "name");
        
        // This part might need some work! Trying to figure stuff out.
        Enemy enemy = new Enemy(position, size, bounds, imagePath, velocity, health);
        AI ai = (AI) Reflection.createInstance(aiAsString, enemy);
        enemy.setAI(ai);
        
        return enemy;
    }

}
