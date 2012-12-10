package util.input.configurecontrollergui.view;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Point2D;


/**
 * Type of View that is responsive to clicks.
 * @author Lance
 *
 */
public class ResponsiveView extends View{
    
    public ResponsiveView (Point2D position, Dimension size) {
        super(position, size);
    }
    
    public ResponsiveView (Point2D position, Dimension size, String imageUrl) {
        super(position, size, imageUrl, position);
    }

    /**
     * Method called upon mouseClick event.
     * @param point - absolute location of click.
     */
    public void mouseClicked (Point point) {
        for (View child : super.myViewChildren) {
            Point p =
                    new Point((int) child.getPosition().getX(),
                              (int) child.getPosition().getY());
            Rectangle bounds = new Rectangle(p, child.getSize());

            if (bounds.contains(point)) {
                ((ResponsiveView) child).mouseClicked(point);
                break;
                
            }
        }
    }

}
