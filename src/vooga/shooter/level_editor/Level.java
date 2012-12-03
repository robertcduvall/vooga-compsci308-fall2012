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
import util.xml.XmlUtilities;
import vooga.shooter.gameObjects.Sprite;


/**
 * Level.java
 * Maintains/Updates Sprites contained in a single level of the game
 * (This is derived from a previous cs308 project)
 * 
 * @author Niel Lebeck, Connor Gordon, Alex Browne
 *      edited by Tommy Petrilak
 * 
 */
public class Level implements Packable<Level> {
    private Image myBackgroundImage;
    private String myBackgroundImagePath;
    private List<Sprite> mySprites;
    private Level myNextLevel;

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
    
    
    public void removeAllSprites() {
        mySprites.clear();
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
            s.paint(g);
        }
    }

    /**
     * For now, we're going to assume that you win the level
     * by destroying all the enemies. This might need to be
     * customizable.
     * 
     * @return a boolean representing whether or not the level
     *         has been won.
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
     *        will be set as the background image
     */
    public void setBackgroundImage (String imagePath) {
        this.myBackgroundImage = (new ImageIcon(imagePath)).getImage();
        this.myBackgroundImagePath = imagePath;
    }

    @Override
    public Document pack () {
        Document doc = XmlUtilities.makeDocument();
        Element element = XmlUtilities.makeElement(doc, "Level", "backgroundImage", myBackgroundImagePath);
        doc.appendChild(element);
        return doc;
    }

    public static Level unpack (Document xmlData) {
        Element root = xmlData.getDocumentElement();
        String bgImagePath = root.getAttribute("backgroundImage");
        String className = Level.class.getName();
        return (Level) Reflection.createInstance(className, bgImagePath);
    }

    /**
     * startLevel method will be overridden at the beginning of each Level,
     * specifying which enemies to create/where/what else to create for each
     * Level
     */
    public void startLevel () {
    }
    
    /**
     * @return the myNextLevel
     */
    public Level getNextLevel () {
        return myNextLevel;
    }

    /**
     * @param myNextLevel the myNextLevel to set
     */
    public void setNextLevel (Level myNextLevel) {
        this.myNextLevel = myNextLevel;
    }

    // /**
    // * Resets level to beginning
    // */
    // public abstract void reset ();

    // TODO: implement this method if needed

}
