package vooga.shooter.gameObjects;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.swing.ImageIcon;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import util.pack.Packable;
import util.xml.XmlUtilities;
import vooga.shooter.gameObjects.spriteUtilities.SpriteActionInterface;

/**
 * Represents an enemy sprite to be used in the game.
 * Enemies start with velocity and health.
 *
 * @author Jesse Starr
 * (add your own name as you edit)
 */
public class Enemy extends Sprite implements Packable {
    
    private String imagePath;

    /**
     * @deprecated Please pass in the imagePath instead of an image.
     * (See the constructor below). We need the imagePath so that we 
     * can recreate the Enemy object in the unpack() method. The original
     * path of the image source file cannot be retrieved from an Image
     * object.
     * 
     * Constructs an enemy character for the game.
     * @param position the center of the image
     * @param size the size of the image
     * @param bounds the size of the canvas
     * @param image the image to use
     * @param velocity the starting velocity for the enemy
     * @param health the starting health of the enemy
     */
    public Enemy (Point position, Dimension size, Dimension bounds,
            Image image, Point velocity, int health) {
        super(position, size, bounds, image, velocity, health);
    }
    
    public Enemy (Point position, Dimension size, Dimension bounds,
        String imagePath, Point velocity, int health) {
        super(position, size, bounds, (new ImageIcon(imagePath)).getImage(), velocity, health);
        this.imagePath = imagePath;
    }

    /**
     * This method is called after the enemy's position is updated.
     * Makes the enemy do something else after moving (e.g. fire a
     * shot).
     */
    protected void continueUpdate() {
        for (Bullet b : getBulletsFired()) {
            b.update();
        }
    }

    /**
     * Returns the type of the sprite.
     * @return "enemy"
     */
    public String getType() {
        return ENEMY_TYPE;
    }

    /**
     * Paints bullets of enemy.
     */
    protected void continuePaint (Graphics pen) {
        List<Bullet> deadBullets = new ArrayList<Bullet>();

        for (Bullet b : getBulletsFired()) {
            if (b.getImage() == null) {
                deadBullets.add(b);
            }
            else {
                b.paint(pen);
            }
        }

        getBulletsFired().removeAll(deadBullets);
    }

    /**
     * Sets the methods for enemies.
     */
    @Override
    void setMethods () {
        //if the enemy is hit by a player's bullet then both
        //bullet and enemy die
        getMapper().addPair(HIT_BY_BULLET, new SpriteActionInterface() {
            public void doAction(Object...o) {
                String bulletOwnerType = ((Bullet) o[0]).getOwner().getType();
                if (PLAYER_TYPE.equals(bulletOwnerType)) {
                    die();
                    ((Bullet) o[0]).die();
                }
            }
        });

        getMapper().addPair(HIT_BY_PLAYER, new SpriteActionInterface() {
            public void doAction(Object...o) {
                die();
                ((Player) o[0]).die();
            }
        });

        //do nothing if an enemy intersects an enemy
        getMapper().addPair(HIT_BY_ENEMY, this);
    }

    @Override
    public Document pack () {
        
        Document doc = XmlUtilities.makeDocument();
        Element root = XmlUtilities.makeElement(doc, "Enemy");
        doc.appendChild(root);
                
        // convert position...
        double pointX = getPosition().getX();
        double pointY = getPosition().getY();
        HashMap<String, String> positionMap = new HashMap<String, String>();
        positionMap.put("x", Integer.toString((int) pointX));
        positionMap.put("y", Integer.toString((int) pointY));
        XmlUtilities.appendElement(doc, root, "position", positionMap);
        
        // convert size...
        double sizeWidth = getSize().getWidth();
        double sizeHeight = getSize().getHeight();
        HashMap<String, String> sizeMap = new HashMap<String, String>();
        sizeMap.put("width", Integer.toString((int) sizeWidth));
        sizeMap.put("height", Integer.toString((int) sizeHeight));
        XmlUtilities.appendElement(doc, root, "size", sizeMap);
        
        // convert bounds...
        double boundsWidth = getBounds().getWidth();
        double boundsHeight = getBounds().getHeight();
        HashMap<String, String> boundsMap = new HashMap<String, String>();
        boundsMap.put("width", Integer.toString((int) boundsWidth));
        boundsMap.put("height", Integer.toString((int) boundsHeight));
        XmlUtilities.appendElement(doc, root, "bounds", boundsMap);
        
        // convert image...
        XmlUtilities.appendElement(doc, root, "image", "path", imagePath);
        
        // convert velocity...
        double velX = getVelocity().getX();
        double velY = getVelocity().getY();
        HashMap<String, String> velMap = new HashMap<String, String>();
        velMap.put("x", Integer.toString((int) velX));
        velMap.put("y", Integer.toString((int) velY));
        XmlUtilities.appendElement(doc, root, "velocity", velMap);
        
        // convert health...
        XmlUtilities.appendElement(doc, root, "health", "value", Integer.toString(getHealth()));
        
        return doc;
    }

    public static Enemy unpack (Document xmlData) {
      
        Element root = (Element) xmlData.getFirstChild();
        
        // parse position...
        Element positionElement = XmlUtilities.getElement(root, "position");
        int pointX = XmlUtilities.getAttributeAsInt(positionElement, "x");
        int pointY = XmlUtilities.getAttributeAsInt(positionElement, "y");
        Point position = new Point(pointX, pointY);
        
        // parse size...
        Element sizeElement = XmlUtilities.getElement(root, "size");
        int sizeWidth = XmlUtilities.getAttributeAsInt(sizeElement, "width");
        int sizeHeight = XmlUtilities.getAttributeAsInt(sizeElement, "height");
        Dimension size = new Dimension(sizeWidth, sizeHeight);
        
        // parse bounds...
        Element boundsElement = XmlUtilities.getElement(root, "bounds");
        int boundsWidth = XmlUtilities.getAttributeAsInt(boundsElement, "width");
        int boundsHeight = XmlUtilities.getAttributeAsInt(boundsElement, "height");
        Dimension bounds = new Dimension(boundsWidth, boundsHeight);
        
        // parse image...
        Element imageElement = XmlUtilities.getElement(root, "image");
        String imagePath = imageElement.getAttribute("path");
        
        // parse velocity...
        Element velElement = XmlUtilities.getElement(root, "velocity");
        int velX = XmlUtilities.getAttributeAsInt(velElement, "x");
        int velY = XmlUtilities.getAttributeAsInt(velElement, "y");
        Point velocity = new Point(velX, velY);
        
        // parse health...
        Element healthElement = XmlUtilities.getElement(root, "health");
        int health = XmlUtilities.getAttributeAsInt(healthElement, "value");
        
        return new Enemy(position, size, bounds, imagePath, velocity, health);
    }

}
