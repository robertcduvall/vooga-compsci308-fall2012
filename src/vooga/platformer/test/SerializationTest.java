package vooga.platformer.test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import vooga.platformer.gameobject.Brick;
import vooga.platformer.gameobject.GameObject;


/**
 * A test class to assure that any changes made to GameObject do not cause it to
 * be unserializable.
 * 
 * @author Grant Oakley
 * 
 */
public class SerializationTest {
    private static final String SERIALIZED_OBJECT_PATH =
            "src/vooga/platformer/test/testSerialization.bin";
    private static final int TEST_X = 1;

    private GameObject myGameObject;

    @Before
    public void initializeGameObject () throws Exception {
        myGameObject =
                new Brick("x=" + TEST_X +
                          ",y=0,width=5,height=5,imagePath=src/games/platformerdemo/player.png,id=0");
    }

    @Test
    public void testSerializable () throws Exception {
        FileOutputStream fos = new FileOutputStream(SERIALIZED_OBJECT_PATH);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(myGameObject);
        oos.close();
    }

    @Test
    public void testDeserializable () throws Exception {
        FileInputStream fis = new FileInputStream(SERIALIZED_OBJECT_PATH);
        ObjectInputStream ois = new ObjectInputStream(fis);
        GameObject gameObject = (GameObject) ois.readObject();
        ois.close();
        Assert.assertEquals(TEST_X, (int) gameObject.getX());
    }

}
