package utils;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

@SuppressWarnings("serial")
public class Max_length_text_document extends PlainDocument {
	// Store maximum characters permitted
	private int max_chars;

	@Override
	public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
		if (str != null && (getLength() + str.length() < max_chars)) {
			super.insertString(offs, str, a);
		}
	}

	public void set_max_chars(int max) {
		max_chars = max;
	}
}