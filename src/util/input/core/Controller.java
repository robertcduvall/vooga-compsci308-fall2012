package util.input.core;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import util.input.exceptions.InvalidControllerActionException;
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
    Controller() {
        mySubscribedElements = new ArrayList<T>();
        myMenuPlate = new HashMap<Integer, BoolTuple<Object, Method>>();
    }

    /**
     * Create a new Controller with an elements that
     * subscribes to its raw data.
     *
     * @param element - The subscribing element
     */
    Controller(T element) {
        this();
        subscribe(element);
    }

    /**
     * Subscribes a class to this controller's events.
     *
     * @param element - The subscribing class
     */
    public void subscribe(T element) {
        mySubscribedElements.add(element);
    }

    /**
     * Object invokes a method every time action and type occur.
     *
     * @param action - The button to listen for
     * @param type - Pressed or released
     * @param o - The invoking object
     * @param method - The method to be invoked
     * @throws NoSuchMethodException
     */
    public void setControl(int action, int type, Object o, String method)
            throws NoSuchMethodException {
        // InvalidControllerType, InvalidControllerActionException {
        Method m = retrieveMethod(o, method);
        // actionValidate(action);//make sure to check for codify decodify
        // typeValidate(type);
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
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    @SuppressWarnings("rawtypes")
    public void setControl(int action, int type, Class c, String method)
            throws NoSuchMethodException, IllegalAccessException,
            InstantiationException {
        // InvalidControllerType, InvalidControllerActionException {

        Method m = retrieveMethod(c, method);
        // actionValidate(action);//make sure to check for codify decodify
        // typeValidate(type);
        myMenuPlate.put(UKeyCode.codify(type, action),
                new BoolTuple<Object, Method>(c, m));
    }

    // protected abstract void typeValidate(int type) throws
    // InvalidControllerType;

    // public abstract void actionValidate(int action) throws
    // InvalidControllerActionException;

    /**
     * Set the desired action on or off.
     *
     * @param action - The controller button/key to listen for
     * @param type - Pressed or released
     * @param isActive - Whether the action should be active or not
     * @throws InvalidControllerActionException
     */
    public void setActionActive(int action, int type, boolean isActive)
            throws InvalidControllerActionException {
        // actionValidate(action);
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
    protected void performReflections(Object inputEvent, String method,
            int actionID) throws IllegalAccessException,
            InvocationTargetException, NoSuchMethodException {
        broadcastToSubscribers(method, inputEvent);
        invokeMethod(actionID);
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
    private void broadcastToSubscribers(String methodName, Object inputEvent)
            throws IllegalAccessException, IllegalArgumentException,
            InvocationTargetException, NoSuchMethodException, SecurityException {
        for (T subscribedElement : mySubscribedElements) {
            Method method = subscribedElement.getClass().getMethod(
                    methodName, inputEvent.getClass());
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
    private void invokeMethod(int actionID) throws IllegalAccessException,
            IllegalArgumentException, InvocationTargetException {
        BoolTuple<Object, Method> retrieveTuple = myMenuPlate.get(actionID);
        if (retrieveTuple != null && retrieveTuple.isActive()) {
            retrieveTuple.getLast().invoke(retrieveTuple.getFirst(),
                    new Object[0]);
        }
    }

    @SuppressWarnings("rawtypes")
    private Method retrieveMethod(Object o, String method)
            throws NoSuchMethodException {
        Class oc = o.getClass();
        Method[] allMethods = oc.getDeclaredMethods();
        for (Method m : allMethods) {
            if (m.getName().equals(method)) {
                return m;
            }
        }
        throw new NoSuchMethodException();
    }

    @SuppressWarnings("rawtypes")
    private Method retrieveMethod(Class c, String method)
            throws NoSuchMethodException {
        for (Method m : c.getMethods()) {
            if (m.getName().equals(method)
                    && Modifier.isStatic(m.getModifiers())) {
                return m;
            }
        }
        throw new NoSuchMethodException();
    }
}
