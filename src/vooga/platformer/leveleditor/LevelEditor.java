package vooga.platformer.leveleditor;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.BevelBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import vooga.platformer.gui.menu.GameButton;
import vooga.platformer.gui.menu.GameListener;

/**
 * 
 * 
 * @author Sam Rang
 *
 */
public class LevelEditor extends JFrame{
    private static final int RIGHT_CLICK = 3;
    private static final Dimension DEFAULT_FRAME_SIZE = new Dimension(640, 480);
    private static final String IMAGE_PATH = "src/vooga/platformer/data/";
    private static final String DEFAULT_BACKGROUND = IMAGE_PATH + "mario.background.jpg";
    private Map<String,List<String>> mySpriteTypes;
    private JFrame myContainer;
    private JPanel myViewPane;
    private BufferedImage backbuffer;
    private Image background;
    private Graphics2D g2d;
    private boolean isRunning;
    private MouseListener myMouseListener;
    private List<Sprite> mySprites;
    private boolean follow;
    private Sprite currentSprite;
    private KeyListener myKeyListener;
    private GameListener myGameListener;
    public static void main (String[] args) {
        new LevelEditor();
    }

    public LevelEditor() {
        super("LevelEditor");
        isRunning = true;
        follow = false;
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

    private void editLoop () {
        while (isRunning) {
            update();
            repaint();
        }
    }

    private void update() {
        g2d.drawImage(background, 0, 0, DEFAULT_FRAME_SIZE.width, DEFAULT_FRAME_SIZE.height, null);
        for (Sprite sprite : mySprites) {
            g2d.drawImage(getImage(sprite.getImagePath()), 
                    sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight(), null);
        }
        if (follow) {
            currentSprite.setX(MouseInfo.getPointerInfo().getLocation().x);
            currentSprite.setY(MouseInfo.getPointerInfo().getLocation().y);
        }
    }

    private Image getImage(String filename) {
        Image ret = null;
        try {
            ret = ImageIO.read(new File(filename));
        } catch (IOException e) {
            System.out.println("file was not found");
            e.printStackTrace();
        }
        return ret;

    }

    private void frameBuild() {
        myContainer = this;
        setPreferredSize(DEFAULT_FRAME_SIZE);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        try {
            UIManager.setLookAndFeel(UIManager
                    .getCrossPlatformLookAndFeelClassName());
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

    private void createListeners () {
        myMouseListener = new MouseAdapter() {
            @Override 
            public void mousePressed(MouseEvent e) {
                if (follow && e.getComponent() == myViewPane) {
                    follow = false;
                }
                else if (e.getButton() == RIGHT_CLICK) {
                    for (Sprite s : mySprites) {
                        if (e.getX() >= s.getX() && e.getX() <= s.getX() + s.getWidth() &&
                                e.getY() >= s.getY() && e.getY() <= s.getY() + s.getHeight()) {
                            //Something with sprites (Popup maybe?)
                        }
                        else {
                            JFileChooser chooser = new JFileChooser();
                            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                                    "JPG & GIF Images", "jpg", "gif");
                            chooser.setFileFilter(filter);
                            int returnVal = chooser.showOpenDialog(myContainer);
                            if (returnVal == JFileChooser.APPROVE_OPTION)  {
                                try {
                                    background = ImageIO.read(chooser.getSelectedFile());
                                }
                                catch (IOException io) {
                                    System.out.println("File not found. Try again");
                                }
                            }
                        }
                    }
                }
            }

        };
        myKeyListener = new KeyAdapter() {

        };
        myGameListener = new GameListener() {
            @Override 
            public void actionPerformed(MouseEvent arg0) {
                createPopupMenu(arg0.getComponent(), arg0.getX(), arg0.getY());
            }
        };
    }
    private GameButton createButton (String spritename) {
        GameButton gb = new GameButton(spritename);
        gb.setGameListener(myGameListener);
        return gb;
    }
    private void createEditPane() {
        backbuffer = new BufferedImage(DEFAULT_FRAME_SIZE.width,
                DEFAULT_FRAME_SIZE.height, BufferedImage.TYPE_INT_RGB);
        g2d = backbuffer.createGraphics();
        JPanel panel = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                g.drawImage(backbuffer, 0, 0, DEFAULT_FRAME_SIZE.width,
                        DEFAULT_FRAME_SIZE.height, myContainer);
            }
        };
        panel.setLayout(new BorderLayout());
        myViewPane = panel;
        panel.addMouseListener(myMouseListener);
        panel.addKeyListener(myKeyListener);
        myContainer.add(panel);
    }

