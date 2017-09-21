package mvc_game_model_cards;

import mvc_game_enums.Card_type;

public class Steal_card extends Abstract_card {

	/**
	 * Constructeur de la carte qui représente la carte vole d'un carte à
	 * l'adversaire
	 * 
	 * @param description
	 *            la description de la carte
	 * @param type
	 *            le type de la carte
	 */
	public Steal_card(String description, Card_type type) {
		super(description, type);
	}

}
