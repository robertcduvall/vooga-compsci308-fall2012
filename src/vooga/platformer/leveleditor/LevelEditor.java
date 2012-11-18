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
public class LevelEditor extends JFrame {
    private static final Dimension BUTTON_BAR_SIZE = new Dimension(50, 150);
    private static final Dimension DEFAULT_FRAME_SIZE = new Dimension(640, 480);
    private static final String IMAGE_PATH = "src/vooga/platformer/data/";
    private Map<String, List<String>> mySpriteTypes;
    private JFrame myContainer;
    private JPanel myViewPane;
    private boolean myGameIsRunning;
    private LevelBoard myBoard;
    private KeyListener myKeyListener;
    private SelectionMouseListener myMouseListener;
    private MouseListener myButtonListener;

    public static void main(String[] args) {
        new LevelEditor();
    }

    /**
     * Frame containing all the elements needed to save, load, and create
     * levels. Allows users to drag and drop sprites onto a level as well
     * as set the background image.
     */
    public LevelEditor() {
        super("LevelEditor");
        myGameIsRunning = true;
        frameBuild();
        fillMap();
        createListeners();
        createEditPane();
        createButtonPanel();
        createTopMenu();
        pack();
        setVisible(true);
        editLoop();
    }

    private void editLoop() {
        while (myGameIsRunning) {
            repaint();
        }
    }

    private void frameBuild() {
        myContainer = this;
        setPreferredSize(DEFAULT_FRAME_SIZE);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }

    private void createListeners() {
        LevelBoard board = new LevelBoard(DEFAULT_FRAME_SIZE);
        myBoard = board;
        myContainer.add(board);
        myMouseListener = myBoard.getMouseListener();
        myKeyListener = new KeyAdapter() {

        };
        myButtonListener = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent arg0) {
                createPopupMenu(arg0.getComponent(), arg0.getX(), arg0.getY());
            }
        };
    }

    private void createEditPane() {
        JPanel panel = new JPanel() {
            @Override
            public void paint(Graphics g) {
                g.clearRect(0, 0, DEFAULT_FRAME_SIZE.width, DEFAULT_FRAME_SIZE.height);
                myBoard.paint(g);
                super.paintComponents(g);
            }
        };
        panel.setLayout(new BorderLayout());
        myViewPane = panel;
        panel.addMouseListener(myMouseListener);
        panel.addMouseMotionListener(myMouseListener);
        panel.addKeyListener(myKeyListener);
        myContainer.add(panel);
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
        subpanel.setPreferredSize(BUTTON_BAR_SIZE);
        for (String sprite : mySpriteTypes.keySet()) {
            subpanel.add(createButton(sprite));
        }
        subpanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        panel.add(subpanel, BorderLayout.CENTER);
        panel.setOpaque(false);
        panel.addMouseMotionListener(myBoard.getMouseListener());
        myViewPane.add(panel, BorderLayout.WEST);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        myBoard.update();
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
                load();
            }
        });
        spriteMenu.add(new AbstractAction("New") {
            @Override
            public void actionPerformed(ActionEvent e) {
                newLevel();
            }
        });
        bar.add(levelMenu);
        bar.add(spriteMenu);
        myViewPane.add(bar, BorderLayout.NORTH);
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
        myBoard = new LevelBoard(DEFAULT_FRAME_SIZE);
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
    }
}
