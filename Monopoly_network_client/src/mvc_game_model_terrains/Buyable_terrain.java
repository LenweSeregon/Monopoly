package mvc_game_model_terrains;

import mvc_game_enums.Terrain_type;

public abstract class Buyable_terrain extends Abstract_terrain {

	protected int buy_price;
	protected String owner;
	protected boolean mortgage_mod;

	/**
	 * Constructeur de la classe qui représente les types de terrain dans le
	 * plateau qui peuvent etre achetable. C'est à dire les propriétés, les
	 * gares et les compagnies qui sont des classes qui hérite
	 * 
	 * @param type
	 *            le type de terrain
	 * @param name
	 *            le nom du terrain
	 * @param num
	 *            le numéro du terrain
	 * @param buy_price
	 *            la valeur d'achat du terrain
	 */
	public Buyable_terrain(Terrain_type type, String name, int num, int buy_price) {
		super(type, name, num);
		this.buy_price = buy_price;
		this.owner = null;
		this.mortgage_mod = false;
	}

	/**
	 * Méthode permettant de savoir si un terrain est en hypothéque ou non
	 * 
	 * @return vrai si le terrain est en hypothéque, faux sinon
	 */
	public boolean is_mortgage() {
		return this.mortgage_mod;
	}

	/**
	 * Méthode permettant de choisir si un terrain est en hypothéque ou non
	 * 
	 * @param value
	 *            la valeur booléenne pour l'hypothque
	 */
	public void set_mortgage(boolean value) {
		this.mortgage_mod = value;
	}

	/**
	 * Méthode permettant de connaitre le prix d'achat d'un terrain achetable
	 * 
	 * @return le prix d'achat du terrain
	 */
	public int get_buy_price() {
		return this.buy_price;
	}

	/**
	 * Méthod permettant de choisir le propriétaire du terrain
	 * 
	 * @param owner
	 *            le nom du propriétaire du terrain, peut valoir null si pas de
	 *            propriétaire
	 */
	public void set_owner(String owner) {
		this.owner = owner;
	}

	/**
	 * Méthode permettant de connaitre le nom du propriétaire
	 * 
	 * @return le nom du propriétaire, null si pas de propriétaire
	 */
	public String get_owner() {
		return this.owner;
	}

	/**
	 * Méthode abstraite redéfini dans les sous classes permettant de connaitre
	 * le prix de loyer du terrain
	 * 
	 * @return le loyer du terrain
	 */
	public abstract int get_rent();
}
