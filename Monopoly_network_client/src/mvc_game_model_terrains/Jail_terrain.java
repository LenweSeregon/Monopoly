package mvc_game_model_terrains;

import mvc_game_enums.Terrain_type;

public class Jail_terrain extends Abstract_terrain {

	/**
	 * Constructeur de la classe qui représente la case prison du plateau
	 * 
	 * @param name
	 *            le nom du terrain
	 * @param num
	 *            le numéro du terrain
	 */
	public Jail_terrain(String name, int num) {
		super(Terrain_type.JAIL, name, num);
	}
}
