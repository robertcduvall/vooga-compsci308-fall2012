package util.gui;

import java.util.HashMap;
import java.util.Map;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;


/**
 * Allows for the creation of a JOptionPane with any number of input fields of
 * type JComboBox and JTextField.
 * 
 * This class is intended to be reusable beyond the scope of the GEDIVA project.
 * 
 * @author geo4
 * 
 * @param <K> key to reference input fields for retrieval of their contents
 */
public class MultiFieldJOptionPane<K> {

    private static final String DEFAULT_TITLE = "Select Options";

    private JFrame myParentFrame;
    private JPanel myPanel;
    private Map<K, JComponent> myFields;
    private Map<K, String> myResults;
    private String myTitle = DEFAULT_TITLE;

    /**
     * Creates a new instance using the specified parent frame.
     * 
     * @param frame parent frame. Null is allowed.
     */
    public MultiFieldJOptionPane (JFrame frame) {
        initialize(frame);
    }

    /**
     * Creates a new instance using the specified parent frame.
     * 
     * @param frame parent frame. Null is allowed.
     * @param title title of the JOptionPane
     */
    public MultiFieldJOptionPane (JFrame frame, String title) {
        myTitle = title;
        initialize(frame);
    }

    private void initialize (JFrame frame) {
        myParentFrame = frame;
        myPanel = new JPanel();
        myFields = new HashMap<K, JComponent>();
        myResults = new HashMap<K, String>();
    }

    /**
     * Adds a combobox to the JOptionPane prior to display.
     * 
     * @param key reference for this combobox
     * @param label label to place in front of the combobox
     * @param box drop down menu to be added to the JOptionPane
     */
    public void addField (K key, String label, JComboBox box) {
        addFieldToPanel(key, label, box);
    }

    /**
     * Adds a text field to the JOptionPane prior to display.
     * 
     * @param key reference for this text field
     * @param label label to place in front of the text field
     * @param textField text field to be added to the JOptionPane
     */
    public void addField (K key, String label, JTextField textField) {
        addFieldToPanel(key, label, textField);
    }

    private void addFieldToPanel (K key, String label, JComponent component) {
        myFields.put(key, component);
        myPanel.add(new JLabel(label));
        myPanel.add(component);
    }

    /**
     * Displays a JOptionPane with the fields that have been added. The results
     * can be returned using getResult(K key).
     */
    public void display () {
        int result =
                JOptionPane.showConfirmDialog(myParentFrame, myPanel, myTitle,
                                              JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            for (K key : myFields.keySet()) {
                JComponent component = myFields.get(key);
                if (component instanceof JComboBox) {
                    myResults.put(key, ((JComboBox) component).getSelectedItem().toString());
                }
                else if (component instanceof JTextField) {
                    myResults.put(key, ((JTextField) component).getText());
                }
            }
        }
    }

    /**
     * Returns the value input by the user for the specified field.
     * 
     * @param key reference value that was associated with the field when it was
     *        added
     * @return the contents of the field as a String
     */
    public String getResult (K key) {
        if (myResults.containsKey(key)) {
            return myResults.get(key);
        }
        else {
            return null;
        }
    }
}

