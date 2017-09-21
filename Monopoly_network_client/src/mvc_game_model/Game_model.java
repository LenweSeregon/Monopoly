package mvc_game_model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Vector;

import mvc_game_enums.Action_done;
import mvc_game_enums.Card_type;
import mvc_game_enums.Color_terrain;
import mvc_game_enums.Company_type;
import mvc_game_enums.Dice_manage_result;
import mvc_game_enums.House_manage_result;
import mvc_game_enums.Mortgage_manage_result;
import mvc_game_enums.Public_type;
import mvc_game_enums.Terrain_type;
import mvc_game_model_terrains.Abstract_terrain;
import mvc_game_model_terrains.Buyable_terrain;
import mvc_game_model_terrains.Card_terrain;
import mvc_game_model_terrains.Company_terrain;
import mvc_game_model_terrains.Go_jail_terrain;
import mvc_game_model_terrains.Go_terrain;
import mvc_game_model_terrains.Jail_terrain;
import mvc_game_model_terrains.Parking_terrain;
import mvc_game_model_terrains.Property_terrain;
import mvc_game_model_terrains.Public_terrain;
import mvc_game_model_terrains.Station_terrain;
import mvc_game_pattern_observer.Observable;
import mvc_game_pattern_observer.Observer;
import mvc_network_controler.Table_terrain_value;
import mvc_network_controler.Terrains_from_server;
import settings.Setting_configuration;

public class Game_model implements Observable {

	private ArrayList<Abstract_terrain> terrains;
	private ArrayList<Observer> observers;
	private ArrayList<Player> players;
	private ArrayList<Dice> dices;
	private Card_container cards;

	private int player_turn;
	private int same_dice_in_a_row;
	private int money_parking;

	private Auction current_auction;
	private Trade current_trade;

	private int nb_house_available;
	private int nb_hotel_available;

	private int nb_turn;
	private int last_transfert;

	private static int SUM_START = 20000;

	/**
	 * Constructeur de la classe qui représente le coueur du jeu et contient
	 * tout les elements logiques du plateau
	 */
	public Game_model() {
		this.terrains = new ArrayList<Abstract_terrain>();
		this.observers = new ArrayList<Observer>();
		this.players = new ArrayList<Player>();
		this.dices = new ArrayList<Dice>();
		this.cards = null;
		this.same_dice_in_a_row = 0;
		this.money_parking = 0;
		this.last_transfert = -1;
		this.nb_turn = 1;
		this.current_auction = null;
		this.current_trade = null;

		this.nb_house_available = Setting_configuration.nb_house;
		this.nb_hotel_available = Setting_configuration.nb_hotel;
	}

	//
	// BUILDING PART
	//

	/**
	 * Méthode qui construit les différents dés
	 */
	public void build_dices() {
		for (int i = 0; i < Setting_configuration.nb_dices; i++) {
			this.dices.add(new Dice(6));
		}
	}

	/**
	 * Méthode permettant de choisir le nombre de dés qu'on veut utiliser
	 * 
	 * @param nb
	 *            le nombre de dés à utiliser
	 */
	public void set_nb_dice(int nb) {
		this.dices.clear();
		for (int i = 0; i < nb; i++) {
			this.dices.add(new Dice(6));
		}
	}

	/**
	 * Méthode qui construit les différents joueurs
	 * 
	 * @param names
	 *            les noms des différents joueurs
	 */
	public void build_players(String[] names) {
		for (String name : names) {
			String[] split = name.split("&!");
			String pos = split[0];
			String n = split[1];
			int position = Integer.valueOf(split[2]);
			this.players.add(new Player(n, Setting_configuration.starting_money, position, Integer.valueOf(pos),
					"pion/" + pos + ".png"));
			if (Integer.valueOf(pos) == 1) {
				this.players.get(this.players.size() - 1).set_should_play(true);
			}

			if (Setting_configuration.random_properties_gave) {
				for (int it = 3; it < 6; it++) {
					this.players.get(this.players.size() - 1)
							.add_terrain_owned((Buyable_terrain) get_terrain_from_number(Integer.valueOf(split[it])));
				}
			}

		}
	}

	/**
	 * Méthode qui contient le conteneur de carte et les différentes cartes
	 */
	public void build_cards() {
		this.cards = new Card_container(terrains);
	}

	/**
	 * Méthode qui permet de récupérer le conteneur de carte
	 * 
	 * @return le conteneur de carte
	 */
	public Card_container get_card_container() {
		return this.cards;
	}

	/**
	 * Méthode qui construit les différents terrains
	 */
	public void build_terrains() {
		Vector<Table_terrain_value> terrains_server = Terrains_from_server.terrains_server;
		for (Table_terrain_value terrain : terrains_server) {
			switch (terrain.get_type()) {
			case CARDS:
				if (terrain.get_name().equals("Luck card")) {
					this.terrains.add(new Card_terrain(terrain.get_name(), terrain.get_number(), Card_type.LUCK));
				} else {
					this.terrains.add(new Card_terrain(terrain.get_name(), terrain.get_number(), Card_type.COMMUNAUTY));
				}
				break;
			case COMPANY:
				if (terrain.get_name().equals("Eau")) {
					this.terrains.add(new Company_terrain(terrain.get_name(), terrain.get_number(), terrain.get_price(),
							terrain.get_rent(), Company_type.SUPPLY));
				} else {
					this.terrains.add(new Company_terrain(terrain.get_name(), terrain.get_number(), terrain.get_price(),
							terrain.get_rent(), Company_type.WATER));
				}
				break;
			case GO:
				this.terrains.add(new Go_terrain(terrain.get_name(), terrain.get_number()));
				break;
			case GO_JAIL:
				this.terrains.add(new Go_jail_terrain(terrain.get_name(), terrain.get_number()));
				break;
			case JAIL:
				this.terrains.add(new Jail_terrain(terrain.get_name(), terrain.get_number()));
				break;
			case PARKING:
				this.terrains.add(new Parking_terrain(terrain.get_name(), terrain.get_number()));
				break;
			case PROPERTY:
				int[] rents = new int[terrain.get_houses().length + 1];

				rents[0] = terrain.get_rent();
				int k = 1;
				for (Integer i : terrain.get_houses()) {
					rents[k] = i;
					k++;
				}

				this.terrains.add(new Property_terrain(terrain.get_name(), terrain.get_number(), terrain.get_price(),
						terrain.get_house_price(), rents, terrain.get_color()));
				break;
			case PUBLIC:
				if (terrain.get_name().equals("Impots")) {
					this.terrains.add(new Public_terrain(terrain.get_name(), terrain.get_number(), terrain.get_rent(),
							Public_type.TAX));
				} else {
					this.terrains.add(new Public_terrain(terrain.get_name(), terrain.get_number(), terrain.get_rent(),
							Public_type.LUXURY));
				}

				break;
			case STATION:
				this.terrains
						.add(new Station_terrain(terrain.get_name(), terrain.get_number(), terrain.get_price(), 2500));
				break;
			default:
				break;
			}
		}

		// Let's sort by number
		this.terrains.sort((o1, o2) -> ((Integer) (o1.get_number())).compareTo(((Integer) (o2.get_number()))));
	}

