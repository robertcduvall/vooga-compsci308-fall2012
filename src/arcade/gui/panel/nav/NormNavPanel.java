package arcade.gui.panel.nav;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;
import arcade.gui.Arcade;
import arcade.gui.panel.ArcadePanel;

/**
 * This will be the top navigation panel. This needs to be implemented.
 * 
 * @author Michael Deng
 * 
 */
public class NormNavPanel extends ANavPanel {

    public NormNavPanel (Arcade a) {
        super(a);
    }

    @Override
    public ArcadePanel createPanel () {
        ArcadePanel myPanel = initializeNewPanel();
        myPanel.setBackground(Color.ORANGE);
        JButton button1 = new JButton("All Games");
        JButton button2 = new JButton("My Profile Page");
        
        myPanel.add(button1);
        myPanel.add(button2);
        
        button1.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed (ActionEvent arg0) {
                getArcade().replacePanel("GameList");
            }
              
          });
          
          button2.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed (ActionEvent e) {
                getArcade().replacePanel("UserProfile");
            }
              
          });
        
        return myPanel;
    }

}
