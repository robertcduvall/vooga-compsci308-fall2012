package vooga.turnbased.gamecreation;

import vooga.turnbased.gameobject.MapPlayerObject;
import java.awt.Image;
import java.io.File;
import java.util.Map;

public class XmlTester {

    /**
     * @param args
     */
    public static void main (String[] args) {
        String xmlPath = "src/vooga/turnbased/resources/Example.xml";
        File xmlFile = new File(xmlPath);
        LevelCreator level = new LevelCreator(xmlFile);
        System.out.println(level.getDocumentElement().getNodeName());
        System.out.println(level.parseBackgroundImage());
        System.out.println(level.parseDimension());
        
        MapPlayerObject mapObject = (MapPlayerObject) level.parseMapPlayer();
        System.out.println(mapObject.getID());
        System.out.println(mapObject.getImage());
        System.out.println(mapObject.getEvent());
        System.out.println(mapObject.getLocation());
        Map<String,Image> testMap = mapObject.getImageMap();
        for (String key : testMap.keySet()) {
            System.out.println(key + " " + testMap.get(key));
        }
    }
}
