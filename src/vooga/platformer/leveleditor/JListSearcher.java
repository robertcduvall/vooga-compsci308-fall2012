package vooga.platformer.leveleditor;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Stack;
import java.util.Vector;
import javax.swing.JList;
import javax.swing.JTextField;


/**
 * This class allows a user to enter text that will, in real time
 * match items in a JList. Any items that match the user entered
 * text will remain available to the user in the list. All items
 * that do not match the user input text will be filtered out. To 
 * use this class, create a JList. Then, instantiate this class.
 * Since this class extends JTextField, it will appear as a text
 * box in your display.
 * 
 * @author Paul Dannenberg
 * 
 */
public class JListSearcher extends JTextField {

    private static final long serialVersionUID = -9110342035216876384L;
    private Object[] myOriginalListItems;
    private JList myListToSearch;
    private String myOriginalText;

    /**
     * Creates a JListSearcher.
     * 
     * @param text The initial text that should occupy the
     *        textbox until the user deletes it.
     * @param listToSearch A list that contains the items to
     *        search over. This list should already be populated
     *        when it is passed to this constructor.
     */
    public JListSearcher(String text, JList listToSearch) {
        super(text);
        myOriginalText = text;
        myOriginalListItems = getItems(listToSearch);
        myListToSearch = listToSearch;
        setKeyListener();
    }

    /**
     * Finds all the items contained by a JList.
     * 
     * @param list The JList whose items will be
     *        returned.
     * @return All the items in the list parameter.
     */
    private Object[] getItems(JList list) {
        Object[] items = new Object[list.getModel().getSize()];
        for (int i = 0; i < list.getModel().getSize(); i++) {
            items[i] = list.getModel().getElementAt(i);
        }
        return items;
    }

    /**
     * Returns all items in this objects JList that match
     * the parameter text.
     * 
     * @param text The parameter with which to match the
     *        JList's items.
     * @return A Vector containing all matching JList items.
     *         (A Vector is used since JLists take Vector's in their
     *         constructors.)
     */
    private Vector<Object> match(String text) {
        Vector<Object> items = new Stack<Object>();
        for (Object myOriginalListItem : myOriginalListItems) {
            String listEntry = (String) myOriginalListItem;
            if (listEntry.startsWith(text)) {
                items.add(myOriginalListItem);
            }
        }
        return items;
    }

    /**
     * Updates the ongoing search. This method is called
     * whenever a key is entered into the text field.
     * 
     * @param inputText The user input text.
     */
    private void refreshList(String inputText) {
        if (!myOriginalText.equals(inputText)) {
            myListToSearch.setListData(match(inputText));
        }
    }

    /**
     * Creates a key listener that will respond to
     * changes in the user's input to the text box.
     */
    private void setKeyListener() {
        super.addKeyListener(new KeyListener() {

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
                refreshList(getText());
            }

            @Override
            public void keyTyped(KeyEvent e) {
            }

        });
    }

}
