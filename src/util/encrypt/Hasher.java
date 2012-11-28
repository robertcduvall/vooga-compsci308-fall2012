package util.encrypt;

public class Hasher {

    public static String hashCode (String string) {
        Integer hash =string.hashCode();
        return hash.toString();
    }
    
    public static String encrypt(String string){
        return string;
    }
    
    public static String decrypt(String string){
        return string;
    }
}
