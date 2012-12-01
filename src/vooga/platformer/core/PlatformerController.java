package vooga.platformer.core;

import games.platformerdemo.DemoLevelFactory;
import games.platformerdemo.Player;
import games.platformerdemo.ShootingStrategy;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Rectangle2D;
import javax.swing.JComponent;
import javax.swing.JPanel;
import util.ingamemenu.GameButton;
import util.ingamemenu.Menu;
import util.input.core.Controller;
import util.input.core.KeyboardController;
import vooga.platformer.level.Level;
import vooga.platformer.level.LevelFactory;
import vooga.platformer.util.enums.PlayState;


@SuppressWarnings("serial")
public class PlatformerController extends JPanel implements Runnable {
    private final int SLEEP_DELAY = 25;

    private Level myCurrentLevel;

    //TODO: Make this variable hold a LevelFactory
    private DemoLevelFactory myLevelFactory;
    private GameInitializer myGameInitializer;
    private KeyboardController myInputController;
    private Player myPlayer;
    
    private Thread animator;

    public PlatformerController(DemoLevelFactory lf, GameInitializer gi) {
        myLevelFactory = lf;
        myGameInitializer = gi;
        myInputController = null;
       
       //this.setFocusable(true);
        
        setupLevel(myGameInitializer.getFirstLevelName());
        myPlayer = myCurrentLevel.getPlayer();
        animator = new Thread(this);
        animator.start();        
    }

    /**
     * The main update cycle method.
     * 
     * @param elapsedTime
     */
    public void update(long elapsedTime) {
        myCurrentLevel.update(elapsedTime);
        PlayState currentState = myCurrentLevel.getLevelStatus();

        if (currentState == PlayState.NEXT_LEVEL) {
            String nextLevelName = myCurrentLevel.getNextLevelName();
            setupLevel(nextLevelName);
        }
    }
    
    public void setInputController(KeyboardController ic) {
        myInputController = ic;
        myCurrentLevel.setInputController(myInputController);
    }
    
    private void setupLevel(String lvlName) {
        myCurrentLevel = myLevelFactory.loadLevel(lvlName);
        Rectangle2D cameraBounds = myCurrentLevel.getCamera().getBounds();
        setPreferredSize(new Dimension((int)cameraBounds.getWidth(), (int)cameraBounds.getHeight()));
        if (myInputController != null) {
            myCurrentLevel.setInputController(myInputController);
        }
    }

    @Override
    public void paint(Graphics pen) {
        myCurrentLevel.paint(pen);
        pen.setColor(Color.BLACK);
        pen.drawString("M - Menu", getSize().width*3/5,getSize().height/4);
        pen.drawString("¡û ¡ú - Move left and right", getSize().width*3/5,getSize().height/4+15);
        pen.drawString("¡ü - Jump", getSize().width*3/5,getSize().height/4+30);
        pen.drawString("Space - Shoot", getSize().width*3/5,getSize().height/4+45);
        
        for(Component c: getComponents()){
            c.paint(pen);
        }
    }

    /**
     * The main game loop
     */
    @Override
    public void run() {
        long beforeTime, timeDiff, sleepTime;

        beforeTime = System.currentTimeMillis();

        while (true) {
            timeDiff = System.currentTimeMillis() - beforeTime;

            update(timeDiff);
            repaint();

            timeDiff = System.currentTimeMillis() - beforeTime;
            sleepTime = SLEEP_DELAY - timeDiff;

            if (sleepTime < 0) {
                sleepTime = 2;
            }

            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                System.out.println("interrupted!");
            }
            beforeTime = System.currentTimeMillis();
        }
    }
    
    /**
     * This is used to test sample implemented game before registered with input team.
     * should be //TODO: removed
     */
    public KeyListener setTemporaryInputListener(){
        KeyListener kl = new KeyAdapter() {
            @Override
            public void keyPressed (KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_LEFT){
                    myPlayer.getMovingStragety().goLeft();
                    
                }
                if(e.getKeyCode()==KeyEvent.VK_RIGHT){
                    myPlayer.getMovingStragety().goRight();
                }
                if(e.getKeyCode()==KeyEvent.VK_UP){
                    myPlayer.getMovingStragety().jump();
                }
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    ShootingStrategy ss = myPlayer.getShootingStrategy();
                    if(ss !=null){
                        ss.shoot();
                    }
                }
            }

            public void keyReleased (KeyEvent e) {
                myPlayer.getMovingStragety().stop();
            }
        };
        MouseMotionListener mml = new MouseAdapter() {
            @Override
            public void mouseClicked (MouseEvent e) {
                //not used so far
            }
        };
        return kl;
    }
    
    private PlatformerController myCanvas = this;
    
    public KeyListener setMenuKeyListener(){
        KeyListener kl = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_M) {
                    final Menu menu = new Menu(myCanvas);
                    GameButton gb1 = new GameButton("greenbutton", "Back");
                    MouseListener gl = new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent arg0) {
                            myCanvas.remove(menu);
                            myCanvas.repaint();
                        }
                    };
                    gb1.addMouseListener(gl);
                    GameButton gb2 = new GameButton("button", "Exit");
                    gb2.addMouseListener(new MouseAdapter(){
                        @Override
                        public void mouseClicked(MouseEvent arg0) {
                            System.exit(0);
                        }
                    });
                    gb2.setSize(new Dimension(130, 130));
                    menu.addButtons(gb1);
                    menu.addButtons(gb2);
                }
            }
        };
        return kl;
    }
}
