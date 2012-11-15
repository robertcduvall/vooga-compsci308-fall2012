package arcade.gui.panel.main;

import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
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
public class LoginMainPanel extends AMainPanel {
    
    public LoginMainPanel (Arcade a) {
        super(a);
        createPanel();
    }

    @Override
    public ArcadePanel createPanel () {
        ArcadePanel myPanel = initializeNewPanel();
        myPanel.setBackground(Color.GRAY);
        myPanel.setLayout(new RelativeLayout());
        BindingFactory bf = new BindingFactory();

        Binding leftEdge = bf.leftEdge();
        Binding topEdge = bf.topEdge();
        Binding bottomEdge = bf.bottomEdge();
        Binding rightEdge = bf.rightEdge();
        RelativeConstraints imageConstraints = new RelativeConstraints();
        imageConstraints.addBindings(leftEdge, topEdge, bottomEdge, rightEdge);
        JTextField textField = new JTextField(20);
        ImageIcon icon = new ImageIcon(ImageReader.loadImage("src/arcade/gui/images", "Arcade_logo2.png"));
        JLabel picLabel = new JLabel(icon);
        
        myPanel.add(textField, imageConstraints);

        myPanel.add(picLabel, imageConstraints);
        return myPanel;
    }

}
