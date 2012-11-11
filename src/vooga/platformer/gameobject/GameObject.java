package vooga.platformer.gameobject;

import java.awt.Graphics;
import java.awt.geom.RectangularShape;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author Niel Lebeck
 *
 */

public abstract class GameObject {
    private boolean removeFlag;
    private List<UpdateStrategy> strategyList;
    private int x;
    private int y;
    
    public GameObject() {
        strategyList = new ArrayList<UpdateStrategy>();
    }
    
    /**
     * 
     * @param inX starting x position
     * @param inY starting y position
     */
    public GameObject(int inX, int inY) {
        this();
        x = inX;
        y = inY;
    }
    
    /**
     * @param configString containing key-value pairs for x and y
     */
    public GameObject(String configString) {
        this();
        Map<String,String> configMap = parseConfigString(configString);
        x = Integer.parseInt(configMap.get("x"));
        y = Integer.parseInt(configMap.get("y"));
    }
    
    protected Map<String,String> parseConfigString(String configString) {
        Map<String,String> configMap = new HashMap<String,String>();
        String[] pairs = configString.split(" ");
        for (String entry : pairs) {
            String[] entrySplit = entry.split("=");
            configMap.put(entrySplit[0],entrySplit[1]);
        }
        return configMap;
    }
    
    protected int getX() {
        return x;
    }
    
    protected int getY() {
        return y;
    }
    
    protected void setX(int inX) {
        x = inX;
    }
    
    protected void setY(int inY) {
        y = inY;
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
    public void update(long elapsedTime) {
        for (UpdateStrategy us : strategyList) {
            us.applyAction();
        }
    }
    
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
    public abstract RectangularShape getShape();
}
