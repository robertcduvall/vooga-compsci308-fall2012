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
        System.out.println("Root: " + level.getDocumentElement().getNodeName());
        System.out.println("Background Image: " + level.parseBackgroundImage());
        System.out.println("Level Dimension: " + level.parseDimension());
        
        System.out.println();
        System.out.println("Player information:");
        MapPlayerObject mapObject = (MapPlayerObject) level.parseMapPlayer();
        System.out.println("ID: " + mapObject.getID());
        System.out.println("Current Image: " + mapObject.getImage());
        System.out.println("Event: " + mapObject.getModeEvent());
        System.out.println("Location: " + mapObject.getLocation());
        
        System.out.println();
        System.out.println("Player Images:");
        Map<String,Image> testMap = mapObject.getImageMap();
        for (String key : testMap.keySet()) {
            System.out.println(key + " " + testMap.get(key));
        }
    }
}
