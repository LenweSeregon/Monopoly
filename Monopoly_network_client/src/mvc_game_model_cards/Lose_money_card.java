package mvc_game_model_cards;

import mvc_game_enums.Card_type;
import mvc_game_enums.Lose_card_type;

public class Lose_money_card extends Abstract_card {

	protected int sum_lose;
	protected int sum_lose_house;
	protected int sum_lose_hotel;
	protected Lose_card_type type_lose;

	/**
	 * Constructeur de la classe qui représente la perte d'argent via les carte
	 * en tant que perte simple
	 * 
	 * @param description
	 *            la description de la carte
	 * @param type
	 *            le type de la carte
	 * @param sum
	 *            la somme a perdre
	 */
	public Lose_money_card(String description, Card_type type, int sum) {
		super(description, type);
		this.sum_lose = sum;
		this.sum_lose_house = 0;
		this.sum_lose_hotel = 0;
		this.type_lose = Lose_card_type.SIMPLE_LOSE;
	}

	/**
	 * Constructeur de la classe qui représente la perte d'argent via les cartes
	 * en tant que perdre combinés de maisons et d'hotels
	 * 
	 * @param description
	 *            la description de la carte
	 * @param type
	 *            le type de la carte
	 * @param sum1
	 *            la somme des maisons
	 * @param sum2
	 *            la somme des hotels
	 */
	public Lose_money_card(String description, Card_type type, int sum1, int sum2) {
		super(description, type);
		this.sum_lose = 0;
		this.sum_lose_house = sum1;
		this.sum_lose_hotel = sum2;
		this.type_lose = Lose_card_type.HOUSE_HOTEL_LOSE;
	}

	/**
	 * Méthode permettant de savoir combien la carte permet de perdre d'argent
	 * 
	 * @return la somme a perdre
	 */
	public int get_sum_lose() {
		return this.sum_lose;
	}

	/**
	 * Méthode permettant de savoir combien coute les maisons en taxe
	 * 
	 * @return le prix de taxe des maisons
	 */
	public int get_house_lose() {
		return this.sum_lose_house;
	}

	/**
	 * Méthode permettant de savoir combien coute les hotel en taxe
	 * 
	 * @return le prix de taxe des hotels
	 */
	public int get_hotel_lose() {
		return this.sum_lose_hotel;
	}

	/**
	 * Méthode permettant de savoir le type de perdre d'argent de la carte
	 * 
	 * @return le type de perdre l'argent
	 */
	public Lose_card_type get_type_earn() {
		return this.type_lose;
	}
}
