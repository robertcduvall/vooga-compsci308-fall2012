package vooga.platformer.leveleditor;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ResourceBundle;
import javax.swing.AbstractListModel;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.border.TitledBorder;


/*
 * If you are a Swing warrior feel free to improve this code... I suggest
 * using a LayoutManager. Good luck!
 */

// TODO Lots of the Sprite specific stuff
// such as passing through something which can load the sprites.
// TODO Add a load button.

/**
 * This class represents a Frame containing the components necessary
 * to launch the Sprite Editor. 
 * 
 * @author Paul Dannenberg
 * 
 */
public class EditorLauncher extends JFrame {

    private JLabel mySearchBoxLabel;
    private JList mySpriteOptions;
    private JPanel myLeft;
    private JPanel myRight;
    private JScrollPane myScrollPane;
    private JTextField mySearchBox;

    // Launches an empty sprite editor, ready to create a brand new sprite.
    private JButton mySpriteCreatorLauncher;

    // Launches the sprite editor, ready to edit the particular selected sprite.
    private JButton mySpriteEditLauncher;

    private ResourceBundle myResources;

    private static final long serialVersionUID = 1L;
    private static final String FONT_TYPE = "Times New Roman";
    private static final int FONT_SIZE = 12;
    private static final int FONT_STYLE = 1;
    private static final Font FONT = new Font(FONT_TYPE, FONT_STYLE, FONT_SIZE);
    private static final int NUMBER_OF_ROWS = 1;
    private static final int NUMBER_OF_COLUMNS = 2;
    private static final int FRAME_WIDTH = 400;
    private static final int FRAME_HEIGHT = 300;
    private static final int LIST_WIDTH = 200;
    private static final int LIST_HEIGHT = 200;

    private static final String SEARCH_BOX_LABEL = "searchBoxLabel";
    private static final String DEFAULT_SEARCH_ENTRY = "defaultSearchEntry";
    private static final String BUTTON_BOX_TITLE = "buttonBoxTitle";
    private static final String SPRITE_CREATOR_BUTTON_LABEL = "spriteCreatorButtonLabel";
    private static final String SPRITE_EDIT_BUTTON_LABEL = "spriteEditButtonLabel";

    /**
     * Creates the EditorLauncher, a window that is used to select which
     * sprites (if any) should be changed.
     * 
     * @param language The language that the buttons/labels should be in.
     */
    public EditorLauncher (String language) {
        myResources = ResourceBundle.getBundle("resources." + language);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        initializeComponents();
        createLayout();
    }

    /**
     * Creates all the different JComponents that will be displayed in the
     * EditorLauncher window.
     */
    private void initializeComponents () {
        mySpriteOptions = createSpriteList();
        myLeft = new JPanel();
        myRight = new JPanel();
        mySpriteCreatorLauncher = new JButton();
        mySpriteEditLauncher = new JButton();
        myScrollPane = new JScrollPane();
        mySearchBox =
                new JListSearcher(myResources.getString(DEFAULT_SEARCH_ENTRY), mySpriteOptions);
        mySearchBoxLabel = new JLabel();

    }

    /**
     * Creates a list of sprites which the user can select from. The selected
     * sprite can then be edited.
     * 
     * @return The JList containing the names of any loaded sprites.
     */
    private JList createSpriteList () {
        JList spriteList = new JList();
        spriteList.setModel(new AbstractListModel() {

            private static final long serialVersionUID = 1L;
            String[] strings = { "Item 1", "Item 2" };

            @Override
            public int getSize () {
                return strings.length;
            }

            @Override
            public Object getElementAt (int i) {
                return strings[i];
            }
        });
        return spriteList;
    }

    /**
     * Sets the style of the border of a particular JPanel to
     * "TitledBorderStyle".
     * 
     * @param toSetBorderFor The panel for which a border should be set.
     * @param title The title of the border.
     */
    private void setPanelBorder (JPanel toSetBorderFor, String title) {
        toSetBorderFor
                .setBorder(BorderFactory.createTitledBorder(null, myResources.getString(title),
                                                            TitledBorder.DEFAULT_JUSTIFICATION,
                                                            TitledBorder.DEFAULT_POSITION, FONT));
    }

    /**
     * Sets up the label for the button in a particular font.
     * 
     * @param button The button for which the label should be set.
     * @param font The font of the label.
     * @param label The String to be displayed on the button.
     */
    private void setButtonLabel (JButton button, Font font, String label) {
        button.setFont(font);
        button.setText(label);
    }

    /**
     * Sets this objects Layout to a GridLayout, dividing it into two separate
     * panels, a left and a right panel.
     */
    private void setFrameLayout () {
        GridLayout myLayout = new GridLayout(NUMBER_OF_ROWS, NUMBER_OF_COLUMNS);
        setLayout(myLayout);
        setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
        setResizable(false);
    }

    /**
     * Adds two panels to the JFrame. One left panel and one right panel.
     */
    private void addPanelsToFrame () {
        add(myLeft);
        add(myRight);
    }

    /**
     * Adds the two buttons, capable of launching the sprite editor to a
     * particular panel.
     * 
     * @param panel
     */
    private void addButtonsToPanel (JPanel panel) {
        panel.add(mySpriteCreatorLauncher);
        panel.add(mySpriteEditLauncher);
    }

    /**
     * Sets up a JScrollPane that acts as a view into this object's JList.
     * This view should support scrolling in case the number of sprites
     * becomes particularly large.
     */
    private void setupScrollingList () {
        myScrollPane.setPreferredSize(new Dimension(LIST_WIDTH, LIST_HEIGHT));
        myScrollPane.setViewportView(mySpriteOptions);
        myLeft.add(myScrollPane);
    }

    /**
     * Creates a text box that allows the user to enter input. This input will
     * cause mySearchBox to search for the possible sprites to which the user's
     * input could refer to.
     */
    private void setupSearchBox () {

        myLeft.add(mySearchBoxLabel, BorderLayout.SOUTH);
        myLeft.add(mySearchBox, BorderLayout.SOUTH);
        createTextLabel();

    }

    /**
     * Creates the label for the search box.
     */
    private void createTextLabel () {
        mySearchBoxLabel.setFont(FONT);
        mySearchBoxLabel.setLabelFor(mySearchBox);
        mySearchBoxLabel.setText(myResources.getString(SEARCH_BOX_LABEL));
    }

    /**
     * Designs the overall layout of the JFrame.
     */
    private void createLayout () {

        setFrameLayout();
        setPanelBorder(myRight, BUTTON_BOX_TITLE);
        setButtonLabel(mySpriteCreatorLauncher, FONT,
                       myResources.getString(SPRITE_CREATOR_BUTTON_LABEL));
        setButtonLabel(mySpriteEditLauncher, FONT, myResources.getString(SPRITE_EDIT_BUTTON_LABEL));
        addPanelsToFrame();
        addButtonsToPanel(myRight);
        setupScrollingList();
        setupSearchBox();

        pack();
    }

}
