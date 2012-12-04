package arcade.gui.frame;

import net.miginfocom.swing.MigLayout;
import arcade.gui.Arcade;


/**
 * 
 * Primary frame creator. Creates the frame for the arcade.
 * 
 * @author Michael Deng
 * 
 */
public class MainFrameCreator extends MigFrameCreator {

    /**
     * Constructor for MainFrameCreator
     * 
     * @param a arcade
     */
    public MainFrameCreator (Arcade a) {
        super(a);
    }

    @Override
    protected void setLayoutManager () {
        getContentPanel().setLayout(new MigLayout("", "[]0[grow]0[]", "[]0[]0[grow]0[]"));
    }

    @Override
    protected void addSubPanels () {

        addPanelWithoutBorder(300, 100, "logo", "align center");
        addPanelWithoutBorder(500, 100, "blank", "growx, align center");
        addPanelWithBorder(300, 100, "user", "align center, wrap");
        addPanelWithoutBorder(1100, 50, "nav", "span, growx, wrap");
        addPanelWithBorder(800, 450, "main", "span 2, grow");
        addPanelWithBorder(300, 450, "search", "growy, wrap");
        addPanelWithoutBorder(1100, 50, "foot", "span, growx");

    }

}
