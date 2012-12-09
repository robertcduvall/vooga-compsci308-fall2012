package games.yaqigame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.WindowConstants;
import arcade.IArcadeGame;
import arcade.gamemanager.GameSaver;
import util.ingamemenu.GameButton;
import util.ingamemenu.Menu;
import vooga.platformer.core.PlatformerController;
import vooga.platformer.core.inputinitializer.KeyControllerOnePlayerInputInitializer;


/**
 * @author Yaqi Zhang
 * 
 *         This game demos how to use a platformer engine to build a game as
 *         well as embed a in-game menu. The game is design through Platformer
 *         Frame. Each level are connected using Conditions. A level can have
 *         multiple Conditions to connect this level to other levels. In this
 *         case each level has two Conditions, one for winning condition and the
 *         other for losing condition. One can plug in more than one conditions
 *         to define multiple ways of winning or losing the game.
 * 
 *         The condition can also been used to create non-learner game flow. For
 *         example, mario standing on a pipe and user pressing down is a
 *         condition
 *         to bring the game stage to another level, like a backroom. Same
 *         approach can bring the mario back to previous level.
 * 
 *         This game also use level plug in to apply gravity on all game objects
 *         as well as adding background image. The design of level plug in
 *         allows flexibility in doing general things to the level. For example
 *         painting strings on the background.
 * 
 *         This game also applies a new CollisionEvent. A CollisionEvent(Type A,
 *         Type B) defines what will happen if type A of GameObject and Type B
 *         of GameObject collides. One can use existing CollisionEvent to apply
 *         on "any" GameObject pair (Some CollisionEvent are specific to certain
 *         kinds of GameObject, eg. a CollisionEvent that changing the velocity
 *         of a GameObject needs to work with a MovingObject). He can also
 *         creates his own CollisionEvents and apply them on existing
 *         GameObjects.
 * 
 *         This game also demos how to create a in game menu and customized
 *         GameButton.
 * 
 *         The model part of platformer engine also support different kinds of
 *         GameObject and they can apply different kinds of
 *         ControlStrategy(strategy relates to input control of a GameObject)
 *         and UpdateStrategy(Strategy updates the behavior of the GameObject).
 *         However because the limitation of time the LevelEditor doesn't
 *         support creating GameObject in level that is not existed in
 *         LevelEditor.
 *         So this part of design can not been reflected in demo games created
 *         using platformer level editor. However one can still creates new kind
 *         of GameObjects that are not instantiated by level files. In this
 *         case, a bullet is not created by the level editor.
 * 
 */
public class Main implements IArcadeGame {

    private static final String INSTRUCTION = "src/games/yaqigame/level/instruction.xml";
    private JFrame myFrame;
    private PlatformerController myPanel;

    /**
     * @param args
     */
    public static void main (String[] args) {
        Main game = new Main();
        game.startGame(INSTRUCTION);
    }

    public void startGame (String level1) {
        myFrame = new JFrame("Yaqi's Demo Game");
        myPanel = new PlatformerController(level1,
                new KeyControllerOnePlayerInputInitializer());
        myFrame.getContentPane().add(myPanel);
        myFrame.pack();
        myFrame.setVisible(true);
        myFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        setUpMenu();
    }

    private void setUpMenu () {
        myPanel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed (KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_M) {
                    myPanel.getLevel().pause();
                    final Menu menu = new Menu(myPanel);
                    GameButton gb1 = new GameButton("blackbutton");
                    gb1.paintString("Back", Color.WHITE);
                    MouseListener gl = new MouseAdapter() {
                        @Override
                        public void mouseClicked (MouseEvent arg0) {
                            myPanel.remove(menu);
                            myPanel.getLevel().unpause();
                            myFrame.repaint();
                        }
                    };
                    gb1.addMouseListener(gl);
                    GameButton gb3 = new GameButton("blackbutton");
                    gb3.paintString("Exit", Color.WHITE);
                    MouseListener gl3 = new MouseAdapter() {
                        @Override
                        public void mouseClicked (MouseEvent arg0) {
                            System.exit(0);
                        }
                    };
                    gb3.addMouseListener(gl3);
                    GameButton gb2 = new GameButton("blackbutton");
                    gb2.paintString("Replay this level", Color.WHITE);
                    MouseListener gl2 = new MouseAdapter() {
                        @Override
                        public void mouseClicked (MouseEvent arg0) {
                            myPanel.replayCurrentLevel();
                            myPanel.remove(menu);
                            myPanel.getLevel().unpause();
                            myFrame.repaint();
                        }
                    };
                    gb2.addMouseListener(gl2);
                    gb2.setSize(new Dimension(
                            GameButton.DEFAULT_BUTTON_WIDTH + 50,
                            GameButton.DEFAULT_BUTTON_HEIGHT));
                    menu.addButton(gb1);
                    menu.addButton(gb3);
                    menu.addButton(gb2);
                    menu.pack();
                    myPanel.repaint();
                }
            }
        });
    }

    @Override
    public void runGame (String userPreferences, GameSaver s) {
        startGame(INSTRUCTION);
    }

    @Override
    public List<Image> getScreenshots () {
        ArrayList<Image> list = new ArrayList<Image>();
        BufferedImage img1 = null;
        BufferedImage img2 = null;
        BufferedImage img3 = null;
        try {
            img1 = ImageIO.read(new File(
                    "src/games/yaqigame/level/screenshot1.jpg"));
            img2 = ImageIO.read(new File(
                    "src/games/yaqigame/level/screenshot2.jpg"));
            img3 = ImageIO.read(new File(
                    "src/games/yaqigame/level/screenshot3.jpg"));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        list.add(img1);
        list.add(img2);
        list.add(img3);
        return list;
    }

    @Override
    public Image getMainImage () {
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(
                    "src/games/yaqigame/level/level1.background.jpg"));
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return img;
    }

    @Override
    public String getDescription () {
        return "Kill all enemies to win the game. Press up to jump, left and right to move, space to shoot, M to bring up menu.";
    }

    @Override
    public String getName () {
        return "Yaqi's Demo Game";
    }
}
