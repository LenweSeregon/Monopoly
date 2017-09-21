package utils;

import java.awt.Canvas;
import java.awt.Font;
import java.util.ArrayList;

public class String_splitter {

	/**
	 * Méthode permettant de connaitre la largeur d'un string en fonction de sa
	 * font
	 * 
	 * @param s
	 *            la chaine de caractere a tester
	 * @param f
	 *            la font a utiliser
	 * @return la taille en pixel que prend la chaine en largeur
	 */
	private static int get_width_string(String s, Font f) {

		Canvas c = new Canvas();
		return c.getFontMetrics(f).stringWidth(s);
	}

	/**
	 * Méthode permettant de connaitre la hauteur d'un string en fonction de sa
	 * font
	 * 
	 * @param f
	 *            la font à utiliser
	 * @return la taille en pixel que prend la chaine en hauteur
	 */
	private static int get_height_font(Font f) {
		Canvas c = new Canvas();
		return c.getFontMetrics(f).getHeight();
	}

	/**
	 * Méthode permettant de splitter un string en fonction de la font pour que
	 * celui ci passe toujours dans la taille donné en argument
	 * 
	 * @param str
	 *            la chaine de caractere a splitter
	 * @param font
	 *            la font a utiliser
	 * @param max
	 *            la taille max en largeur
	 * @param width
	 *            la largeur effective que l'on veut tester
	 * @param pos_x
	 *            la position x ou l'on démarre
	 * @param pos_y
	 *            la position y ou l'on démarre
	 * @param order_y
	 *            dans quel ordre on souhaite mettre les string splitter
	 * @return
	 */
	public static ArrayList<String_swing> splitted_string(String str, Font font, int max, int width, int pos_x,
			int pos_y, int order_y) {

		ArrayList<String_swing> strings = new ArrayList<String_swing>();
		String[] splitted = str.split(" ");

		int y = pos_y;

		String current_string = "";
		int current_width = 0;
		for (String s : splitted) {
			int width_string = get_width_string(s + " ", font);
			if (current_width + width_string <= max) {
				current_string += s + " ";
				current_width += width_string;
			} else {
				int x = (width - current_width) / 2;
				String_swing to_add = new String_swing(current_string, x, y);
				strings.add(to_add);
				y = y + order_y * (get_height_font(font) + 5);
				current_string = s + " ";
				current_width = get_width_string(s + " ", font);

			}
		}

		if (!current_string.equals("")) {
			int x = (width - current_width) / 2;
			String_swing to_add = new String_swing(current_string, x, y);
			strings.add(to_add);
		}

		return strings;
	}
}
