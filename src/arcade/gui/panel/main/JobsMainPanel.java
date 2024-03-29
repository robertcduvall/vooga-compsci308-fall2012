package arcade.gui.panel.main;

import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import net.miginfocom.swing.MigLayout;
import arcade.gui.Arcade;
import arcade.gui.panel.ArcadePanel;


/**
 * 
 * @author Michael Deng
 * 
 */
public class JobsMainPanel extends AMainPanel {

    public JobsMainPanel (Arcade a) {
        super(a);
    }

    @Override
    public ArcadePanel createPanel () {
        ArcadePanel myPanel = initializeNewPanel();

        ImageIcon icon = new ImageIcon("src/arcade/gui/images/jobsindia.png");

        JLabel label = new JLabel(icon);
//        label.setText("This is the Jobs page.");
        label.setForeground(Color.WHITE);
        label.setVerticalTextPosition(JLabel.CENTER);
        label.setHorizontalTextPosition(JLabel.CENTER);

        myPanel.setLayout(new MigLayout("", "[grow]", "[grow]"));
        myPanel.add(label, "align center");

        return myPanel;
    }

}