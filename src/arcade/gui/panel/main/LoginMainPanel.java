package arcade.gui.panel.main;

import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
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
 * @author Michael Deng, Robert Bruce
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

        Binding userLeftEdge = bf.leftEdge();
        Binding userTopEdge = bf.topEdge();
        Binding userBottomEdge = bf.bottomEdge();
        Binding userRightEdge = bf.rightEdge();
        RelativeConstraints userConstraints = new RelativeConstraints();
        userConstraints.addBindings(userLeftEdge, userTopEdge, userBottomEdge, userRightEdge);
        

        JTextField userNameField = new JTextField(17);
        myPanel.add(userNameField, userConstraints);

        RelativeConstraints passwordConstraints = new RelativeConstraints();
        Binding passLeftEdge = bf.leftEdge();
        Binding passTopEdge = bf.below(userNameField);
        Binding passBottomEdge = bf.bottomEdge();
        Binding passRightEdge = bf.rightEdge();
        passwordConstraints.addBindings(passLeftEdge, passTopEdge, passBottomEdge, passRightEdge);
        JPasswordField passwordNameField = new JPasswordField(17);
        myPanel.add(passwordNameField, passwordConstraints);

        System.out.println("LoginMainPanel");

        return myPanel;
    }

}
