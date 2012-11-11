package vooga.platformer.leveleditor;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Collection;


/**
 * Responsible for placing sprites on the LevelBoard. This class
 * is a fairly simple implementation. It allows sprites to be
 * placed on the LevelBoard as long as they are not on top of
 * other sprites.
 * 
 * @author Paul Dannenberg
 * 
 */
public class SpritePlacementManager implements ISpritePlacementManager {

    private LevelBoard myLevelBoard;
    private Collection<Sprite> mySelectedSprites;

    /**
     * Creates a new SpritePlacementManager object. This
     * object will oversee the placement of sprites for
     * a particular LevelBoard.
     * 
     * @param board The board whose sprite placement this
     *        object will manage.
     */
    public SpritePlacementManager(LevelBoard board) {
        myLevelBoard = board;
        mySelectedSprites = new ArrayList<Sprite>();
    }

    @Override
    public void positionSprite(Sprite sprite) {
        if (isValidPosition(sprite)) {
            myLevelBoard.add(sprite);
        }

    }

    @Override
    public void selectSprite(Sprite sprite) {
        // TODO Auto-generated method stub

    }

    @Override
    public void clearSprite(Sprite toClear) {
        myLevelBoard.remove(toClear);
    }

    @Override
    public void selectRegion(Rectangle region) {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean isValidPosition(Sprite sprite) {
        return false;
        // TODO Auto-generated method stub

    }

}
