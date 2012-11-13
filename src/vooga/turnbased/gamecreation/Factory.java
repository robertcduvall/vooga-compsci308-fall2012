package vooga.turnbased.gamecreation;

import java.util.HashMap;


/**
 * Factory accepts String className and any number of constructor arguments,
 * finds corresponding constructor, instantiates and returns casted object.
 * 
 * Example usage:
 * ## Constructor1
 * public SomeClass1 (Point p, int i, String s, Sprite sp) {}
 * ## Constructor 2
 * public SomeClass2 (char c, boolean b, HashMap<String,Sprite>) {}
 * ## Usage
 * SomeClass1 obj1 = myFactory.instantiate(
 * "somepackage1.SomeClass1", myPoint, myInt, myString, mySprite);
 * SomeClass2 obj2 = myFactory.instantiate(
 * "somepackage2.SomeClass2", myChar, myBool, myHashMap);
 * 
 * @author volodymyr
 * 
 */
public class Factory {
    /**
     * Needed to support primitives in constructors b/c Java autoboxes generics.
     */
    @SuppressWarnings("serial")
    private HashMap<Class<?>, Class<?>> myUnboxedPrimitives = new HashMap<Class<?>, Class<?>>() {
        {
            put(Byte.class, byte.class);
            put(Short.class, short.class);
            put(Integer.class, int.class);
            put(Long.class, long.class);
            put(Character.class, char.class);
            put(Float.class, float.class);
            put(Double.class, double.class);
            put(Boolean.class, boolean.class);
            put(Void.class, void.class);
        }
    };

    /**
     * @param className e.g. package.name.ClassName, or ClassName.class.getName()
     * @param args any number of constructor arguments
     * @return returns instance of class
     */
    @SuppressWarnings("unchecked")
    public <T, E> E instantiate (String className, T ... args) {
        try {
            Class<?>[] paramTypes = new Class<?>[args.length];
            for (int i = 0; i < args.length; i++) {
                paramTypes[i] = parseClass(args[i]);
            }
            Class<?> objectClass = Class.forName(className);
            return (E) objectClass.getConstructor(paramTypes).newInstance(args);
        }
        catch (Exception e) {
            System.err.println("Do not use boxed primitives in constructor.");
            e.printStackTrace();
            return null;
        }
    }

    private <T> Class<?> parseClass (T obj) {
        Class<?> clazz = obj.getClass();
        Class<?> primitiveVersion = myUnboxedPrimitives.get(clazz);
        return (primitiveVersion == null) ? clazz : primitiveVersion;
    }
}
