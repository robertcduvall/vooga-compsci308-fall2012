package vooga.shooter.level_editor;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.ImageIcon;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import util.pack.Packable;
import util.reflection.Reflection;
import util.xml.XmlBuilder;
import vooga.shooter.gameObjects.Sprite;

/**
 * Level.java
 * Maintains/Updates Sprites contained in a single level of the game
 * (This is derived from a previous cs308 project)
 * 
 * @author Niel Lebeck, Connor Gordon, Alex Browne
 * 
 */
public class Level implements Packable<Level>{
    private Image myBackgroundImage;
    private String myBackgroundImagePath;
    private List<Sprite> mySprites;

    /**
     * Default constructor
     */
    public Level () {
        mySprites = new ArrayList<Sprite>();
    }
    
    /**
     * 
     * @param backgroundImagePath the path to a background image
     */
    public Level (String backgroundImagePath) {
        this();
        setBackgroundImage(backgroundImagePath);
    }
    
    /**
     * 
     * @param backgroundImageFile a background image file
     */
    public Level (File backgroundImageFile) {
        this(backgroundImageFile.getPath());
    }

    /**
     * Returns unmodifiable version of all sprites contained in the level
     * 
     * @return list of contained sprites
     */
    public Iterable<Sprite> getSpriteList () {
        return Collections.unmodifiableList(mySprites);
    }
    
    /**
     * Add specified sprite to the level
     * 
     * @param s Sprite to be added
     */
    public void addSprite (Sprite s) {
        mySprites.add(s);
    }

    /**
     * Removes specified sprite from the level
     * 
     * @param s Sprite to be removed
     */
    public void removeSprite (Sprite s) {
        mySprites.remove(s);
    }


    /**
     * Paints each sprite in the level
     * 
     * @param g Graphics Drawing Object
     * @param offsetX relative point of origin- x
     * @param offsetY relative point of origin -y
     */
    public void paintSprites (Graphics g, int offsetX, int offsetY) {
        for (Sprite s : mySprites) {
            // uncomment this when the errors in Sprite.java are fixed
            // s.draw(g);
        }
    }
    
    /**
     * For now, we're going to assume that you win the level
     * by destroying all the enemies. This might need to be
     * customizable.
     * 
     * @return a boolean representing whether or not the level
     * has been won.
     */
    
    public boolean winningConditionsMet () {
        return mySprites.isEmpty();
    }
    
    /**
     * @return myBackgroundImage
     */
    public Image getBackgroundImage () {
        return myBackgroundImage;
    }
    
    /**
     * @param imageFile a file to set as the background image
     */
    public void setBackgroundImage (File imageFile) {
        setBackgroundImage(imageFile.getPath());
    }
    
    /**
     * @param imagePath the pathname to an image file that
     * will be set as the background image
     */
    public void setBackgroundImage (String imagePath) {
        this.myBackgroundImage = (new ImageIcon(imagePath)).getImage();
        this.myBackgroundImagePath = imagePath;
    }
    

    @Override
    public Document pack () {
        // TODO Use our consolidated xml tools for this.
        Document doc = XmlBuilder.createDocument();
        Element rootElement = doc.createElement("Level");
        doc.appendChild(rootElement);
        rootElement.setAttribute("backgroundImage", myBackgroundImagePath);
        return doc;
    }

    @Override
    public Level unpack (Document xmlData) {
        Element rootElement = (Element) xmlData.getFirstChild();
        String bgImagePath = rootElement.getAttribute("backgroundImage");
        String className = this.getClass().getName();
        return (Level) Reflection.createInstance(className, bgImagePath);
    }

//    /**
//     * Resets level to beginning
//     */
//    public abstract void reset ();
    
    // TODO: implement this method if needed

}
