package arcade.gui.frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.HashMap;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.border.Border;
import arcade.gui.Arcade;
import arcade.gui.panel.ArcadePanel;


/**
 * The top-level abstract class for frame creators
 * 
 * @author Michael Deng
 * 
 */
public abstract class AbstractFrameCreator {

    private final String ARCADE_NAME = "Duke CS 308 Arcade";
    private final int FRAME_HEIGHT = 700;
    private final int FRAME_WIDTH = 1130;
    private final boolean FRAME_RESIZABLE = true;

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
        createContentPanel();

        // create the arcadeframe
        ArcadeFrame myFrame = new ArcadeFrame(myArcade, ARCADE_NAME, myPanels);

        // set size
        // myFrame.setSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
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
     * Creates and populates the contentPanel
     * The contentPanel is fit into the contentPane of the Frame
     */
    protected void createContentPanel () {
        myContentPanel = new ArcadePanel(myArcade, "contentpanel");
        myContentPanel.removeAll();
        // myContentPanel.setPreferredSize(new Dimension(FRAME_WIDTH,
        // FRAME_HEIGHT));

        myContentPanel.setBackground(Color.BLACK);
        
        setLayoutManager();
        addSubPanels();
        
    }

    /**
     * Must be implemented with assignment of layout manager
     */
    abstract protected void setLayoutManager();
    
    /**
     * Must be implemented with instructions to add each subpanel
     */
    abstract protected void addSubPanels();
    
    
    
    
    /**
     * Call this method on each base panel in the frame
     * to prepare it for use in the arcade
     */
    protected void setupPanel (String panelType, ArcadePanel panel) {

        // create holder panel
        ArcadePanel holder = new ArcadePanel(myArcade, panelType + "holder");
        holder.setLayout(new BorderLayout());
        holder.setBackground(Color.BLACK);

        // add reference to holder panel to map
        myPanels.put(panelType, holder);

        // add holder panel to scrollpane
        JScrollPane scrollPane = new JScrollPane(holder);
        Border border = BorderFactory.createEmptyBorder(0, 0, 0, 0);
        scrollPane.setBorder(border);

        // holder.setAutoscrolls(true);

        // add scrollpane to sizing panel
        panel.setLayout(new BorderLayout());
        panel.add(scrollPane, BorderLayout.CENTER);

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
    protected Arcade getArcade () {
        return myArcade;
    }

}