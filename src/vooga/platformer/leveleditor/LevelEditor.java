package vooga.platformer.leveleditor;

import java.awt.BorderLayout;
import java.awt.Color;
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
import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.BevelBorder;
<<<<<<< HEAD
import vooga.platformer.gui.menu.GameButton;
import vooga.platformer.gui.menu.GameListener;
=======
import javax.swing.filechooser.FileNameExtensionFilter;
import vooga.platformer.gui.menu.*;
>>>>>>> f5741ba1b396d7e73c1993f7f217c5c9362159a8


public class LevelEditor extends JFrame {
    private static final Dimension DEFAULT_FRAME_SIZE = new Dimension(640, 480);
    private static final String IMAGE_PATH = "src/vooga/platformer/data/";
    private Map<String, List<String>> mySpriteTypes;
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
<<<<<<< HEAD

    public static void main(String[] args) {
=======
    private GameListener myGameListener;
    public static void main (String[] args) {
>>>>>>> f5741ba1b396d7e73c1993f7f217c5c9362159a8
        new LevelEditor();
    }

    public LevelEditor() {
        super("LevelEditor");
        isRunning = true;
        follow = false;
        background = null;
        frameBuild();
        fillMap();
        createListeners();
        createEditPane();
        createButtonPanel();
        pack();
        setVisible(true);
        editLoop();
    }
<<<<<<< HEAD

    private void createListeners() {
        myMouseListener = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (follow) {
                    follow = false;
                }
            }

        };
        myKeyListener = new KeyAdapter() {

        };

    }

    private void editLoop() {
        while (isRunning) {
            System.out.println("update");
=======

    private void editLoop () {
        while(isRunning){
>>>>>>> f5741ba1b396d7e73c1993f7f217c5c9362159a8
            update();
            repaint();
        }
    }

    private void update() {
<<<<<<< HEAD
        g2d.drawImage(getImage(IMAGE_PATH + "mario.background.jpg"), 0, 0,
                DEFAULT_FRAME_SIZE.width, DEFAULT_FRAME_SIZE.height, null);
        for (Sprite sprite : mySprites) {
            g2d.drawImage(getImage(sprite.getImagePath()), sprite.getX(),
                    sprite.getY(), sprite.getWidth(), sprite.getHeight(), null);
=======
        g2d.drawImage(background, 0, 0, DEFAULT_FRAME_SIZE.width, DEFAULT_FRAME_SIZE.height, null);
        for(Sprite sprite : mySprites) {
            g2d.drawImage(getImage(sprite.getImagePath()), sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight(), null);
>>>>>>> f5741ba1b396d7e73c1993f7f217c5c9362159a8
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
<<<<<<< HEAD

    private GameButton createButton(final String spritename) {
        GameButton gb = new GameButton(spritename);
        GameListener gl = new GameListener() {
            @Override
=======
    private void createListeners () {
        myMouseListener = new MouseAdapter() {
            @Override 
            public void mousePressed(MouseEvent e) {
                if(follow && e.getComponent() == myViewPane) {
                    follow = false;
                } else if(e.getButton() == 3) {
                    for(Sprite s : mySprites) {
                        if(e.getX() >= s.getX() && e.getX() <= s.getX() + s.getWidth()
                                && e.getY() >= s.getY() && e.getY() <= s.getY() + s.getHeight()){
                        } else {
                            JFileChooser chooser = new JFileChooser();
                            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                                "JPG & GIF Images", "jpg", "gif");
                            chooser.setFileFilter(filter);
                            int returnVal = chooser.showOpenDialog(myContainer);
                            if(returnVal == JFileChooser.APPROVE_OPTION)  {
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
>>>>>>> f5741ba1b396d7e73c1993f7f217c5c9362159a8
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
                    Sprite s = new Sprite(event.getActionCommand(), x, y, 20,
                            20, IMAGE_PATH + event.getActionCommand() + ".png");
                    mySprites.add(s);
                    follow = true;
                    currentSprite = s;
                }
            });
            pop.add(j);
        }
        pop.show(comp, x, y);
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
