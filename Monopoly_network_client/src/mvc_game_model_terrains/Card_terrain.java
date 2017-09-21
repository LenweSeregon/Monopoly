package mvc_game_model_terrains;

import mvc_game_enums.Card_type;
import mvc_game_enums.Terrain_type;

public class Card_terrain extends Abstract_terrain {

	private Card_type card_type;

	/**
	 * Constructeur de la classe représentant les terrains qui sont des cartes
	 * 
	 * @param name
	 *            le nom du terrain
	 * @param num
	 *            le numéro du terrain
	 * @param card_type
	 *            le type de la carte
	 */
	public Card_terrain(String name, int num, Card_type card_type) {
		super(Terrain_type.CARDS, name, num);
		this.card_type = card_type;
	}

	/**
	 * Méthode permettant de savoir le type dune carte entre chance et
	 * communauté
	 * 
	 * @return le type de la carte
	 */
	public Card_type get_card_type() {
		return this.card_type;
	}

}
