package util.input.inputhelpers;

/**
 * Created with IntelliJ IDEA.
 * User: lance
 * Date: 11/6/12
 * Time: 9:32 AM
 * To change this template use File | Settings | File Templates.
 */
public class UKeyCode {

    public static int codify(int segOne, int segTwo) {
        String sCode = "" + segOne + segTwo;
        return Integer.parseInt(sCode);
    }

}
