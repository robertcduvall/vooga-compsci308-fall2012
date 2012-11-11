package vooga.platformer.leveleditor;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.BevelBorder;
import vooga.platformer.gui.menu.*;


public class LevelEditor extends JFrame{
    private static final Dimension DEFAULT_FRAME_SIZE = new Dimension(640, 480);
    private Map<String,List<String>> mySprites;
    private JFrame myContainer;
    private JPanel myViewPane;
    private BufferedImage backbuffer;
    private Graphics2D g2d;
    public static void main (String[] args) {
        new LevelEditor();
    }
    public LevelEditor () {
        super("LevelEditor");
        myContainer = this;
        setPreferredSize(DEFAULT_FRAME_SIZE);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        catch (InstantiationException e) {
            e.printStackTrace();
        }
        catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        fillMap();
        createEditPane();
        createButtonPanel();
        setVisible(true);
        pack();
    }
    private GameButton createButton (final String spritename) {
        GameButton gb = new GameButton(spritename);
        GameListener gl = new GameListener() {
            @Override 
            public void actionPerformed(MouseEvent arg0) {
                createPopupMenu(arg0.getComponent(), arg0.getX(), arg0.getY());
            }
        };
        gb.setGameListener(gl);
        return gb;
    }
    private void createEditPane () {
        backbuffer = new BufferedImage(DEFAULT_FRAME_SIZE.width, DEFAULT_FRAME_SIZE.height, BufferedImage.TYPE_INT_RGB);
        g2d = backbuffer.createGraphics();
        JPanel panel = new JPanel() {
            @Override
            public void paint(Graphics g) {
                g.drawImage(backbuffer, 0, 0, this);
                super.paint(g);
            }
        };
        panel.setLayout(new BorderLayout());
        myViewPane = panel;
        myContainer.add(panel);
    }
    private void createButtonPanel () {
        JPanel panel = new JPanel();
        JPanel subpanel = new JPanel();
        subpanel.setLayout(new GridLayout(mySprites.size(), 1));
        subpanel.setPreferredSize(new Dimension(50, 150));
        for(String  sprite : mySprites.keySet()) {
            subpanel.add(createButton(sprite));
        }
        subpanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        panel.add(subpanel, BorderLayout.CENTER);
        myViewPane.add(panel, BorderLayout.WEST);
    }
    protected void createPopupMenu (Component comp, int x, int y) {
        JPopupMenu pop = new JPopupMenu();
        for(String subsprite : mySprites.get(comp.getName())){
            JMenuItem j = new JMenuItem(subsprite);
            j.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent event) {
                    System.out.println(event.getActionCommand());
                }
            });
            pop.add(j);
        }
        pop.show(comp, x, y);
    }
    private void fillMap() {
        mySprites = new HashMap<String, List<String>>();
        List<String> list = new ArrayList<String>();
        list.add("Yoshi"); list.add("Pink Yoshi"); list.add("Yoshi Egg"); list.add("Flying Yoshi");
        mySprites.put("Yoshi", list); list = new ArrayList<String>();
        list.add("Mario"); list.add("Fireflower Mario"); list.add("Mario on Yoshi"); list.add("Baby Mario");
        mySprites.put("Mario", list); list = new ArrayList<String>();
        list.add("Squished Goomba"); list.add("Giant Goomba"); list.add("Tiny Goomba");
        mySprites.put("Goomba", list); list = new ArrayList<String>();
        list.add("Bowser"); list.add("Baby Bowser");
        mySprites.put("Bowser", list); 

    }
}
