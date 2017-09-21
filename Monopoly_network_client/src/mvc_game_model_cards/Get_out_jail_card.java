package mvc_game_model_cards;

import mvc_game_enums.Card_type;

public class Get_out_jail_card extends Abstract_card {

	/**
	 * Constructeur de la carte qui reprséente la carte qui fait sortir de
	 * prison
	 * 
	 * @param description
	 *            la descriptin de la carte
	 * @param type
	 *            le type de la carte
	 */
	public Get_out_jail_card(String description, Card_type type) {
		super(description, type);
	}
}