	//
	// MANAGE PART
	//

	/**
	 * Méthode permettant de récupérer le dernier joueur de la partie
	 * 
	 * @return le dernier joueur de la partie
	 */
	public Player get_last_player() {
		for (Player p : players) {
			if (!p.get_surrend()) {
				return p;
			}
		}
		return null;
	}

	/**
	 * Méthode permettant d'ajouter un certain nombre de maison
	 * 
	 * @param nb
	 *            le nombre de maison a ajouter
	 */
	public void add_nb_house(int nb) {
		this.nb_house_available += nb;
	}

	/**
	 * Méthode permettant d'ajouter un certain nombre d'hotel
	 * 
	 * @param nb
	 *            le nombre d'hotel a ajouter
	 */
	public void add_nb_hotel(int nb) {
		this.nb_hotel_available += nb;
	}

	/**
	 * Méthode permettant de commencer un echange via les deux pseudos de joueur
	 * fournis en argument
	 * 
	 * @param pseudo_first
	 *            le pseudo du premier joueur qui prend part à l'enchere
	 * @param pseudo_second
	 *            le pseudo du deuxiéme joueur qui prend part à l'enchere
	 */
	public void start_trade(String pseudo_first, String pseudo_second) {
		this.current_trade = new Trade(pseudo_first, pseudo_second);
	}

	/**
	 * Méthode permettant de finir un echange
	 */
	public void end_trade() {
		this.current_trade = null;
	}

	/**
	 * Méthode permettant de récupérer l'échange courant avec toutes ces
	 * informations
	 * 
	 * @return l'instance de l'échange en cours
	 */
	public Trade get_trade() {
		return this.current_trade;
	}

	/**
	 * Méthode permettant d'ajouter une propriété au trade à un joueur via son
	 * pseudo
	 * 
	 * @param pseudo
	 *            le pseudo du joueur qui ajouter une propriété de trade
	 * @param number
	 *            le numéro de la propriété à ajouter
	 */
	public void add_property_trade(String pseudo, int number) {
		this.current_trade.add_property(pseudo, number);
	}

	/**
	 * Méthode permettant de retirer une propriété au trade à un joueur via son
	 * pseudo
	 * 
	 * @param pseudo
	 *            le pseudo du joueur qui retire une propriété de trade
	 * @param number
	 *            le numéro de la propriété à retirer
	 */
	public void remove_property_trade(String pseudo, int number) {
		this.current_trade.remove_property(pseudo, number);
	}

	/**
	 * Méthode permettant de changer l'argent donner par un joueur pour
	 * l'échange
	 * 
	 * @param pseudo
	 *            le pseudo du joueur
	 * @param money
	 *            l'argent du joueur
	 */
	public void change_money_trade(String pseudo, int money) {
		this.current_trade.set_money_value(pseudo, money);
	}

	/**
	 * Méthode permettant de récupérer la somme du parking public
	 * 
	 * @return la somme du parking public
	 */
	public int get_parking_sum() {
		return this.money_parking;
	}

	/**
	 * Méthode permettant de définir la somme attribué au parking public
	 * 
	 * @param sum
	 *            la somme a définir
	 */
	public void set_parking_sum(int sum) {
		this.money_parking = sum;
	}

	/**
	 * Méthode permettant de commencer une enchere en donnant comme parametre le
	 * numéro de la propriété qui est mise au enchere
	 * 
	 * @param number_property
	 *            le numéro de la propriété au enchere
	 */
	public void start_auction(int number_property) {
		Abstract_terrain terrain = get_terrain_from_number(number_property);
		if (terrain instanceof Buyable_terrain) {
			Buyable_terrain buyable = (Buyable_terrain) terrain;
			this.current_auction = new Auction(buyable, players);
		} else {
			return;
		}
	}

	/**
	 * Méthode étant appelé pour changer la valeur d'enchere qu'un joueur durant
	 * l'enchere courante
	 * 
	 * @param pseudo
	 *            le pseudo du joueur qui a changé son enchere
	 * @param value
	 *            la valeur de son enchere
	 */
	public void change_auction_player(String pseudo, int value) {
		if (this.current_auction != null) {
			this.current_auction.set_auction_from_pseudo(pseudo, value);
		}
	}

	/**
	 * Méthode étant appelé pour signaler la fin de l'enchere
	 */
	public void end_auction() {
		this.current_auction = null;
	}

	/**
	 * Méthode permettant de récupérer l'enchere actuel
	 * 
	 * @return l'enchere actuelle
	 */
	public Auction get_auction() {
		return this.current_auction;
	}

	/*
	 * Méthode qui permet de lancer les différents dés
	 */
	public void roll_dices() {
		for (Dice d : this.dices) {
			d.roll_dice();
		}
	}

	/**
	 * Méthode qui permet de récupérer la somme des dés
	 * 
	 * @return la somme des dés
	 */
	public int get_sum_dices() {
		int sum = 0;
		for (Dice d : this.dices) {
			sum += d.get_value();
		}
		return sum;
	}

	/**
	 * Méthode qui permet de récupérer le produit des dés
	 * 
	 * @return le produit des dés
	 */
	public int get_mult_dices() {
		int mult = 1;
		for (Dice d : this.dices) {
			mult *= d.get_value();
		}
		return mult;
	}

