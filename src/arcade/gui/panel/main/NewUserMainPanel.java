package arcade.gui.panel.main;

import java.awt.Color;
import arcade.gui.Arcade;
import arcade.gui.panel.ArcadePanel;

public class NewUserMainPanel extends AMainPanel{

    public NewUserMainPanel (Arcade a) {
        super(a);
        // TODO Auto-generated constructor stub
    }

    @Override
    public ArcadePanel createPanel () {
        ArcadePanel myPanel = initializeNewPanel();
        myPanel.setBackground(Color.YELLOW);
        System.out.println("NewUserMainPanel");
        
        return myPanel;
    }

}
