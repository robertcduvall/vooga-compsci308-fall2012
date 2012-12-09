package vooga.platformer.gameobject;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import util.camera.Camera;
import util.configstring.ConfigStringParser;
import vooga.platformer.level.Level;


/**
 * 
 * @author Niel Lebeck
 * @author Yaqi Zhang (revised)
 * @author Grant Oakley (modified)
 * @author Sam Rang (added sprite methods)
 * 
 */
public abstract class GameObject implements Comparable<GameObject>,
        Serializable {
    private static final long serialVersionUID = 1L;

    private boolean removeFlag;
    private Map<String, UpdateStrategy> strategyMap;
    private Map<String, String> attributeMap;
    private double x;
    private double y;
    private double width;
    private double height;
    private ImageIcon defaultImage;
    private int id;
    private int hp;
    private Level myLevel;



    /**
     * @param configString containing key-value pairs for the GameObject's
     *        parameters. The
     *        config string should contain a sequence of these pairs separated
     *        by commas (','), and within
     *        each pair, the key should be separated from the value by an '='
     *        character.
     */
    public GameObject (double inX, double inY, double inWidth, double inHeight, int inId,
            File defaultImageFile)
        throws IOException {
        strategyMap = new HashMap<String, UpdateStrategy>();
        x = inX;
        y = inY;
        width = inWidth;
        height = inHeight;
        id = inId;
        defaultImage = new ImageIcon(ImageIO.read(defaultImageFile));
    }

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
        y = inY;
    }

    public int getId () {
        return id;
    }

    public Level getLevel () {
        return myLevel;
    }

    public double getWidth () {
        return width;
    }

    public double getHeight () {
        return height;
    }

    public void setSize (double width, double height) {
        this.width = width;
        this.height = height;
    }

    /**
     * Sort GameObjects by ID
     * 
     * @param go GameObject
     */
    public int compareTo (GameObject go) {
        int diff = this.getId() - go.getId();
        if (diff != 0) {
            return diff;
        }
        else {
            return this.hashCode() - go.hashCode();
        }
    }

    /**
     * Add a strategy to this GameObject's strategy list.
     * 
     * @param stratName the Class Name of the Strategy, not includes package
     *        name
     * @param strat strategy
     */
    public void addStrategy (String stratName, UpdateStrategy strat) {
        strategyMap.put(stratName, strat);
    }

    /**
     * @param strat Strategy
     */
    public void addStrategy (UpdateStrategy strat) {
        String classString = strat.getClass().toString();
        String split[] = classString.split(" ");
        String name = split[split.length-1];
        String stratName = "";
        if(name.contains(".")){
            String names[] = name.split("\\.");
            stratName =  names[names.length-1];
        }else{
            stratName = name;
        }
        strategyMap.put(stratName, strat);
    }

    private void removeStrategy (UpdateStrategy strat) {
        strategyMap.remove(strat);
    }

    /**
     * Remove a strategy from the list.
     * 
     * @param strategyName Class name of the strategy without package name, eg.
     *        PlayerMovingStrategy.
     */
    public void removeStrategy (String strategyName) {
        UpdateStrategy strat = strategyMap.get(strategyName);
        removeStrategy(strat);
    }

    /**
     * @param stratName Strategy Name
     */
    public UpdateStrategy getStrategy (String stratName) {
        return strategyMap.get(stratName);
    }

    /**
     * Used by concrete subclasses to work with the strategy list.
     * 
     * @return the strategy list
     */
    protected Iterable<UpdateStrategy> getStrategyList () {
        return strategyMap.values();
    }

    /**
     * Update the GameObject. This method is called once per update cycle.
     * 
     * @param elapsedTime time duration of the update cycle
     */
    public void update (Level level, long elapsedTime) {
        myLevel = level;
        for (UpdateStrategy us : strategyMap.values()) {
            us.applyAction();
        }
    }

    /**
     * Paints the GameObject to the given Graphics object.
     * 
     * @param pen Graphics object to paint on
     * @param cam camera
     */
    public void paint (Graphics pen, Camera cam) {
        double x = getX();
        double y = getY();
        Rectangle2D rect = cam.getBounds();
        double xOffset = rect.getX();
        double yOffset = rect.getY();

        if (getShape().intersects(rect)) {
            pen.drawImage(
                    getCurrentImage().getScaledInstance((int) width,
                            (int) height, Image.SCALE_DEFAULT),
                    (int) (x - xOffset), (int) (y - yOffset), null);
        }
    }

    /**
     * @return the current Image of this GameObject
     */
    public Image getCurrentImage () {
        return defaultImage.getImage();
    }

    /**
     * @param img of the obj
     */
    public void setImage (Image img) {
        defaultImage = new ImageIcon(img);
    }

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
     * Damage the GameObject by given amount of HP.
     * 
     * @param amount
     */
    public void damage (int amount) {
        hp -= amount;
    }

    /**
     * Gives the GameObject's bounds.
     * 
     * @return GameObject's bounds.
     */
    public Rectangle2D getShape () {
        return new Rectangle2D.Double(x, y, width, height);
    }

    /**
     * Returns a boolean of whether or not a point is contained by the object.
     * 
     * @param p Point of interest
     * @return boolean of whether the point is inside the bounds of the object
     */
    public boolean containsPoint (Point p) {
        return p.x >= getX() && p.x <= getX() + getWidth() && p.y >= getY()
                && p.y <= getY() + getHeight();
    }

    /**
     * Flips the sprites image across it's vertical axis.
     * 
     */
    public void flipImage () {
        AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
        tx.translate(-getCurrentImage().getWidth(null), 0);
        AffineTransformOp op = new AffineTransformOp(tx,
                AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        setImage(op.filter((BufferedImage) getCurrentImage(), null));
    }

}
