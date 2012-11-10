package vooga.platformer.gameobject;

import java.awt.Graphics;
import java.awt.geom.RectangularShape;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Niel Lebeck
 *
 */

public abstract class GameObject {
    private boolean removeFlag;
    private RectangularShape myShape;
    private List<UpdateStrategy> strategyList;
    
    public GameObject() {
        strategyList = new ArrayList<UpdateStrategy>();
    }
    
    /**
     * Add a strategy to this GameObject's strategy list.
     * @param strat
     */
    public void addStrategy(UpdateStrategy strat) {
        strategyList.add(strat);
    }
    
    /**
     * Remove a strategy from the list.
     * @param strat
     */
    public void removeStrategy(UpdateStrategy strat) {
        strategyList.remove(strat);
    }
    
    /**
     * Used by concrete subclasses to work with the strategy list.
     * @return the strategy list
     */
    protected Iterable<UpdateStrategy> getStrategyList() {
        return strategyList;
    }
    
    /**
     * Update the GameObject. This method is called once per update cycle.
     * @param elapsedTime time duration of the update cycle
     */
    public abstract void update(long elapsedTime);
    
    /**
     * Paints the GameObject to the given Graphics object.
     * @param pen Graphics object to paint on
     */
    public abstract void paint(Graphics pen);
    
    /**
     * Mark the GameObject for removal by the Level. The level should delete
     * all marked GameObjects at the end of the update cycle.
     */
    public void markForRemoval() {
        removeFlag = true;
    }
    
    /**
     * Used to determine whether a GameObject should be removed.
     * @return true if the GameObject is marked for removal
     */
    public boolean checkForRemoval() {
        return removeFlag;
    }
    
    /**
     * Gives the GameObject's bounds.
     * 
     * @return GameObject's bounds.
     */
    public RectangularShape getShape () {
        return myShape;
    }
}
