package util.input.core;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import util.input.inputhelpers.BoolTuple;
import util.input.inputhelpers.UKeyCode;


/**
 * This class represents an abstract controller to provide input.
 * 
 * @author Amay, Lance
 * 
 * @param <T>
 */
public abstract class Controller<T> {

    private List<T> mySubscribedElements;
    private Map<Integer, BoolTuple<Object, Method>> myMenuPlate;

    /**
     * Create a new Controller.
     */
    public Controller() {
        mySubscribedElements = new ArrayList<T>();
        myMenuPlate = new HashMap<Integer, BoolTuple<Object, Method>>();
    }

    /**
     * Create a new Controller with an elements that
     * subscribes to its raw data.
     *
     * @param element - The subscribing element
     */
    public Controller(T element) {
        this();
        subscribe(element);
    }

    /**
     * Subscribes a class to this controller's events.
     *
     * @param element - The subscribing class
     */
    public void subscribe (T element) {
        mySubscribedElements.add(element);
    }

    /**
     * Object invokes a method every time action and type occur.
     *
     * @param action - The button to listen for
     * @param type - Pressed or released
     * @param o - The invoking object
     * @param method - The method to be invoked
     * @throws NoSuchMethodException - thrown if the 
     * string method passed in is not a method of Object o
     * @throws IllegalAccessException -" thrown when an 
     * application tries to reflectively create an instance (other than an array), 
     * set or get a field, or invoke a method, but the currently executing method does not have access to the definition of 
     * the specified class, field, method or constructor"
     */
    public void setControl(int action, int type, Object o, String method)
            throws NoSuchMethodException,
            IllegalAccessException{
        Method m;
        m = retrieveMethod(o, method);
        myMenuPlate.put(UKeyCode.codify(type, action),
                new BoolTuple<Object, Method>(o, m));
    }

    /**
     * Class invokes a static method every time action and type occur.
     *
     * @param action - The controller button/key to listen for
     * @param type - Pressed or released
     * @param c - The invoking Class
     * @param method - The static method to be invoked
     * @throws NoSuchMethodException - thrown if the string method passed in is not a method of Object o
     * @throws InstantiationException- "thrown when an application tries to create an instance of a class 
     * using the newInstance method in class Class, but the specified class object cannot be instantiated because it is an
     *  interface or is an abstract class."
     * @throws IllegalAccessException -" thrown when an application tries to reflectively create an instance (other than an array), 
     * set or get a field, or invoke a method, but the currently executing method does not have access to the definition of 
     * the specified class, field, method or constructor"
     */
    @SuppressWarnings("rawtypes")
    public void setControl (int action, int type, Class c, String method)
            throws NoSuchMethodException, IllegalAccessException,
            InstantiationException {
        Method m = retrieveMethod(c, method);
        myMenuPlate.put(UKeyCode.codify(type, action),
                new BoolTuple<Object, Method>(c, m));
    }

    /**
     * Set the desired action on or off.
     *
     * @param action - The controller button/key to listen for
     * @param type - Pressed or released
     * @param isActive - Whether the action should be active or not
     */
    public void setActionActive(int action, int type, boolean isActive){
        if (isActive) {
            myMenuPlate.get(UKeyCode.codify(type, action)).activate();
        }
        else {
            myMenuPlate.get(UKeyCode.codify(type, action)).deactivate();
        }
    }

    /**
     * @param e
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     */
    protected void performReflections (Object inputEvent, String method, int actionID)
            throws IllegalAccessException,
            InvocationTargetException,
            NoSuchMethodException {
        broadcastToSubscribers(method, inputEvent);
        invokeMethod(actionID);
    }

    //get rid of this and set actionID to -1 for the previous one
    protected void performReflections (Object inputEvent, String method)
            throws IllegalAccessException,
            InvocationTargetException,
            NoSuchMethodException {
        broadcastToSubscribers(method, inputEvent);
    }

    /**
     * Broadcast an event that does not need a description to your subscribers.
     * @param e
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     */
    //get rid of this
    protected void broadcast (String methodName) throws IllegalAccessException,
    InvocationTargetException,
    NoSuchMethodException {
        for (T subscribedElement : mySubscribedElements) {
            Method method = subscribedElement.getClass().getMethod(methodName);
            System.out.println(method);
            method.invoke(subscribedElement);
        }
    }

    // PRIVATE METHODS
    /**
     * broadcasts the method to all subscribed elements.
     *
     * @param methodName
     * @param inputEvent
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     * @throws SecurityException
     * @throws NoSuchMethodException
     */
    private void broadcastToSubscribers (String methodName, Object inputEvent)
            throws IllegalAccessException,
            IllegalArgumentException,
            InvocationTargetException,
            NoSuchMethodException,
            SecurityException {
        //Make inputEvent null and we no longer need broadcast
        for (T subscribedElement : mySubscribedElements) {
            Method method =
                    subscribedElement.getClass().getMethod(methodName, inputEvent.getClass());
            System.out.println(method);
            method.invoke(subscribedElement, inputEvent);
        }
    }

    /**
     * @param actionID
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     */
    private void invokeMethod (int actionID) throws IllegalAccessException,
    IllegalArgumentException, InvocationTargetException {
        BoolTuple<Object, Method> retrieveTuple = myMenuPlate.get(actionID);
        if (retrieveTuple != null && retrieveTuple.isActive()) {
            retrieveTuple.getLast().invoke(retrieveTuple.getFirst(), new Object[0]);
        }
    }

    @SuppressWarnings("rawtypes")
    private Method retrieveMethod(Object o, String method)
            throws NoSuchMethodException,IllegalAccessException{
        Class oc = o.getClass();
        Method[] allMethods = oc.getMethods();
        for (Method m : allMethods) {
            if (m.getName().equals(method)) { return m; }
        }
        accessLegalityCheck(o, method);
        throw new NoSuchMethodException();
    }


    @SuppressWarnings("rawtypes")
    private Method retrieveMethod(Class c, String method)
            throws NoSuchMethodException, IllegalAccessException,
            InstantiationException{
        for (Method m : c.getMethods()) {
            if (m.getName().equals(method) && Modifier.isStatic(m.getModifiers())) { return m; }
        }
        accessLegalityCheck(c, method);
        instantiationLegalityCheck(c, method);
        throw new NoSuchMethodException();
    }

    private void accessLegalityCheck (Object o, String method) 
            throws IllegalAccessException {
        Class oc = o.getClass();
        Method[] allMethods = oc.getDeclaredMethods();
        for (Method m : allMethods) {
            if (!m.isAccessible()) {
                throw new IllegalAccessException();
            }
        }
    }
    
    private void instantiationLegalityCheck (Class c, String method) 
            throws InstantiationException{
        if (Modifier.ABSTRACT==c.getModifiers()|| Modifier.ABSTRACT==Modifier.INTERFACE){
            throw new InstantiationException();
        }
    }
}
