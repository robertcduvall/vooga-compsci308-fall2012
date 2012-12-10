package games.xidugame;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import vooga.platformer.core.PlatformerController;
import vooga.platformer.core.inputinitializer.KeyControllerOnePlayerInputInitializer;
import arcade.IArcadeGame;
import arcade.gamemanager.GameSaver;

public class XiDuGame implements IArcadeGame{ 
    
    private static final String myFirstLevelName = "src/games/xidugame/levelone.xml";

    /**
     * @param args
     */
    public static void main (String[] args) {
        XiDuGame game = new XiDuGame();
        game.start(myFirstLevelName);

    }

    @Override
    public void runGame (String userPreferences, GameSaver s) {
        start(myFirstLevelName);
    }

    private void start (String levelName) {
        JFrame frame = new JFrame("XiDu's Mario Style Game");
        PlatformerController controller = new PlatformerController(levelName,
                new KeyControllerOnePlayerInputInitializer());
        frame.getContentPane().add(controller);
        frame.pack();
        frame.setVisible(true);
    }

    @Override
    public List<Image> getScreenshots () {
        List<Image> imgList = new ArrayList<Image>();
        Image ss = null;
        try {
            ss = ImageIO.read(new File("src/games/xidugame/background.jpeg"));
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
            img = ImageIO.read(new File("src/games/xidugame/background.jpeg"));
        }
        catch (IOException e) {
            System.out.println("Could not find main image");
        }
        return img;
    }

    @Override
    public String getDescription () {
        return "A basic platforming game,jump and shoot!";
    }

    @Override
    public String getName () {
        return "XiDu's Mario Game";
    }

}
