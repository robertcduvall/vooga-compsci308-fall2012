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
public class AboutMainPanel extends AMainPanel {

    public AboutMainPanel (Arcade a) {
        super(a);
    }

    @Override
    public ArcadePanel createPanel () {
        ArcadePanel myPanel = initializeNewPanel();

        ImageIcon icon = new ImageIcon("src/arcade/gui/images/rcd.jpg");
        JLabel label = new JLabel(icon);
        //label.setText("This is the About page.");
        label.setForeground(Color.WHITE);
        label.setVerticalTextPosition(JLabel.CENTER);
        label.setHorizontalTextPosition(JLabel.CENTER);

        myPanel.setLayout(new MigLayout("", "[grow]", "[grow]"));
        myPanel.add(label, "align center");

        return myPanel;
    }

}