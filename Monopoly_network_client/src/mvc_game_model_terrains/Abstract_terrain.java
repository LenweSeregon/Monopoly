package mvc_game_model_terrains;

import mvc_game_enums.Terrain_type;

public abstract class Abstract_terrain {

	protected int number;
	protected String name;
	protected Terrain_type type;

	/**
	 * Constructeur de la classe abstraite représentant un terrain. En effet
	 * tout les terrains disposent d'un nom, d'un type et d'un numéro qui
	 * représente la position sur le plateau
	 * 
	 * @param type
	 *            le type du terrain
	 * @param name
	 *            le nom du terrain
	 * @param num
	 *            le numéro du terrain
	 */
	public Abstract_terrain(Terrain_type type, String name, int num) {

		this.name = name;
		this.number = num;
		this.type = type;

	}

	/**
	 * Méthode permettant de connaitre le type du terrain
	 * 
	 * @return enumeration qui représente le type du terrain
	 */
	public Terrain_type get_type() {
		return this.type;
	}

	/**
	 * Méthode permettant de connaitre le nom du terrain
	 * 
	 * @return le nom du terrain
	 */
	public String get_name() {
		return this.name;
	}

	/**
	 * Méthode permettant de connaitre le numéro du terrain
	 * 
	 * @return le nom du terrain
	 */
	public int get_number() {
		return this.number;
	}
}
