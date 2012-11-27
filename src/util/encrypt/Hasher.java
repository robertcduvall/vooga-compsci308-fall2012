package util.encrypt;

public class Hasher {

    public static String hashCode (String string) {
        Integer hash = 0;
        for (int i = 0; i < string.length(); i++) {
            hash += (int) string.charAt(i)*(i+31);
        }
        return hash.toString();
    }
}
