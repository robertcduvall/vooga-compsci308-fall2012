package arcade.gui.panel.logo;

import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import net.miginfocom.swing.MigLayout;
import edu.cmu.relativelayout.Binding;
import edu.cmu.relativelayout.BindingFactory;
import edu.cmu.relativelayout.RelativeConstraints;
import edu.cmu.relativelayout.RelativeLayout;
import arcade.gui.Arcade;
import arcade.gui.panel.AbstractPanelCreator;
import arcade.gui.panel.ArcadePanel;
import arcade.utility.ImageReader;


/**
 * 
 * @author Michael Deng
 * 
 */
abstract public class ALogoPanel extends AbstractPanelCreator {

    private static final String PANEL_TYPE = "logo";

    public ALogoPanel (Arcade a) {
        super(a);
        super.setPanelType(PANEL_TYPE);

    }

    protected ArcadePanel addLogo (String fileName, ArcadePanel myPanel) {
        
        ImageIcon icon = new ImageIcon(ImageReader.loadImage("src/arcade/gui/images", fileName));
        JLabel picLabel = new JLabel(icon);

        myPanel.setLayout(new MigLayout("", "[c]", "[c]"));
        myPanel.add(picLabel, "align center");
        
        return myPanel;
    }

}
