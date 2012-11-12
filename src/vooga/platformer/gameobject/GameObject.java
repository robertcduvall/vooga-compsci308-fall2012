package vooga.platformer.gameobject;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import util.camera.Camera;
import vooga.platformer.level.Level;


/**
 * 
 * @author Niel Lebeck
 * 
 */

public abstract class GameObject {
    private boolean removeFlag;
    private List<UpdateStrategy> strategyList;
    private double x;
    private double y;
    private double width;
    private double height;

<<<<<<< HEAD
    private GameObject() {
=======
    private GameObject () {
>>>>>>> f5741ba1b396d7e73c1993f7f217c5c9362159a8
        strategyList = new ArrayList<UpdateStrategy>();
    }

    /**
     * 
     * @param inX starting x position
     * @param inY starting y position
     */
    public GameObject (double inX, double inY, double inWidth, double inHeight) {
        this();
        x = inX;
        y = inY;
        width = inWidth;
        height = inHeight;
    }

    /**
     * @param configString containing key-value pairs for x and y
     */
    public GameObject (String configString) {
        this();
        Map<String, String> configMap = parseConfigString(configString);
        x = Double.parseDouble(configMap.get("x"));
        y = Double.parseDouble(configMap.get("y"));
        width = Double.parseDouble(configMap.get("width"));
        height = Double.parseDouble(configMap.get("height"));
    }

<<<<<<< HEAD
    protected Map<String, String> parseConfigString(String configString) {
=======
    protected Map<String, String> parseConfigString (String configString) {
>>>>>>> f5741ba1b396d7e73c1993f7f217c5c9362159a8
        Map<String, String> configMap = new HashMap<String, String>();
        String[] pairs = configString.split(" ");
        for (String entry : pairs) {
            String[] entrySplit = entry.split("=");
            configMap.put(entrySplit[0], entrySplit[1]);
        }
        return configMap;
    }

<<<<<<< HEAD
    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(double inX) {
        x = inX;
    }

    public void setY(double inY) {
=======
    public double getX () {
        return x;
    }

    public double getY () {
        return y;
    }

    public void setX (double inX) {
        x = inX;
    }

    public void setY (double inY) {
>>>>>>> f5741ba1b396d7e73c1993f7f217c5c9362159a8
        y = inY;
    }

    /**
     * Add a strategy to this GameObject's strategy list.
     * 
     * @param strat
     */
    public void addStrategy (UpdateStrategy strat) {
        strategyList.add(strat);
    }

    /**
     * Remove a strategy from the list.
     * 
     * @param strat
     */
    public void removeStrategy (UpdateStrategy strat) {
        strategyList.remove(strat);
    }

    /**
     * Used by concrete subclasses to work with the strategy list.
     * 
     * @return the strategy list
     */
    protected Iterable<UpdateStrategy> getStrategyList () {
        return strategyList;
    }

    /**
     * Update the GameObject. This method is called once per update cycle.
     * 
     * @param elapsedTime time duration of the update cycle
     */
    public void update (Level level, long elapsedTime) {
        for (UpdateStrategy us : strategyList) {
            us.applyAction();
        }
    }

    /**
     * Paints the GameObject to the given Graphics object.
     * 
     * @param pen Graphics object to paint on
     */
<<<<<<< HEAD
    public void paint(Graphics pen, Camera cam) {
        double x = getX();
        double y = getY();
        Rectangle2D rect = cam.getBounds();
        double xOffset = rect.getX();
        double yOffset = rect.getY();
        if (getShape().intersects(rect)) {
=======
    public void paint (Graphics pen, Camera cam) {
        double x = this.getX();
        double y = this.getY();
        Rectangle2D rect = cam.getBounds();
        double xOffset = rect.getX();
        double yOffset = rect.getY();
        if (this.getShape().intersects(rect)) {
>>>>>>> f5741ba1b396d7e73c1993f7f217c5c9362159a8
            pen.drawImage(getCurrentImage(), (int) (x - xOffset),
                    (int) (y - yOffset), null);
        }
    }

    /**
     * @return the current Image of this GameObject
     */
<<<<<<< HEAD
    public abstract Image getCurrentImage();
=======
    public abstract Image getCurrentImage ();
>>>>>>> f5741ba1b396d7e73c1993f7f217c5c9362159a8

    /**
     * Mark the GameObject for removal by the Level. The level should delete
     * all marked GameObjects at the end of the update cycle.
     */
    public void markForRemoval () {
        removeFlag = true;
    }

    /**
     * Used to determine whether a GameObject should be removed.
     * 
     * @return true if the GameObject is marked for removal
     */
    public boolean checkForRemoval () {
        return removeFlag;
    }

    /**
     * Gives the GameObject's bounds.
     * 
     * @return GameObject's bounds.
     */
<<<<<<< HEAD
    public Rectangle2D getShape() {
=======
    public Rectangle2D getShape () {
>>>>>>> f5741ba1b396d7e73c1993f7f217c5c9362159a8
        return new Rectangle2D.Double(x, y, width, height);
    }
}
