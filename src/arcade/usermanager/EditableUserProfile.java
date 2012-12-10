package arcade.usermanager;

public class EditableUserProfile {

    private String myName;
    private String myPicture;
    
    private String myFirstName;
    private String myLastName;
    private UserXMLWriter myXmlWriter;
    
    public EditableUserProfile(String name, String picture, String firstName, String lastName){
        myName=name;
        myPicture=picture;
        myFirstName=firstName;
        myLastName=lastName;
        myXmlWriter=new UserXMLWriter();
        
    }
    
    public String getUserName(){
        return myName;
    }
    
    public String getUserPicture(){
        return myPicture;
    }
    
    public String getUserFirstName(){
        return myFirstName;
    }
    
    public String getUserLastName(){
        return myLastName;
    }
    public void setPicture (String picture) {
        myPicture = picture;
        myXmlWriter.updateUserInfo(myName,"picture",picture);
        
    }

    

}
