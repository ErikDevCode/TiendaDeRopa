package logica;

import javax.swing.text.*;

public class MaxLengthDocumentFilter extends DocumentFilter {
    private final int max;

    public MaxLengthDocumentFilter(int max) {
        this.max = max;
    }

    @Override
    public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
        if ((fb.getDocument().getLength() + string.length()) <= max) {
            super.insertString(fb, offset, string, attr);
        }
    }

    @Override
    public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
        if ((fb.getDocument().getLength() - length + text.length()) <= max) {
            super.replace(fb, offset, length, text, attrs);
        }
    }
}
