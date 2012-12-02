package vooga.platformer.gameobject;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import util.camera.Camera;
import util.configstring.ConfigStringParser;
import vooga.platformer.level.Level;


/**
 * 
 * @author Niel Lebeck
 * @author Yaqi Zhang (revised)
 * @author Grant Oakley (modified)
 * 
 */
public abstract class GameObject implements Comparable<GameObject>, Serializable {
    private static final long serialVersionUID = 1L;
    protected static final String X_TAG = "x";
    protected static final String Y_TAG = "y";
    protected static final String WIDTH_TAG = "width";
    protected static final String HEIGHT_TAG = "height";
    protected static final String DEFAULT_IMAGE_TAG = "imagePath";
    private static final String ID_TAG = "id";

    private boolean removeFlag;
    private Map<String, UpdateStrategy> strategyMap;
    private double x;
    private double y;
    private double width;
    private double height;
    private ImageIcon defaultImage;
    private int id;
    private Level myLevel;

    // Change this to public because no config str provided when creating
    // GameObject during runtime
    /**
     * Create GameObject during runtime of the
     */
    public GameObject () {
        strategyMap = new HashMap<String, UpdateStrategy>();
    }

    /**
     * @param configString containing key-value pairs for the GameObject's
     *        parameters. The
     *        config string should contain a sequence of these pairs separated
     *        by commas (','), and within
     *        each pair, the key should be separated from the value by an '='
     *        character.
     */
    public GameObject (String configString) {
        this();
        Map<String, String> configMap = ConfigStringParser.parseConfigString(configString);
        x = Double.parseDouble(configMap.get(X_TAG));
        y = Double.parseDouble(configMap.get(Y_TAG));
        width = Double.parseDouble(configMap.get(WIDTH_TAG));
        height = Double.parseDouble(configMap.get(HEIGHT_TAG));
        String defaultImageName = configMap.get(DEFAULT_IMAGE_TAG);
        id = Integer.parseInt(configMap.get(ID_TAG));
        try {
            defaultImage = new ImageIcon(ImageIO.read(new File(defaultImageName)));
        }
        catch (IOException e) {
            // TODO Handle this exception in a more acceptable manner, or do not
            // use config string instantiation.
            System.out.println("could not load image " + defaultImageName);
            System.exit(0);
        }
    }

    /**
     * Gets a map in which the keys are parameter tag names, and the strings
     * that they map to are the descriptions of the values that should be passed
     * for that tag.
     * 
     * A null return value means that this GameObject subclass requires no
     * additional parameters be passed in its config string in order to be
     * instantiated.
     * 
     * @return a map of config string parameter tags and descriptions of the
     *         values that should be passed with these tags
     * @author Grant Oakley
     */
    public Map<String, String> getConfigStringParams () {
        Map<String, String> params = new HashMap<String, String>();
        params.put(X_TAG, "x position of the object");
        params.put(Y_TAG, "y position of the object");
        params.put(WIDTH_TAG, "width of the object");
        params.put(HEIGHT_TAG, "height of the object");
        params.put(ID_TAG, "ID for sprite. Should be unique.");
        params.put(DEFAULT_IMAGE_TAG, "file name of the image to the be the default image.");
        return params;
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
     * @param StrategyName the Class Name of the Strategy, not includes package
     *        name
     * @param strat strategy
     */
    public void addStrategy (String StrategyName, UpdateStrategy strat) {
        strategyMap.put(StrategyName, strat);
    }

    /**
     * Remove a strategy from the list.
     * 
     * @param strat strategy
     */
    public void removeStrategy (UpdateStrategy strat) {
        strategyMap.remove(strat);
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
            pen.drawImage(getCurrentImage().getScaledInstance((int) width, (int) height,
                                                              Image.SCALE_DEFAULT),
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
     * Gives the GameObject's bounds.
     * 
     * @return GameObject's bounds.
     */
    public Rectangle2D getShape () {
        return new Rectangle2D.Double(x, y, width, height);
    }

}
