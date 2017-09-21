package mvc_game_model_terrains;

import mvc_game_enums.Company_type;
import mvc_game_enums.Terrain_type;

public class Company_terrain extends Buyable_terrain {

	private int rent;
	private Company_type type_company;

	/**
	 * Constructeur de la classe qui représente les compagnies. Cette classe
	 * hérite de buyable terrain
	 * 
	 * @param name
	 *            le nom du terrain
	 * @param num
	 *            le numéro du terrain
	 * @param price
	 *            le prix du terrain
	 * @param rent
	 *            le loyer du terrain
	 * @param type
	 *            le type de compagnie
	 */
	public Company_terrain(String name, int num, int price, int rent, Company_type type) {
		super(Terrain_type.COMPANY, name, num, price);
		this.rent = rent;
		this.type_company = type;
	}

	@Override
	public int get_rent() {
		return this.rent;
	}

	/**
	 * Méthode permettant de connaitre le type de compagnie entre eau et
	 * electricite
	 * 
	 * @return le type de compagnie
	 */
	public Company_type get_type_company() {
		return this.type_company;
	}
}
