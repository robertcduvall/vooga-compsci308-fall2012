package arcade.gui.panel.user;

import arcade.gui.Arcade;
import arcade.gui.panel.AbstractPanelCreator;
import arcade.gui.panel.ArcadePanel;


/**
 * Abstract class used to create user panels
 * 
 * @author Michael Deng
 * 
 */
abstract public class AUserPanel extends AbstractPanelCreator {

    private static final String PANEL_TYPE = "user";

    /**]
     * 
     * @param a
     */
    public AUserPanel (Arcade a) {
        super(a);
        super.setPanelType(PANEL_TYPE);
    }

    protected ArcadePanel preparePanel (ArcadePanel newPanel) {
        // nothing
        return newPanel;
    }
}
