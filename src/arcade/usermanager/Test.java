package arcade.usermanager;

import java.util.ResourceBundle;

public class Test {
    private static String myUserBasicFilePath;
    private static String myUserMessageFilePath;
    private static String myUserGameFilePath;
    private UserXMLReader myXMLReader;
    private UserXMLWriter myXMLWriter;
    private final String successString = "Successful";
    private static ResourceBundle resource;
    
    public static void main(String[] args){
        resource = ResourceBundle.getBundle("resources.properties");
        myUserBasicFilePath = resource.getString("BasicFilePath");
        myUserMessageFilePath = resource.getString("MessageFilePath");
        myUserGameFilePath = resource.getString("GameFilePath");

        
    }

}
