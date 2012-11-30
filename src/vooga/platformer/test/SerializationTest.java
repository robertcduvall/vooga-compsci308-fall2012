package vooga.platformer.test;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import vooga.platformer.gameobject.Brick;
import vooga.platformer.gameobject.GameObject;


public class SerializationTest {

    private GameObject myGameObject;

    @Before
    public void initializeGameObject () throws Exception {
        myGameObject =
                new Brick(
                          "x=0,y=0,width=5,height=5,imagePath=src/games/platformerdemo/player.png,id=0");
    }

    @Test
    public void testSerializable () throws Exception {
        FileOutputStream fos = new FileOutputStream("serial");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(myGameObject);
        oos.close();
    }

}
