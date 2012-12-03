package vooga.shooter.level_editor;

import java.awt.Dimension;
import java.awt.Point;
import java.io.File;
import java.util.HashMap;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import util.xml.XmlUtilities;
import vooga.shooter.gameObjects.Enemy;

public class LevelFactory {

    public static Level loadLevel(Document xmlDoc) {
        return null;
        
    }
    
    public static Level loadLevel(File xmlFile) {
        return null;
        
    }
  
// From Level
    
//    @Override
//    public Document pack () {
//        Document doc = XmlUtilities.makeDocument();
//        Element element = XmlUtilities.makeElement(doc, "Level", "backgroundImage", myBackgroundImagePath);
//        doc.appendChild(element);
//        
//        for (Sprite sprite : mySprites) {
//            if (sprite.getClass().getName() == Enemy.class.getName()) {
//                Enemy enemy = (Enemy) sprite;
//                Document enemyDoc = sprite.pack();
//            }
//        }
//        
//        return doc;
//    }
//
//    public static Level unpack (Document xmlData) {
//        Element root = xmlData.getDocumentElement();
//        String bgImagePath = root.getAttribute("backgroundImage");
//        String className = Level.class.getName();
//        return (Level) Reflection.createInstance(className, bgImagePath);
//    }
    

// From Enemy    
//    
//    @Override
//    public Document pack () {
//        
//        Document doc = XmlUtilities.makeDocument();
//        Element root = XmlUtilities.makeElement(doc, "Enemy");
//        doc.appendChild(root);
//                
//        // convert position...
//        double pointX = getPosition().getX();
//        double pointY = getPosition().getY();
//        HashMap<String, String> positionMap = new HashMap<String, String>();
//        positionMap.put("x", Integer.toString((int) pointX));
//        positionMap.put("y", Integer.toString((int) pointY));
//        XmlUtilities.appendElement(doc, root, "position", positionMap);
//        
//        // convert size...
//        double sizeWidth = getSize().getWidth();
//        double sizeHeight = getSize().getHeight();
//        HashMap<String, String> sizeMap = new HashMap<String, String>();
//        sizeMap.put("width", Integer.toString((int) sizeWidth));
//        sizeMap.put("height", Integer.toString((int) sizeHeight));
//        XmlUtilities.appendElement(doc, root, "size", sizeMap);
//        
//        // convert bounds...
//        double boundsWidth = getBounds().getWidth();
//        double boundsHeight = getBounds().getHeight();
//        HashMap<String, String> boundsMap = new HashMap<String, String>();
//        boundsMap.put("width", Integer.toString((int) boundsWidth));
//        boundsMap.put("height", Integer.toString((int) boundsHeight));
//        XmlUtilities.appendElement(doc, root, "bounds", boundsMap);
//        
//        // convert image...
//        XmlUtilities.appendElement(doc, root, "image", "path", imagePath);
//        
//        // convert velocity...
//        double velX = getVelocity().getX();
//        double velY = getVelocity().getY();
//        HashMap<String, String> velMap = new HashMap<String, String>();
//        velMap.put("x", Integer.toString((int) velX));
//        velMap.put("y", Integer.toString((int) velY));
//        XmlUtilities.appendElement(doc, root, "velocity", velMap);
//        
//        // convert health...
//        XmlUtilities.appendElement(doc, root, "health", "value", Integer.toString(getHealth()));
//        
//        return doc;
//    }
//
//    public static Enemy unpack (Document xmlData) {
//      
//        Element root = (Element) xmlData.getFirstChild();
//        
//        // parse position...
//        Element positionElement = XmlUtilities.getElement(root, "position");
//        int pointX = XmlUtilities.getAttributeAsInt(positionElement, "x");
//        int pointY = XmlUtilities.getAttributeAsInt(positionElement, "y");
//        Point position = new Point(pointX, pointY);
//        
//        // parse size...
//        Element sizeElement = XmlUtilities.getElement(root, "size");
//        int sizeWidth = XmlUtilities.getAttributeAsInt(sizeElement, "width");
//        int sizeHeight = XmlUtilities.getAttributeAsInt(sizeElement, "height");
//        Dimension size = new Dimension(sizeWidth, sizeHeight);
//        
//        // parse bounds...
//        Element boundsElement = XmlUtilities.getElement(root, "bounds");
//        int boundsWidth = XmlUtilities.getAttributeAsInt(boundsElement, "width");
//        int boundsHeight = XmlUtilities.getAttributeAsInt(boundsElement, "height");
//        Dimension bounds = new Dimension(boundsWidth, boundsHeight);
//        
//        // parse image...
//        Element imageElement = XmlUtilities.getElement(root, "image");
//        String imagePath = imageElement.getAttribute("path");
//        
//        // parse velocity...
//        Element velElement = XmlUtilities.getElement(root, "velocity");
//        int velX = XmlUtilities.getAttributeAsInt(velElement, "x");
//        int velY = XmlUtilities.getAttributeAsInt(velElement, "y");
//        Point velocity = new Point(velX, velY);
//        
//        // parse health...
//        Element healthElement = XmlUtilities.getElement(root, "health");
//        int health = XmlUtilities.getAttributeAsInt(healthElement, "value");
//        
//        return new Enemy(position, size, bounds, imagePath, velocity, health);
//    }

    

    
}
