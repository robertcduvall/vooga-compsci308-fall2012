package games.robsgame;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import arcade.IArcadeGame;
import arcade.gamemanager.GameSaver;
import arcade.utility.ImageReader;
/**
 * 
 * @author Robert Bruce
 * This code isn't the best & is from the first assignment.
 * I just wanted to add it to the arcade so we had more
 * games to play.
 */
public class RunRobsGame implements IArcadeGame {

    @Override
    public void runGame (String userPreferences, GameSaver s) {
        System.out.println("Trying to run!");
        JFrame myFrame = new JFrame("Rob's Tenting 2D");
        JPanel myPanel = new JPanel();
        GameWindow myGame = new GameWindow();
        myPanel.add(myGame);

        myFrame.setLocationRelativeTo(null);
        myFrame.add(myPanel);
        myGame.init();
        myGame.start();
        myFrame.pack();
        myFrame.setVisible(true);
    }

    @Override
    public List<Image> getScreenshots () {
        Image img = ImageReader.loadImage("src/games/robsgame/images", "kVille1.png");
        List<Image> ret = new ArrayList<Image>();
        ret.add(img);
        return ret;
    }

    @Override
    public Image getMainImage () {
        Image img = ImageReader.loadImage("src/games/robsgame/images", "kVille1.png");
        return img;
    }

    @Override
    public String getDescription () {
        return "The game I made for the first assignment." +
                " Still slightly buggy, but I wanted" +
                " to add it to the arcade anyways.";
    }

    @Override
    public String getName () {
        return "Rob's Tenting 2D";
    }

}
