package vooga.platformer.leveleditor;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.border.BevelBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import util.ingamemenu.GameButton;

/**
 * Frame containing all the elements needed to build and save a level
 *
 * @author Sam Rang
 */
@SuppressWarnings("serial")
public class LevelEditor extends JPanel {
    private static final int SPRITE_BUTTON_SIZE = 40;
    private static final int BUTTON_BAR_WIDTH = 50;
    private static final String IMAGE_PATH = "src/vooga/platformer/data/";
    private Map<String, List<String>> mySpriteTypes;
    private JPanel myContainer;
    private LevelBoard myBoard;
    private KeyListener myKeyListener;
    private MouseListener myMouseListener;
    private MouseMotionListener myMouseMotionListener;
    private MouseListener myButtonListener;
    private int myViewOffset;

    /**
     * Frame containing all the elements needed to save, load, and create
     * levels. Allows users to drag and drop sprites onto a level as well
     * as set the background image.
     * 
     * @param parent JPanel containing LevelEditor
     */
    public LevelEditor(JPanel parent) {
        myContainer = parent;
        myBoard = new LevelBoard(new Dimension(myContainer.getSize()));
        fillMap();
        createListeners();
        add(createButtonPanel(), BorderLayout.WEST);
        add(myBoard, BorderLayout.CENTER);
        add(new EditorMenuBar(this), BorderLayout.NORTH);
    }

    private void createListeners() {
        myMouseListener = myBoard.getMouseListener();
        myKeyListener = new KeyAdapter() {
            @Override 
            public void keyPressed (KeyEvent arg0) {
                System.out.println(arg0.getKeyCode());
//                switch (arg0.getKeyCode()) {
//                    case KeyEvent.VK_LEFT:
//                        if (myViewOffset == 0) {
//                            bumpLeft();
//                        }
//                        else {
//                            myViewOffset -= myContainer.getSize().width;
//                        }
//                        break;
//                    case KeyEvent.VK_RIGHT: 
//                        if (myViewOffset == myBoard.getWidth() - myContainer.getSize().width) {
//                        }
//                }
            }

            private void bumpLeft () {
                System.out.println("Already at beginning!");
            }
            private void bumpRight () {
                System.out.println("Already at end!");
            }
        };
        myButtonListener = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent arg0) {
                createPopupMenu(arg0.getComponent(), arg0.getX(), arg0.getY());
            }
        };
//        addMouseListener(myMouseListener);
//        addKeyListener(myKeyListener);
//        myContainer.addMouseListener(myMouseListener);
//        myContainer.addMouseMotionListener(myMouseListener);
//        myContainer.addKeyListener(myKeyListener);
    }

    private GameButton createButton(String spritename) {
        GameButton gb = new GameButton(spritename);
        gb.addMouseListener(myButtonListener);
        gb.setButtonSize(SPRITE_BUTTON_SIZE, SPRITE_BUTTON_SIZE);
        return gb;
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel();
        JPanel subpanel = new JPanel();
        subpanel.setLayout(new GridLayout(mySpriteTypes.size(), 1));
        subpanel.setPreferredSize(new Dimension(BUTTON_BAR_WIDTH, 
                BUTTON_BAR_WIDTH * mySpriteTypes.size()));
        for (String sprite : mySpriteTypes.keySet()) {
            subpanel.add(createButton(sprite));
        }
        subpanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        panel.add(subpanel, BorderLayout.CENTER);
        panel.setOpaque(false);
        panel.addMouseMotionListener(myBoard.getMouseMotionListener());
        return panel;
    }
    private void createPopupMenu(final Component comp, final int x,
            final int y) {
        JPopupMenu pop = new JPopupMenu();
        for (String subsprite : mySpriteTypes.get(comp.getName())) {
            JMenuItem j = new JMenuItem(subsprite);
            j.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent event) {
                    // TODO replace null value for spriteID with a unique id value
                    Sprite s = new Sprite(event.getActionCommand(), x, y, SPRITE_BUTTON_SIZE, SPRITE_BUTTON_SIZE,
                            null, IMAGE_PATH + event.getActionCommand() + ".png");
                    myBoard.add(s);
                }
            });
            pop.add(j);
        }
        pop.show(comp, x, y);
    }

    protected void save() {
        myBoard.save();
    }

    protected void newLevel() {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder;
        try {
            docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();
        } 
        catch (ParserConfigurationException e) {
            System.out.println("Directory does not exist, try again");
            newLevel();
        }
        // root elements
        myBoard = new LevelBoard(myContainer.getSize());
        myMouseListener = myBoard.getMouseListener();
        myMouseMotionListener = myBoard.getMouseMotionListener();
    }

    protected void clear() {
        myBoard.clear();
    }

    protected void load() {
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "XML Level files", "xml");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(chooser);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            try {
                URL myURL = new URL(chooser.getSelectedFile().getPath());
                myBoard.load(myURL);
            } 
            catch (IOException io) {
                System.out.println("File not found. Try again");
            }
        }
    }

    private void fillMap() {
        mySpriteTypes = new HashMap<String, List<String>>();
        List<String> list = new ArrayList<String>();
        list.add("Yoshi");
        list.add("Pink Yoshi");
        mySpriteTypes.put("Yoshi", list);
        list = new ArrayList<String>();
        list.add("Mario");
        mySpriteTypes.put("Mario", list);
        list = new ArrayList<String>();
        list.add("Bowser");
        list.add("Baby Bowser");
        mySpriteTypes.put("Bowser", list);
        list = new ArrayList<String>();
        list.add("Brick");
        list.add("Question Block");
        mySpriteTypes.put("Block", list);
    }
}
