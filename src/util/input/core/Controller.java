package util.input.core;

import util.input.input_utils.BoolTuple;
import util.input.input_utils.UKeyCode;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;
import util.input.exceptions.InvalidControllerActionException;

/**
 * This class represents an abstract controller to provide input.
 * 
 * @author Amay, Lance
 *
 * @param <T>
 */
public abstract class Controller<T> {

    List<T> subscribedElements;
    Map<String, String> objectMethodMap;
    Map<Integer, BoolTuple<Object, Method>> menuPlate;

    /**
     * Create a new Controller.
     */
    Controller () {
        objectMethodMap = new HashMap<String, String>();
        subscribedElements = new ArrayList<T>();
        menuPlate = new HashMap<Integer, BoolTuple<Object, Method>>();
    }

    /**
     * Create a new Controller with an elements that
     * subscribes to its raw data.
     * 
     * @param element - The subscribing element
     */
    Controller (T element) {
        this();
        subscribe(element);
    }

    /**
     * Subscribes a class to this controller's events
     *      
     * @param element - The subscribing class
     */
    public void subscribe (T element) {
        subscribedElements.add(element);
    }

    /**
     * Object invokes a method every time action and type occur
     * 
     * @param action - The button to listen for
     * @param type - Pressed or released
     * @param o - The invoking object
     * @param method - The method to be invoked
     * @throws NoSuchMethodException
     */
    public void setControl (int action, int type, Object o, String method)
            throws NoSuchMethodException {
        // InvalidControllerType, InvalidControllerActionException {
        Method m = retrieveMethod(o, method);
        // actionValidate(action);//make sure to check for codify decodify
        // typeValidate(type);
        menuPlate.put(UKeyCode.codify(type, action),
                new BoolTuple<Object, Method>(o, m));
    }

    /**
     * Class invokes a static method every time action and type occur
     * 
     * @param action - The controller button/key to listen for
     * @param type - Pressed or released
     * @param c - The invoking Class
     * @param method - The static method to be invoked
     * @throws NoSuchMethodException     
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public void setControl (int action, int type, Class c, String method)
            throws NoSuchMethodException, IllegalAccessException,
            InstantiationException {
        // InvalidControllerType, InvalidControllerActionException {

        Method m = retrieveMethod(c, method);
        // actionValidate(action);//make sure to check for codify decodify
        // typeValidate(type);
        menuPlate.put(UKeyCode.codify(type, action),
                new BoolTuple<Object, Method>(c, m));
    }

    // protected abstract void typeValidate(int type) throws
    // InvalidControllerType;

    // public abstract void actionValidate(int action) throws
    // InvalidControllerActionException;

    
    /**
     * Set the desired action on or off
     * 
     * @param action - The controller button/key to listen for
     * @param type - Pressed or released
     * @param isActive - Whether the action should be active or not
     * @throws InvalidControllerActionException
     */
    public void setActionActive (int action, int type, boolean isActive)
            throws InvalidControllerActionException {
        // actionValidate(action);
        if(isActive) menuPlate.get(UKeyCode.codify(type, action)).activate();
        else menuPlate.get(UKeyCode.codify(type, action)).deactivate();
    }
    
    
    /**
     * @param e
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     */
    protected void performReflections (Object inputEvent, String method, int actionID) throws IllegalAccessException,
            InvocationTargetException, NoSuchMethodException {
        broadcastToSubscribers(method, inputEvent);
        invokeMethod(actionID);
    }

    // PRIVATE METHODS
    /**
     * broadcasts the method to all subscribed elements
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
            throws IllegalAccessException, IllegalArgumentException,
            InvocationTargetException, NoSuchMethodException, SecurityException {
        Method method = this.getClass().getMethod(methodName,
                inputEvent.getClass());
        for (T subscribedElement : subscribedElements) {
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
        BoolTuple<Object, Method> retrieveTuple = menuPlate.get(actionID);
        if (retrieveTuple != null && retrieveTuple.isActive()) {
            retrieveTuple.getLast().invoke(retrieveTuple.getFirst(),
                    new Object[0]);
        }
    }
    
    private Method retrieveMethod (Object o, String method)
            throws NoSuchMethodException {
        Class oc = o.getClass();
        Method[] allMethods = oc.getDeclaredMethods();
        for (Method m : allMethods) {
            if (m.getName().equals(method)) {
                // ask TA return boolean or exception handle
                return m;
            }
        }
        throw new NoSuchMethodException();
    }

    private Method retrieveMethod (Class c, String method)
            throws NoSuchMethodException {
        for (Method m : c.getMethods()) {
            if (m.getName().equals(method)
                    && Modifier.isStatic(m.getModifiers())) { return m; }
        }
        throw new NoSuchMethodException();
    }
}
