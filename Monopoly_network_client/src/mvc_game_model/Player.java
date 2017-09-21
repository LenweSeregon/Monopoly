package mvc_game_model;

import java.util.ArrayList;

import mvc_game_model_terrains.Buyable_terrain;
import mvc_game_model_terrains.Property_terrain;
import settings.Setting_configuration;

public class Player {

	private String pseudo;
	private int money;

	private boolean in_jail;
	private boolean surrend;
	private boolean is_playing;

	private int position_on_board;
	private String piece_name;
	private int position_playing;

	private int nb_card_out_jail;
	private int nb_turn_in_jail;

	private ArrayList<Buyable_terrain> ref_terrains_owned;

	/**
	 * Constructeur de la classe qui représente un joueur dans notre plateau. Ce
	 * joueur est une entité principal puisque c'est avec lui que chaque tour
	 * est réalisé et il permet de connaitre l'etat des différents joueurs dans
	 * le réseau via cette copie locale
	 * 
	 * @param pseudo
	 *            le pseudo du joueur
	 * @param money
	 *            l'argent du joueur
	 * @param position
	 *            la position du joueur
	 * @param position_playing
	 *            la position de jeu du joueur
	 * @param piece_name
	 *            le nom de la piece détenu par le joueur
	 */
	public Player(String pseudo, int money, int position, int position_playing, String piece_name) {
		this.pseudo = pseudo;
		this.position_playing = position_playing;
		this.money = money;
		this.surrend = false;
		this.in_jail = false;
		this.is_playing = false;
		this.nb_card_out_jail = 0;
		this.nb_turn_in_jail = 0;
		this.position_on_board = position;
		this.piece_name = piece_name;
		this.ref_terrains_owned = new ArrayList<Buyable_terrain>();
	}

	/**
	 * Méthode permettant de connaitre le nombre total de maison dont dispose le
	 * joueur
	 * 
	 * @return le nombre de maison
	 */
	public int get_nb_houses_total() {
		int nb_house = 0;
		for (Buyable_terrain buyable : ref_terrains_owned) {
			if (buyable instanceof Property_terrain) {
				Property_terrain prop = (Property_terrain) buyable;
				if (prop.get_nb_house() != 5) {
					nb_house += prop.get_nb_house();
				}
			}
		}
		return nb_house;
	}

	/**
	 * Méthode permettant de connaitre le nombre total d'hotel dont dispose le
	 * joueur
	 * 
	 * @return le nombre d'hotel
	 */
	public int get_nb_hotel_total() {
		int nb_hotel = 0;
		for (Buyable_terrain buyable : ref_terrains_owned) {
			if (buyable instanceof Property_terrain) {
				Property_terrain prop = (Property_terrain) buyable;
				if (prop.get_nb_house() == 5) {
					nb_hotel += 1;
				}
			}
		}
		return nb_hotel;
	}

	/**
	 * Méthode permettant de connaitre le capital du joueur. Le capital du
	 * joueur est calculé avec tout l'argent dont il dispose + les maisons + les
	 * propriétés de vente / d'hypotheque
	 * 
	 * @return le capital du joueur
	 */
	public int get_capital() {
		int capital = money;
		for (Buyable_terrain terrain : ref_terrains_owned) {
			if (terrain instanceof Property_terrain) {
				Property_terrain prop = (Property_terrain) terrain;
				int price = (int) (prop.get_house_price() * ((float) Setting_configuration.percentage_sell / 100));
				capital += prop.get_nb_house() * price;
			}

			if (!terrain.is_mortgage()) {
				capital += (int) (terrain.get_buy_price() * 0.50f);
			}
		}
		return capital;
	}

	/**
	 * Méthode permettant de définir combien de carte pour sortir de prison
	 * dispose le joueur
	 * 
	 * @param nb
	 *            le nombre de carte pour sortir de prison
	 */
	public void set_nb_card_out_jail(int nb) {
		this.nb_card_out_jail = nb;
	}

	/**
	 * Méthode permettant de savoir combien de carte pour sortir de prison
	 * dispose le joueur
	 * 
	 * @return le nombre de carte sortie de prison dont le joueur dispose
	 */
	public int get_nb_card_out_jail() {
		return this.nb_card_out_jail;
	}

	/**
	 * Méthode permettant d'ajouter un terrain à la liste des terrains détenus
	 * par ce joueur. Cette méthode modifie directement aussi le propriétaire du
	 * terrain
	 * 
	 * @param terrain
	 *            le terrain que l'on souhaite ajouter à notre joueur
	 */
	public void add_terrain_owned(Buyable_terrain terrain) {
		this.ref_terrains_owned.add(terrain);
		terrain.set_owner(this.pseudo);
	}

