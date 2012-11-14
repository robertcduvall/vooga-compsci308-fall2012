package games.platformerdemo;

import java.awt.Dimension;
import java.awt.Rectangle;
import util.camera.Camera;
import vooga.platformer.collision.BasicCollisionChecker;
import vooga.platformer.collision.CollisionChecker;
import vooga.platformer.level.Level;
import vooga.platformer.level.LevelFactory;
import vooga.platformer.util.camera.FollowingCamera;

public class DemoLevelFactory extends LevelFactory {

    @Override
    public Level loadLevel (String levelName) {
        if (levelName.equals("level1")) {
            /*
             * Set up the CollisionChecker and Camera objects for this level--eventually we will want this to be
             * customizable via config file, but for now, we could move this code to the Level subclass's constructor.
             */
            CollisionChecker checker = new SloppyCollisionChecker();
            FollowingCamera cam = new FollowingCamera(new Dimension(800, 600), new Rectangle(3200, 2400));
            
            /*
             * Instantiate a new Level instance and add GameObjects to it. You guys should provide this functionality.
             */
            Level currLevel = new TestLevel(new Dimension(3200, 2400), checker, cam);
            Player player1 = new Player("x=4,y=5,width=50,height=50");
            currLevel.addGameObject(player1);
            currLevel.addGameObject(new Enemy("x=400,y=5,width=50,height=50"));
            currLevel.addGameObject(new Brick("x=4,y=300,width=50,height=50"));
            currLevel.addGameObject(new Brick("x=400,y=300,width=50,height=50"));
            
            /*
             * This code tells the camera to follow the player object. This code would be tricky for you guys to do.
             * We could just have the Level be responsible for keeping track of its Player and setting up the Camera appropriately.
             */
            cam.setTarget(player1);
            return currLevel;
        }
        else {
            return null;
        }
    }
}
