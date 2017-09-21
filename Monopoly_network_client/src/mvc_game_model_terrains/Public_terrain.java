package mvc_game_model_terrains;

import mvc_game_enums.Public_type;
import mvc_game_enums.Terrain_type;

public class Public_terrain extends Abstract_terrain {

	private int cost;
	private Public_type public_type;

	/**
	 * Constructeur e la classe qui représente les terrains publiques du plateau
	 * 
	 * @param name
	 *            le nom du terain
	 * @param num
	 *            le numéro du terrain
	 * @param cost
	 *            le prix du terrain
	 * @param type
	 *            le type du terrain public
	 */
	public Public_terrain(String name, int num, int cost, Public_type type) {
		super(Terrain_type.PUBLIC, name, num);
		this.cost = cost;
		this.public_type = type;
	}

	/**
	 * Méthode permettant de connaitre le cout des impots
	 * 
	 * @return le cout a payer
	 */
	public int get_cost() {
		return this.cost;
	}

	/**
	 * Méthode permettant de connaitre le type de terrain public du terrain
	 * 
	 * @return le type de terrain
	 */
	public Public_type get_public_type() {
		return this.public_type;
	}
}
