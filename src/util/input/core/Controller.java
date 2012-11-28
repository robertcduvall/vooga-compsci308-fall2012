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

    protected static final int NO_ACTION = -1;
    private List<T> mySubscribedElements;
    private DataTable myDataTable;

    /**
     * Create a new Controller.
     */
    public Controller () {
        mySubscribedElements = new ArrayList<T>();
        myDataTable = new DataTable();

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
     *         string method passed in is not a method of Object o
     * @throws IllegalAccessException -" thrown when an
     *         application tries to reflectively create an instance (other than
     *         an array),
     *         set or get a field, or invoke a method, but the currently
     *         executing method does not have access to the definition of
     *         the specified class, field, method or constructor"
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
    public void setControl (int action, int type, Object o, String method, String describeButton,
                            String describeAction) throws NoSuchMethodException,
                                                  IllegalAccessException {

        Method m;
        m = retrieveMethod(o, method);

        Map<String, Object> dataIn = new HashMap<String, Object>();
        dataIn.put("Button Description", describeButton);
        dataIn.put("Action Description", describeAction);
        dataIn.put("KeyCode", UKeyCode.codify(type, action));
        dataIn.put("Tuple", new BoolTuple<Object, Method>(o, m));

        myDataTable.addNewRowEntry(dataIn);

        myDataTable.viewContents();
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

        Map<String, Object> dataIn = new HashMap<String, Object>();
        dataIn.put("Button Description", describeButton);
        dataIn.put("Action Description", describeButton);
        dataIn.put("KeyCode", UKeyCode.codify(type, action));
        dataIn.put("Tuple", new BoolTuple<Object, Method>(c, m));
    }

    /**
     * Set the desired action on or off.
     * 
     * @param action - The controller button/key to listen for
     * @param type - Pressed or released
     */
    @SuppressWarnings("unchecked")
    public void activateAction (int action, int type) {
        BoolTuple<Object, Method> rowElement = getObjectMethodPair(action, type);
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
        BoolTuple<Object, Method> rowElement = getObjectMethodPair(action, type);
        rowElement.deactivate();
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

    protected void performReflections (String method, int actionID) throws IllegalAccessException,
                                                                   InvocationTargetException,
                                                                   NoSuchMethodException {
        performReflections(null, method, actionID);
    }

    
    
    // PRIVATE METHODS
    
    @SuppressWarnings("unchecked")
    private BoolTuple<Object, Method> getObjectMethodPair (int action, int type) {
        UnmodifiableRowElement r = myDataTable.find("KeyCode", UKeyCode.codify(type, action));
        BoolTuple<Object, Method> rowElement = (BoolTuple<Object, Method>) r.getEntry("Tuple");
        return rowElement;
    }
    
    
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
    @SuppressWarnings("unchecked")
    private void invokeMethod (int actionID) throws IllegalAccessException,
                                            IllegalArgumentException, InvocationTargetException {
        UnmodifiableRowElement r = myDataTable.find("KeyCode", actionID);

        if (r != null) {

            BoolTuple<Object, Method> retrieveTuple =
                    (BoolTuple<Object, Method>) r.getEntry("Tuple");

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
            myDataTable.addNewColumn("Button Description,Action Description,KeyCode,Tuple");
        }
        catch (RepeatedColumnNameException e) {
            e.printStackTrace();
        }
        catch (InvalidXMLTagException e){
            e.printStackTrace();
        }
    }
}
