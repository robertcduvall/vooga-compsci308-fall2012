package vooga.platformer.leveleditor;

import java.awt.Rectangle;
import java.awt.geom.Point2D;
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
    public void clearSelection() {
        mySelectedSprites.clear();
    }

    @Override
    public void selectSprite(Sprite sprite) {
        mySelectedSprites.add(sprite);

    }

    @Override
    public boolean selectSprite(Point2D point) {
        int initialNumberSelected = mySelectedSprites.size();
        selectRegion(new Rectangle((int) point.getX(), (int) point.getY(), 0, 0));
        int finalNumberSelected = mySelectedSprites.size();
        return finalNumberSelected != initialNumberSelected;
    }

    @Override
    public void removeSprite(Sprite toRemove) {
        myLevelBoard.remove(toRemove);
    }

    @Override
    public void selectRegion(Rectangle region) {
        for (Sprite currentlyPlacedSprites : myLevelBoard.getSprites()) {
            if (currentlyPlacedSprites.isIntersecting(region)) {
                mySelectedSprites.add(currentlyPlacedSprites);
            }
        }

    }

    @Override
    public boolean isValidPosition(Sprite sprite) {
        for (Sprite currentlyPlacedSprites : myLevelBoard.getSprites()) {
            if (sprite.isIntersecting(currentlyPlacedSprites.getOutline()))
                return false;
        }
        return true;

    }

}
