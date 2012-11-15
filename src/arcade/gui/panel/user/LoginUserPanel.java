package arcade.gui.panel.user;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import edu.cmu.relativelayout.Binding;
import edu.cmu.relativelayout.BindingFactory;
import edu.cmu.relativelayout.RelativeConstraints;
import edu.cmu.relativelayout.RelativeLayout;
import arcade.gui.Arcade;
import arcade.gui.panel.ArcadePanel;

/**
 * This is the user panel during login.
 * It has 2 buttons: Login and New User
 * 
 * @author Michael Deng
 *
 */
public class LoginUserPanel extends AUserPanel {

    private GridBagConstraints c;
    
    public LoginUserPanel (Arcade a) {
        super(a);
    }

    @Override
    public ArcadePanel createPanel () {
        ArcadePanel myPanel = initializeNewPanel();
        System.out.println("LoginUserPanel");

        myPanel.setBackground(Color.BLACK);
//        myPanel.setLayout(new GridBagLayout());
//        
//        JLabel welcomeLabel = new JLabel("Welcome!");
//        c = new GridBagConstraints();
//        c.fill = GridBagConstraints.BOTH;
//        c.gridx = 0;
//        c.gridy = 0;
//        c.gridwidth = 2;
//        c.gridheight = 1;
//        c.weightx = 0.5;
//        c.weighty = 1;
//        // c.ipadx = ;
//        // c.ipady = ;
//        myPanel.add(welcomeLabel, c);
//        
//        
//        JButton firstButton = new JButton("Login");
//        c.fill = GridBagConstraints.BOTH;
//        c.gridx = GridBagConstraints.RELATIVE;
//        c.gridy = 0;
//        c.gridwidth = 1;
//        c.gridheight = 1;
//        c.weightx = 0.25;
//        c.weighty = 1;
//         c.ipadx = 10;
//         c.ipady = 10;
//        
//        firstButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed (ActionEvent e) {
//                getArcade().replacePanel("LoginMainPanel");
//            }
//        });
//        
//        myPanel.add(firstButton, c); 
//        
//        
//        JButton secondButton = new JButton("New User");
//        c.fill = GridBagConstraints.BOTH;
//        c.gridx = GridBagConstraints.RELATIVE;
//        c.gridy = 0;
//        c.gridwidth = 1;
//        c.gridheight = 1;
//        c.weightx = 0.25;
//        c.weighty = 1;
//        c.ipadx = 10;
//        c.ipady = 10;
//        
//        firstButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed (ActionEvent e) {
//                getArcade().replacePanel("NewUserMainPanel");
//            }
//        });
//        
//        myPanel.add(secondButton, c); 
        
        
//        myPanel.setLayout(new RelativeLayout());
//
//        JLabel welcomeLabel = new JLabel("Welcome!", JLabel.CENTER);
//        JButton button1 = new JButton("Login");
//        JButton button2 = new JButton("New User");
//
//        welcomeLabel.setForeground(Color.WHITE);
//        
//        // Create a BindingFactory with the default margin and control spacing:
//        BindingFactory bf = new BindingFactory();
//
//        // Make some bindings using our BindingFactory. Note how leftEdge() and
//        // topEdge() don't
//        // take any arguments, but rightOf() needs the control we want to be to
//        // the right of.
//        Binding leftEdge = bf.leftEdge();
//        Binding topEdge = bf.topEdge();
//        
//        Binding rightOfButton1 = bf.rightOf(button1);
//        Binding rightOfLabel1 = bf.rightOf(welcomeLabel);
//        Binding bottomEdge = bf.bottomEdge();
//
//        // Create a constraints object for each component, and add the bindings
//        // to the
//        // constraints:
//        RelativeConstraints label1Constraints = new RelativeConstraints();
//        label1Constraints.addBindings(leftEdge, topEdge, bottomEdge);
//        
//        
//        RelativeConstraints button1Constraints = new RelativeConstraints();
//        button1Constraints.addBindings(topEdge, rightOfLabel1);
//
//        RelativeConstraints button2Constraints = new RelativeConstraints();
//        button2Constraints.addBinding(rightOfButton1);
//        button2Constraints.addBinding(topEdge);
//
//        // Lastly, add the components to the panel with the constraints:
//        myPanel.add(welcomeLabel, label1Constraints);
//        myPanel.add(button1, button1Constraints);
//        myPanel.add(button2, button2Constraints);
        
        
//        myPanel.setLayout(new GridLayout(1, 3, 10, 10));
//        
//      JLabel welcomeLabel = new JLabel("Welcome!");
//      JButton button1 = new JButton("Login");
//      JButton button2 = new JButton("New User");
//
//      myPanel.add(welcomeLabel);
//      myPanel.add(button1);
//      myPanel.add(button2);
        
        
      JLabel welcomeLabel = new JLabel("Welcome!", JLabel.CENTER);
      welcomeLabel.setForeground(Color.WHITE);
      JButton button1 = new JButton("Login");
      JButton button2 = new JButton("New User");
      
      button1.addActionListener(new ActionListener(){

        @Override
        public void actionPerformed (ActionEvent arg0) {
            getArcade().replacePanel("MainDefault");
        }
          
      });
      
      button2.addActionListener(new ActionListener(){

        @Override
        public void actionPerformed (ActionEvent e) {
            getArcade().replacePanel("NewUser");
        }
          
      });
        
        
        GroupLayout layout = new GroupLayout(myPanel);
         myPanel.setLayout(layout);
         layout.setAutoCreateGaps(true);
         layout.setAutoCreateContainerGaps(true);
        
         layout.setHorizontalGroup(layout.createSequentialGroup()
                                   .addContainerGap(40, 40)
                                   .addComponent(welcomeLabel)
                                   .addComponent(button1)
                                   .addComponent(button2));
         
         layout.setVerticalGroup(layout.createSequentialGroup()
                                 .addContainerGap(30, 30)
                                 .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                                           .addComponent(welcomeLabel)
                                           .addComponent(button1)
                                           .addComponent(button2)));
         
        
        
        return myPanel;
    }

}