    private void createButtonPanel() {
        JPanel panel = new JPanel();
        JPanel subpanel = new JPanel();
        subpanel.setLayout(new GridLayout(mySpriteTypes.size(), 1));
        subpanel.setPreferredSize(new Dimension(50, 150));
        for (String sprite : mySpriteTypes.keySet()) {
            subpanel.add(createButton(sprite));
        }
        subpanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        panel.add(subpanel, BorderLayout.CENTER);
        panel.setOpaque(false);
        myViewPane.add(panel, BorderLayout.WEST);
    }

    protected void createPopupMenu(final Component comp, final int x,
            final int y) {
        JPopupMenu pop = new JPopupMenu();
        for (String subsprite : mySpriteTypes.get(comp.getName())) {
            JMenuItem j = new JMenuItem(subsprite);
            j.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent event) {
                    Sprite s = new Sprite(event.getActionCommand(), x, y, 40, 40, 
                            IMAGE_PATH + event.getActionCommand() + ".png");
                    mySprites.add(s);
                    follow = true;
                    currentSprite = s;
                }
            });
            pop.add(j);
        }
        pop.show(comp, x, y);
    }
    private void createTopMenu () {
        JMenuBar bar = new JMenuBar();
        JMenu fileMenu = new JMenu("Level");
        fileMenu.add(new AbstractAction("Load") {
            @Override
            public void actionPerformed (ActionEvent e) {
                load();
            }
        });
        fileMenu.add(new AbstractAction("Clear") {
            @Override
            public void actionPerformed (ActionEvent e) {
                clear();
            }
        });
        fileMenu.add(new AbstractAction("New"){
            @Override
            public void actionPerformed (ActionEvent e) {
                newLevel();
            }
        });
        fileMenu.addMouseListener(myMouseListener);
        JMenu spriteMenu = new JMenu("Sprite");
        spriteMenu.add(new AbstractAction("Load") {
            @Override
            public void actionPerformed (ActionEvent e) {
                load();
            }
        });
        spriteMenu.add(new AbstractAction("New"){
            @Override
            public void actionPerformed (ActionEvent e) {
                newLevel();
            }
        });
        spriteMenu.addMouseListener(myMouseListener);
        bar.add(fileMenu);
        bar.add(spriteMenu);
        myViewPane.add(bar, BorderLayout.NORTH);
    }
    protected void newLevel () {
        System.out.println("New Level");
    }
    protected void clear () {
        System.out.println("Clear pallet");
    }
    protected void load () {
        System.out.println("Load existing level");
    }
    private void fillMap() {
        mySpriteTypes = new HashMap<String, List<String>>();
        List<String> list = new ArrayList<String>();
        list.add("Yoshi");
        list.add("Pink Yoshi"); // list.add("Yoshi Egg");
                                // list.add("Flying Yoshi");
        mySpriteTypes.put("Yoshi", list);
        list = new ArrayList<String>();
        list.add("Mario"); // list.add("Fireflower Mario");
                           // list.add("Mario on Yoshi");
                           // list.add("Baby Mario");
        mySpriteTypes.put("Mario", list);
        list = new ArrayList<String>();
        // list.add("Squished Goomba"); list.add("Giant Goomba");
        // list.add("Tiny Goomba");
        // mySpriteTypes.put("Goomba", list); list = new ArrayList<String>();
        list.add("Bowser");
        list.add("Baby Bowser");
        mySpriteTypes.put("Bowser", list);
        mySprites = new ArrayList<Sprite>();
    }
}
