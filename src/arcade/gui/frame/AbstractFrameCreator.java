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
 * The top-level abstract class for the framecreator hierarchy.
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

    /**
     * Constructor for the AbstractFrameCreator
     * 
     * @param a reference to the arcade
     */
    public AbstractFrameCreator (Arcade a) {
        myArcade = a;
        myPanels = new HashMap<String, ArcadePanel>();
    }

    /**
     * This method is the main method that will
     * set up the Java JFrame. This method is called by the
     * arcade to create the JFrame.
     * 
     * @return an ArcadeFrame to use in the arcade
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

        // set other frame-related properties
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myFrame.setLocationRelativeTo(null);

        myFrame.pack();
        myFrame.setVisible(true);
        return myFrame;

    }

    /**
     * Creates and populates the contentPanel (which fits into the
     * contentpane of the ArcadeFrame).
     */
    protected void createContentPanel () {
        myContentPanel = new ArcadePanel(myArcade, "contentpanel");
        myContentPanel.removeAll();

        myContentPanel.setBackground(Color.BLACK);

        setLayoutManager();
        addSubPanels();
    }

    /**
     * Must be implemented with assignment of layout manager. This
     * method specifies which layout manager we are using to layout
     * the ArcadeFrame.
     */
    abstract protected void setLayoutManager ();

    /**
     * Must be implemented with instructions to add each subpanel. This
     * method specifies which subpanels are added to the ArcadeFrame
     * and their locations in the frame.
     */
    abstract protected void addSubPanels ();

    /**
     * Call this method on each subpanel in the ArcadeFrame. This method
     * sets up the subpanel with a scrollpane and a holder panel (so that
     * panels may be swapped in and out on the fly).
     * 
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

        // add scrollpane to sizing panel
        panel.setLayout(new BorderLayout());
        panel.add(scrollPane, BorderLayout.CENTER);

    }

    /**
     * This is a getter method for the contentpanel
     * of the ArcadeFrame. Use this method to set the
     * layout manager for the content panel.
     * 
     * @return reference to the content panel
     */
    protected ArcadePanel getContentPanel () {
        return myContentPanel;
    }

    /**
     * Getter method for a reference to the arcade.
     * 
     * @return reference to the arcade
     */
    protected Arcade getArcade () {
        return myArcade;
    }

}
