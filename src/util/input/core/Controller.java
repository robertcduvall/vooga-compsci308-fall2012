package util.input.core;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import util.datatable.DataTable;
import util.datatable.UnmodifiableRowElement;
import util.datatable.exceptions.InvalidXMLTagException;
import util.datatable.exceptions.RepeatedColumnNameException;
import util.input.inputhelpers.FlagPair;
import util.input.inputhelpers.UKeyCode;


/**
 * This class represents an abstract controller to provide input.
 * 
 * @author Amay, Lance
 * 
 * @param <T>
 */
public abstract class Controller<T> {

    protected static final int NO_ACTION = -1;
    private static final int NUM_COLUMNS = 4;
    private static final String BUTTON_DESCRIPTION = "Button Description";
    private static final String ACTION_DESCRIPTION = "Action Description";
    private static final String KEYCODE = "KeyCode";
    private static final String TUPLE = "Tuple";

    private String[] myColumnName;

    private List<T> mySubscribedElements;
    private DataTable myDataTable;

    /**
     * Create a new Controller.
     */
    public Controller () {
        mySubscribedElements = new ArrayList<T>();
        myDataTable = new DataTable();
        myColumnName = new String[NUM_COLUMNS];
        myColumnName[0] = BUTTON_DESCRIPTION;
        myColumnName[1] = ACTION_DESCRIPTION;
        myColumnName[2] = KEYCODE;
        myColumnName[3] = TUPLE;
        createTable();
    }

    /**
     * Create a new Controller with an element that
     * subscribes to its raw data.
     * 
     * @param element - The subscribing element
     */
    public Controller (T element) {
        this();
        subscribe(element);
    }

    /**
     * Subscribes a class to this controller's events.
     * 
     * @param subscriber - The subscribing class
     */
    public void subscribe (T subscriber) {
        mySubscribedElements.add(subscriber);
    }

    /**
     * Unsubscribes a class to this controller's events.
     * 
     * @param unsubscriber - The unsubscribing class
     */
    public void unSubscribe (T unsubscriber) {
        mySubscribedElements.remove(unsubscriber);
    }

    /**
     * Object invokes a method every time action and type occur.
     * 
     * @param action - The button to listen for
     * @param type - Pressed or released
     * @param o - The invoking object
     * @param method - The method to be invoked
     * @throws NoSuchMethodException - thrown if the
     *         string method passed in is not a method of Object o
     * @throws IllegalAccessException -" thrown when an
     *         application tries to reflectively create an instance (other than
     *         an array),
     *         set or get a field, or invoke a method, but the currently
     *         executing method does not have access to the definition of
     *         the specified class, field, method or constructor"
     * @throws InstantiationException
     */
    public void setControl (int action, int type, Object o, String method)
                                                                          throws NoSuchMethodException,
                                                                          IllegalAccessException {
        setControl(action, type, o, method, null, null);
    }

    /**
     * Class invokes a static method every time action and type occur.
     * 
     * @param action - The controller button/key to listen for
     * @param type - Pressed or released
     * @param c - The invoking Class
     * @param method - The static method to be invoked
     * @throws NoSuchMethodException - thrown if the string method passed in is
     *         not a method of Object o
     * @throws InstantiationException- "thrown when an application tries to
     *         create an instance of a class
     *         using the newInstance method in class Class, but the specified
     *         class object cannot be instantiated because it is an
     *         interface or is an abstract class."
     * @throws IllegalAccessException -" thrown when an application tries to
     *         reflectively create an instance (other than an array),
     *         set or get a field, or invoke a method, but the currently
     *         executing method does not have access to the definition of
     *         the specified class, field, method or constructor"
     */
    @SuppressWarnings("rawtypes")
    public void setControl (int action, int type, Class c, String method)
                                                                         throws NoSuchMethodException,
                                                                         IllegalAccessException,
                                                                         InstantiationException {
        setControl(action, type, c, method, null, null);
    }

    /**
     * Object invokes a method every time action and type occur.
     * Inserts button description, action description, keycode, and
     * <object, method> tuple into DataTable
     * 
     * @param action - The button to listen for
     * @param type - Pressed or released
     * @param o - The invoking object
     * @param method - The method to be invoked
     * @param describeButton - Button description
     * @param describeAction - Action description invoked upon button
     * @throws NoSuchMethodException - thrown if the
     *         string method passed in is not a method of Object o
     * @throws IllegalAccessException -" thrown when an
     *         application tries to reflectively create an instance (other than
     *         an array),
     *         set or get a field, or invoke a method, but the currently
     *         executing method does not have access to the definition of
     *         the specified class, field, method or constructor"
     */
    @SuppressWarnings("rawtypes")
    public void setControl (int action, int type, Object o, String method, String describeButton,
                            String describeAction) throws NoSuchMethodException,
                                                  IllegalAccessException {
        Method m = retrieveMethod(o, method);

        addControlToTable(action, type, o, m, describeButton, describeAction);

    }

    /**
     * Class invokes a static method every time action and type occur.
     * Inserts button description, action description, keycode, and
     * <class, method> tuple into DataTable
     * 
     * @param action - The controller button/key to listen for
     * @param type - Pressed or released
     * @param c - The invoking Class
     * @param method - The static method to be invoked
     * @param describeButton - Button description
     * @param describeAction - Action description invoked upon button
     * @throws NoSuchMethodException - thrown if the string method passed in is
     *         not a method of Object o
     * @throws InstantiationException- "thrown when an application tries to
     *         create an instance of a class
     *         using the newInstance method in class Class, but the specified
     *         class object cannot be instantiated because it is an
     *         interface or is an abstract class."
     * @throws IllegalAccessException -" thrown when an application tries to
     *         reflectively create an instance (other than an array),
     *         set or get a field, or invoke a method, but the currently
     *         executing method does not have access to the definition of
     *         the specified class, field, method or constructor"
     */
    @SuppressWarnings("rawtypes")
    public void setControl (int action, int type, Class c, String method, String describeButton,
                            String describeAction) throws NoSuchMethodException,
                                                  IllegalAccessException, InstantiationException {
        Method m = retrieveMethod(c, method);
        addControlToTable(action, type, c, m, describeButton, describeAction);
    }

