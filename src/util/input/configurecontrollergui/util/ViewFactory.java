package util.input.configurecontrollergui.util;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.geom.Point2D;
import javax.swing.JComponent;
import util.datatable.exceptions.InvalidXMLTagException;
import util.datatable.exceptions.RepeatedColumnNameException;
import util.input.configurecontrollergui.controller.GridController;
import util.input.configurecontrollergui.controller.RadioButtonController;
import util.input.configurecontrollergui.view.Frame;
import util.input.configurecontrollergui.view.GridView;
import util.input.configurecontrollergui.view.RadioButtonView;
import util.input.configurecontrollergui.view.View;
import util.input.core.InputControlModifier;
import util.input.core.Controller;


public class ViewFactory {
    private Frame myFrame;
    private InputControlModifier myInputModifier;

    /**
     * In charge of populating a canvas on behalf of a controller
     * Specific to stock
     * @param canvas to populate
     * @throws InvalidXMLTagException 
     * @throws RepeatedColumnNameException 
     */
    public ViewFactory (Frame frame, Controller gameController) throws RepeatedColumnNameException, InvalidXMLTagException {
        myFrame = frame;
        myInputModifier = new InputControlModifier(gameController);
    }

    public void generateHeader () {
        Dimension headerSize=new Dimension(Config.HEADER_WIDTH,Config.HEADER_HEIGHT);
        Point headerLocation= new Point(Config.HEADER_X_LOC,Config.HEADER_Y_LOC);
        View titleView=new View(headerLocation,headerSize);
        myFrame.addView(titleView);
        
    }

    public void generateGrid () {
        Dimension gridSize=new Dimension(Config.GRID_WIDTH,Config.GRID_HEIGHT);
        Point gridLocation= new Point(Config.GRID_X_LOC,Config.GRID_Y_LOC);
        GridController gridController = new GridController(Config.GRID_NUM_ROWS,Config.GRID_NUM_COLUMNS);
        GridView gridView = new GridView(gridLocation, gridSize, gridController );
        gridView.addImage(Config.GRID_IMAGE, Config.GRID_IMAGE_LOCATION, Config.GRID_IMAGE_SIZE);
        myFrame.addView(gridView);
        gridView.initialize();
    }
    
    public void generateRadioButtons(){
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        RadioButtonView rbView = new RadioButtonView(new RadioButtonController(myInputModifier));
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                RadioButtonView.createAndShowGUI();
            }
        });
    }
    
}
