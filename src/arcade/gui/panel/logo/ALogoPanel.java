package arcade.gui.panel.logo;

import java.awt.Dimension;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import net.miginfocom.swing.MigLayout;
import arcade.gui.Arcade;
import arcade.gui.panel.AbstractPanelCreator;
import arcade.gui.panel.ArcadePanel;
import arcade.utility.ImageReader;


/**
 * Abstract class for creating logo panels
 * 
 * @author Michael Deng
 * 
 */
abstract public class ALogoPanel extends AbstractPanelCreator {

    private static final String PANEL_TYPE = "logo";

    /**
     * 
     * @param a
     */
    public ALogoPanel (Arcade a) {
        super(a);
        super.setPanelType(PANEL_TYPE);

    }

    protected ArcadePanel preparePanel (ArcadePanel newPanel){
        // nothing 
        return newPanel;
    }

    
    protected ArcadePanel addLogo (String fileName, ArcadePanel myPanel) {
        ImageIcon icon = new ImageIcon(ImageReader.loadImage("src/arcade/gui/images", fileName));
        JLabel picLabel = new JLabel(icon);
        myPanel.setLayout(new MigLayout("", "[c]", "[c]"));
        myPanel.add(picLabel, "align center");
        
        myPanel.setPreferredSize(new Dimension(0,0));
        return myPanel;
    }

}
