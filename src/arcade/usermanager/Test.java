package arcade.usermanager;

import java.util.ResourceBundle;

/**
 * 
 * @author difan
 *
 */

public class Test {
    private static String myUserBasicFilePath;
    private static String myUserMessageFilePath;
    private static String myUserGameFilePath;
    private static UserXMLReader myXMLReader;
    private static UserXMLWriter myXMLWriter;
    private final String successString = "Successful";
    private static ResourceBundle resource;
    private static SocialCenter mySocialCenter;
    
    public static void main(String[] args){
        myXMLWriter=new UserXMLWriter();
        
        mySocialCenter= SocialCenter.getInstance();
        System.out.println("successful");
    }
    
    private void testBundle(){
        resource = ResourceBundle.getBundle("arcade.usermanager.filePath");
        myUserBasicFilePath = resource.getString("BasicFilePath");
        myUserMessageFilePath = resource.getString("MessageFilePath");
        myUserGameFilePath = resource.getString("GameFilePath");
        System.out.println(myUserGameFilePath);
        System.out.println(myUserMessageFilePath);
    }

}
