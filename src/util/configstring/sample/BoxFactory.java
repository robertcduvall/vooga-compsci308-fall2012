package util.configstring.sample;

import java.util.Scanner;
import util.reflection.Reflection;

/**
 * A sample class demonstrating the use of config strings. See the javadoc in ConfigStringParser for
 * a description of the config string approach.
 * @author Niel Lebeck
 *
 */
public class BoxFactory {
    public static void main(String[] args) {
        System.out.println("To make a box, type the type of Box you want and its config string");
        System.out.println("Ex: vooga.platformer.util.DoubleBox width=5,height=10,double=3.14159");
        System.out.println("Type 'exit' to exit");
        
        Scanner consoleScanner = new Scanner(System.in);
        
        while (true) {
            String str = consoleScanner.nextLine();
            if ("exit".equals(str)) {
                System.exit(0);
            }
            String[] split = str.split(" ");
            String className = split[0];
            String configString = split[1];

            /* 
             * Here is the advantage of config strings: we do not need to worry about the fact that
             * different Box subclasses have different member variables to initialize. The difference in
             * parameters is encapsulated in the config string format. As long as the user gives the
             * right config string for the desired Box subclass, the class will initialize
             * itself properly.
             * 
             * Without config strings, we would have to take the user's input, figure out what types of
             * parameters the Box subclass takes in its constructor, parse the user's input to
             * initialize those objects, and finally cast all of those types of objects to Objects.
             * The config string provides a standardized constructor format so that Box subclasses perform
             * this task themselves, which is more extensible and no longer requires any casts to Objects.
             */
            Box b = (Box)Reflection.createInstance(className, configString);
            
            System.out.println("Made a new box!");
            b.printDimensions();
            b.printContents();
        }
    }
}
