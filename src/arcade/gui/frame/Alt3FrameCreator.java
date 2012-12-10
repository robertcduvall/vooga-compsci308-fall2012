package arcade.gui.frame;

import net.miginfocom.swing.MigLayout;
import arcade.gui.Arcade;


/**
 * 
 * Yet another alternative frame creator. Creates the frame for the arcade.
 * 
 * @author Michael Deng
 * 
 */
public class Alt3FrameCreator extends MigFrameCreator {

    // specifies the sizes for each panel location in the frame
    private static final int LOGO_WIDTH = 300;
    private static final int LOGO_HEIGHT = 100;
    private static final int USER_WIDTH = 300;
    private static final int USER_HEIGHT = 100;
    private static final int NAV_WIDTH = 1100;
    private static final int NAV_HEIGHT = 50;
    private static final int MAIN_WIDTH = 800;
    private static final int MAIN_HEIGHT = 550;
    private static final int SEARCH_WIDTH = 300;
    private static final int SEARCH_HEIGHT = 350;
    private static final int FOOT_WIDTH = 1100;
    private static final int FOOT_HEIGHT = 50;

    /**
     * Constructor for AltFrameCreator
     * 
     * @param a reference to the arcade
     */
    public Alt3FrameCreator (Arcade a) {
        super(a);
    }

    /**
     * Method sets the layout manager to MIG layout
     */
    @Override
    protected void setLayoutManager () {
        getContentPanel().setLayout(new MigLayout("", "[grow]0[]", "[]0[]0[]0[grow]0[]"));
    }

    /**
     * Method adds the specified panels to the frame layout. This
     * method specifies the location and size of each panel in the
     * layout.
     */
    @Override
    protected void addSubPanels () {

        addPanelWithoutBorder(NAV_WIDTH, NAV_HEIGHT, "nav", "span, growx, wrap");
        addPanelWithBorder(MAIN_WIDTH, MAIN_HEIGHT, "main", "span 1 3, grow");
        addPanelWithBorder(LOGO_WIDTH, LOGO_HEIGHT, "logo", "align center, wrap");
        addPanelWithBorder(USER_WIDTH, USER_HEIGHT, "user", "align center, wrap");
        addPanelWithBorder(SEARCH_WIDTH, SEARCH_HEIGHT, "search", "align center, growy, wrap");
        addPanelWithoutBorder(FOOT_WIDTH, FOOT_HEIGHT, "foot", "span, growx");

    }

}
