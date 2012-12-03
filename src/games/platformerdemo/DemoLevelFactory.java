package games.platformerdemo;

import vooga.platformer.level.Level;
import vooga.platformer.level.LevelFactory;

public class DemoLevelFactory {

    public Level loadLevel (String levelName) {
        return LevelFactory.loadLevel("src/games/platformerdemo/demoLevel.xml");
        
        /*
         * The level name "level1" corresponds to an instance of TestLevel with the listed GameObjects added to it. Multiple
         * level names can be instances of the same level class--they would just have different GameObjects added. The factory
         * should handle making this connection between level name and Level class instance somehow.
         */
//        if (levelName.equals("level1")) {
//            /*
//             * Set up the CollisionChecker and Camera objects for this level--eventually we will want this to be
//             * customizable via config file, but for now, we could handle this code in the Level subclass's constructor.
//             */
//            CollisionChecker checker = new SloppyCollisionChecker();
//            FollowingCamera cam = new FollowingCamera(new Dimension(800, 600), new Rectangle(3200, 2400));
//            
//            /*
//             * Instantiate a new Level instance and add GameObjects to it. You guys should provide this functionality.
//             */
//            Level currLevel = new TestLevel(new Dimension(3200, 2400), checker, cam);
//            Player player1 = new Player("x=4,y=5,width=33,height=50");
//            currLevel.addGameObject(player1);
//            currLevel.setPlayer(player1);
//            currLevel.addGameObject(new Enemy("x=400,y=5,width=40,height=37"));
//            currLevel.addGameObject(new StaticObject("x=4,y=300,width=50,height=50"));
//            currLevel.addGameObject(new StaticObject("x=54,y=300,width=50,height=50"));
//            currLevel.addGameObject(new StaticObject("x=105,y=300,width=50,height=50"));
//            currLevel.addGameObject(new StaticObject("x=400,y=300,width=50,height=50"));
//            currLevel.addGameObject(new StaticObject("x=450,y=300,width=50,height=50"));
//            currLevel.addGameObject(new StaticObject("x=500,y=300,width=50,height=50"));
//            currLevel.addGameObject(new StaticObject("x=550,y=300,width=50,height=50"));
//            currLevel.addGameObject(new StaticObject("x=600,y=300,width=50,height=50"));
//            currLevel.addGameObject(new StaticObject("x=650,y=300,width=50,height=50"));
//            currLevel.addGameObject(new StaticObject("x=650,y=250,width=50,height=50"));
//            /*
//             * This code tells the camera to follow the player object. This code would be tricky for you guys to do.
//             * We could just have the Level be responsible for keeping track of its Player and setting up the Camera appropriately.
//             */
//            cam.setTarget(player1);
//            return currLevel;
//        }
//        else {
//            return null;
//        }
    }
}