    /**
     * Set the desired action on or off.
     * 
     * @param action - The controller button/key to listen for
     * @param type - Pressed or released
     */
    @SuppressWarnings("unchecked")
    public void activateAction (int action, int type) {
        FlagPair<Object, Method> rowElement = getObjectMethodPair(action, type);
        rowElement.activate();
    }

    /**
     * Set the desired action on or off.
     * 
     * @param action - The controller button/key to listen for
     * @param type - Pressed or released
     */
    @SuppressWarnings("unchecked")
    public void deactivateAction (int action, int type) {
        FlagPair<Object, Method> rowElement = getObjectMethodPair(action, type);
        rowElement.deactivate();
    }

    // ****************************** PROTECTED METHODS
    // *************************************

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

    protected void performReflections (String method, int actionID) throws IllegalAccessException,
                                                                   InvocationTargetException,
                                                                   NoSuchMethodException {
        performReflections(null, method, actionID);
    }

    // ****************************************************************************************

    // ********************************* PRIVATE METHODS
    // **************************************

    private void addControlToTable (int action, int type, Object o, Method method,
                                    String describeButton, String describeAction) {
        Map<String, Object> dataIn = new HashMap<String, Object>();
        dataIn.put(TUPLE, new FlagPair<Object, Method>(o, method));
        insertInMap(dataIn, describeButton, describeAction, UKeyCode.codify(type, action));

        myDataTable.addNewRow(dataIn);
    }

    @SuppressWarnings("unchecked")
    private FlagPair<Object, Method> getObjectMethodPair (int action, int type) {
        UnmodifiableRowElement r = myDataTable.find("KeyCode", UKeyCode.codify(type, action));
        FlagPair<Object, Method> rowElement = (FlagPair<Object, Method>) r.getEntry("Tuple");
        return rowElement;
    }

    // broadcasts the method to all subscribed elements.
    @SuppressWarnings("rawtypes")
    private void broadcastToSubscribers (String methodName, Object inputEvent)
                                                                              throws IllegalAccessException,
                                                                              IllegalArgumentException,
                                                                              InvocationTargetException,
                                                                              NoSuchMethodException,
                                                                              SecurityException {
        // Make inputEvent null and we no longer need broadcast
        Class inputType = null;
        if (inputEvent != null) {
            inputType = inputEvent.getClass();
        }
        for (T subscribedElement : mySubscribedElements) {
            Method method = subscribedElement.getClass().getMethod(methodName, inputType);

            method.invoke(subscribedElement, inputEvent);
        }
    }

    // Invokes a method using an object based on the actionID
    @SuppressWarnings("unchecked")
    private void invokeMethod (int actionID) throws IllegalAccessException,
                                            IllegalArgumentException, InvocationTargetException {
        UnmodifiableRowElement r = myDataTable.find("KeyCode", actionID);

        if (r != null) {

            FlagPair<Object, Method> retrieveTuple = (FlagPair<Object, Method>) r.getEntry("Tuple");

            if (retrieveTuple != null && retrieveTuple.isActive()) {
                retrieveTuple.getLast().invoke(retrieveTuple.getFirst(), new Object[0]);
            }
        }
    }

    @SuppressWarnings("rawtypes")
    private Method retrieveMethod (Object o, String method) throws NoSuchMethodException,
                                                           IllegalAccessException {
        Class oc = o.getClass();
        Method[] allMethods = oc.getMethods();
        for (Method m : allMethods) {
            if (m.getName().equals(method)) { return m; }
        }
        accessLegalityCheck(o, method);
        throw new NoSuchMethodException();
    }

    @SuppressWarnings("rawtypes")
    private Method retrieveMethod (Class c, String method) throws NoSuchMethodException,
                                                          IllegalAccessException,
                                                          InstantiationException {
        for (Method m : c.getMethods()) {
            if (m.getName().equals(method) && Modifier.isStatic(m.getModifiers())) { return m; }
        }
        accessLegalityCheck(c, method);
        instantiationLegalityCheck(c, method);
        throw new NoSuchMethodException();
    }

    @SuppressWarnings("rawtypes")
    private void accessLegalityCheck (Object o, String method) throws IllegalAccessException {
        Class oc = o.getClass();
        Method[] allMethods = oc.getDeclaredMethods();
        for (Method m : allMethods) {
            if (!m.isAccessible()) { throw new IllegalAccessException(); }
        }
    }

    @SuppressWarnings("rawtypes")
    private void instantiationLegalityCheck (Class c, String method) throws InstantiationException {
        if (Modifier.ABSTRACT == c.getModifiers() || Modifier.ABSTRACT == Modifier.INTERFACE) { throw new InstantiationException(); }
    }

    private void createTable () {
        try {
            myDataTable.addNewColumns(myColumnName);
        }
        catch (RepeatedColumnNameException e) {
            e.printStackTrace();
        }
        catch (InvalidXMLTagException e) {
            e.printStackTrace();
        }
    }

    private void insertInMap (Map<String, Object> dataIn, String describeButton,
                              String describeAction, int keyCode) {
        dataIn.put(BUTTON_DESCRIPTION, describeButton);
        dataIn.put(ACTION_DESCRIPTION, describeAction);
        dataIn.put(KEYCODE, keyCode);
    }
}
