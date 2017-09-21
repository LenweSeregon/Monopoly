package mvc_game_model_terrains;

import mvc_game_enums.Terrain_type;

public class Station_terrain extends Buyable_terrain {

	private int rent;

	/**
	 * Constructeur de la classe représentant lese gares
	 * 
	 * @param name
	 *            le nom du terrain
	 * @param num
	 *            le numéro du terrain
	 * @param price
	 *            le prix du terrain
	 * @param rent
	 *            le loyer du trrain
	 */
	public Station_terrain(String name, int num, int price, int rent) {
		super(Terrain_type.STATION, name, num, price);
		this.rent = rent;
	}

	@Override
	public int get_rent() {
		return this.rent;
	}

}
