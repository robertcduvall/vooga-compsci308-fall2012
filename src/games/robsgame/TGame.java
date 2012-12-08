package games.robsgame;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Timer;

import javax.imageio.ImageIO;
import javax.swing.JApplet;
import arcade.utility.ImageReader;

/**
 * A 2D 3rd Person Shoot 'Em Up game based on Duke Tenting.
 * 
 * @author Robert W. Bruce III
 * 
 * This code isn't the best & is from the first assignment.
 * I just wanted to add it to the arcade so we had more
 * games to play.
 */
@SuppressWarnings("serial")
public class TGame extends JApplet implements Settings {
    Image background;
    Timer timer;
    ArrayList<Sprite> gameSprites;
    ArrayList<Sprite> spawnSprites;
    ArrayList<Sprite> remSprites;
    private GameWindow myContainer;
    List<Image> myImages;
    List<AudioClip> mySounds;
    int waveNumber;
    int cycleCount;
    Sprite user;
    private int gameState; // 0-splash 1-main 2-play SaveCameron 3-play Survival
    // 4-gameoverDeath 5-gameoverCameron 6-about 7-win
    private int lastPressed; // Remembers what the last pressed key was.
    private int countPressed; // Counts how many times in a row the key has been
    // pressed.
    private int menuSelected; // 0-SaveCameron 1-Survival
    private boolean needToLoad;
    private boolean cameronAttack;
    private String message;

    public TGame() {
        System.out.println("Crap. Try again.");
    }

    public TGame(GameWindow container) {
        System.out.println("Well, made it this far...");
        myContainer = container;
        gameSprites = new ArrayList<Sprite>();
        spawnSprites = new ArrayList<Sprite>();
        remSprites = new ArrayList<Sprite>();
        myImages = loadImages(IMAGE_LOCATION);
        mySounds = loadSounds(SOUND_LOCATION);
        cycleCount = 0;
        waveNumber = 0;
        gameState = 0;
        needToLoad = true;
        cameronAttack = true;
        message = "";
    }

    public int getInKey() {
        return myContainer.getLastKeyPressed();
    }

    public Point getInMouse() {
        return myContainer.getMousePosition();
    }

    public Image loadImage(String imageName) {
        return ImageReader.loadImage(IMAGE_LOCATION, imageName);
    }

    public void loadLevelOne() {
        // Set the background image:
        gameSprites.clear();
        background = loadImage("kVille1.png");

        user = new Player();
        addSprite(user);

        waveNumber = 0;
        // Set the sprites:
        if (!cameronAttack)
            waveOne();
        else
            waveTwo();

        System.out.println("Level One is Loaded!");
    }

    public void win(Graphics2D pen) {
        // Draw the win image.
        background = loadImage("win.png");
        pen.drawImage(background, 0, 0, WINDOW_SIZE.width, WINDOW_SIZE.height,
                null);
        // Wait 4 seconds before switching to the main menu.
        new java.util.Timer().schedule(new java.util.TimerTask() {
            @Override
            public void run() {
                gameState = 1;
            }
        }, 4000);
    }

    private void waveOne() {
        cycleCount = 0;
        waveNumber++;
        if (waveNumber > NUMBER_OF_WAVES) { // All waves have gone
            if (gameSprites.size() == 1) // All Enemies dead.
            {
                System.out.println("You win!");
                gameState = 7;
            }
            return;
        }
        // Set up Sprites for premade level!
        Sprite enemy1 = new Enemy(new Point(100, 700), ENEMY_SIZE, 0.0, user);
        addSprite(enemy1);
        Sprite enemy2 = new Enemy(new Point(100, 400), ENEMY_SIZE, 0.0, user);
        addSprite(enemy2);
        Sprite enemy3 = new Enemy(new Point(300, 700), ENEMY_SIZE, 0.0, user);
        addSprite(enemy3);
        Sprite enemy4 = new Enemy(new Point(500, 700), ENEMY_SIZE, 0.0, user);
        addSprite(enemy4);
        Sprite enemy5 = new Enemy(new Point(700, 700), ENEMY_SIZE, 0.0, user);
        addSprite(enemy5);
    }

    /**
     * Sets the gamestate. Gamestates correspond to: 0-splash 1-main 2-play
     * SaveCameron 3-play Survival 4-gameoverDeath 5-gameoverCameron 6-about
     * 7-win
     * 
     * @param newState
     */
    public void setGameState(int newState) {
        gameState = newState;
    }

