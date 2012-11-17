package test.util;

import util.reflection.*;
import static org.junit.Assert.*;


/**
 * A Collection of utility methods that allow for
 * delayed assertions, or assert statements with
 * a timeout. They can be used to test conditions that
 * should "eventually" be true, when it is not known
 * exactly when they will be true.
 * 
 * As a more concrete example, these tests can be
 * used to check that a moving ball "eventually" collides
 * with a wall, or that a player that stays in one spot
 * "eventually" loses all health.
 * 
 * It works best if you do a static import like so:
 * import static test.util.AssertWithTimeout.*
 * 
 * Note: this depends on Duvall's reflection util code
 * in util.reflection.*
 * 
 * @author Alex Browne
 */

public class AssertWithTimeout {

    /**
     * Invokes two methods continuously until their return values
     * are equal, or until timeout.
     * 
     * @param message the message of the assertEquals statement
     * @param method1 a method in object target1
     * @param target1 an object that has a method method1
     * @param method2 a method in object target2
     * @param target2 an object that has a method method2
     * @param ms the timeout in milliseconds
     */

    public static void assertEqualsWithTimeout (String message, String method1,
            Object target1, String method2, Object target2, int ms) {

        Object response1 = null;
        Object response2 = null;
        long startTime = System.currentTimeMillis();
        long maxTime = startTime + ms;
        while (System.currentTimeMillis() < maxTime) {

            // invoke the first method and store the response
            response1 = Reflection.callMethod(target1, method1);

            // invoke the second method and store the response
            response2 = Reflection.callMethod(target2, method2);

            // check if the two responses are equal
            if (response1.equals(response2)) {
                break;
            }

            sleep(20);
        }

        // if we've reached this point, the while loop ended because
        // the return value of the two methods was equal
        // or the loop timed out.

        assertEquals(message, response1, response2);
        return;
    }

    /**
     * Invokes a method continuously until its return value
     * equals some desired value, or until timeout.
     * 
     * @param message the message of the assertEquals statement
     * @param method the name of a method of target,
     *        which will be continuously invoked and compared
     *        against desiredValue
     * @param target an object that has method
     * @param desiredValue the value to which the return value
     *        of method will be compared
     * @param ms the timeout in milliseconds
     */

    public static void assertEqualsWithTimeout (String message, String method,
            Object target, Object desiredValue, int ms) {

        Object response = null;
        long startTime = System.currentTimeMillis();
        long maxTime = startTime + ms;
        while (System.currentTimeMillis() < maxTime) {

            // invoke the method and store the response
            response = Reflection.callMethod(target, method);

            // check if the response equals the desired value
            if (response.equals(desiredValue)) {
                break;
            }

            sleep(20);
        }

        // if we've reached this point, the while loop ended because
        // the method returned something that was equal to the desired
        // value, or the loop timed out.

        assertEquals(message, desiredValue, response);
        return;
    }

    /**
     * Invokes a method continuously until its return value
     * is true, or until timeout.
     * 
     * @param message the message of the assertEquals statement
     * @param method the name of a method of target,
     *        which will be continuously invoked and checked
     *        to see if it's true
     * @param target an object that has method
     * @param ms the timeout in milliseconds
     */

    public static void assertTrueWithTimeout (String message, String method,
            Object target, int ms) {
        boolean response = false;
        long startTime = System.currentTimeMillis();
        long maxTime = startTime + ms;
        while (System.currentTimeMillis() < maxTime) {

            // invoke the method and store the response
            response = (Boolean) Reflection.callMethod(target, method);

            // check if the response equals the desired value
            if (response) {
                break;
            }

            sleep(20);
        }

        // if we've reached this point, the while loop ended because
        // the method returned something that was equal to the desired
        // value, or the loop timed out.

        assertTrue(message, response);
        return;
    }

    /**
     * Invokes a method continuously until its return value
     * is greater than some lower limit, or until timeout.
     * 
     * @param message the message of the assertEquals statement
     * @param method the name of a method of target,
     *        which will be continuously invoked and compared
     *        against lowerLimit
     * @param target an object that has method
     * @param lowerLimit the value to which the return value
     *        of method will be compared
     * @param ms the timeout in milliseconds
     */

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static void assertGreaterThanWithTimeout (String message,
            String method, Object target, Comparable lowerLimit, int ms) {

        Comparable response = 0;
        long startTime = System.currentTimeMillis();
        long maxTime = startTime + ms;
        while (System.currentTimeMillis() < maxTime) {

            // invoke the method and store the response
            response = (Comparable) Reflection.callMethod(target, method);

            // check if the response is greater than the lower limit
            if (response.compareTo(lowerLimit) > 0) {
                break;
            }

            sleep(20);
        }

        // if we've reached this point, the while loop ended because
        // the method returned something that was greater than the lower
        // limit, or the loop timed out.

        assertTrue(message, response.compareTo(lowerLimit) > 0);
        return;
    }

    /**
     * Invokes a method continuously until its return value
     * is less than some upper limit, or until timeout.
     * 
     * @param message the message of the assertEquals statement
     * @param method the name of a method of target,
     *        which will be continuously invoked and compared
     *        against upperLimit
     * @param target an object that has method
     * @param upperLimit the value to which the return value
     *        of method will be compared
     * @param ms the timeout in milliseconds
     */

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static void assertLessThanWithTimeout (String message,
            String method, Object target, Comparable upperLimit, int ms) {

        Comparable response = 0;
        long startTime = System.currentTimeMillis();
        long maxTime = startTime + ms;
        while (System.currentTimeMillis() < maxTime) {

            // invoke the method and store the response
            response = (Comparable) Reflection.callMethod(target, method);

            // check if the response is less than the upper limit
            if (response.compareTo(upperLimit) < 0) {
                break;
            }

            sleep(20);
        }

        // if we've reached this point, the while loop ended because
        // the method returned something that was less than the upper
        // limit, or the loop timed out.

        assertTrue(message, (response.compareTo(upperLimit) < 0));
        return;
    }

    /**
     * A utility method to make above calls to sleep()
     * more concise.
     * 
     * @param ms the time to sleep in milliseconds.
     */

    public static void sleep (int ms) {
        try {
            Thread.sleep(ms);
        }
        catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
