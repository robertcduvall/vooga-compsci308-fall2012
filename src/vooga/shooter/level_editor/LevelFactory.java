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
     *          level information.
     * @return the Level object created from the
     *          xml data.
     */
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
         
         Player player = null;
         
         
         Element sibling = (Element) child;
         
         while (sibling != null) {
                          
             if (sibling.getNodeName() == "Player") {
                 
                 player = unpackPlayer(sibling);
                 
             } else {
             
                 // Instantiate an enemy from the data in the Element
                 Enemy enemy = unpackEnemy(sibling, player);
                 level.addSprite(enemy);
                 
             }
             
             sibling = (Element) sibling.getNextSibling();
             
         }
         
         return level;
    }

    /**
     * Loads a level from an XML file.
     * 
     * @param xmlDoc the XML file with all the
     *          level information.
     * @return the Level object created from the
     *          xml data.
     */
    public static Level loadLevel (File xmlFile) {
        return loadLevel(XmlUtilities.makeDocument(xmlFile));
    }

    /**
     * Converts a Level object to its canonical
     * xml representation.
     * 
     * @param level the Level object to be converted
     *          to XML.
     * @return an XML document with all the level
     *          data.
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
                enemy.getImagePath().split("src/")[1]);

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

    private static Enemy unpackEnemy (Element enemyElement, Player player) {

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
        AI ai = (AI) Reflection.createInstance(aiAsString, enemy, player);
        enemy.setAI(ai);
        
        return enemy;
    }
    
    private static Element packPlayer (Player player, Document doc, Element root) {

        // here lies the bug. Somehow player is being
        // passed in as null here.
        if (player == null) {
            return null;
        }
        
        Element playerElement = XmlUtilities.appendElement(doc, root, "Player");
                
        // convert position...
        double pointX = player.getPosition().getX();
        double pointY = player.getPosition().getY();
        HashMap<String, String> positionMap = new HashMap<String, String>();
        positionMap.put("x", Integer.toString((int) pointX));
        positionMap.put("y", Integer.toString((int) pointY));
        XmlUtilities.appendElement(doc, playerElement, "position", positionMap);

        // convert size...
        double sizeWidth = player.getSize().getWidth();
        double sizeHeight = player.getSize().getHeight();
        HashMap<String, String> sizeMap = new HashMap<String, String>();
        sizeMap.put("width", Integer.toString((int) sizeWidth));
        sizeMap.put("height", Integer.toString((int) sizeHeight));
        XmlUtilities.appendElement(doc, playerElement, "size", sizeMap);

        // convert bounds...
        double boundsWidth = player.getBounds().getWidth();
        double boundsHeight = player.getBounds().getHeight();
        HashMap<String, String> boundsMap = new HashMap<String, String>();
        boundsMap.put("width", Integer.toString((int) boundsWidth));
        boundsMap.put("height", Integer.toString((int) boundsHeight));
        XmlUtilities.appendElement(doc, playerElement, "bounds", boundsMap);

        // convert image...
        XmlUtilities.appendElement(doc, playerElement, "image", "path",
                player.getImagePath().split("src/")[1]);

        // convert velocity...
        double velX = player.getVelocity().getX();
        double velY = player.getVelocity().getY();
        HashMap<String, String> velMap = new HashMap<String, String>();
        velMap.put("x", Integer.toString((int) velX));
        velMap.put("y", Integer.toString((int) velY));
        XmlUtilities.appendElement(doc, playerElement, "velocity", velMap);

        // convert health...
        XmlUtilities.appendElement(doc, playerElement, "health", "value",
                Integer.toString(player.getHealth()));
        
        return playerElement;
    }

    private static Player unpackPlayer (Element playerElement) {

        // parse position...
        Element positionElement = XmlUtilities.getElement(playerElement, "position");
        int pointX = XmlUtilities.getAttributeAsInt(positionElement, "x");
        int pointY = XmlUtilities.getAttributeAsInt(positionElement, "y");
        Point position = new Point(pointX, pointY);

        // parse size...
        Element sizeElement = XmlUtilities.getElement(playerElement, "size");
        int sizeWidth = XmlUtilities.getAttributeAsInt(sizeElement, "width");
        int sizeHeight = XmlUtilities.getAttributeAsInt(sizeElement, "height");
        Dimension size = new Dimension(sizeWidth, sizeHeight);

        // parse bounds...
        Element boundsElement = XmlUtilities.getElement(playerElement, "bounds");
        int boundsWidth = XmlUtilities
                .getAttributeAsInt(boundsElement, "width");
        int boundsHeight = XmlUtilities.getAttributeAsInt(boundsElement,
                "height");
        Dimension bounds = new Dimension(boundsWidth, boundsHeight);

        // parse image...
        Element imageElement = XmlUtilities.getElement(playerElement, "image");
        String imagePath = imageElement.getAttribute("path");

        // parse velocity...
        Element velElement = XmlUtilities.getElement(playerElement, "velocity");
        int velX = XmlUtilities.getAttributeAsInt(velElement, "x");
        int velY = XmlUtilities.getAttributeAsInt(velElement, "y");
        Point velocity = new Point(velX, velY);

        // parse health...
        Element healthElement = XmlUtilities.getElement(playerElement, "health");
        int health = XmlUtilities.getAttributeAsInt(healthElement, "value");
        
        // This part might need some work! Trying to figure stuff out.
        Player player = new Player(position, size, bounds, imagePath, velocity, health);
        
        return player;
    }

}
