package arcade.gui.panel.user;

import java.awt.Color;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import arcade.gui.Arcade;
import arcade.gui.panel.ArcadePanel;

/**
 * This is the normal user panel post-login.
 * This will show:
 * "Welcome, Michael" and then have 2 buttons: Logout and Exit
 * 
 * @author Michael Deng
 *
 */
public class NormUserPanel extends AUserPanel {

    public NormUserPanel (Arcade a) {
        super(a);
    }

    @Override
    public ArcadePanel createPanel () {

        ArcadePanel myPanel = initializeNewPanel();
        System.out.println("NormUserPanel");

        myPanel.setBackground(Color.BLACK);
    
        JLabel welcomeLabel = new JLabel("Welcome, REALLYLONGNAME!", JLabel.CENTER);
        welcomeLabel.setForeground(Color.WHITE);
        JButton button1 = new JButton("Logout");
          
          
          GroupLayout layout = new GroupLayout(myPanel);
           myPanel.setLayout(layout);
           layout.setAutoCreateGaps(true);
           layout.setAutoCreateContainerGaps(true);
          
           layout.setHorizontalGroup(layout.createSequentialGroup()
                                     .addContainerGap(20, 20)
                                     .addComponent(welcomeLabel)
                                     .addComponent(button1));
           
           layout.setVerticalGroup(layout.createSequentialGroup()
                                   .addContainerGap(30, 30)
                                   .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                                             .addComponent(welcomeLabel)
                                             .addComponent(button1)));
           
          
          
          return myPanel;
    
    
    
    }


    

}
