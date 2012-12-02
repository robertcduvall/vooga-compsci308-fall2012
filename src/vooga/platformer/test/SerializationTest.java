package vooga.platformer.test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import vooga.platformer.gameobject.StaticObject;
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
    private static final int TEST_X_0 = 5;
    private static final int TEST_X_1 = 10;

    private GameObject myGameObject0;
    private GameObject myGameObject1;

    @Before
    public void initializeGameObject () throws Exception {
        myGameObject0 =
                new StaticObject("x=" + TEST_X_0 +
                          ",y=0,width=5,height=5,imagePath=src/games/platformerdemo/player.png,id=0");
        myGameObject1 =
                new StaticObject("x=" + TEST_X_1 +
                          ",y=0,width=5,height=5,imagePath=src/games/platformerdemo/player.png,id=0");
    }

    @Test
    public void testSerializable () throws Exception {
        FileOutputStream fos = new FileOutputStream(SERIALIZED_OBJECT_PATH);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(myGameObject0);
        oos.writeObject(myGameObject1);
        oos.close();
    }

    @Test
    public void testDeserializable () throws Exception {
        FileInputStream fis = new FileInputStream(SERIALIZED_OBJECT_PATH);
        ObjectInputStream ois = new ObjectInputStream(fis);
        GameObject readGameObject0 = (GameObject) ois.readObject();
        GameObject readGameObject1 = (GameObject) ois.readObject();
        ois.close();
        Assert.assertTrue(readGameObject0.getX() != readGameObject1.getX());
        // Assert.assertEquals(TEST_X_0, (int) readGameObject0.getX());
    }

}
