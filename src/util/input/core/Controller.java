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
 * Created with IntelliJ IDEA.
 * User: lance
 * Date: 11/3/12
 * Time: 7:42 PM
 * To change this template use File | Settings | File Templates.
 * 
 * @author Lance, Amay
 */
public abstract class Controller<T> {

    List<T> subscribedElements;
    Map<String, String> objectMethodMap;
    Map<Integer, BoolTuple<Object, Method>> menuPlate;

    Controller () {
        objectMethodMap = new HashMap<String, String>();
        subscribedElements = new ArrayList<T>();
        menuPlate = new HashMap<Integer, BoolTuple<Object, Method>>();
    }

    Controller (T element) {
        this();
        subscribe(element);
    }

    /**
     * Subscribes a class to this controller's events
     */
    public void subscribe (T element) {
        subscribedElements.add(element);
    }

    /**
     * Object invokes a method every time action occurs
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

    // Sets the action on or off
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
