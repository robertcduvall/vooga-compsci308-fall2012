package vooga.platformer.leveleditor;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import util.ingamemenu.GameButton;
import vooga.platformer.gameobject.GameObject;
import vooga.platformer.gameobject.StaticObject;

/**
 * Frame containing all the elements needed to build and save a level
 *
 * @author Sam Rang
 */
@SuppressWarnings("serial")
public class LevelEditor extends JPanel {
    private static final int OBJECT_BUTTON_SIZE = 40;
    private static final int BUTTON_BAR_WIDTH = 50;
    private static final String DATA_PATH = "/src/vooga/platformer/data/";
    private List<String> myObjectTypes;
    private LevelBoard myBoard;
    private KeyListener myKeyListener;
    private JPanel myButtonPanel;
    private JMenuBar myMenuBar;

    /**
     * Frame containing all the elements needed to save, load, and create
     * levels. Allows users to drag and drop sprites onto a level as well
     * as set the background image.
     * 
     * @param parent JPanel containing LevelEditor
     */
    public LevelEditor(Dimension dim) {
        setLayout(new BorderLayout());
        setSize(new Dimension(dim));
        myBoard = new LevelBoard(getSize());
        myBoard.setLayout(new BorderLayout());
        fillMap();
        createListeners();
        addKeyListener(myKeyListener);
        myButtonPanel = createButtonPanel();
        myMenuBar = new EditorMenuBar(this);
        add(myMenuBar, BorderLayout.NORTH);
        add(myBoard, BorderLayout.CENTER);
        myBoard.add(myButtonPanel, BorderLayout.SOUTH);

    }

    public KeyListener getKeyListener() {
        return myKeyListener;
    }
    
    /**
     * 
     * @param plugin
     */
    public void addLevelPlugin(String plugin) {
        if("Gravity".equals(plugin)) { 
            final LevelBoard current = myBoard;
            final JPopupMenu jpop = new JPopupMenu();
            JLabel gravitylabel = new JLabel("Enter Gravity Value:");
            final JTextField gravityfield = new JTextField();
            JButton accept = new JButton("OK");
            accept.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed (ActionEvent arg0) {
                    String val = gravityfield.getText();
                    try {
                         int temp = Integer.parseInt(val);
                         current.setGrav(temp);
                    }
                    catch (NumberFormatException e) {
                        JLabel errormsg = new JLabel("Entry invalid.\n Enter an integer.");
                        jpop.add(errormsg);
                        jpop.pack();
                        jpop.repaint();
                    }
                }
            });
            jpop.add(gravitylabel);
            jpop.add(gravityfield);
            jpop.add(accept);
            jpop.show(this, getWidth()/2-50, getHeight()/2-40);
        }
        
    }
    private void createListeners() {
        myKeyListener = myBoard.getKeyListener();
    }

    private GameButton createButton(String objectname) {
        GameButton gb = new GameButton(objectname);
        gb.addMouseListener(myBoard.getButtonListener());
        gb.setButtonSize(OBJECT_BUTTON_SIZE, OBJECT_BUTTON_SIZE);
        return gb;
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel();
        JPanel subpanel = new JPanel();
        subpanel.setLayout(new GridLayout(1, myObjectTypes.size()));
        subpanel.setSize(new Dimension(BUTTON_BAR_WIDTH, 
                BUTTON_BAR_WIDTH * myObjectTypes.size()));
        for (String type : myObjectTypes) {
            subpanel.add(createButton(type));
        }
        subpanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        panel.add(subpanel);
        panel.setOpaque(false);
        return panel;
    }
//    private void createSpriteTypePopupMenu(final Component comp, final int x,
//            final int y) {
//        JPopupMenu pop = new JPopupMenu();
//        for (String subsprite : myObjectTypes.get(comp.getName())) {
//            JMenuItem j = new JMenuItem(subsprite);
//            j.addActionListener(new ActionListener() {
//                @Override
//                public void actionPerformed(ActionEvent event) {
//                    GameObject obj;
//                    File f = new File(System.getProperty("user.dir")+DATA_FOLDER+"Default.png");
//                    if("StaticObject".equals(event.getActionCommand())) {
//                        try {
//                            obj = new StaticObject((double)comp.getX(), (double)comp.getY(),
//                                    (double)OBJECT_BUTTON_SIZE, (double)OBJECT_BUTTON_SIZE,
//                                    myBoard.nextID(), f);
//                            myBoard.addObject(obj);
//                        }
//                        catch (IOException e) {
//                            // TODO Auto-generated catch block
//                            e.printStackTrace();
//                        }
//                    }
//                }
//            });
//            pop.add(j);
//        }
//        pop.show(comp, x, y);
//    }

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
        myBoard = new LevelBoard(getSize());
    }

    protected void clear() {
        myBoard.clear();
    }

    protected void load() {
        JFileChooser chooser = new JFileChooser(System.getProperty("user.dir")+DATA_PATH);
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
                io.printStackTrace();
            }
        }
    }

    private void fillMap() {
        myObjectTypes = new ArrayList<String>();
        myObjectTypes.add("StaticObject");
        myObjectTypes.add("Enemy");
        myObjectTypes.add("Player");
        myObjectTypes.add("Plugin");
    }
}
