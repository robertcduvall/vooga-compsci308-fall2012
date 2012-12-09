package util.input.configurecontrollergui.view;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collection;
import javax.swing.ImageIcon;
import javax.swing.JComponent;

/**
 * @author Lance
 *
 */
public class View extends JComponent {
    
    protected Collection<View> myViewChildren = new ArrayList<View>();
    protected Point2D myPosition;
    protected Dimension mySize;
    private ImageIcon myBgImage;
    private Color myBackgroundColor = Color.BLUE;
    private Point2D myImageLocation;
    private Point2D myOffset;

    
    /**
     * Initializes a view.
     *
     * @param position of the top left corner relative to parent
     * @param size of the view
     */
    public View(Point2D position, Dimension size) {
        myPosition = position;
        mySize = size;
    }

    /**
     * Initializes a view.
     *
     * @param position of the top left corner relative to parent
     * @param size of the view 
     * @param imageUrl location of background image
     */
    public View(Point2D position, Dimension size, String imageUrl,Point2D imageLocation) {
        myPosition = position;
        mySize = size;
        myBgImage = new ImageIcon(this.getClass().getResource(imageUrl));
    }

    /**
     * Paints the view and all of its children.
     *
     * @param pen used to paint
     */
    public void paint(Graphics2D pen) {
        pen.setColor(myBackgroundColor);
        pen.fillRect((int) myPosition.getX(), (int) myPosition.getY(),
                (int) mySize.getWidth(), (int) mySize.getHeight());
        
        for (View child : myViewChildren) {
            child.paint(pen);
        }
    }

    /**
     * Adds a child to this view.
     * The children are graphs or labels (currently).
     *
     * @param v the view to be added
     */
    public void addViewChild(View v) {
        v.offsetPosition(myPosition);
        myViewChildren.add(v);
    }
    
    /**
     * Offsets the position of the view so that
     * it and its parent do not overlap.
     * 
     * @param parentPosition the point to offset this view's
     *        position to
     */
    public void offsetPosition (Point2D parentPosition) {
        //takes position relative to parent and replaces with absolute position
        myPosition =
                new Point2D.Double(myPosition.getX() + parentPosition.getX(),
                                   myPosition.getY() + parentPosition.getY());
        

        myOffset =  new Point2D.Double(myPosition.getX(), myPosition.getY());
    }


    
    public Point2D getOffset (){
        return myOffset;
    }
    /**
     * Returns the position of this view.
     */
    public Point2D getPosition () {
        return myPosition;
    }
    
    public Dimension getSize(){
        return mySize;
    }
    
}