    private void waveTwo() {
        cycleCount = 0;
        waveNumber++;
        if (waveNumber > NUMBER_OF_WAVES) { // All waves have gone
            if (gameSprites.size() == 1) // All Enemies dead.
            {
                System.out.println("You win!");
                gameState = 7;
            }
            return;
        }
        // Set up Sprites for premade level!
        Sprite enemy1 = new Enemy(new Point(100, 700), ENEMY_SIZE, 0.0,
                new Point(900, 100));
        addSprite(enemy1);
        Sprite enemy2 = new Enemy(new Point(100, 400), ENEMY_SIZE, 0.0,
                new Point(900, 100));
        addSprite(enemy2);
        Sprite enemy3 = new Enemy(new Point(300, 700), ENEMY_SIZE, 0.0,
                new Point(900, 100));
        addSprite(enemy3);
        Sprite enemy4 = new Enemy(new Point(500, 700), ENEMY_SIZE, 0.0,
                new Point(900, 100));
        addSprite(enemy4);
        Sprite enemy5 = new Enemy(new Point(700, 700), ENEMY_SIZE, 0.0,
                new Point(900, 100));
        addSprite(enemy5);
    }

    public void playerDeath(Graphics2D pen) {
        // Draw the gameover image.
        background = loadImage("gameover_death.png");
        pen.drawImage(background, 0, 0, WINDOW_SIZE.width, WINDOW_SIZE.height,
                null);
        // Wait 1 second before switching to the main menu.
        new java.util.Timer().schedule(new java.util.TimerTask() {
            @Override
            public void run() {
                gameState = 1;
            }
        }, 4000);
    }

    public void addSprite(Sprite toAdd) {
        spawnSprites.add(toAdd); // Adds spawned bullets / sprites to a
        // temporary ArrayList
        // so that adding them mid-update doesnt
        // cause a crash.
    }

    public void remSprite(Sprite toRemove) {
        remSprites.add(toRemove); // Adds spawned bullets / sprites to a
        // temporary ArrayList
        // so that adding them mid-update doesnt cause a crash.
    }

    /**
     * Update all characters in the game.
     */
    public void update() {
        if (gameState != 2 && gameState != 3) // If game hasn't started yet, don't create / deal
            // with sprites.
            return;
        cycleCount++;
        if (cycleCount == CYCLES_BETWEEN_WAVES)
            if (!cameronAttack)
                waveOne();
            else
                waveTwo();
        for (Sprite b : gameSprites) {
            b.update(this);
            if(cameronAttack && b.enemyInCameron()) gameState = 5;

        }
        if (!spawnSprites.isEmpty()) {
            gameSprites.addAll(spawnSprites);
            spawnSprites.removeAll(spawnSprites);
        }
        if (!remSprites.isEmpty()) {
            gameSprites.removeAll(remSprites);
            remSprites.removeAll(remSprites);
        }
    }

    public Set<Integer> getPressedArray() {
        return myContainer.getPressedArray();
    }

    /**
     * Render all characters in the game.
     */
    public void paint(Graphics2D pen) {// 0-splash 1-main 2-play SaveCameron
        // 3-play Survival 4-gameoverDeath
        // 5-gameoverCameron 6-about 7-win
        switch (gameState) {
            case 0: // Splash
                splash(pen);
                break;
            case 1: // Main Menu
                needToLoad = true;
                mainMenu(pen);
                message = "";
                break;
            case 2: // Gameplay SaveCameron
                cameronAttack = true;
                if (needToLoad)
                    loadLevelOne();
                needToLoad = false;
                playGame(pen);
                break;
            case 3: // Gameplay Survival
                cameronAttack = false;
                if (needToLoad)
                    loadLevelOne();
                needToLoad = false;
                playGame(pen);
                break;
            case 4: // gameoverDeath
                playerDeath(pen);
                break;
            case 5: // gameoverCameron
                cameronLoss(pen);
                break;
            case 6: // about
                about(pen);
                break;
            case 7:
                win(pen);
                break;
            default:
                System.out.println("Something went wrong with gameState3!");
                break;
        }
    }

    public void about(Graphics2D pen) {
        // Draw the about image. Make sure it is first.
        background = loadImage("about2.png");
        pen.drawImage(background, 0, 0, WINDOW_SIZE.width, WINDOW_SIZE.height,
                null);
        // Wait 1 second before switching to the main menu.
        new java.util.Timer().schedule(new java.util.TimerTask() {
            @Override
            public void run() {
                gameState = 1;
            }
        }, 5000);
    }

    public void splash(Graphics2D pen) {
        // Draw the splash image. Make sure it is first.
        background = loadImage("Splash1.png");
        pen.drawImage(background, 0, 0, WINDOW_SIZE.width, WINDOW_SIZE.height,
                null);
        // Wait 1 second before switching to the main menu.
        new java.util.Timer().schedule(new java.util.TimerTask() {
            @Override
            public void run() {
                gameState = 1;
            }
        }, 1000);
    }