	/**
	 * Méthode qui permet de savoir si tout les dés ont la meme valeure.
	 * 
	 * @return vrai si tous les dés sont identiques, faux sinon
	 */
	public boolean dices_are_same() {
		int val = dices.get(0).get_value();
		for (int i = 1; i < dices.size(); i++) {
			if (dices.get(i).get_value() != val) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Méthode qui permet de savoir combien de gare posséde le joueur avec le
	 * pseudo en parametre
	 * 
	 * @param pseudo
	 *            le pseudo du joueur dont on veut l'information
	 * @return le nombre de gare possédé par le joueur
	 */
	public int get_nb_station_owned_by_player(String pseudo) {
		Player p = get_player_from_pseudo(pseudo);
		int i = 0;
		for (Buyable_terrain terrain : p.get_terrains_owned()) {
			if (terrain.get_type() == Terrain_type.STATION) {
				i++;
			}
		}
		return i;
	}

	/**
	 * Méthode qui permet de savoir combien de compagnie posséde le joueur avec
	 * le pseudo en parametre
	 * 
	 * @param pseudo
	 *            le pseudo du joueur dont on veut l'information
	 * @return le nombre de compagnie possédé par le joueur
	 */
	public int get_nb_company_owned_by_player(String pseudo) {
		Player p = get_player_from_pseudo(pseudo);
		int i = 0;
		for (Buyable_terrain terrain : p.get_terrains_owned()) {
			if (terrain.get_type() == Terrain_type.COMPANY) {
				i++;
			}
		}
		return i;
	}

	/**
	 * Méthode qui permet de savoir si un joueur posséde tout les terrains d'une
	 * couleur donnée
	 * 
	 * @param pseudo
	 *            le pseudo du joueur dont on veut l'information
	 * @param color
	 *            la couleur que l'on veut tester
	 * @return vrai si le joueur posséde tout les terrains, faux sinon
	 */
	public boolean player_own_all_color_terrains(String pseudo, Color_terrain color) {

		Player p = this.get_player_from_pseudo(pseudo);
		int expected = 0;
		if (color == Color_terrain.BROWN || color == Color_terrain.DARK_BLUE) {
			expected = 2;
		} else {
			expected = 3;
		}

		int i = 0;
		for (Buyable_terrain terrain : p.get_terrains_owned()) {
			if (terrain.get_type() == Terrain_type.PROPERTY) {
				Property_terrain prop = (Property_terrain) terrain;
				if (prop.get_color() == color) {
					i++;
				}
			}
		}

		return i == expected;
	}

	/**
	 * Méthode qui permet de savoir si les terrains d'une couleur sont équalisé
	 * (plus ou moins 1 au maximum
	 * 
	 * @param pseudo
	 *            le pseudo du joueur que l'on veut tester
	 * @param number
	 *            le numéro de la propriété de base que l'on veut tester
	 * @param color
	 *            la couleur a tester
	 * @param nb_house_wanted
	 *            le futur nombre de maison voulu
	 * @return vrai si les maisons sont équalisé, faux sinon
	 */
	public boolean houses_are_equalize(String pseudo, int number, Color_terrain color, int nb_house_wanted) {
		Player p = this.get_player_from_pseudo(pseudo);
		for (Buyable_terrain terrain : p.get_terrains_owned()) {
			if (terrain.get_type() == Terrain_type.PROPERTY) {
				Property_terrain prop = (Property_terrain) terrain;
				if (prop.get_color() == color && prop.get_number() != number) {
					int gap = Math.abs(prop.get_nb_house() - nb_house_wanted);
					if (gap != 0 && gap != 1) {
						return false;
					}
				}
			}
		}
		return true;
	}

	/**
	 * Méthode qui permet de savoir si les terrains d'une couleur sont équalisé
	 * (plus ou moins 1 au maximum
	 * 
	 * @param pseudo
	 *            le pseudo du joueur que l'on veut tester
	 * @param number
	 *            le numéro de la propriété de base que l'on veut tester
	 * @param color
	 *            la couleur a tester
	 * @param nb_house_wanted
	 *            le futur nombre de maison voulu
	 * @return vrai si les maisons sont équalisé, faux sinon
	 */
	public boolean house_are_equalize_selling(String pseudo, int number, Color_terrain color, int nb_house_wanted) {
		Player p = this.get_player_from_pseudo(pseudo);
		for (Buyable_terrain terrain : p.get_terrains_owned()) {
			if (terrain.get_type() == Terrain_type.PROPERTY) {
				Property_terrain prop = (Property_terrain) terrain;
				if (prop.get_color() == color && prop.get_number() != number) {
					int gap = Math.abs(prop.get_nb_house() - nb_house_wanted);
					if (gap != 0 && gap != 1) {
						return false;
					}
				}
			}
		}
		return true;
	}

	/**
	 * Méthode permettant de savoir si un joueur n'a aucune maison sur un groupe
	 * de couleur donné
	 * 
	 * @param pseudo
	 *            le pseudo du joueur pour le test
	 * @param color
	 *            la couleur de terrain que l'on veut tester
	 * @return vrai si le joueur ne dispose d'aucune maison sur les terrains de
	 *         couleur, faux sinon
	 */
	public boolean no_house_on_color(String pseudo, Color_terrain color) {
		Player p = this.get_player_from_pseudo(pseudo);
		for (Buyable_terrain terrain : p.get_terrains_owned()) {
			if (terrain.get_type() == Terrain_type.PROPERTY) {
				Property_terrain prop = (Property_terrain) terrain;
				if (prop.get_color() == color && prop.get_nb_house() > 0) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Méthode permettant de savoir si un joueur n'a aucune propriété hypothéqué
	 * sur un groupe de couleur donné
	 * 
	 * @param pseudo
	 *            le pseudo du joueur pour le test
	 * @param color
	 *            la couleur de terrain que l'on veut tester
	 * @return vrai si le joueur ne dippose d'aucun terrain de la couleur ayant
	 *         des hypothéques, faux sinon
	 */
	public boolean no_mortgage_on_color(String pseudo, Color_terrain color) {
		Player p = this.get_player_from_pseudo(pseudo);
		for (Buyable_terrain terrain : p.get_terrains_owned()) {
			if (terrain.get_type() == Terrain_type.PROPERTY) {
				Property_terrain prop = (Property_terrain) terrain;
				if (prop.get_color() == color && prop.is_mortgage()) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Méthode qui permet d'hypothéquer un terrain
	 * 
	 * @param pseudo
	 *            le pseudo du joueur qui veut hypothéquer
	 * @param number
	 *            le numéro de la propriété à hypothéquer
	 * @return une valeur d'énumération en fonction du résultat de la logique
	 */
	public Mortgage_manage_result mortgage_from_number(String pseudo, int number) {
		Abstract_terrain terrain = this.get_terrain_from_number(number);
		if (terrain instanceof Buyable_terrain) {
			Buyable_terrain buyable = (Buyable_terrain) terrain;
			String owner = buyable.get_owner();
			if (owner != null && !owner.equals(pseudo)) {
				return Mortgage_manage_result.NOT_YOU;
			}
			if (buyable instanceof Property_terrain) {
				Property_terrain prop = (Property_terrain) (buyable);
				if (prop.is_mortgage()) {
					return Mortgage_manage_result.ALREADY_MORTGAGE;
				} else if (prop.get_nb_house() != 0) {
					return Mortgage_manage_result.HOUSE_ON;
				} else if (!no_house_on_color(pseudo, prop.get_color())) {
					return Mortgage_manage_result.HOUSE_ON_COLOR;
				} else {
					int sum_add = (int) (buyable.get_buy_price() * 0.5);
					Player p = this.get_player_from_pseudo(pseudo);
					p.set_money(p.get_money() + sum_add);
					buyable.set_mortgage(true);
					return Mortgage_manage_result.OK;
				}
			} else {
				if (buyable.is_mortgage()) {
					return Mortgage_manage_result.ALREADY_MORTGAGE;
				} else {
					int sum_add = (int) (buyable.get_buy_price() * 0.5);
					Player p = this.get_player_from_pseudo(pseudo);
					p.set_money(p.get_money() + sum_add);
					buyable.set_mortgage(true);
					return Mortgage_manage_result.OK;
				}
			}
		} else {
			return Mortgage_manage_result.NOT_BUYABLE;
		}
	}

	/**
	 * Méthode qui permet de déhypothequer un terrain
	 * 
	 * @param pseudo
	 *            le pseudo du joueur qui veut déhypothequer
	 * @param number
	 *            le numéro de la propriété à déhypothequer
	 * @return une valeur d'énumération en fonction du résultat de la logique
	 */
	public Mortgage_manage_result unmortgage_from_number(String pseudo, int number) {
		Abstract_terrain terrain = this.get_terrain_from_number(number);
		if (terrain instanceof Buyable_terrain) {
			Buyable_terrain buyable = (Buyable_terrain) terrain;
			Player p = this.get_player_from_pseudo(pseudo);
			int sum = (int) (buyable.get_buy_price() * 0.5)
					+ ((((int) (buyable.get_buy_price() * 0.5)) * Setting_configuration.rate_rebuy_hypotheck) / 100);

			if (!buyable.get_owner().equals(pseudo)) {
				return Mortgage_manage_result.NOT_YOU;
			} else if (!buyable.is_mortgage()) {
				return Mortgage_manage_result.ALREADY_UNMORTGAGE;
			} else if (p.get_money() < sum) {
				return Mortgage_manage_result.NOT_ENOUGH_MONEY;
			} else {
				((Buyable_terrain) terrain).set_mortgage(false);
				p.set_money(p.get_money() - sum);
				return Mortgage_manage_result.OK;
			}
		} else {
			return Mortgage_manage_result.NOT_BUYABLE;
		}
	}

	/**
	 * Permet de connaitre la somme du dernier transfert d'argent
	 * 
	 * @return la somme du dernier transfert
	 */
	public int get_last_transfert() {
		return this.last_transfert;
	}

	/**
	 * Méthode qui permet de récupérer les joueurs via une arraylist
	 * 
	 * @return une arraylist contenant une référence sur les joueurs
	 */
	public ArrayList<Player> get_players() {
		return this.players;
	}

	/**
	 * Méthode qui permet de récupérer les terrains via une arraylist
	 * 
	 * @return une arraylist contenant une référence sur les terrains
	 */
	public ArrayList<Abstract_terrain> get_terrains() {
		return this.terrains;
	}

	/**
	 * Méthode qui permet de récupérer les dés via une arraylist
	 * 
	 * @return une arraylist contenant une référence sur les dés
	 */
	public ArrayList<Dice> get_dices() {
		return this.dices;
	}

	/**
	 * Méthode qui permet de gérer la position d'un joueur
	 * 
	 * @param player
	 *            le joueur auquel on doti s'occuper
	 * @return une énumération représentant le résultat de l'action
	 */
	public Action_done manage_position(String player) {
		this.last_transfert = -1;

		Player p = this.get_player_from_pseudo(player);
		Abstract_terrain terrain = terrains.get(p.get_position() - 1);
		if (terrain instanceof Buyable_terrain) {
			return manage_buyable_terrain(terrain, p);
		} else if (terrain instanceof Go_terrain) {
			if (Setting_configuration.double_sum_on_case_start) {
				p.set_money(p.get_money() + SUM_START);
				this.last_transfert = SUM_START;
				return Action_done.MONEY_EARN;
			} else {
				return Action_done.NOTHING_SPECIAL;
			}
		} else if (terrain instanceof Go_jail_terrain) {
			get_player_from_pseudo(player).set_in_jail(true);
			get_player_from_pseudo(player).move_to_position(11);
			return Action_done.JAIL;
		} else if (terrain instanceof Parking_terrain) {
			if (Setting_configuration.earn_money_on_parking) {
				p.set_money(p.get_money() + money_parking);
				money_parking = 0;
				this.last_transfert = money_parking;
				return Action_done.MONEY_EARN;
			} else {
				return Action_done.NOTHING_SPECIAL;
			}
		} else if (terrain instanceof Public_terrain) {
			Public_terrain pub = (Public_terrain) (terrain);
			int cost = pub.get_cost();
			if (Setting_configuration.tax_according_to_player_money) {
				cost = (int) (0.1 * p.get_money());
			}

			this.last_transfert = cost;

			return Action_done.TAXE_PAYED;
		} else if (terrain instanceof Card_terrain) {
			Card_terrain card = (Card_terrain) terrain;
			if (card.get_card_type() == Card_type.COMMUNAUTY) {
				return Action_done.COMMUNAUTY_CARD;
			} else {
				return Action_done.CHANCE_CARD;
			}
		} else {
			return Action_done.NOTHING_SPECIAL;
		}
	}

	/**
	 * Méthode qui permet de gérer une action particuliére qui est la gestion de
	 * la position lorsque l'on tombe sur une propriété
	 * 
	 * @param terrain
	 *            le terrain sur lequel on va gérer la position du joueur
	 * @param player
	 *            le joueur qui est tombé sur cette case
	 * @return une énumération représentant le résultat de l'action
	 */
	public Action_done manage_buyable_terrain(Abstract_terrain terrain, Player player) {
		Buyable_terrain buyable = (Buyable_terrain) (terrain);

		String owner = buyable.get_owner();
		if (owner == null) {
			return Action_done.BUYABLE_TERRAIN;
		} else if (owner.equals(player.get_pseudo())) {
			return Action_done.NOTHING_SPECIAL;
		} else {
			if (!buyable.is_mortgage()) {
				Player owner_player = get_player_from_pseudo(owner);
				if (owner_player.is_in_jail()) {
					if (!Setting_configuration.allow_earn_money_during_jailing) {
						return Action_done.PLAYER_JAILING;
					}
				}
				int price = buyable.get_rent();
				if (buyable instanceof Property_terrain) {
					Property_terrain prop = (Property_terrain) buyable;
					if (prop.get_nb_house() == 0 && player_own_all_color_terrains(owner, prop.get_color())) {
						price *= 2;
					}
				} else if (buyable instanceof Station_terrain) {
					int nb_station = get_nb_station_owned_by_player(owner);
					if (nb_station == 1) {
						price = 2500;
					} else if (nb_station == 2) {
						price = 5000;
					} else if (nb_station == 3) {
						price = 10000;
					} else if (nb_station == 4) {
						price = 20000;
					} else {
						return Action_done.NOTHING_SPECIAL;
					}
				} else if (buyable instanceof Company_terrain) {
					int nb_company = get_nb_company_owned_by_player(owner);
					if (nb_company == 1) {
						price = 400 * get_sum_dices();
					} else if (nb_company == 2) {
						price = 1000 * get_sum_dices();
					} else {
						return Action_done.NOTHING_SPECIAL;
					}
				}
				this.last_transfert = price;

				return Action_done.RENT_PAYED;
			} else {
				return Action_done.TERRAIN_MORTGAGE;
			}
		}
	}

	/**
	 * Méthode qui permet de gérer la valeure des dés
	 * 
	 * @param player
	 *            le joueur qui a lancé les dés
	 * @return vrai si la gestion c'est bien passé, faux sinon
	 */
	public Dice_manage_result manage_dice_rolled(String player) {
		int sum = get_sum_dices();
		Player p = this.get_player_from_pseudo(player);

		// Gestion variable mult
		if (Setting_configuration.jail_if_sum_3_dices) {
			if (get_mult_dices() >= 50) {
				return Dice_manage_result.MULT50;
			}
		}

		// Gestion 3 fois double / triple
		if (dices.size() > 1 && dices_are_same()) {
			same_dice_in_a_row++;
			if (same_dice_in_a_row == 3) {
				if (Setting_configuration.jail_3_double) {
					return Dice_manage_result.THREE_TIME_SAME;
				} else {
					int new_pos = p.get_position() + sum;
					if (new_pos > 40) {
						p.set_money(p.get_money() + SUM_START);
						new_pos %= 40;
						if (new_pos == 0) {
							new_pos += 1;
						}
						last_transfert += SUM_START;
					}
					p.move_to_position(new_pos);

					return Dice_manage_result.NOTHING;
				}
			}

			if (p.is_in_jail()) {
				int new_pos = p.get_position() + sum;
				if (new_pos > 40) {
					p.set_money(p.get_money() + SUM_START);
					new_pos %= 40;
					if (new_pos == 0) {
						new_pos += 1;
					}
					last_transfert += SUM_START;
				}
				p.move_to_position(new_pos);
				return Dice_manage_result.SAME_VALUE_AND_JAILING;
			}
		}

		int new_pos = p.get_position() + sum;
		if (new_pos > 40) {
			p.set_money(p.get_money() + SUM_START);
			new_pos %= 40;
			if (new_pos == 0) {
				new_pos += 1;
			}
			last_transfert += SUM_START;
		}
		p.move_to_position(new_pos);

		return Dice_manage_result.NOTHING;
	}

	/**
	 * Méthode permettant d'ajouter une maison dans la possibiltié que cela soit
	 * possible dans l'algorithme
	 * 
	 * @param pseudo
	 *            le pseudo du joueur qui veut ajouter une maison
	 * @param number
	 *            le numéro de la maison ou l'on veut ajouter une maison
	 * @return
	 */
	public House_manage_result add_house_on_terrain(String pseudo, int number) {
		Player p = get_player_from_pseudo(pseudo);
		Abstract_terrain terrain = get_terrain_from_number(number);
		if (terrain instanceof Property_terrain) {
			Property_terrain prop = (Property_terrain) terrain;
			int price = prop.get_house_price();
			Color_terrain color_target = prop.get_color();
			if (!prop.get_owner().equals(pseudo)) {
				return House_manage_result.NOT_YOU;
			} else if (prop.is_mortgage()) {
				return House_manage_result.IS_MORTGAGE;
			} else if (!player_own_all_color_terrains(pseudo, color_target)) {
				return House_manage_result.NOT_ALL_HOUSES;
			} else if (prop.get_nb_house() >= 5) {
				return House_manage_result.FULL_HOUSE;
			} else if (!no_mortgage_on_color(pseudo, prop.get_color())) {
				return House_manage_result.MORTGAGE_COLOR;
			} else if (p.get_money() < price) {
				return House_manage_result.NOT_ENOUGH_MONEY;
			} else {
				if (!Setting_configuration.no_care_equality_house) {
					if (!houses_are_equalize(pseudo, number, prop.get_color(), prop.get_nb_house() + 1)) {
						return House_manage_result.NOT_EQUALIZE;
					}
				}

				int nb_house_after_add = prop.get_nb_house() + 1;
				if (nb_house_after_add == 5) {
					if (this.nb_hotel_available > 0) {
						this.nb_hotel_available--;
						this.nb_house_available += 4;
						p.set_money(p.get_money() - price);
						prop.set_nb_house(prop.get_nb_house() + 1);
						return House_manage_result.OK;
					} else {
						return House_manage_result.NOT_ENOUGH_HOTEL;
					}
				} else {
					if (this.nb_house_available > 0) {
						this.nb_house_available--;
						p.set_money(p.get_money() - price);
						prop.set_nb_house(prop.get_nb_house() + 1);
						return House_manage_result.OK;
					} else {
						return House_manage_result.NOT_ENOUGH_HOUSE;
					}
				}
			}
		} else {
			return House_manage_result.NOT_AN_HOUSE;
		}
	}

	/**
	 * Méthode permettant de retirer une maison dans la possibiltié que cela
	 * soit possible dans l'algorithme
	 * 
	 * @param pseudo
	 *            le pseudo du joueur qui veut retirer une maison
	 * @param number
	 *            le numéro de la maison ou l'on veut retirer une maison
	 * @return
	 */
	public House_manage_result remove_house_on_terrain(String pseudo, int number) {
		Player p = get_player_from_pseudo(pseudo);
		Abstract_terrain terrain = get_terrain_from_number(number);
		if (terrain instanceof Property_terrain) {
			Property_terrain prop = (Property_terrain) terrain;

			if (!prop.get_owner().equals(pseudo)) {
				return House_manage_result.NOT_YOU;
			}
			if (prop.is_mortgage()) {
				return House_manage_result.IS_MORTGAGE;
			}
			if (prop.get_nb_house() > 0) {
				int price = (int) (prop.get_house_price() * ((float) Setting_configuration.percentage_sell / 100));
				if (!Setting_configuration.no_care_equality_house) {
					if (!house_are_equalize_selling(pseudo, number, prop.get_color(), prop.get_nb_house() - 1)) {
						return House_manage_result.NOT_EQUALIZE;
					}
				}

				if (prop.get_nb_house() == 5) {
					if (this.nb_house_available >= 4) {
						this.nb_house_available -= 4;
						this.nb_hotel_available++;
						p.set_money(p.get_money() + price);
						prop.set_nb_house(prop.get_nb_house() - 1);
						return House_manage_result.OK;
					} else {
						return House_manage_result.NOT_ENOUGH_HOUSE;
					}
				} else {
					this.nb_house_available++;
					p.set_money(p.get_money() + price);
					prop.set_nb_house(prop.get_nb_house() - 1);
					return House_manage_result.OK;
				}
			} else {
				return House_manage_result.NO_HOUSE;
			}
		} else {
			return House_manage_result.NOT_AN_HOUSE;
		}
	}

	/**
	 * Méthode permettant de choisir la valeure des dés
	 * 
	 * @param values
	 *            les différentes valeurs de dés
	 */
	public void set_dices_values(Vector<Integer> values) {
		for (int i = 0; i < values.size(); i++) {
			dices.get(i).set_value(values.get(i));
		}
	}

	/**
	 * Méthode permettant d'acheter un terrain dans la possibilité que le joueur
	 * dispose de l'argent
	 * 
	 * @return vrai si l'achat a été effectué, faux sinon
	 */
	public boolean buy_terrain_current_player() {
		Player p = this.get_player_from_turn();
		Buyable_terrain terrain = (Buyable_terrain) terrains.get(p.get_position() - 1);
		if (p.get_money() >= terrain.get_buy_price()) {
			p.add_terrain_owned(terrain);
			p.set_money(p.get_money() - terrain.get_buy_price());
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Méthode permettant d'acheter un terrain dans la possibilité que le joueur
	 * en parametre dispose de l'argent
	 * 
	 * @param pseudo
	 *            le pseudo du joueur qui veut acheter
	 * @return vrai si le terrain a été acheté, faux sinon
	 */
	public boolean buy_terrain_player_from_pseudo(String pseudo) {
		Player p = this.get_player_from_pseudo(pseudo);
		Buyable_terrain terrain = (Buyable_terrain) terrains.get(p.get_position() - 1);
		if (p.get_money() >= terrain.get_buy_price()) {
			p.add_terrain_owned(terrain);
			p.set_money(p.get_money() - terrain.get_buy_price());
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Méthode permettant d'acheter un terrain dans la possibilité que le joueur
	 * en parametre dispose de l'argent et à une position précise
	 * 
	 * @param pseudo
	 *            le pseudo du joueur qui veut acheter
	 * @param position
	 *            la position de terrain que l'on veut acheter
	 * @return vrai si le terrain a été acheté, faux sinon
	 */
	public boolean buy_terrain_player_from_pseudo_to_position(String pseudo, int position) {
		Player p = this.get_player_from_pseudo(pseudo);
		Buyable_terrain terrain = (Buyable_terrain) terrains.get(position - 1);
		if (terrain.get_owner() == null && p.get_money() >= terrain.get_buy_price()) {
			p.add_terrain_owned(terrain);
			p.set_money(p.get_money() - terrain.get_buy_price());
			return true;
		} else {
			return false;
		}
	}

	public boolean sell_terrain_current_player(int number) {
		Abstract_terrain terrain = get_terrain_from_number(number);
		if (terrain instanceof Buyable_terrain) {
			Buyable_terrain buyable = (Buyable_terrain) (terrain);
			if (buyable.get_owner() == get_player_from_turn().get_pseudo()) {
				if (buyable instanceof Property_terrain) {
					Property_terrain prop = (Property_terrain) (buyable);
					if (prop.get_nb_house() == 0) {
						if (prop.is_mortgage()) {
							return false;
						}
						String owner = prop.get_owner();
						Player own = get_player_from_pseudo(owner);
						own.remove_terrain_owned(buyable);
						int earn = (int) (buyable.get_buy_price() * 0.8f);
						own.set_money(own.get_money() + earn);
						return true;
					} else {
						return false;
					}
				} else {
					if (buyable.is_mortgage()) {
						return false;
					}
					String owner = buyable.get_owner();
					Player own = get_player_from_pseudo(owner);
					own.remove_terrain_owned(buyable);
					int earn = (int) (buyable.get_buy_price() * 0.8f);
					own.set_money(own.get_money() + earn);
					return true;
				}
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	public Buyable_terrain get_free_buyable_terrain_random() {
		ArrayList<Buyable_terrain> buyables = new ArrayList<Buyable_terrain>();

		for (Abstract_terrain terrain : terrains) {
			if (terrain instanceof Buyable_terrain) {
				if (((Buyable_terrain) terrain).get_owner() == null) {
					buyables.add((Buyable_terrain) terrain);
				}
			}
		}

		Random rand = new Random();
		return buyables.get(rand.nextInt(buyables.size()));
	}

	public boolean sell_property_from_pseudo_to_position(String pseudo, int position) {

		Abstract_terrain terrain = get_terrain_from_number(position);
		if (terrain instanceof Buyable_terrain) {
			Buyable_terrain buyable = (Buyable_terrain) (terrain);
			if (buyable.get_owner().equals(pseudo)) {
				if (buyable instanceof Property_terrain) {
					Property_terrain prop = (Property_terrain) (buyable);
					if (prop.get_nb_house() == 0) {
						if (prop.is_mortgage()) {
							return false;
						}
						String owner = prop.get_owner();
						Player own = get_player_from_pseudo(owner);
						own.remove_terrain_owned(buyable);
						int earn = (int) (buyable.get_buy_price() * 0.8f);
						own.set_money(own.get_money() + earn);
						return true;
					} else {
						return false;
					}
				} else {
					if (buyable.is_mortgage()) {
						return false;
					}
					String owner = buyable.get_owner();
					Player own = get_player_from_pseudo(owner);
					own.remove_terrain_owned(buyable);
					int earn = (int) (buyable.get_buy_price() * 0.8f);
					own.set_money(own.get_money() + earn);
					return true;
				}
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	/**
	 * Méthode permettant de connaitre le nombre de joueur en partie (n'ayant
	 * pas abandonné)
	 * 
	 * @return le nombre de joueur en partie
	 */
	public int get_player_in_game() {
		int sum = 0;
		for (Player p : players) {
			if (!p.get_surrend()) {
				sum++;
			}
		}
		return sum;
	}

	/**
	 * Méthode permettant de récupérer un joueur via son pseudo
	 * 
	 * @param pseudo
	 *            le pseudo du joueur que l'on veut récupérer
	 * @return le joueur via le pseudo, null si pas trouvé
	 */
	public Player get_player_from_pseudo(String pseudo) {
		for (Player p : players) {
			if (p.get_pseudo().equals(pseudo)) {
				return p;
			}
		}
		return null;
	}

	/**
	 * Méthode permettant de récupérer un joueur via le tour du joueur
	 * 
	 * @return le joueur
	 */
	public Player get_player_from_turn() {
		return this.players.get(this.player_turn);
	}

	/**
	 * Permet de passer au joueur suivant
	 */
	public void next_player() {
		this.nb_turn++;
		this.player_turn++;
		this.same_dice_in_a_row = 0;
		if (player_turn == this.players.size()) {
			this.player_turn = 0;
		}

		if (get_player_from_turn().get_surrend()) {
			this.nb_turn--;
			if (get_player_in_game() > 0) {
				this.next_player();
			}
		}
	}

	/**
	 * Permet de récupérer le nombre de tour actuel dans la partie
	 * 
	 * @return le nombre de tour
	 */
	public int get_nb_turn() {
		return this.nb_turn;
	}

	/**
	 * Permet de récupérer le jouer qui a gagné la partie. Cette fonction gére
	 * aussi directement le mode suicide
	 * 
	 * @return le houeur qui a gagné
	 */
	public Player get_winner() {
		ArrayList<Integer> sums = new ArrayList<Integer>();
		// Adding
		for (Player p : players) {
			if (p.get_surrend()) {
				sums.add(0);
			} else {
				sums.add(p.get_capital());
			}
		}
		// Managing
		if (Setting_configuration.suicide_mod) {
			int min_value = Collections.max(sums);
			int index_min = sums.indexOf(min_value);
			return players.get(index_min);
		} else {
			int max_value = Collections.max(sums);
			int index_max = sums.indexOf(max_value);
			return players.get(index_max);
		}
	}

	/**
	 * Permet de récupérer le nom d'un terrain via son numéro
	 * 
	 * @param number
	 *            le numéro du terrain
	 * @return le nom du terrain, null si pas trouvé
	 */
	public String get_terrain_name_from_number(int number) {
		for (Abstract_terrain terrain : terrains) {
			if (terrain.get_number() == number) {
				return terrain.get_name();
			}
		}
		return null;
	}

	/**
	 * Permet de récupérer le terrain via son numéro
	 * 
	 * @param number
	 *            le numéro du terrain
	 * @return le terrain, nul si pas trouvé
	 */
	public Abstract_terrain get_terrain_from_number(int number) {
		for (Abstract_terrain terrain : terrains) {
			if (terrain.get_number() == number) {
				return terrain;
			}
		}
		return null;
	}

	/**
	 * Permet de récupérer le propriétaire du terrain par rapport au numéro de
	 * terrain
	 * 
	 * @param number
	 *            le numéro de terrain
	 * @return le nom du propriétaire, null si pas de propriétaire
	 */
	public String get_owner_name_of_property(int number) {
		Abstract_terrain terrain = terrains.get(number - 1);
		if (terrain instanceof Buyable_terrain) {
			return ((Buyable_terrain) terrain).get_owner();
		} else {
			return null;
		}
	}

	/**
	 * Méthode qui permet de choisir l'argent d'un joueur
	 * 
	 * @param pseudo
	 *            le joueur dont l'on veut modifier l'argent
	 * @param money
	 *            l'argent a mettre au joueur
	 */
	public void set_player_money(String pseudo, int money) {
		get_player_from_pseudo(pseudo).set_money(money);
	}

	/**
	 * Méthode permettant de dire au jeu qu'un joueur a abandonné
	 * 
	 * @param pseudo
	 *            le pseudo du joueur qui a abandonné
	 */
	public void set_player_surrend(String pseudo) {
		get_player_from_pseudo(pseudo).set_surrend(true);
	}

	//
	// OBSERVER PART
	//

	@Override
	public void add_observer(Observer ob) {
		this.observers.add(ob);
	}

	@Override
	public void remove_observer(Observer ob) {
		this.observers.remove(ob);
	}

	@Override
	public void remove_all_observers() {
		this.observers.clear();
	}

	@Override
	public void notify_model_loaded(ArrayList<Abstract_terrain> terrains, ArrayList<Player> players,
			String your_pseudo) {
		for (Observer ob : observers) {
			ob.update_model_loaded(terrains, players, your_pseudo);
		}
	}

	@Override
	public void notify_player_money_change(String pseudo, int money) {
		for (Observer ob : observers) {
			ob.update_player_money_change(pseudo, money);
		}
	}

	@Override
	public void notify_player_message(String pseudo, String message) {
		for (Observer ob : observers) {
			ob.update_player_message(pseudo, message);
		}
	}

	@Override
	public void notify_player_turn(String pseudo) {
		for (Observer ob : observers) {
			ob.update_player_turn(pseudo);
		}
	}

	@Override
	public void notify_player_surrend(String pseudo) {
		for (Observer ob : observers) {
			ob.update_player_surrend(pseudo);
		}
	}

	@Override
	public void notify_winner(String pseudo_winner) {
		for (Observer ob : observers) {
			ob.update_winner(pseudo_winner);
		}
	}

	@Override
	public void notify_animation_dice(boolean launch) {
		for (Observer ob : observers) {
			ob.update_animation_dice(launch);
		}
	}

	@Override
	public void notify_dice_values(Vector<Integer> values) {
		for (Observer ob : observers) {
			ob.update_dice_values(values);
		}
	}

	@Override
	public void notify_piece_move(String pseudo, int position, boolean fast_move) {
		for (Observer ob : observers) {
			ob.update_piece_move(pseudo, position, fast_move);
		}
	}

	@Override
	public void notify_buy_possible(int number_house) {
		for (Observer ob : observers) {
			ob.update_buy_possible(number_house);
		}
	}

	@Override
	public void notify_buyed_house(String pseudo, int position_house) {
		for (Observer ob : observers) {
			ob.update_buyed_house(pseudo, position_house);
		}
	}

	@Override
	public void notify_mortgage_house(String pseudo, int number_house, boolean mortgaging) {
		for (Observer ob : observers) {
			ob.update_mortgage_house(pseudo, number_house, mortgaging);
		}
	}

	@Override
	public void notify_nb_house_property(String pseudo, int number_property, int number_house) {
		for (Observer ob : observers) {
			ob.update_nb_house_property(pseudo, number_property, number_house);
		}
	}

	@Override
	public void notify_player_jail(String pseudo, boolean in_jail) {
		for (Observer ob : observers) {
			ob.update_player_jail(pseudo, in_jail);
		}
	}

	@Override
	public void notify_refused_buy_house() {
		for (Observer ob : observers) {
			ob.update_refused_buy_house();
		}
	}

	@Override
	public void notify_need_manage_jail(String pseudo, boolean have_to_manage) {
		for (Observer ob : observers) {
			ob.update_need_manage_jail(pseudo, have_to_manage);
		}
	}

	@Override
	public void notify_selled_house(String pseudo, int position_house) {
		for (Observer ob : observers) {
			ob.update_selled_house(pseudo, position_house);
		}
	}

	@Override
	public void notify_need_pay_rent(String owner, int sum, boolean have_to_pay, String debter) {
		for (Observer ob : observers) {
			ob.update_need_pay_rent(owner, sum, have_to_pay, debter);
			if (have_to_pay) {
				Player p = get_player_from_pseudo(debter);
				if (p.get_capital() < sum) {
					notify_bankrupty(owner, sum, p.get_pseudo());
				}
			}
		}
	}

	@Override
	public void notify_auction_start(ArrayList<Player> players, int number_property, boolean auction) {
		for (Observer ob : observers) {
			ob.update_auction_start(players, number_property, auction);
		}
	}

	@Override
	public void notify_auction_value(String pseudo, int value) {
		for (Observer ob : observers) {
			ob.update_auction_value(pseudo, value);
		}
	}

	@Override
	public void notify_show_card_info(Card_type type_card, String description, boolean show) {
		for (Observer ob : observers) {
			ob.update_show_card_info(type_card, description, show);
		}
	}

	@Override
	public void notify_want_to_trade(Player emitter, ArrayList<Player> players, boolean show) {
		for (Observer ob : observers) {
			ob.update_want_to_trade(emitter, players, show);
		}
	}

	@Override
	public void notify_start_trade(Player first, Player second, boolean show) {
		for (Observer ob : observers) {
			ob.update_start_trade(first, second, show);
		}
	}

	@Override
	public void notify_trade_change_properties(int number_property, boolean add) {
		for (Observer ob : observers) {
			ob.update_trade_change_properties(number_property, add);
		}
	}

	@Override
	public void notify_trade_change_money(int money) {
		for (Observer ob : observers) {
			ob.update_trade_change_money(money);
		}
	}

	@Override
	public void notify_trade_validate(boolean versus, boolean validate) {
		for (Observer ob : observers) {
			ob.update_trade_validate(versus, validate);
		}
	}

	@Override
	public void notify_pause_game(boolean pause) {
		for (Observer ob : observers) {
			ob.update_pause_game(pause);
		}
	}

	@Override
	public void notify_repaint_root() {
		for (Observer ob : observers) {
			ob.update_repaint_root();
		}
	}

	@Override
	public void notify_fire_go() {
		for (Observer ob : observers) {
			ob.update_fire_go();
		}
	}

	@Override
	public void notify_message_receive(String message) {
		for (Observer ob : observers) {
			ob.update_message_receive(message);
		}
	}

	@Override
	public void notify_bankrupty(String owner, int sum, String debter) {
		for (Observer ob : observers) {
			ob.update_bankrupty(owner, sum, debter);
		}
	}

	@Override
	public void notify_start_panel_steal_card(Player stealer, ArrayList<Player> players, boolean show) {
		for (Observer ob : observers) {
			ob.update_start_panel_steal_card(stealer, players, show);
		}
	}

	@Override
	public void notify_start_panel_teleport_station(ArrayList<Station_terrain> stations, boolean show) {
		for (Observer ob : observers) {
			ob.update_start_panel_teleport_station(stations, show);
		}
	}

	@Override
	public void notify_start_panel_chose_nb_dice(boolean show) {
		for (Observer ob : observers) {
			ob.update_start_panel_chose_nb_dice(show);
		}
	}
}
