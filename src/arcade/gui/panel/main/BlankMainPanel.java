package arcade.gui.panel.main;

import java.awt.Color;
import javax.swing.JLabel;
import net.miginfocom.swing.MigLayout;
import arcade.gui.Arcade;
import arcade.gui.panel.ArcadePanel;


/**
 * 
 * @author Michael Deng
 * 
 */
public class BlankMainPanel extends AMainPanel {

    public BlankMainPanel (Arcade a) {
        super(a);
    }

    @Override
    public ArcadePanel createPanel () {
        ArcadePanel myPanel = initializeNewPanel();

        JLabel label = new JLabel();
        label.setText("Please login to begin.");
        label.setForeground(Color.WHITE);
        label.setVerticalTextPosition(JLabel.CENTER);
        label.setHorizontalTextPosition(JLabel.CENTER);

        myPanel.setLayout(new MigLayout("", "[grow]", "[grow]"));
        myPanel.add(label, "align center");
        
        return myPanel;
    }

}
