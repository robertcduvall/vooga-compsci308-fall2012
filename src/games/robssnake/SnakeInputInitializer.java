package games.robssnake;

import games.robssnake.strategy.DoNothingUpdateStrategy;
import games.robssnake.strategy.GoDownStrategy;
import games.robssnake.strategy.GoLeftStrategy;
import games.robssnake.strategy.GoRightStrategy;
import games.robssnake.strategy.GoUpStrategy;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.List;
import arcade.utility.ImageReader;
import util.input.core.AndroidController;
import util.input.core.KeyboardController;
import util.input.core.WiiController;
import vooga.platformer.core.PlatformerController;
import vooga.platformer.core.inputinitializer.InputInitializer;
import vooga.platformer.core.inputinitializer.InputTeamPlayerAdapter;
import vooga.platformer.gameobject.Enemy;
import vooga.platformer.gameobject.GameObject;
import vooga.platformer.gameobject.MovingObject;
import vooga.platformer.gameobject.Player;
import vooga.platformer.level.Level;
import vooga.platformer.level.levelplugin.SimpleBackgroundPainter;
import vooga.platformer.util.camera.StaticCamera;
/**
 * Input initializer for my Snake-type game.
 * Also does a few setup things to the level to make the game work.
 * @author Robert Bruce
 *
 */
public class SnakeInputInitializer implements InputInitializer {
    Player myPlayer;
    Adapter myAdapter;
    @Override
    public void setUpInput (List<GameObject> objects,
            PlatformerController platformControl) {
        loadLevelStuff(objects, platformControl);
        myPlayer = null;
        for (GameObject go : objects) {
            if (go instanceof Player) {
                myPlayer = (Player) go;
                myPlayer.addControlStrategy("goUp", new GoUpStrategy(myPlayer));
                myPlayer.addControlStrategy("goLeft", new GoLeftStrategy(myPlayer));
                myPlayer.addControlStrategy("goRight", new GoRightStrategy(myPlayer));
                myPlayer.addControlStrategy("goDown", new GoDownStrategy(myPlayer));
                myPlayer.addStrategy("GravityStrategy", new DoNothingUpdateStrategy(myPlayer));
            }
        }
        myAdapter = new Adapter(myPlayer);
        KeyboardController keyControl = new KeyboardController(platformControl);
        //WiiController wiiControl = new WiiController(1);
        //AndroidController androidController = new AndroidController(1);
        try {
            keyControl.setControl(KeyEvent.VK_UP, KeyboardController.PRESSED, myAdapter, "goUp");
            keyControl.setControl(KeyEvent.VK_LEFT, KeyboardController.PRESSED, myAdapter, "goLeft");
            keyControl.setControl(KeyEvent.VK_RIGHT, KeyboardController.PRESSED, myAdapter, "goRight");
            keyControl.setControl(KeyEvent.VK_DOWN, KeyboardController.PRESSED, myAdapter, "goDown");
            // wiiControl.setControl(WiiController.WIIMOTE_BUTTON_UP, WiiController.BUTTON_PRESSED, myAdapter, "goUp");
            // wiiControl.setControl(WiiController.WIIMOTE_BUTTON_LEFT, WiiController.BUTTON_PRESSED, myAdapter, "goLeft");
            // wiiControl.setControl(WiiController.WIIMOTE_BUTTON_RIGHT, WiiController.BUTTON_PRESSED, myAdapter, "goRight");
            // wiiControl.setControl(WiiController.WIIMOTE_BUTTON_DOWN, WiiController.BUTTON_PRESSED, myAdapter, "goDown");
            //TODO: Add android controller:
            // androidController.setControl(KeyEvent.VK_UP, KeyboardController.PRESSED, myAdapter, "goUp");
            // androidController.setControl(KeyEvent.VK_LEFT, KeyboardController.PRESSED, myAdapter, "goLeft");
            // androidController.setControl(KeyEvent.VK_RIGHT, KeyboardController.PRESSED, myAdapter, "goRight");
            // androidController.setControl(KeyEvent.VK_DOWN, KeyboardController.PRESSED, myAdapter, "goDown");
        }
        catch (NoSuchMethodException e) {
            System.out.println("Error initializing KeyboardController: no such method");
            e.printStackTrace();
        }
        catch (IllegalAccessException e) {
            System.out.println("Error initializing KeyboardController: illegal access");
            e.printStackTrace();
        }
    }
    private void loadLevelStuff (List<GameObject> objects,
            PlatformerController platformControl) {
        Level myLevel = platformControl.getLevel();
        myLevel.addPlugin(new SimpleBackgroundPainter(
                new File("src/games/robssnake/images/background.png")));
        myLevel.setCamera(new StaticCamera(myLevel.getDimension(),
                new Rectangle(myLevel.getDimension().width,
                        myLevel.getDimension().height)));
        makeEnemiesMushrooms(objects, platformControl);
    }

    private void makeEnemiesMushrooms (List<GameObject> objects,
            PlatformerController platformControl) {
        Image mushroomImage = ImageReader.loadImage("src/games/robssnake/images", "mushroom.png");
        for (GameObject go : objects) {
            if (go instanceof Enemy) {
                go.setImage(mushroomImage);
                go.addStrategy("GravityStrategy", new DoNothingUpdateStrategy((MovingObject)go));
            }
        }
    }
    public class Adapter {
        Player aPlayer;
        public Adapter(Player player) {
            aPlayer = player;
        }
        public void goLeft() {
            myPlayer.fireControlStrategy("goLeft");
        }
        public void goRight() {
            myPlayer.fireControlStrategy("goRight");
        }
        public void goUp() {
            myPlayer.fireControlStrategy("goUp");
        }
        public void goDown() {
            myPlayer.fireControlStrategy("goDown");
        }
    }

}