	/**
	 * Méthode permettant de supprimer un terrain à la liste des terrains
	 * détenus par ce joueur. Cette méthode modifie directement le propriétaire
	 * du terrain en le mettant a null
	 * 
	 * @param terrain
	 *            le terrain que l'on souhaite enleever a notre joueur
	 */
	public void remove_terrain_owned(Buyable_terrain terrain) {
		this.ref_terrains_owned.remove(terrain);
		terrain.set_owner(null);
	}

	/**
	 * Méthode permettant de connaitre a quelle position le joueur joue
	 * 
	 * @return la position de jeu du joueur
	 */
	public int get_position_playing() {
		return this.position_playing;
	}

	/**
	 * Méthode permettant de connaitre le prix d'un terrain que posséde le
	 * joueur via son numéro
	 * 
	 * @param number
	 *            le numéro du terrain
	 * @return le prix du terrain si le joueur posséde ce terrain, -1 sinon
	 */
	public int get_price_terrain_from_number(int number) {
		for (Buyable_terrain t : ref_terrains_owned) {
			if (t.get_number() == number) {
				return t.get_rent();
			}
		}
		return -1;
	}

	/**
	 * Méthode permettant de récupérer les différents terrains que posséde le
	 * joueur
	 * 
	 * @return un array list contenant les différents terrains du joueur
	 */
	public ArrayList<Buyable_terrain> get_terrains_owned() {
		return this.ref_terrains_owned;
	}

	/**
	 * Permet de savoir si le joueur détient le terrain dont le numéro est donné
	 * en argument
	 * 
	 * @param number
	 *            le numéro de la propriété
	 * @return vrai si le joueur détient la propriété, faux sinon
	 */
	public boolean is_owning_terrain(int number) {
		for (Buyable_terrain t : ref_terrains_owned) {
			if (t.get_number() == number) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Méthode permettant de connaitre le nom de la piece du joueur
	 * 
	 * @return le nom de la piece du joueur
	 */
	public String get_piece_name() {
		return this.piece_name;
	}

	/**
	 * Méthode permettant de dire au joueur que c'est à son tour ou non de jouer
	 * 
	 * @param value
	 *            la valeur a mettre dans le fait de jouer ou non
	 */
	public void set_should_play(boolean value) {
		this.is_playing = value;
	}

	/**
	 * Méthode permettant de savoir si le joueur doit jouer ou non
	 * 
	 * @return
	 */
	public boolean is_playing() {
		return this.is_playing;
	}

	/**
	 * Méthode permettant de dire au joueur de changer sa position sur le
	 * plateau jusqu'a la position donnée
	 * 
	 * @param position
	 *            la position ou le joueur doit bouger
	 */
	public void move_to_position(int position) {
		this.position_on_board = position;
	}

	/**
	 * Méthode permettant de connaitre la position sur le plateau du joueur
	 * 
	 * @return la position du joueur sur le plateau
	 */
	public int get_position() {
		return this.position_on_board;
	}

	/**
	 * Méthode permettant de choisir de mettre ou non le joueur en prison
	 * 
	 * @param in_jail
	 *            la valeur boolénne pour mettre ou non le joueur en prison
	 */
	public void set_in_jail(boolean in_jail) {
		this.nb_turn_in_jail = 1;
		this.in_jail = in_jail;
	}

	/**
	 * Méthode permettant d'augmenter le nombre de tour qu'a passé le joueur en
	 * prison
	 */
	public void increment_turn_in_jail() {
		this.nb_turn_in_jail++;
	}

	/**
	 * Méthode permettant de récupérer le nombre de tour qu'a passé un joueur en
	 * prison
	 * 
	 * @return le nombre de tour en prison
	 */
	public int get_turn_in_jail() {
		return this.nb_turn_in_jail;
	}

	/**
	 * Méthode permettant de choisir le nombre d'argent dont dipose le joueur
	 * 
	 * @param value
	 *            le nombre d'argent du joueur
	 */
	public void set_money(int value) {
		this.money = value;
	}

	/**
	 * Méthode permettant de savoir si un jouur se trouve ou non en prison
	 * 
	 * @return vrai si le joueur est en prison, faux sinon
	 */
	public boolean is_in_jail() {
		return this.in_jail;
	}

	/**
	 * Méthode permettant de connaitre le pseudo du joueur
	 * 
	 * @return le pseudo du joueur
	 */
	public String get_pseudo() {
		return this.pseudo;
	}

	/**
	 * Méthode permettant de connaitre le nom de mono dont dispose un joueurr
	 * 
	 * @return le nombre d'argent dont dispose le joueur
	 */
	public int get_money() {
		return this.money;
	}

	/**
	 * Méthode permettant de savoir si le joueur a abandonner
	 * 
	 * @return vrai si le joueur a abandonner, faux sinon
	 */
	public boolean get_surrend() {
		return this.surrend;
	}

	/**
	 * Méthode permettant de spécifier qu'une joueur abnadonne ou non
	 * 
	 * @param sur
	 *            la valeur booléenne pour l'abandon
	 */
	public void set_surrend(boolean sur) {
		this.surrend = sur;
	}

}
