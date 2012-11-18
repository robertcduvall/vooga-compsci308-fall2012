package vooga.shooter.level_editor;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import vooga.shooter.gameObjects.Sprite;

/**
 * Level.java
 * Maintains/Updates Sprites contained in a single level of the game
 * (This is derived from a previous cs308 project)
 * 
 * @author Niel Lebeck, Connor Gordon, Alex Browne
 * 
 */
public class Level {
    private List<Sprite> mySpriteList;

    /**
     * Initializes Sprite and Character lists
     */
    public Level () {
        mySpriteList = new ArrayList<Sprite>();
    }

    /**
     * Returns unmodifiable version of all sprites contained in the level
     * 
     * @return list of contained sprites
     */
    public Iterable<Sprite> getSpriteList () {
        return Collections.unmodifiableList(mySpriteList);
    }
    
    /**
     * Add specified sprite to the level
     * 
     * @param s Sprite to be added
     */
    public void addSprite (Sprite s) {
        mySpriteList.add(s);
    }

    /**
     * Removes specified sprite from the level
     * 
     * @param s Sprite to be removed
     */
    public void removeSprite (Sprite s) {
        mySpriteList.remove(s);
    }


    /**
     * Paints each sprite in the level
     * 
     * @param g Graphics Drawing Object
     * @param offsetX relative point of origin- x
     * @param offsetY relative point of origin -y
     */
    public void paintSprites (Graphics g, int offsetX, int offsetY) {
        for (Sprite s : mySpriteList) {
            // uncomment this when the errors in Sprite.java are fixed
            //s.draw(g);
        }
    }
    
    /**
     * Serializes the Level into the proper xml format.
     * This stores all the information about the level
     * and the sprites it contains.
     * @return String the Level serialized as an xml string.
     */
    
    public String serialize() {
        // TODO: implement this using a shared writer.
        return null;
    }

//    /**
//     * Resets level to beginning
//     */
//    public abstract void reset ();
    
    // TODO: implement this method if needed

}
