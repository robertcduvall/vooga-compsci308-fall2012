package arcade.gui.panel.user;

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
        // TODO Auto-generated constructor stub
    }

    @Override
    public ArcadePanel createPanel () {
        // TODO Auto-generated method stub
        return null;
    }


    
//    public static void main(String [] args){
//        Arcade testArcade = new Arcade();
//        JFrame testFrame = new JFrame();
//        UserPanel testMe = new UserPanel(testArcade);
//        testFrame.getContentPane().add(testMe);
//        testFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        testFrame.pack();
//        testFrame.setSize(new Dimension(500, 500));
//        testFrame.setVisible(true);
//        
//    }

}