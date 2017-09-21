package utils;

public class String_swing {
	private int pos_x;
	private int pos_y;
	private String string;

	/**
	 * Constructeur de la classe qui représente un string splitté avec la
	 * chaine, sa position X et s aposition Y
	 * 
	 * @param s
	 *            la string
	 * @param x
	 *            la position X
	 * @param y
	 *            la position Y
	 */
	public String_swing(String s, int x, int y) {
		this.pos_x = x;
		this.pos_y = y;
		this.string = s;
	}

	/**
	 * Méthode permettant de récupérer la string
	 * 
	 * @return la string de la chaine splitté
	 */
	public String get_string() {
		return string;
	}

	/**
	 * Méthode permettant de récupérer la position X de la chaine
	 * 
	 * @return la position X de la chaine
	 */
	public int get_x() {
		return pos_x;
	}

	/**
	 * Méthode permettant de récupérer la position Y de la chaine
	 * 
	 * @return la position Y de la chaine
	 */
	public int get_y() {
		return pos_y;
	}
}
