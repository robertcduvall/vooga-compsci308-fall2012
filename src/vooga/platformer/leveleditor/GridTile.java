package vooga.platformer.leveleditor;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import vooga.platformer.leveleditor.Sprite;
import vooga.platformer.leveleditor.leveldrawer.IEditorObject;


/**
 * A class representing a grid tile.
 * 
 * @author Paul Dannenberg
 * 
 */
public class GridTile extends Sprite implements IEditorObject {

    private Image myDefaultImage, myHighLightedImage;
    private final URL myDefaultImagePath = getClass().getResource(
            "/images/TransparentBlue.png");
    private final URL myHighlightedImagePath = getClass().getResource(
            "/images/OpaqueBlue.png");
    private static final String UNFOUND_IMAGE_MESSAGE = "GridTile image file not found";
    private int myWidth, myHeight;

    /**
     * Creates a new GridTile and initializes its images.
     * 
     * @param width The width of the tile in pixels.
     * @param height The tile height in pixels.
     */
    public GridTile(int width, int height) {
        myWidth = width;
        myHeight = height;
        initializeImages(myWidth, myHeight);
    }

    /**
     * Reads the possible images for the tiles, a light blue tile and a dark
     * blue tile. These are resized to fit the tile.
     * 
     * @param width The width the image should be resized to in order to fit the
     *        tile.
     * @param height The height the image should be resized to in order to fit
     *        the tile.
     */
    private void initializeImages(int width, int height) {
        myDefaultImage = resizeImage(readImage(myDefaultImagePath), width,
                height);
        setImage(myDefaultImage);
        myHighLightedImage = resizeImage(readImage(myHighlightedImagePath),
                width, height);
    }

    /**
     * Attempts to read an image from a particular directory.
     * 
     * @param path The path from which to read the image.
     * @return The image that was read out. Returns null if image
     *         was not found, this really shouldn't happen though.
     */
    private Image readImage(URL path) {
        try {
            return ImageIO.read(path);
        } catch (IOException e) {
            System.err.println(UNFOUND_IMAGE_MESSAGE + " at " + path);
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Resizes the image to a particular width and height.
     * 
     * @param toResize The image to be resized.
     * @param width The width to resize to.
     * @param height The height to resize to.
     * @return The resized image.
     */
    private Image resizeImage(Image toResize, int width, int height) {
        return toResize
                .getScaledInstance(myWidth, myHeight, Image.SCALE_SMOOTH);
    }

    /**
     * Places a sprite on the grid tile. This will
     * place the sprite in the center of the tile.
     * 
     * @param toPosition The sprite to be placed
     */
    public void position(IEditorObject toPosition) {
        Point2D center = findCenter(getOutline().getBounds());
        toPosition.setCenter((int) center.getX(), (int) center.getY());
    }

    /**
     * Makes the tile appear highlighted.
     */
    public void highlight() {
        setImage(myHighLightedImage);
    }

    /**
     * Make the tile's image revert to its
     * default unhighlighted appearance.
     */
    public void unHighlight() {
        setImage(myDefaultImage);
    }

    private Point2D findCenter(Rectangle rectangle) {
        return new Point(rectangle.x + rectangle.width / 2, rectangle.y
                + rectangle.height / 2);
    }

    @Override
    public void paint(Graphics2D pen) {
        super.paint(pen, null);
        // This needs to be standardized.
    }

    @Override
    public Point2D getCenter() {
        Rectangle tileShape = getOutline().getBounds();
        return findCenter(tileShape);
    }

    @Override
    public void setCenter(int x, int y) {
        //TODO 
    }
    

    
}
