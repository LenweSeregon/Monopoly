package mvc_game_model_terrains;

import mvc_game_enums.Color_terrain;
import mvc_game_enums.Terrain_type;

public class Property_terrain extends Buyable_terrain {

	private Color_terrain color;
	private int nb_house;
	private int[] rents;
	private int house_price;

	/**
	 * Constructeur de la classe qui représente une propriété sur le plateau
	 * 
	 * @param name
	 *            le nom du terrain
	 * @param num
	 *            le numéro du terrain
	 * @param price
	 *            le prix du terrain
	 * @param house_price
	 *            le prix des maisons du terrain
	 * @param rents
	 *            les loyers du terrain
	 * @param color
	 *            la couleur du terrain
	 */
	public Property_terrain(String name, int num, int price, int house_price, int[] rents, Color_terrain color) {
		super(Terrain_type.PROPERTY, name, num, price);

		this.color = color;
		this.nb_house = 0;
		this.house_price = house_price;
		this.rents = new int[rents.length];
		for (int i = 0; i < rents.length; i++) {
			this.rents[i] = rents[i];
		}
	}

	/**
	 * Méthode permettant de choisir le nombe de maison sur le terrain
	 * 
	 * @param nb
	 *            le nombre de maison
	 */
	public void set_nb_house(int nb) {
		this.nb_house = nb;
	}

	/**
	 * Méthode permettant de connaitre le nombre de maison sur le terrain
	 * 
	 * @return le nombre de maison sur le terrain
	 */
	public int get_nb_house() {
		return this.nb_house;
	}

	/**
	 * Méthode permettant de connaitre le prix d'une maison pour ce terrain
	 * 
	 * @return le prix d'un maison
	 */
	public int get_house_price() {
		return this.house_price;
	}

	/**
	 * Méthode permettant de connaitre la ca couleur du terrain
	 * 
	 * @return l'enumeration qui représente la couleur du terrain
	 */
	public Color_terrain get_color() {
		return this.color;
	}

	/**
	 * Méthode permettant de récupérer un tableau de tout les loyers du terrain
	 * en fonction des maisons
	 * 
	 * @return un tableau des différents loyer
	 */
	public int[] get_rents() {
		return this.rents;
	}

	@Override
	public int get_rent() {
		return this.rents[nb_house];
	}

}
