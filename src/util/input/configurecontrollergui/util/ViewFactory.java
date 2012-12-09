package util.input.configurecontrollergui.util;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.geom.Point2D;
import javax.swing.JComponent;
import util.datatable.exceptions.InvalidXMLTagException;
import util.datatable.exceptions.RepeatedColumnNameException;
import util.datatable.exceptions.UnrecognizedColumnNameException;
import util.input.configurecontrollergui.controller.GridController;
import util.input.configurecontrollergui.controller.RadioButtonController;
import util.input.configurecontrollergui.view.Frame;
import util.input.configurecontrollergui.view.GridView;
import util.input.configurecontrollergui.view.RadioButtonView;
import util.input.configurecontrollergui.view.View;
import util.input.core.InputControlModifier;
import util.input.core.Controller;


/**
 * This serves as a factory that
 * initializes various portions of the 
 * Control GUI.
 * 
 * @author Lance, Amay
 *
 */
public class ViewFactory {
    private Frame myFrame;
    private InputControlModifier myInputModifier;
    private GridView myGridView;
    private RadioButtonView myRadioView;
    
    @SuppressWarnings("rawtypes")
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
        myGridView = new GridView(gridLocation, gridSize, gridController );
        myGridView.addImage(Config.GRID_IMAGE, Config.GRID_IMAGE_LOCATION, Config.GRID_IMAGE_SIZE);
        myFrame.addView(myGridView);
        myGridView.initialize();
    }
    
    public void generateRadioButtons(){
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        myRadioView = new RadioButtonView(new RadioButtonController(myInputModifier));
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                RadioButtonView.createAndShowGUI();
            }
        });
    }
    
    
    public String getOldButton() {
        return RadioButtonView.getRadioButtonPressed();
    }
    
    public String getNewButton() {
        int oldButtonID = myGridView.getLastButtonPressed();
        return (Config.findButton(oldButtonID));
    }
    
    public void swap() throws UnrecognizedColumnNameException {
        String oldButton = this.getOldButton();
        String newButton = this.getNewButton();
        System.out.println(oldButton + "|||" + newButton);
        myInputModifier.swap(oldButton, newButton);
        generateRadioButtons();
    }
}
