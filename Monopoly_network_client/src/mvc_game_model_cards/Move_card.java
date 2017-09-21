package mvc_game_model_cards;

import mvc_game_enums.Card_type;

public class Move_card extends Abstract_card {

	private int position_to_move;

	/**
	 * Constructeur de la classe qui représente les mouvements demandé via les
	 * cartes
	 * 
	 * @param description
	 *            la description de la carte
	 * @param type
	 *            le type de la carte
	 */
	public Move_card(String description, Card_type type, int position) {
		super(description, type);
		this.position_to_move = position;
	}

	/**
	 * Méthode permettant de savoir vers quelle case le joueur doit se diriger
	 * 
	 * @return le numéro de la maison ou le joueur doit se déplacer
	 */
	public int get_position_to_move() {
		return this.position_to_move;
	}
}
