package mvc_game_model_cards;

import mvc_game_enums.Card_type;

public class Go_jail_card extends Abstract_card {

	/**
	 * Constructeur de la carte qui reprséente la carte qui envoie en prison
	 * 
	 * @param description
	 *            la descriptin de la carte
	 * @param type
	 *            le type de la carte
	 */
	public Go_jail_card(String description, Card_type type) {
		super(description, type);
	}
}
