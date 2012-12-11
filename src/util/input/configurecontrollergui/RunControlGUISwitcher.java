package util.input.configurecontrollergui;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;
import util.datatable.exceptions.InvalidXMLTagException;
import util.datatable.exceptions.RepeatedColumnNameException;
import util.input.configurecontrollergui.util.Config;
import util.input.configurecontrollergui.util.ViewFactory;
import util.input.configurecontrollergui.view.Frame;
import util.input.configurecontrollergui.view.RadioButtonView;
import util.input.core.Controller;
import util.input.core.KeyboardController;

/**
 * This class pops up 2 GUIs:
 * 1 is a list of current controller mappings of buttons and actions
 * 2 is a GUI displaying a controller
 * Clicking on a radio button in the list
 * and then click on a button in the controller GUI
 * Pressing "ENTER" assigns this newly clicked button
 * to the action clicked in the list.
 * It swaps positions with the older button being clicked in the list as shown when you run the program.
 * 
 * There is a Config.java where the developer needs to assign which grid numbers map to a button description
 * in the GUI created.
 * This map needs to be complete in order for clicking a button on the GUI to work
 * 
 * @author Amay, Lance
 *
 */
public class RunControlGUISwitcher {

    private static final Dimension SIZE =
            new Dimension(Config.FRAME_WIDTH, Config.FRAME_HEIGHT);

    private static final String TITLE = "Game Settings";
    private static ViewFactory myViewFactory;
    private static Controller gameController;
    private static Frame myDisplayFrame;
    
    public RunControlGUISwitcher() {
        myDisplayFrame = new Frame(SIZE);
    }
    
    public static void main (String[] args) throws NoSuchMethodException, IllegalAccessException, RepeatedColumnNameException, InvalidXMLTagException {
        RunControlGUISwitcher obj = new RunControlGUISwitcher();
        KeyboardController testControl = new KeyboardController(myDisplayFrame);
        obj.initGUISwitcher(testControl);
    }

    /**
     * 
     * @param args unused inputs
     * @throws InvalidXMLTagException 
     * @throws RepeatedColumnNameException 
     * @throws IllegalAccessException 
     * @throws NoSuchMethodException 
     */
    public void initGUISwitcher (Controller controller) throws RepeatedColumnNameException, InvalidXMLTagException, NoSuchMethodException, IllegalAccessException {
        gameController = controller;
        //gameController.setControl(KeyEvent.VK_SPACE, KeyEvent.KEY_PRESSED, randObj, "doNothing", "Space Pressed", "DO NOTHING!!");
        ///gameController.setControl(KeyEvent.VK_UP, KeyEvent.KEY_PRESSED, randObj, "doSomething", "Up Pressed", "DO SOMETHING!!");
        // create container that will work with Window manager
        JFrame frame = new JFrame(TITLE);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // add our user interface components to Frame and show it
        frame.getContentPane().add(myDisplayFrame);
        frame.pack();
        frame.setVisible(true);
        
        Config.initMap();

        myViewFactory = new ViewFactory(myDisplayFrame, gameController);
        myViewFactory.generateHeader();
        myViewFactory.generateGrid();
        myViewFactory.generateRadioButtons();
        KeyboardController tempController = new KeyboardController(myDisplayFrame);
        tempController.setControl(KeyEvent.VK_ENTER, KeyEvent.KEY_PRESSED, myViewFactory, "swap", "Enter Pressed", "SWAP");
    }
    
    public void doNothing() throws NoSuchMethodException, IllegalAccessException {
        System.out.println("Space called me earlier");
    }
    
    public void doSomething() {
        System.out.println("Up called me earlier");
    }
}
