package mvc_game_model_cards;

import mvc_game_enums.Card_type;
import mvc_game_enums.Earn_card_type;

public class Earn_money_card extends Abstract_card {

	protected int sum_earn;
	protected Earn_card_type type_earn;

	public Earn_money_card(String description, Card_type type, int sum, Earn_card_type earn) {
		super(description, type);
		this.sum_earn = sum;
		this.type_earn = earn;
	}

	/**
	 * Méthode permettant de savoir combien la carte permet de gagner d'argent
	 * 
	 * @return la somme a gagner
	 */
	public int get_sum_earn() {
		return this.sum_earn;
	}

	/**
	 * Méthode permettant de savoir le type de gagne d'argent de la carte
	 * 
	 * @return le type de gagner l'argent
	 */
	public Earn_card_type get_type_earn() {
		return this.type_earn;
	}
}
