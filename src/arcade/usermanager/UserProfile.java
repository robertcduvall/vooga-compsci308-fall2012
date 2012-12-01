package arcade.usermanager;

import java.util.List;

public class UserProfile {
    private String myName;
    private String myPicture;
    
    private String myFirstName;
    private String myLastName;
    public UserProfile(String name, String picture, String firstName, String lastName){
        myName=name;
        myPicture=picture;
        myFirstName=firstName;
        myLastName=lastName;
        
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

}
