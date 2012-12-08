package games.yaqigame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.WindowConstants;
import arcade.IArcadeGame;
import arcade.gamemanager.GameSaver;
import util.ingamemenu.GameButton;
import util.ingamemenu.Menu;
import vooga.platformer.core.PlatformerController;
import vooga.platformer.core.inputinitializer.KeyControllerOnePlayerInputInitializer;


public class Main implements IArcadeGame {

    private static final String LEVEL1 = "src/games/yaqigame/level/level1.xml";
    private JFrame myFrame;
    private PlatformerController myPanel;

    /**
     * @param args
     */
    public static void main (String[] args) {
        Main game = new Main();
        game.startGame(LEVEL1);
    }

    public void startGame (String level1) {
        myFrame = new JFrame("Yaqi's Demo Game");
        myPanel = new PlatformerController(level1,
                new KeyControllerOnePlayerInputInitializer());
        myFrame.getContentPane().add(myPanel);
        myFrame.pack();
        myFrame.setVisible(true);
        myFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        
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
        startGame(LEVEL1);
    }

    @Override
    public List<Image> getScreenshots () {
        return null;
    }

    @Override
    public Image getMainImage () {
        return null;
    }

    @Override
    public String getDescription () {
        return "Collect all gold coins to win the game, use left and right to move, up to jump, space to shoot, 'M' to bring up the menu";
    }

    @Override
    public String getName () {
        return "Yaqi's Demo Game";
    }
}
