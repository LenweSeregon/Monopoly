package mvc_game_model_terrains;

import mvc_game_enums.Terrain_type;

public class Parking_terrain extends Abstract_terrain {

	/**
	 * Constructeur de la classe qui représente la case parking du terrain
	 * 
	 * @param name
	 *            le nom du terrain
	 * @param num
	 *            le numéro du terrain
	 */
	public Parking_terrain(String name, int num) {
		super(Terrain_type.PARKING, name, num);
	}
}
