package vooga.platformer.core;

import games.platformerdemo.DemoLevelFactory;
import games.platformerdemo.Player;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Rectangle2D;
import javax.swing.JComponent;
import javax.swing.JPanel;
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
}
