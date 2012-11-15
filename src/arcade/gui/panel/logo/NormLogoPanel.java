package arcade.gui.panel.logo;

import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import edu.cmu.relativelayout.Binding;
import edu.cmu.relativelayout.BindingFactory;
import edu.cmu.relativelayout.RelativeConstraints;
import edu.cmu.relativelayout.RelativeLayout;
import arcade.gui.Arcade;
import arcade.gui.panel.ArcadePanel;
import arcade.utility.ImageReader;


/**
 * 
 * @author Michael Deng
 * 
 */
public class NormLogoPanel extends ALogoPanel {

    public NormLogoPanel (Arcade a) {
        super(a);

    }

    @Override
    public ArcadePanel createPanel () {
        ArcadePanel myPanel = initializeNewPanel();
        System.out.println("NormLogoPanel");

        
        myPanel.setBackground(Color.BLACK);
        myPanel.setLayout(new RelativeLayout());
        BindingFactory bf = new BindingFactory();
        
        Binding leftEdge = bf.leftEdge();
        Binding topEdge = bf.topEdge();
        Binding bottomEdge = bf.bottomEdge();
        Binding rightEdge = bf.rightEdge();
        RelativeConstraints imageConstraints = new RelativeConstraints();
        imageConstraints.addBindings(leftEdge, topEdge, bottomEdge, rightEdge);
              
        ImageIcon icon =
                new ImageIcon(ImageReader.loadImage("src/arcade/gui/images", "Arcade_logo.png"));
        JLabel picLabel = new JLabel(icon);
        
        myPanel.add(picLabel, imageConstraints);
        
        return myPanel;
    }

}
