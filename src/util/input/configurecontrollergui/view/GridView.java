package util.input.configurecontrollergui.view;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Point2D;
import javax.swing.ImageIcon;
import util.input.configurecontrollergui.controller.GridController;
import util.input.configurecontrollergui.util.Config;


/**
 * A view focused on providing a grid style flexibility. 
 * @author Lance, Amay
 *
 */
@SuppressWarnings("serial")
public class GridView extends ResponsiveView{
    
    private GridController myGridController;
    private String myGridImage;
    private Point myGridImageLocation;
    private Dimension myGridImageSize;
    private int myLastButtonPressed;
    
    /**
     * Initializing a grid view.
     * @param position - location of the view.
     * @param size - size of the view.
     * @param gridController - grid controller.
     */
    public GridView (Point2D position, Dimension size, GridController gridController) {
        super(position, size);
        myGridController = gridController;
    }

    /**
     * Initializing a grid view.
     * @param position - location of the view.
     * @param size - size of the view.
     * @param bgColor -background color of the view.
     * @param gridController - grid controller.
     */
    public GridView (Point2D position, Dimension size, String imageUrl, GridController gridController) {
        super(position, size, imageUrl);
        myGridController = gridController;
    }
   

    /**
     * Method called upon mouseClick event.
     * @param point - absolute location of click.
     */
    @Override
    public void mouseClicked (Point point) {
        super.mouseClicked(point);
        int gridNum = myGridController.findGridNumber(point);
        System.out.println(gridNum);
        myLastButtonPressed = gridNum;
    }
    
    /**
     * Returns the last button pressed.
     * @return
     */
    public int getLastButtonPressed() {
        return myLastButtonPressed;
    }

    public void addImage (String gridImage, Point gridImageLocation,
            Dimension gridImageSize) {
        myGridImage=gridImage;
        myGridImageLocation=gridImageLocation;
        myGridImageSize=gridImageSize;
    }
    
    public void paint (Graphics2D pen){
        super.paint(pen);
        if(myGridImage!=null){
            ImageIcon gridImage = new ImageIcon(this.getClass().getResource(myGridImage));
            pen.drawImage(gridImage.getImage(),Config.GRID_X_LOC, Config.GRID_Y_LOC,(int) myGridImageSize.getWidth(), (int) myGridImageSize.getHeight(), this);
        }
        for(double yPos: myGridController.getHorizontalLines()) {
            pen.drawLine(0, (int) yPos, (int) Config.GRID_IMAGE_SIZE.getWidth(), (int) yPos);
        }
        for(double xPos: myGridController.getVerticalLines()) {
            pen.drawLine((int) xPos, (int) getOffset().getY(), (int) xPos, (int) getOffset().getY() + (int) Config.GRID_IMAGE_SIZE.getHeight());
        }
    }

    public void initialize () {
        myGridController.setOffset(getOffset());
        myGridController.initializePoints();
    }


}