    public void mainMenu(Graphics2D pen) {
        background = loadImage("Main_Menu.png");
        pen.drawImage(background, 0, 0, WINDOW_SIZE.width, WINDOW_SIZE.height,
                null);
        pen.setColor(Color.white);
        pen.fillPolygon(drawSelected(menuSelected));
        if (lastPressed == myContainer.myLastKeyPressed)
            countPressed++;
        else
            countPressed = 0;
        // Ugh. Here I need to work on an algorithm that, no matter what,
        // accepts a click to
        // move 1 menu option, but requires a hold-down or double tap for 2.
        if (countPressed > 1) {
            switch (myContainer.myLastKeyPressed) {
                case 38: // UArrow - up
                    if (menuSelected > 0)
                        menuSelected--;
                    break;
                case 87: // W - up
                    if (menuSelected > 0)
                        menuSelected--;
                    break;
                case 40: // DArrow - down
                    if (menuSelected < 2)
                        menuSelected++;
                    break;
                case 83: // S - down
                    if (menuSelected < 2)
                        menuSelected++;
                    break;
                case 10: // Enter
                    menuOptionSelected();
                    break;
                case 32: // Spacebar
                    menuOptionSelected();
                    break;
                default:
                    break;
            }

            countPressed = 0;
        }
        lastPressed = myContainer.myLastKeyPressed;
    }

    public void cameronLoss(Graphics2D pen) {
        // Draw the gameover image.
        background = loadImage("gameover_cameron.png");
        pen.drawImage(background, 0, 0, WINDOW_SIZE.width, WINDOW_SIZE.height,
                null);
        // Wait 1 second before switching to the main menu.
        new java.util.Timer().schedule(new java.util.TimerTask() {
            @Override
            public void run() {
                gameState = 1;
            }
        }, 4000);
    }

    // 0-splash 1-main 2-play SaveCameron
    // 3-play Survival 4-gameoverDeath
    // 5-gameoverCameron 6-about 7-win
    private void menuOptionSelected() {
        switch (menuSelected) {
            case 0: // Save Cameron
                gameState = 2;
                break;
            case 1: // Survival
                gameState = 3;
                break;
            case 2: // about
                gameState = 6;
                break;
            default:
                System.out.println("Something went wrong with the menuSelected!");
        }
    }

    private Polygon drawSelected(int menuSelected) {
        switch (menuSelected) {
            case 0: // Hover Campaign
                int[] xPoints1 = { 350, 350, 360 };
                int[] yPoints1 = { 400, 420, 410 };
                return new Polygon(xPoints1, yPoints1, 3);
            case 1: // Hover Settings
                int[] xPoints2 = { 370, 370, 380 };
                int[] yPoints2 = { 490, 510, 500 };
                return new Polygon(xPoints2, yPoints2, 3);
            case 2: // Hover Quit
                int[] xPoints3 = { 385, 385, 395 };
                int[] yPoints3 = { 540, 560, 550 };
                return new Polygon(xPoints3, yPoints3, 3);
            default:
                System.out.println("Something went wrong with menu selection!");
                return new Polygon();
        }

    }

    public void playGame(Graphics2D pen) {
        pen.drawImage(background, 0, 0, WINDOW_SIZE.width, WINDOW_SIZE.height,
                null);
        for (Sprite b : gameSprites) {
            b.paint(pen);
        }
    }

    /**
     * Returns size (in pixels) of the game area.
     */
    @Override
    public Dimension getSize() {
        return myContainer.getSize();
    }

    /**
     * Returns all images in the given directory
     * 
     * The given directory should be given relative to the source code, this
     * function will find its absolute location.
     */
    private List<AudioClip> loadSounds(String directory) {
        try {
            URL path = getClass().getResource(directory);
            List<AudioClip> results = new ArrayList<AudioClip>();
            for (String file : new File(path.toURI()).list()) {
                results.add(myContainer.getAudioClip(path, file));
            }
            return results;
        } catch (Exception e) {
            // should not happen
            return new ArrayList<AudioClip>();
        }
    }

    /**
     * Returns all images in the given directory
     * 
     * The given directory should be given relative to the source code, this
     * function will find its absolute location.
     */
    private List<Image> loadImages(String directory) {
        try {
            URL path = getClass().getResource(directory);
            List<Image> results = new ArrayList<Image>();
            for (String file : new File(path.toURI()).list()) {
                results.add(myContainer.getImage(path, file));
            }
            return results;
        } catch (Exception e) {
            // should not happen
            return new ArrayList<Image>();
        }
    }
}
