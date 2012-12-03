package vooga.shooter.level_editor;

import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;

/**
 * JTextField that only takes numeric input.
 * @author Zachary Hopping
 *
 */

public class NumericJTextField extends JTextField {
    
    public NumericJTextField(int cols) {
        super(cols);
    }

    protected Document createDefaultModel() {
        return new NumericDocument();
    }

    static class NumericDocument extends PlainDocument {

        public void insertString(int offs, String str, AttributeSet a) 
            throws BadLocationException {

            if (str == null) {
                return;
            }
            char[] numbers = str.toCharArray();
            String newString = "";
            for (int i = 0; i < numbers.length; i++) {
                if(Character.isDigit(numbers[i])) {
                    newString = newString + numbers[i];
                }
            }
            super.insertString(offs, newString, a);
        }
    }
}
