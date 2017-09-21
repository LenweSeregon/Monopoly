package mvc_game_model_terrains;

import mvc_game_enums.Terrain_type;

public class Go_jail_terrain extends Abstract_terrain {

	/**
	 * Constructur de la classe qui représente la case aller en prison
	 * 
	 * @param name
	 *            le nom du terrain
	 * @param num
	 *            le numéro du terrain
	 */
	public Go_jail_terrain(String name, int num) {
		super(Terrain_type.GO_JAIL, name, num);
	}
}
