package arcade.gui.frame;

import net.miginfocom.swing.MigLayout;
import arcade.gui.Arcade;


/**
 * 
 * Alternative frame creator. Creates the frame for the arcade.
 * 
 * @author Michael Deng
 * 
 */
public class AltFrameCreator extends MigFrameCreator {

    /**
     * Constructor for AltFrameCreator
     * 
     * @param a arcade
     */
    public AltFrameCreator (Arcade a) {
        super(a);
    }

    @Override
    protected void setLayoutManager () {
        getContentPanel().setLayout(new MigLayout("", "[]0[grow]0[]", "[]0[]0[grow]0[]"));
    }

    @Override
    protected void addSubPanels () {
        // logo panel
        addPanelWithoutBorder(300, 100, "logo", "align center");

        // blank panel
        addPanelWithoutBorder(500, 100, "blank", "growx, align center");

        // user panel
        addPanelWithoutBorder(300, 100, "user", "align center, wrap");

        // nav panel
        addPanelWithoutBorder(1100, 50, "nav", "span, growx, wrap");

        // foot panel
        addPanelWithoutBorder(1100, 50, "foot", "span, growx");

        // main panel
        addPanelWithBorder(800, 450, "main", "span 2, grow");

        // search panel
        addPanelWithBorder(300, 450, "search", "growy, wrap");
    }

}
