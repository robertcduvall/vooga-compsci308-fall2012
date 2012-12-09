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
import util.input.core.KeyboardController;

public class Main {

    private static final Dimension SIZE =
            new Dimension(Config.FRAME_WIDTH, Config.FRAME_HEIGHT);

    private static final String TITLE = "Game Settings";

    /**
     * Runs the GEDIVA project
     * 
     * @param args unused inputs
     * @throws InvalidXMLTagException 
     * @throws RepeatedColumnNameException 
     * @throws IllegalAccessException 
     * @throws NoSuchMethodException 
     */
    public static void main (String[] args) throws RepeatedColumnNameException, InvalidXMLTagException, NoSuchMethodException, IllegalAccessException {
        Frame myDisplayFrame = new Frame(SIZE);
        KeyboardController gameController = new KeyboardController(myDisplayFrame);
        Main randObj = new Main();
        gameController.setControl(KeyEvent.VK_SPACE, KeyEvent.KEY_PRESSED, randObj, "doNothing", "Space Pressed", "DO NOTHING!!");
        gameController.setControl(KeyEvent.VK_UP, KeyEvent.KEY_PRESSED, randObj, "doSomething", "Up Pressed", "DO SOMETHING!!");
        // create container that will work with Window manager
        JFrame frame = new JFrame(TITLE);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // add our user interface components to Frame and show it
        frame.getContentPane().add(myDisplayFrame);
        frame.pack();
        frame.setVisible(true);
        

        ViewFactory viewFactory = new ViewFactory(myDisplayFrame, gameController);
        viewFactory.generateHeader();
        viewFactory.generateGrid();
        viewFactory.generateRadioButtons();
        
    }
    
    public void doNothing() {
        System.out.println("I DO NOTHING");
    }
    
    public void doSomething() {
        System.out.println("Still don't really do anything");
    }
}
