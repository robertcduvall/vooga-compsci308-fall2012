package vooga.turnbased;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import arcade.IArcadeGame;
import arcade.gamemanager.GameSaver;


public class Start implements IArcadeGame {

    private static final String myFirstLevelName = "some name";
   
    @Override
    public void runGame (String userPreferences, GameSaver s) {
        start(myFirstLevelName);
    }
   
    public static void main (String[] args) {
        Start game = new Start();
        game.start(myFirstLevelName);
    }
   
    public void start(String levelName) {
        
    }

    @Override
    public List<Image> getScreenshots () {
        List<Image> imgList = new ArrayList<Image>();
        Image ss = null;
        try {
            ss = ImageIO.read(new File(""));
        }
        catch (IOException e) {
            System.out.println("Could not find screenshot");
        }
        imgList.add(ss);
        return imgList;
    }

    @Override
    public Image getMainImage () {
        Image img = null;
        try {
            img = ImageIO.read(new File("src/games/nielgame/images/mario.jpg"));
        }
        catch (IOException e) {
            System.out.println("Could not find main image");
        }
        return img;
    }

    @Override
    public String getDescription () {
        return "some game";
    }

    @Override
    public String getName () {
        return "blablabla";
    }

}
