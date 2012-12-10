package arcade.gui.frame;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import arcade.gui.Arcade;
import arcade.gui.panel.ArcadePanel;


/**
 * Class that is part of the framecreator hierarchy. This class is used
 * to create new framecreators using the MIG layout manager. This class
 * serves the purpose of reducing repeated code of framecreators that use
 * the MIG layout.
 * 
 * @author Michael Deng
 * 
 */
public abstract class MigFrameCreator extends AbstractFrameCreator {

    /**
     * Constructor for the MigFrameCreator
     * 
     * @param a reference to the arcade
     */
    public MigFrameCreator (Arcade a) {
        super(a);
    }

    /**
     * Method used to add a panel (with a border) in the layout
     * 
     * @param width width of the panel
     * @param height height of the panel
     * @param panelName name of the panel
     * @param migLayout mig layout specifications for the panel
     * @return The panel that was added
     */
    protected ArcadePanel addPanelWithBorder (int width, int height, String panelName,
                                              String migLayout) {

        ArcadePanel panel = addPanelWithoutBorder(width, height, panelName, migLayout);
        panel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 5));

        return panel;
    }

    /**
     * Method used to add a panel (without a border) in the layout
     * 
     * @param width width of the panel
     * @param height height of the panel
     * @param panelName name of the panel
     * @param migLayout mig layout specifications for the panel
     * @return The panel that was added
     */
    protected ArcadePanel addPanelWithoutBorder (int width, int height, String panelName,
                                                 String migLayout) {

        ArcadePanel panel = new ArcadePanel(getArcade(), "base");
        panel.setMinimumSize(new Dimension(width, height));
        setupPanel(panelName, panel);
        getContentPanel().add(panel, migLayout);

        return panel;
    }

}
