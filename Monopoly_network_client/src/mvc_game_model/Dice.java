package mvc_game_model;

import java.util.Random;

public class Dice {

	private int nb_face;
	private int value;

	public static boolean random = false;

	/**
	 * Constructeur de la classe qui représente le modele des dés dans le jeu.
	 * Cette classe wrap le principe assez simple des dés pour pouvoir lancer
	 * les dés et récupérer leurs valeurs
	 * 
	 * @param nb_face
	 */
	public Dice(int nb_face) {
		this.nb_face = nb_face;
	}

	/**
	 * Méthode permettant de récupérer la valeur du dés
	 * 
	 * @return la valeur du dé
	 */
	public int get_value() {
		return this.value;
	}

	/**
	 * Méthode permettant de faire rouler le dé et lu idonner une valeur entre 1
	 * et 6
	 */
	public void roll_dice() {
		Random rand_int = new Random();
		this.value = rand_int.nextInt(nb_face) + 1;
	}

	/**
	 * Méthode permettant de choisir la valeur que l'on veut attribuer à notre
	 * dé
	 * 
	 * @param value
	 *            la valeur que l'on veut attribuer
	 */
	public void set_value(int value) {
		this.value = value;
	}

}