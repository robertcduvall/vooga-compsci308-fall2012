package games.squareattack.engine;

import games.squareattack.controllers.AndroidControllerStrategy;
import games.squareattack.controllers.ControllerStrategy;
import games.squareattack.controllers.KeyboardControllerStrategy;
import games.squareattack.gui.GameFrame;
import games.squareattack.gui.MenuBar;
import games.squareattack.gui.PowerBar;
import games.squareattack.gui.Score;
import games.squareattack.objects.WallBuilder;
import games.squareattack.rules.BoundryRule;
import games.squareattack.rules.EnemyCollisionRule;
import games.squareattack.rules.GameOverRule;
import games.squareattack.rules.GameRule;
import games.squareattack.rules.WallBallCollisionRule;
import games.squareattack.rules.WallBallSpawnRule;
import games.squareattack.rules.WallCollisionRule;
import games.squareattack.sprites.Sprite;
import games.squareattack.sprites.Square;
import games.squareattack.sprites.WallBall;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JPanel;
import util.input.android.events.AndroidVibration;
import util.mathvector.MathVector2D;


/**
 * 
 * @author Ben Schwab
 * 
 */
@SuppressWarnings("serial")
public class GameManager extends JPanel implements Runnable {

    public static final String ANDROID = "Android";
    public static final String KEYBOARD = "Keyboard";
    public static final String KEYBOARD_WASD = "Keyboard_WASD";
    public static final String KEYBOARD_ARROW = "Keyboard_Arrows";
    public static final String NONE = "None";

    public enum State {
        Starting, Running, Over, Paused
    }
    
    public static final int GAME_FPS = 35;
    private final int myDelay = 1000 / GAME_FPS;
    private Thread gameThread;
    private Square attackerOne;
    private Square attackerTwo;
    private Square attackerThree;
    private Square defenderOne;
    private State myState;
    private WallBuilder myWallBuilder;
    private GameFrame myParent;
    private List<GameRule> myGameRules;
    private List<Square> myAttackers = new ArrayList<Square>();
    private MenuBar myTopMenu;
    private PowerBar myPowerBar;
    private List<WallBall> myWallBalls = new ArrayList<WallBall>();
    private Rectangle myGameArea;
    private Score myScore;
    private List<Sprite> allSquares = new ArrayList<Sprite>();
    private Map<Square, ControllerStrategy> controllerSettings =
            new HashMap<Square, ControllerStrategy>();
    private TextManager myTextManager = new TextManager(this, false, myScore);
    private String[] controllers;
    private OnScreenText myPauseText;

    public GameManager (GameFrame parent, String[] controllers) {
        this.setFocusable(true);
        this.requestFocus();
        this.controllers = controllers;
        myState = State.Starting;
        myParent = parent;
        myGameArea = new Rectangle(0, 50, GameFrame.GameWidth, GameFrame.GameHeight - 100);
        attackerOne = new Square(new Dimension(50, 50), new Point(50, 50), Color.RED);
        attackerTwo = new Square(new Dimension(50, 50), new Point(50, 250), Color.BLUE);
        attackerThree = new Square(new Dimension(50, 50), new Point(50, 450), Color.YELLOW);
        defenderOne = new Square(new Dimension(50, 50), new Point(950, 500), Color.GREEN);
        setUpGame();
        myPauseText = new OnScreenText();
        myPauseText.addText("Game Paused.", Integer.MAX_VALUE, new Point(500,300), 50);

    }

    public void setUpGame () {
        
        myWallBalls.clear();
        myTopMenu = new MenuBar(new Rectangle(0, 0, GameFrame.GameWidth, 50));
        myPowerBar = new PowerBar(new Rectangle(700, 5, 200, 35));
        myWallBuilder =
                new WallBuilder(GameFrame.GameWidth, GameFrame.GameHeight, Color.GRAY, myPowerBar);
        myTopMenu.addComponent(myPowerBar);
        myPowerBar.addPower(40);
        myScore = new Score(new Rectangle(100, 25, 200, 20));
        myTopMenu.addComponent(myScore);
        initalizeControllerStrategies();
        allSquares.addAll(myAttackers);
        allSquares.add(defenderOne);
        setGameRules();
    }

    private void initalizeControllerStrategies () {
        if (controllerSettings.size() > 0) {
            redoControllers();
        }
        else{
            for (int i = 0; i < controllers.length; i++) {
                if (ANDROID.equals(controllers[i])) {
                    createAndroidControllerStrategy(i + 1);
                }
                else if (KEYBOARD_WASD.equals(controllers[i])) {
                    createKeyboardControllerStrategy(i + 1, KeyboardControllerStrategy.WASD);
                }
                else if (KEYBOARD_ARROW.equals(controllers[i])) {
                    createKeyboardControllerStrategy(i + 1, KeyboardControllerStrategy.ARROWS);
                }
            }
        }

    }

    private void redoControllers () {
        ControllerStrategy curStrat;

        curStrat = controllerSettings.remove(defenderOne);
        defenderOne = new Square(new Dimension(50, 50), new Point(950, 500), Color.GREEN);
        System.out.println("resetting defender one!");
        resetSquare(defenderOne, curStrat);

        curStrat = controllerSettings.remove(attackerOne);
        attackerOne = new Square(new Dimension(50, 50), new Point(50, 50), Color.RED);
        resetSquare(attackerOne, curStrat);

        curStrat = controllerSettings.remove(attackerTwo);
        attackerTwo = new Square(new Dimension(50, 50), new Point(50, 250), Color.BLUE);
        resetSquare(attackerTwo, curStrat);

        curStrat = controllerSettings.remove(attackerThree);
        attackerThree = new Square(new Dimension(50, 50), new Point(50, 450), Color.YELLOW);
        resetSquare(attackerThree, curStrat);

    }

