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
import util.reflection.Reflection;
import vooga.platformer.gameobject.GameObject;
import vooga.platformer.gameobject.StaticObject;
import vooga.platformer.level.condition.Condition;
import vooga.platformer.level.condition.DefeatAllEnemiesCondition;
import vooga.platformer.level.levelplugin.SimpleBackgroundPainter;

/**
 * Frame containing all the elements needed to build and save a level
 *
 * @author Sam Rang
 */
@SuppressWarnings("serial")
public class LevelEditor extends JPanel {
    private static final int OBJECT_BUTTON_SIZE = 40;
    private static final int BUTTON_BAR_WIDTH = 50;
    private static final String DATA_PATH = System.getProperty("user.dir")+"/src/vooga/platformer/data/";
    private List<String> myObjectTypes;
    private LevelBoard myBoard;
    private KeyListener myKeyListener;
    private JPanel myButtonPanel;
    private JMenuBar myMenuBar;
    private JTextField myTextField;

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
        if("SimpleBackgroundPainter".equals(plugin)) {
            JFileChooser chooser = new
                    JFileChooser(DATA_PATH);
            FileNameExtensionFilter filter =
                    new FileNameExtensionFilter("JPG & GIF Images", "jpg", "gif", "png");
            chooser.setFileFilter(filter);
            int returnVal = chooser.showOpenDialog(chooser);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                SimpleBackgroundPainter myBackground = new SimpleBackgroundPainter(chooser.getSelectedFile());
                myBoard.addPlugin(myBackground);
            }
        }
//        else if ("") {
//            
//        }
    }
    public void addLevelCondtions (String con) {
        JFileChooser chooser = new JFileChooser(DATA_PATH);
        FileNameExtensionFilter filter =
                new FileNameExtensionFilter("Level XML Files", "xml");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(chooser);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            if("DefeatAllEnemiesCondition".equals(con)) {
                myBoard.addCondition(new DefeatAllEnemiesCondition(chooser.getSelectedFile().getPath()));
            }
            else if("DestroySpecificObjectCondition".equals(con)) {
                System.out.println("haha funny");
            }
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

    protected void save() {
        myBoard.save();
    }

    protected void newLevel() {
        myBoard.clear();
        final JPopupMenu pop = valueEntry("Level Name", new ActionListener() {

            @Override
            public void actionPerformed (ActionEvent e) {
                myBoard.setName(myTextField.getText());
            }
            
        });
        pop.show(this, getWidth()/2-50, getHeight()/2-40);
    }

    protected void clear() {
        myBoard.clear();
    }

    protected void load() {
        JFileChooser chooser = new JFileChooser(DATA_PATH);
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "XML Level files", "xml");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(chooser);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            myBoard.load(chooser.getSelectedFile().getPath());
        }
    }
    
    private JPopupMenu valueEntry (String askFor, ActionListener onOK) {
        JPopupMenu jpop = new JPopupMenu();
        JLabel askUserFor = new JLabel(askFor + " :");
        myTextField = new JTextField();
        JButton accept = new JButton("OK");
        accept.addActionListener(onOK);
        jpop.add(askUserFor);
        jpop.add(myTextField);
        jpop.add(accept);
        return jpop;
    }
    
    private void fillMap() {
        myObjectTypes = new ArrayList<String>();
        myObjectTypes.add("StaticObject");
        myObjectTypes.add("Enemy");
        myObjectTypes.add("Player");
    }
}
