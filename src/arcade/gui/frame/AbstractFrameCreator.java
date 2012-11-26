package arcade.gui.frame;

import java.awt.Dimension;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JFrame;
import arcade.gui.Arcade;
import arcade.gui.panel.ArcadePanel;

/**
 * The top-level abstract class for frame creators
 * 
 * @author Michael Deng
 *
 */
public abstract class AbstractFrameCreator {

    private static final String ARCADE_NAME = "Duke CS 308 Arcade";
    private static final int FRAME_HEIGHT = 650;
    private static final int FRAME_WIDTH = 1100;
    private static final boolean FRAME_RESIZABLE = true;
    
    private Arcade myArcade;
    private ArcadePanel myContentPanel;
    private Map<String, ArcadePanel> myPanels;
    
    
    
    public AbstractFrameCreator (Arcade a){
        myArcade = a;
        myPanels = new HashMap<String, ArcadePanel>();
        
        myContentPanel = new ArcadePanel(myArcade, "contentpanel");
    }
    
    
    
    
    /**
     * this method is the main method that will
     * set up the jframe.. and get it read to add
     * 
     * @return
     */
    public ArcadeFrame createFrame () {
        
        // layout the content panel
        layoutContentPanel();

        ArcadeFrame myFrame = new ArcadeFrame(myArcade, ARCADE_NAME, myPanels);

        // set size
        myFrame.setSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
        myFrame.setMinimumSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
        myFrame.setResizable(FRAME_RESIZABLE);

        // add myContentPanel
        myFrame.getContentPane().removeAll();
        myFrame.getContentPane().add(myContentPanel);

        // set other things
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myFrame.setLocationRelativeTo(null);

        // myFrame.pack();
        myFrame.setVisible(true);
        return myFrame;
        
    }
    
    
    /**
     * Method allows programmer to define the placement
     * and size of panels in the frame, using a layout manager
     * 
     * This method lays out the base panels in the frame. 
     * Remember to add each base panel to the content panel.
     */
    protected abstract void layoutContentPanel () ;
        

    /**
     * Call this method on each base panel in the frame
     * to prepare it for use in the arcade
     */
    protected void setupPanel(String panelType, ArcadePanel panel){
        
        // add scrollpane 
        
        
        // add holder panel
        
        
        // add reference to holder panel to map
        
        
    }
    
    
    /**
     * 
     * @return reference to the content panel
     */
    protected ArcadePanel getContentPanel(){
        return myContentPanel;
    }
    
    
    
}
