package arcade.usermanager;

import java.io.IOException;
import java.util.ResourceBundle;


/**
 * test user part of arcade
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
    private static  SocialCenter mySocialCenter;
    private static UserManager myUserManager;

    public static void main (String[] args) throws Exception {

        mySocialCenter = new SocialCenter();
        // testLogOn();
        // testRegister();

       // testSendMessage();
        myUserManager=UserManager.getInstance();
        testUserProfile();
       

    }

    private static void testSendMessage () {
        mySocialCenter.sendMessage("Howard", "mdeng1990", "Hi");
    }

    private static void testRegister () throws Exception {
        boolean status2 =
                mySocialCenter.registerUser("testuser2", "password", "firstname", "lastname");
    }

    private static void testLogOn () throws Exception {
        boolean status = mySocialCenter.logOnUser("Howard", "password");
        System.out.println(status);

    }

    private void testXml () throws IOException {
        myXMLWriter = new UserXMLWriter();
        myXMLWriter.makeUserXML("counter", "clock", "wise", "jesus", "fixyourerrors.");

    }

    private void testBundle () {
        resource = ResourceBundle.getBundle("arcade.usermanager.filePath");
        myUserBasicFilePath = resource.getString("BasicFilePath");
        myUserMessageFilePath = resource.getString("MessageFilePath");
        myUserGameFilePath = resource.getString("GameFilePath");
        System.out.println(myUserGameFilePath);
        System.out.println(myUserMessageFilePath);
    }
    
    private static void testUserProfile(){
       //myUserManager.getUserProfile(userName);
        myUserManager.getAllUserProfile();
        
    }

}
