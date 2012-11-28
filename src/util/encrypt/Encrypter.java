package util.encrypt;

import javax.xml.bind.DatatypeConverter;


/**
 * Provides hashing and encoding functions for user data.
 * 
 * @author Howard
 * 
 */
public final class Encrypter {

    private Encrypter () {

    }

    /**
     * Returns a hex hash value for a given string.
     * 
     * @param str string to hash
     * @return
     */
    public static String hashCode (String str) {
        Integer hash = str.hashCode();
        System.out.println(str);
        System.out.println(encode(str));
        System.out.println(decode(encode(str)));
        System.out.println(Integer.toHexString(hash));
        return Integer.toHexString(hash);
    }

    /**
     * Encodes a string with base64 encoding.
     * 
     * @param str String to encode
     * @return
     */
    public static String encode (String str) {
        byte[] encoded = str.getBytes();
        return DatatypeConverter.printBase64Binary(encoded);
    }

    /**
     * Decodes a base64 string.
     * 
     * @param encodedstr Encoded string to decode
     * @return
     */
    public static String decode (String encodedstr) {
        byte[] decoded = DatatypeConverter.parseBase64Binary(encodedstr);
        return new String(decoded);
    }
}
