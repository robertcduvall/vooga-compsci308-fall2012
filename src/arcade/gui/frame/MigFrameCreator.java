package arcade.gui.frame;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import arcade.gui.Arcade;
import arcade.gui.panel.ArcadePanel;

public abstract class MigFrameCreator extends AbstractFrameCreator {

    public MigFrameCreator (Arcade a) {
        super(a);
  
    }

    /**
     * 
     * @param width width of the panel
     * @param height height of the panel
     * @param panelName name of the panel
     * @param migLayout mig layout specifications for the panel
     * @return The panel that was added
     */
    protected ArcadePanel addPanelWithBorder (int width, int height, String panelName, String migLayout) {

        ArcadePanel panel = addPanelWithoutBorder(width, height, panelName, migLayout);
        panel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 5));
        
        return panel;
    }

    /**
     * 
     * @param width width of the panel
     * @param height height of the panel
     * @param panelName name of the panel
     * @param migLayout mig layout specifications for the panel
     * @return The panel that was added
     */
    protected ArcadePanel addPanelWithoutBorder (int width, int height, String panelName, String migLayout) {

        ArcadePanel panel = new ArcadePanel(getArcade(), "base");
        panel.setMinimumSize(new Dimension(width, height));
        setupPanel(panelName, panel);
        getContentPanel().add(panel, migLayout);
        
        return panel;
    }
    
    
}