    private void resetSquare (Square newSquare, ControllerStrategy strat) {
        if (strat != null) {
            System.out.println("replacing strat");
            controllerSettings.put(newSquare, strat);
            strat.setTarget(newSquare);
        }
        else {
            System.out.println("strategy null");
        }
    }

    private void createKeyboardControllerStrategy (int number, int type) {

        KeyboardControllerStrategy myControllerStategy;
        switch (number) {
            case 2:
                myControllerStategy = new KeyboardControllerStrategy(attackerOne, this, type);
                controllerSettings.put(attackerOne, myControllerStategy);
                myAttackers.add(attackerOne);
                break;
            case 3:
                myControllerStategy = new KeyboardControllerStrategy(attackerTwo, this, type);
                controllerSettings.put(attackerTwo, myControllerStategy);
                myAttackers.add(attackerTwo);
                break;
            case 4:
                myControllerStategy = new KeyboardControllerStrategy(attackerThree, this, type);
                controllerSettings.put(attackerThree, myControllerStategy);
                myAttackers.add(attackerThree);
                break;

        }

    }

    private void createAndroidControllerStrategy (int number) {
        AndroidControllerStrategy myAndroidStrategy;
        switch (number) {
            case 1:
                myAndroidStrategy =
                new AndroidControllerStrategy(defenderOne, 1, true, myWallBuilder);
                controllerSettings.put(defenderOne, myAndroidStrategy);

                break;
            case 2:
                myAndroidStrategy =
                new AndroidControllerStrategy(attackerOne, 2, false, myWallBuilder);
                controllerSettings.put(attackerOne, myAndroidStrategy);
                myAttackers.add(attackerOne);
                break;
            case 3:
                myAndroidStrategy =
                new AndroidControllerStrategy(attackerTwo, 3, false, myWallBuilder);
                controllerSettings.put(attackerTwo, myAndroidStrategy);
                myAttackers.add(attackerTwo);
                break;

            case 4:
                myAndroidStrategy =
                new AndroidControllerStrategy(attackerThree, 4, false, myWallBuilder);
                controllerSettings.put(attackerThree, myAndroidStrategy);
                myAttackers.add(attackerThree);
                break;
        }
    }

    private void setGameRules () {
        myGameRules = new ArrayList<GameRule>();
        myGameRules.add(new GameOverRule(defenderOne, this));
        myGameRules.add(new WallCollisionRule(myWallBuilder, myAttackers));
        myGameRules.add(new EnemyCollisionRule(myAttackers, defenderOne, this));
        myGameRules.add(new WallBallSpawnRule(myGameArea, myWallBalls));
        myGameRules
        .add(new WallBallCollisionRule(myAttackers, defenderOne, myWallBalls, myPowerBar));
        myGameRules.add(new BoundryRule(myGameArea, allSquares));
    }

    @Override
    public void paint (Graphics g) {
        Graphics2D pen = (Graphics2D) g;
        clearScreen(pen);
        switch (myState) {
            case Starting:
                paintCountDown(pen);
                break;
            case Running:
                paintRunning(pen);
                break;
            case Over:
                paintOver(pen);
                break;
            case Paused:
                myPauseText.drawText(pen);
                break;
        }
    }

    private void paintOver (Graphics2D pen) {
        myTextManager.paint(pen);

    }

    private void paintRunning (Graphics2D pen) {
        checkGameRules();
        for (Square s : myAttackers) {
            s.update();
            s.paint(pen);
        }

        defenderOne.update();
        defenderOne.paint(pen);
        myWallBuilder.paint(pen);
        paintMenuBar(pen);
        for (int i = myWallBalls.size() - 1; i >= 0; i--) {
            WallBall curWallBall = myWallBalls.get(i);
            curWallBall.paint(pen);
        }

    }

    private void paintCountDown (Graphics2D pen) {
        myTextManager.paint(pen);

    }

    private void clearScreen (Graphics2D pen) {
        pen.setColor(Color.BLACK);
        pen.fillRect(0, 0, getWidth(), getHeight());
    }

    private void paintMenuBar (Graphics2D pen) {
        myTopMenu.draw(pen);
    }

    @Override
    public void run () {
        long beforeTime;
        long timeDiff;
        long sleep;
        beforeTime = System.currentTimeMillis();
        while (true) {
            repaint();
            timeDiff = System.currentTimeMillis() - beforeTime;
            sleep = myDelay - timeDiff;
            if (sleep < 0) {
                sleep = 2;
            }
            try {
                Thread.sleep(sleep);
            }
            catch (InterruptedException e) {

            }

            beforeTime = System.currentTimeMillis();
        }

    }

    public void checkGameRules () {
        for (GameRule gR : myGameRules) {
            gR.checkRule();
        }
    }

    public void startGame () {
        gameThread = new Thread(this);
        gameThread.start();

    }

    public void gameOver () {
        myState = State.Over;
        myTextManager = new TextManager(this, true, myScore);

    }

    public void vibrateController (Square s, AndroidVibration v) {
        ControllerStrategy curStrat = controllerSettings.get(s);
        if (controllerSettings.get(s) instanceof AndroidControllerStrategy) {
            AndroidControllerStrategy androidStrat = (AndroidControllerStrategy) curStrat;
            androidStrat.postVibration(v);
        }
    }

    public void setState (State s) {
        myState = s;
    }

    public void startCountdown () {
        myTextManager = new TextManager(this, false, myScore);
        setUpGame();
    }

    public void togglePlay () {
        if(myState == State.Paused){
           
            myState = State.Running;
        }
        else if(myState == State.Running){
          
            myState = State.Paused;
        }

    }
    
     

}
