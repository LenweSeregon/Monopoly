package UI_elements;

import java.awt.Font;

import javax.swing.JLabel;

import utils.Resources;

@SuppressWarnings("serial")
public class My_JLabel extends JLabel {

	/**
	 * Constructeur de la classe représentant nos JLabel personnel pour
	 * l'application. Ce sont simplements des JLabel qui utilise la font de
	 * l'application et réalise un centrage vertical et horizontal
	 * 
	 * @param text
	 *            le texte que l'on met à notre jlabel personnalisé
	 * @param font_size
	 *            la taille de la font que l'on utilise
	 */
	public My_JLabel(String text, float font_size) {
		super(text);
		this.setFont(Resources.get_font("kabel.ttf").deriveFont(Font.BOLD,
				font_size));
		this.setVerticalAlignment(JLabel.CENTER);
		this.setHorizontalAlignment(JLabel.CENTER);
	}
}
