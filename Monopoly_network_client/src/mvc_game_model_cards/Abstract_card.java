package mvc_game_model_cards;

import mvc_game_enums.Card_type;

public abstract class Abstract_card {

	protected String description;
	protected Card_type type;

	/**
	 * Constructeur de la classe qui représente une carte abstraite dans les
	 * paquets de carte
	 * 
	 * @param description
	 */
	public Abstract_card(String description, Card_type type) {
		this.description = description;
		this.type = type;
	}

	/**
	 * Une méthode permettant de récupérer la description d'une carte
	 * 
	 * @return la description de la carte
	 */
	public String get_description() {
		return this.description;
	}

	/**
	 * Méthode permettant de récupérer le type d'une carte
	 * 
	 * @return le type de la carte
	 */
	public Card_type get_card_type() {
		return this.type;
	}
}
