package mvc_network_controler;

import mvc_game_enums.Color_terrain;
import mvc_game_enums.Terrain_type;

public class Table_terrain_value {

	private boolean editable;
	private int number;
	private String name;
	private Color_terrain color;
	private int price;
	private int rent;
	private int[] houses;
	private int house_price;
	private Terrain_type type;

	/**
	 * Constructeur de la classe représentant un terrain de manière logique dans
	 * notre tableau. Cette classe va simplement contenir toutes les
	 * informations nécessaires pour pouvoir modifier et envoyer les
	 * informations via le réseau
	 * 
	 * @param editable
	 *            est ce que la cellule est editable
	 * @param number
	 *            le numero du terrain
	 * @param name
	 *            le nom du terrain
	 * @param color
	 *            la couleur du terrain
	 * @param price
	 *            le prix du terrain
	 * @param rent
	 *            le loyer du terrain
	 * @param house_price
	 *            le prix de la maison
	 * @param houses
	 *            les loyers avec maisons
	 * @param type
	 *            le type de terrain
	 */
	public Table_terrain_value(boolean editable, int number, String name, Color_terrain color, int price, int rent,
			int house_price, int[] houses, Terrain_type type) {
		this.editable = editable;
		this.number = number;
		this.name = name;
		this.color = color;
		this.price = price;
		this.rent = rent;
		this.house_price = house_price;

		this.houses = new int[houses.length];
		for (int i = 0; i < houses.length; i++) {
			this.houses[i] = houses[i];
		}
		this.type = type;
	}

	/**
	 * Permet de savoir si la cellule est editable
	 * 
	 * @return vrai si editable, faux sinon
	 */
	public boolean is_editable() {
		return editable;
	}

	/**
	 * Permet de mettre une valeur à editable
	 * 
	 * @param editable
	 *            la valeur à appliquer
	 */
	public void set_editable(boolean editable) {
		this.editable = editable;
	}

	/**
	 * Permet de connaitre le prix d'uen maison
	 * 
	 * @return le prix d'une maison
	 */
	public int get_house_price() {
		return this.house_price;
	}

	/**
	 * Permet de choisir le prix d'une maison
	 * 
	 * @param number
	 *            le prix de la maison
	 */
	public void set_house_price(int number) {
		this.house_price = number;
	}

	/**
	 * Permet de connaitre le numéro du terrain
	 * 
	 * @return le numéro du terrain
	 */
	public int get_number() {
		return number;
	}

	/**
	 * Permet de choisir le numero du terrain
	 * 
	 * @param number
	 *            le numero du terrain
	 */
	public void set_number(int number) {
		this.number = number;
	}

	/**
	 * Permet de connaitre le nom du terrain
	 * 
	 * @return le nom du terrain
	 */
	public String get_name() {
		return name;
	}

	/**
	 * Permet de choisir le nom du terrain
	 * 
	 * @param name
	 *            le nom du terrain
	 */
	public void set_name(String name) {
		this.name = name;
	}

	/**
	 * Permet de connaitre la couleur du terrain
	 * 
	 * @return la couleur du terrain
	 */
	public Color_terrain get_color() {
		return color;
	}

	/**
	 * Permet de choisir la couleur du terrain
	 * 
	 * @param color
	 *            la couleur du terrain
	 */
	public void set_color(Color_terrain color) {
		this.color = color;
	}

	/**
	 * Permet de connaitre le prix de la propriété
	 * 
	 * @return le prix de la propriété
	 */
	public int get_price() {
		return price;
	}

	/**
	 * Permet de choisir le prix de la propriété
	 * 
	 * @param price
	 *            le prix de la propriété
	 */
	public void set_price(int price) {
		this.price = price;
	}

	/**
	 * Permet de connaitre le loyer du terrain
	 * 
	 * @return le loyrer du terrain
	 */
	public int get_rent() {
		return rent;
	}

	/**
	 * Permet de choisir le loyer du terrain
	 * 
	 * @param rent
	 *            le loyer du terrain
	 */
	public void set_rent(int rent) {
		this.rent = rent;
	}

	/**
	 * Permet de connaitre le prix des loyers avec les maisons
	 * 
	 * @return les prix avec les maisons
	 */
	public int[] get_houses() {
		return houses;
	}

	/**
	 * Permet de choisir le prix des loyers avec les maisons
	 * 
	 * @param houses
	 *            les prix des maisons
	 */
	public void set_houses(int[] houses) {
		this.houses = houses;
	}

	/**
	 * Permet de choisir le prix avec un nombre de maison particulier
	 * 
	 * @param i
	 *            le nombre de maison
	 * @param value
	 *            le prix pour ce nombre de maison
	 */
	public void set_houses_number(int i, int value) {
		this.houses[i] = value;
	}

	/**
	 * Permet de connaitre le type du terrain
	 * 
	 * @return le type du terrain
	 */
	public Terrain_type get_type() {
		return type;
	}

	/**
	 * Permet de choisir le type du terrain
	 * 
	 * @param type
	 *            le type du terrain
	 */
	public void set_type(Terrain_type type) {
		this.type = type;
	}

}
