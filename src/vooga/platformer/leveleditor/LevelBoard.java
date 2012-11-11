package vooga.platformer.leveleditor;

import java.awt.Graphics2D;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import javax.swing.JPanel;


/*
 * I've decided to decouple this class from the actual act of sprite placement.
 * The reason for this is that sprite placement has the potential to become
 * VERY complicated, particularly if we implement the "square grid" idea, in
 * which the sprite snaps into position based on the position of the
 * closest grid square. We also may not want sprites to be placed on top of
 * each other etc. Therefore, I've decided to delegate responsibility of
 * actually placing sprites to a sprite placement manager which can interact
 * with this class through its protected methods.
 */

/**
 * Represents the main window for the level editor. Will display a collection
 * of Sprites and will oversee the results of all user actions.
 * 
 * @author Paul Dannenberg
 * 
 */
public class LevelBoard extends JPanel implements ISavable, IUpdatable,
        IPaintable {

    private static final long serialVersionUID = -3528519211577278934L;
    private Collection<Sprite> mySprites;
    private ISpritePlacementManager myPlacementManager;

    /**
     * Creates a new LevelBoard, visible to the user. The LevelBoard starts
     * off empty.
     */
    public LevelBoard() {
        mySprites = new ArrayList<Sprite>();
        myPlacementManager = new SpritePlacementManager(this);
    }

    @Override
    public void update() {
        for (Sprite s : mySprites) {
            s.update();
        }
    }

    @Override
    public void paint(Graphics2D pen) {
        for (Sprite s : mySprites) {
            s.paint(pen);
        }
    }

    @Override
    public void save() {
        // TODO Auto-generated method stub

    }

    @Override
    public void load(URL path) {
        // TODO Auto-generated method stub

    }

    /**
     * @return An unmodifiable Collection of the sprites
     *         currently positioned on the board.
     */
    protected Collection<Sprite> getSprites() {
        return Collections.unmodifiableCollection(mySprites);
    }

    /**
     * Adds a sprite to the board. This method does not
     * check for any conditions (such as sprites being)
     * added on top of each other.
     * 
     * @param sprite
     */
    protected void add(Sprite sprite) {
        mySprites.add(sprite);
    }

    /**
     * Will remove a sprite from the board.
     * 
     * @param sprite The sprite that should
     *        be removed.
     */
    protected void remove(Sprite sprite) {
        mySprites.remove(sprite);
    }
}
