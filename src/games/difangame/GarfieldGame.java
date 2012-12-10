package games.difangame;

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

public class GarfieldGame implements IArcadeGame {
    
    private static final String myFirstLevelName = "src/games/difangame/levels/level1.xml";
    private static final String myDescription="garfield eat food";
    private static final String myName="garfield game";
    
    public static void main (String[] args) {
        GarfieldGame game = new GarfieldGame();
        game.start(myFirstLevelName);
       
    }
    
    @Override
    public void runGame (String userPreferences, GameSaver s) {
       
        start(myFirstLevelName);
       
        
    }

    public void start(String levelName) {
        JFrame frame = new JFrame("Mario Style Game");
        PlatformerController controller = new PlatformerController(levelName,
                new KeyControllerOnePlayerInputInitializer());
        frame.getContentPane().add(controller);
        frame.pack();
        frame.setVisible(true);
    }

    @Override
    public List<Image> getScreenshots () {
        ArrayList<Image> list=new ArrayList<Image>();
        Image img = null;
        try {
            img = ImageIO.read(new File("src/games/difangame/images/garfield.jpg"));
        }
        catch (IOException e) {
            System.out.println("Could not find main image");
        }
        list.add(img);
        return list;
    }

    @Override
    public  Image getMainImage () {
        Image img = null;
        try {
            img = ImageIO.read(new File("src/games/difangame/images/garfield.jpg"));
        }
        catch (IOException e) {
            System.out.println("Could not find main image");
        }
        return img;
    }

    @Override
    public String getDescription () {
        return myDescription;
    }

    @Override
    public String getName () {
        return myName;
    }

}
