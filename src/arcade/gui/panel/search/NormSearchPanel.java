package arcade.gui.panel.search;

import javax.swing.JPanel;
import arcade.gui.Arcade;
import arcade.gui.panel.ArcadePanel;

/**
 * 
 * @author Michael Deng
 * 
 */
public class NormSearchPanel extends ASearchPanel {

    public NormSearchPanel (Arcade a) {
        super(a);
    }

    @Override
    public ArcadePanel createPanel () {
        ArcadePanel myPanel = initializeNewPanel();
        System.out.println("NormSearchPanel");
        
        return myPanel;
    }


    

}
