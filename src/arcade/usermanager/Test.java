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
         //testRegister();
      //  testDeleteUser();
       // testSendMessage();
        myUserManager=UserManager.getInstance();
        testGetGame();
       
       

    }

    private static void testSendMessage () {
        mySocialCenter.sendMessage("Howard","test", "xin ru zhi shui");
    }

    private static void testRegister () throws Exception {
        boolean status2 =
                mySocialCenter.registerUser("testuser3", "password", "firstname", "lastname");
    }
    private static void testDeleteUser(){
        mySocialCenter.deleteUser("testuser3", "password");
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
    
    private static void testModifyXml(){
        User test=myUserManager.getUser("test");
        test.setPicture("haha");
    }
    private static void testGetMessage(){
        User test=myUserManager.getUser("test");
        test.getMyMessage();
    }
    private static void testGetGame(){
        User Howard=myUserManager.getUser("Howard");
        GameData dg=Howard.getGameData("Platformer");
        dg.setGameInfo("Howard","timesplayed", "5");
        
        
    }

}
