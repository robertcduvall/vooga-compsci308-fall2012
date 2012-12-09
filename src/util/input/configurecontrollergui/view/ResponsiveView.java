package util.input.configurecontrollergui.view;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Point2D;


public class ResponsiveView extends View{
    
    public ResponsiveView (Point2D position, Dimension size) {
        super(position, size);
    }
    
    public ResponsiveView (Point2D position, Dimension size, String imageUrl) {
        super(position, size, imageUrl, position);
    }

    /**
     * Decides where the mouse is clicked and determines
     * whether the click is handled by this view or
     * one of its children.
     * 
     * @param point of the mouse click
     */
    public void mouseClicked (Point point) {
        // if the point is inside the bound of a child view,
        // the child takes over click interaction.
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
