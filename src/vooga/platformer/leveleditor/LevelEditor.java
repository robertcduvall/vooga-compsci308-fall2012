package vooga.platformer.leveleditor;

import org.w3c.dom.Document;
import util.ingamemenu.GameButton;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Frame containing all the elements needed to build and save a level
 *
 * @author Sam Rang
 */
@SuppressWarnings("serial")
public class LevelEditor extends JPanel {
    private static final int BUTTON_BAR_WIDTH = 50;
    private static final String IMAGE_PATH = "src/vooga/platformer/data/";
    private Map<String, List<String>> mySpriteTypes;
    private JFrame myContainer;
    private JPanel myViewPane;
    private boolean myGameIsRunning;
    private LevelBoard myBoard;
    private KeyListener myKeyListener;
    private SelectionMouseListener myMouseListener;
    private MouseListener myButtonListener;
    private int myViewOffset;

    /**
     * Frame containing all the elements needed to save, load, and create
     * levels. Allows users to drag and drop sprites onto a level as well
     * as set the background image.
     */
    public LevelEditor(JFrame parent) {
        myContainer = parent;
        myContainer.setTitle("Level Editor");
        fillMap();
        createListeners();
        createEditPane();
        createButtonPanel();
        createTopMenu();
        add(myViewPane);
        setVisible(true);
    }

    private void createListeners() {
        LevelBoard board = new LevelBoard(myContainer.getPreferredSize());
        myBoard = board;
        myMouseListener = myBoard.getMouseListener();
        myKeyListener = new KeyAdapter() {
            @Override 
            public void keyPressed (KeyEvent arg0) {
                switch (arg0.getKeyCode()) {
                    case KeyEvent.VK_LEFT:
                        if (myViewOffset == 0) {
                            bumpLeft();
                        }
                        else {
                            myViewOffset -= myContainer.getSize().width;
                        }
                        break;
                    case KeyEvent.VK_RIGHT: 
                        if (myViewOffset == myBoard.getWidth() - myContainer.getSize().width) {
                        }
                }
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
    }
    private void createEditPane() {
        JPanel panel = new JPanel()
        {
            @Override
            public void paint(Graphics g) {
                super.paintComponents(g);
                //                myBoard.paint(g);
            }
        };
        panel.setLayout(new BorderLayout());
        myViewPane = panel;
        panel.addMouseListener(myMouseListener);
        panel.addMouseMotionListener(myMouseListener);
        panel.addKeyListener(myKeyListener);
//        myViewPane.add(myBoard);
        myViewOffset = 0;
    }

    private GameButton createButton(String spritename) {
        GameButton gb = new GameButton(spritename);
        gb.addMouseListener(myButtonListener);
        return gb;
    }

    private void createButtonPanel() {
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
        panel.addMouseMotionListener(myBoard.getMouseListener());
        myViewPane.add(panel, BorderLayout.WEST);
    }

    public void update() {
        myBoard.update();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        myViewPane.paint(g);
    }

    private void createPopupMenu(final Component comp, final int x,
            final int y) {
        JPopupMenu pop = new JPopupMenu();
        for (String subsprite : mySpriteTypes.get(comp.getName())) {
            JMenuItem j = new JMenuItem(subsprite);
            j.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent event) {
                    Sprite s = new Sprite(event.getActionCommand(), x, y, 40, 40,
                            IMAGE_PATH + event.getActionCommand() + ".png");
                    myBoard.add(s);
                }
            });
            pop.add(j);
        }
        pop.show(comp, x, y);
    }

    private void createTopMenu() {
        JMenuBar bar = new JMenuBar();
        JMenu levelMenu = new JMenu("Level");
        levelMenu.add(new AbstractAction("Load") {
            @Override
            public void actionPerformed(ActionEvent e) {
                load();
            }
        });
        levelMenu.add(new AbstractAction("Clear") {
            @Override
            public void actionPerformed(ActionEvent e) {
                clear();
            }
        });
        levelMenu.add(new AbstractAction("New") {
            @Override
            public void actionPerformed(ActionEvent e) {
                newLevel();
            }
        });
        levelMenu.add(new AbstractAction("Save") {
            @Override
            public void actionPerformed(ActionEvent e) {
                save();
            }
        });

        JMenu spriteMenu = new JMenu("Sprite");
        spriteMenu.add(new AbstractAction("Load") {
            @Override
            public void actionPerformed(ActionEvent e) {
                //load();
            }
        });
        spriteMenu.add(new AbstractAction("New") {
            @Override
            public void actionPerformed(ActionEvent e) {
                //newLevel();
            }
        });
        bar.add(levelMenu);
        bar.add(spriteMenu);
//        myViewPane.add(bar, BorderLayout.NORTH);

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
        myViewPane.addMouseListener(myBoard.getMouseListener());
        myViewPane.addMouseMotionListener(myBoard.getMouseListener());
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
