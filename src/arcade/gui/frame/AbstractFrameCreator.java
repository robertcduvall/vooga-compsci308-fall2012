package arcade.gui.frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
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
    private static final int FRAME_HEIGHT = 700;
    private static final int FRAME_WIDTH = 1130;
    private static final boolean FRAME_RESIZABLE = true;

    private Arcade myArcade;
    private ArcadePanel myContentPanel;
    private Map<String, ArcadePanel> myPanels;

    public AbstractFrameCreator (Arcade a) {
        myArcade = a;
        myPanels = new HashMap<String, ArcadePanel>();
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

        // create the arcadeframe
        ArcadeFrame myFrame = new ArcadeFrame(myArcade, ARCADE_NAME, myPanels);

        // set size
//        myFrame.setSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
        myFrame.setMinimumSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
        myFrame.setResizable(FRAME_RESIZABLE);

        // add myContentPanel
        myFrame.getContentPane().removeAll();
        myFrame.getContentPane().setLayout(new BorderLayout());
        myFrame.getContentPane().add(myContentPanel, BorderLayout.CENTER);

        // set other things
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myFrame.setLocationRelativeTo(null);

         myFrame.pack();
        myFrame.setVisible(true);
        return myFrame;

    }

    /**
     * Method allows programmer to define the placement
     * and size of panels in the frame, using a layout manager
     * 
     * This method lays out the base panels in the frame.
     * Remember to add each base panel to the content panel.
     * 
     * Each subclass MUST override this method, and 
     * call up: super.layoutContentPanel()
     */
    protected void layoutContentPanel (){
        myContentPanel = new ArcadePanel(myArcade, "contentpanel");
        myContentPanel.removeAll();
//        myContentPanel.setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
//        myContentPanel.setMaximumSize(new Dimension(10000, 10000));

        myContentPanel.setBackground(Color.BLACK);
    }

    /**
     * Call this method on each base panel in the frame
     * to prepare it for use in the arcade
     */
    protected void setupPanel (String panelType, ArcadePanel panel) {

        // create holder panel
        ArcadePanel holder = new ArcadePanel(myArcade, panelType + "holder");
        holder.setLayout(new BorderLayout());
//        holder.setPreferredSize(new Dimension( 2000,2000));

        // add reference to holder panel to map
        myPanels.put(panelType, holder);

        // add holder panel to scrollpane
        JScrollPane scrollPane = new JScrollPane(holder);
        holder.setAutoscrolls(true);
//        scrollPane.setPreferredSize(new Dimension(300, 300));

        // add scrollpane to sizing panel
        panel.setLayout(new BorderLayout());
        panel.add(scrollPane, BorderLayout.CENTER);
        
        
//        panel.add(holder, BorderLayout.CENTER);

    }

    /**
     * 
     * @return reference to the content panel
     */
    protected ArcadePanel getContentPanel () {
        return myContentPanel;
    }
    
    /**
     * 
     * @return reference to the arcade
     */
    protected Arcade getArcade(){
        return myArcade;
    }

}
