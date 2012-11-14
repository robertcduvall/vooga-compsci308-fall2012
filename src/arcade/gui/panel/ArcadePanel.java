package arcade.gui.panel;

import javax.swing.JPanel;
import arcade.gui.Arcade;


/**
 * 
 * @author Michael
 * 
 */
public class ArcadePanel extends JPanel {

    private String myPanelType;
    private Arcade myArcade;

    public ArcadePanel (Arcade a, String panelType) {
        myArcade = a;
        myPanelType = panelType;
    }

    public String getPanelType () {
        return myPanelType;
    }
    
    public Arcade getArcade(){
        return myArcade;
    }

}
