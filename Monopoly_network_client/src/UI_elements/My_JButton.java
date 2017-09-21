package UI_elements;

import java.awt.Font;

import javax.swing.JButton;

import utils.Resources;

@SuppressWarnings("serial")
public class My_JButton extends JButton {

	/**
	 * Constructeur de la classe qui représente un bouton personnalisé de notre
	 * application. Celui ci utilise une font spécial et n'affiche pas certains
	 * elements comme le fond et les bords
	 * 
	 * @param text
	 *            le texte de notre JButton personnalisé
	 * @param font_size
	 *            la taille de la police de notre JButton personnalisé
	 */
	public My_JButton(String text, float font_size) {
		super(text);
		this.setFont(Resources.get_font("kabel.ttf").deriveFont(Font.BOLD, font_size));
		this.setHorizontalAlignment(JButton.CENTER);
		this.setVerticalAlignment(JButton.CENTER);
		this.setFocusPainted(false);
		this.setBorderPainted(false);
		this.setContentAreaFilled(false);
	}
}
