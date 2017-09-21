package mvc_network_model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Vector;

import com.google.gson.Gson;

import configure_settings.Setting_configuration;
import configure_terrain_view.My_table_model;
import configure_terrain_view.Table_terrain_value;
import configure_terrain_view.Terrain_type;

public class Game_entity implements Runnable {

	private Vector<Client_network_manager> clients_ref;
	private Vector<Boolean> clients_ready;
	volatile private boolean is_running;

	volatile private boolean go_on_config;
	volatile private boolean go_on_config_terrain;

	volatile private Thread config_general;
	volatile private Thread config_terrain;
	volatile private Thread build_board;
	volatile private boolean in_pause;

	volatile private Vector<Table_terrain_value> terrains;

	/**
	 * Constructeur de la classe représentant une partie de jeu. En effet, cette
	 * classe permet de gérer une partie entre joueurs. Elle contient une
	 * référence vers les clients et permet de lancer les configurations et de
	 * ne communiquer qu'avec les joueurs présents.
	 * 
	 * @param clients
	 */
	public Game_entity(Vector<Client_network_manager> clients) {
		this.is_running = false;
		this.go_on_config = false;
		this.go_on_config_terrain = false;
		this.in_pause = false;
		this.config_general = null;
		this.config_terrain = null;
		this.build_board = null;
		this.clients_ref = new Vector<Client_network_manager>();
		this.clients_ready = new Vector<Boolean>();
		for (Client_network_manager c : clients) {
			this.clients_ref.add(c);
			this.clients_ready.add(new Boolean(false));
		}
	}

	/**
	 * Méthode permettant de récupérer tout les clients dans la partie
	 * 
	 * @return les clients de la partie dans un vecteur
	 */
	public Vector<Client_network_manager> get_clients_in_game() {
		return this.clients_ref;
	}

	/**
	 * Permet de connaitre le nombre de joueur dans la partie
	 * 
	 * @return le nombre de joueur
	 */
	public int get_nb_clients_in_game() {
		return this.clients_ref.size();
	}

	/**
	 * Permet de stopper la partie lorsque plus aucun joueur est la pour
	 * permettre de stopper le thread
	 */
	public void stop_game() {
		this.is_running = false;
	}

	/**
	 * Permet de supprimer un client spécifique de la partie par rapport à son
	 * id
	 * 
	 * @param pseudo
	 *            le pseudo du client à supprimer de la partie
	 */
	public void remove_client(String pseudo) {
		int position = -1;
		for (int i = 0; i < clients_ref.size(); i++) {
			if (clients_ref.get(i).get_pseudo().equals(pseudo)) {
				position = i;
			}
		}

		if (position != -1) {
			clients_ref.remove(position);
		}
	}

	/**
	 * Permet de savoir si un client avec un pseudo donné se trouve dans la
	 * partie
	 * 
	 * @param pseudo
	 *            le pseudo du client à chercher
	 * @return vrai si il est dans la partie, faux sinon
	 */
	public boolean client_in_this_game(String pseudo) {
		for (Client_network_manager c : clients_ref) {
			if (c.get_pseudo().equals(pseudo)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Méthode permettant de dispatcher un message aux clients de la partie en
	 * fonction du parser, cette méthode s'assure juste de la bonne construction
	 * de la phrase.
	 * 
	 * @param pseudo
	 *            le pseudo du client qui envoie le message
	 * @param message
	 *            le message qui a été envoyé
	 */
	public void dispatch_message_to_client(String pseudo, String message) {
		int i = 0;
		String[] split = message.split("&!");
		for (Client_network_manager c : clients_ref) {
			if (c.get_pseudo().equals(pseudo)) {
				if (message.equals("GAME CONFIG OK")) {
					go_on_config = true;
				} else if (message.equals("GAME CONFIG TERRAINS OK")) {
					go_on_config_terrain = true;
				} else if (message.equals("GAME BUILD_BOARD OK")) {
					this.clients_ready.set(i, true);
				} else if (message.equals("SURREND")) {
					broadcast_to_all_player_from(pseudo, "GAME&!IN_GAME&!SURREND&!" + pseudo);
				} else if (message.equals("NEXT_PLAYER")) {
					broadcast_to_all_player("GAME&!IN_GAME&!NEXT_PLAYER");
				} else if (message.equals("WINNER")) {
					broadcast_to_all_player("GAME&!IN_GAME&!WINNER&!" + pseudo);
				} else if (message.equals("DICE_ROLLING")) {
					broadcast_to_all_player("GAME&!IN_GAME&!DICE_ROLLING");
				} else if (split[0].equals("DICE_VALUES")) {
					broadcast_to_all_player("GAME&!IN_GAME&!" + message);
				} else if (split[0].equals("PIECE_MOVE")) {
					broadcast_to_all_player("GAME&!IN_GAME&!PIECE_MOVE&!" + split[1] + "&!" + split[2]);
				} else if (split[0].equals("PLAYER_BUY")) {
					broadcast_to_all_player_from(pseudo, "GAME&!IN_GAME&!PLAYER_BUY&!" + split[1] + "&!" + split[2]);
				} else if (split[0].equals("MORTGAGE")) {
					broadcast_to_all_player_from(pseudo,
							"GAME&!IN_GAME&!MORTGAGE&!" + split[1] + "&!" + split[2] + "&!" + split[3]);
				} else if (split[0].equals("UNMORTGAGE")) {
					broadcast_to_all_player_from(pseudo,
							"GAME&!IN_GAME&!UNMORTGAGE&!" + split[1] + "&!" + split[2] + "&!" + split[3]);
				} else if (split[0].equals("ADD_HOUSE")) {
					broadcast_to_all_player_from(pseudo,
							"GAME&!IN_GAME&!ADD_HOUSE&!" + split[1] + "&!" + split[2] + "&!" + split[3]);
				} else if (split[0].equals("JAIL_OUT")) {
					broadcast_to_all_player_from(pseudo, "GAME&!IN_GAME&!JAIL_OUT&!" + split[1]);
				} else if (split[0].equals("REMOVE_PROP")) {
					broadcast_to_all_player_from(pseudo, "GAME&!IN_GAME&!REMOVE_PROP&!" + split[1] + "&!" + split[2]);
				} else if (split[0].equals("PAY")) {
					broadcast_to_all_player("GAME&!IN_GAME&!PAY&!" + split[1] + "&!" + split[2] + "&!" + split[3]);
				} else if (split[0].equals("AUCTION_START")) {
					broadcast_to_all_player("GAME&!IN_GAME&!AUCTION_START&!" + split[1]);
					new Thread(new Runnable() {
						@Override
						public void run() {
							try {
								Thread.sleep(Setting_configuration.minutes_for_auction * 1000);
								broadcast_to_all_player("GAME&!IN_GAME&!AUCTION_END");
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
					}).start();
				} else if (split[0].equals("AUCTION_VALUE")) {
					broadcast_to_all_player("GAME&!IN_GAME&!AUCTION_VALUE&!" + split[1] + "&!" + split[2]);
				} else if (split[0].equals("TRADE")) {
					broadcast_to_all_player("GAME&!IN_GAME&!TRADE&!" + split[1] + "&!" + split[2]);
				} else if (split[0].equals("TRADE_PROP")) {
					broadcast_to_all_player_from(pseudo,
							"GAME&!IN_GAME&!TRADE_PROP&!" + split[1] + "&!" + split[2] + "&!" + split[3]);
				} else if (split[0].equals("TRADE_MONEY")) {
					broadcast_to_all_player_from(pseudo, "GAME&!IN_GAME&!TRADE_MONEY&!" + split[1] + "&!" + split[2]);
				} else if (split[0].equals("TRADE_CANCEL")) {
					broadcast_to_all_player("GAME&!IN_GAME&!TRADE_CANCEL");
				} else if (split[0].equals("TRADE_VALIDATE")) {
					broadcast_to_all_player_from(pseudo,
							"GAME&!IN_GAME&!TRADE_VALIDATE&!" + split[1] + "&!" + split[2]);
				} else if (split[0].equals("TRADE_END")) {
					broadcast_to_all_player("GAME&!IN_GAME&!TRADE_END");
				} else if (split[0].equals("PAUSE")) {
					in_pause = Boolean.valueOf(split[1]);
					broadcast_to_all_player("GAME&!IN_GAME&!PAUSE&!" + split[1]);
				} else if (split[0].equals("BANKRUPTCY")) {
					broadcast_to_all_player(
							"GAME&!IN_GAME&!BANKRUPTCY&!" + split[1] + "&!" + split[2] + "&!" + split[3]);
				} else if (split[0].equals("STOLE")) {
					broadcast_to_all_player("GAME&!IN_GAME&!STOLE&!" + split[1] + "&!" + split[2] + "&!" + split[3]);
				} else if (split[0].equals("TELEPORT")) {
					broadcast_to_all_player("GAME&!IN_GAME&!TELEPORT&!" + split[1] + "&!" + split[2]);
				} else if (message.split(" ")[2].equals("MESSAGE")) {
					String[] splitted = message.split(" ");
					String reconstitute = "";
					for (int k = 3; k < splitted.length; k++) {
						if (k == splitted.length - 1) {
							reconstitute += splitted[k];
						} else {
							reconstitute += splitted[k] + " ";
						}
					}
					broadcast_to_all_player("GAME&!IN_GAME&!MESSAGE&!" + reconstitute);
				}
			}
			i++;
		}
	}

	/**
	 * Méthode permettant d'envoyer un message à tout les joueurs de la partie
	 * 
	 * @param message
	 *            le message a envoyer
	 */
	public void broadcast_to_all_player(String message) {
		for (Client_network_manager c : clients_ref) {
			c.send_message(message);
		}
	}

	/**
	 * Méthode permettant d'envoyer un message à tout les joueurs de la partie
	 * sauf celui qui a émis le message
	 * 
	 * @param pseudo_broadcaster
	 *            le pseudo du client qui a émis le message
	 * @param message
	 *            le message a envoyer
	 */
	public void broadcast_to_all_player_from(String pseudo_broadcaster, String message) {
		for (Client_network_manager c : clients_ref) {
			if (!(c.get_pseudo().equals(pseudo_broadcaster))) {
				c.send_message(message);
			}
		}
	}

	/**
	 * Méthode permettant de démarrer une partie
	 */
	public void start_game() {
		this.broadcast_to_all_player("GENERAL" + " " + "GAME_READY");
	}

	/**
	 * Méthode permettant d'envoyer les configuration de variantes aux joueurs,
	 * cette méthode crée d'abord tout les messages et les envoyes ensuie en
	 * s'assurant que le dernier message a bine été lu
	 */
	private void send_config() {
		Vector<String> messages_config_to_send = new Vector<String>();
		messages_config_to_send.add("GAME" + "&!" + "CONFIG" + "&!" + "START");
		messages_config_to_send
				.add("GAME" + "&!" + "CONFIG" + "&!" + "starting_money=" + Setting_configuration.starting_money);
		messages_config_to_send
				.add("GAME" + "&!" + "CONFIG" + "&!" + "random_position=" + Setting_configuration.random_position);
		messages_config_to_send.add("GAME" + "&!" + "CONFIG" + "&!" + "random_properties_gave="
				+ Setting_configuration.random_properties_gave);
		messages_config_to_send.add("GAME" + "&!" + "CONFIG" + "&!" + "nb_dices=" + Setting_configuration.nb_dices);
		messages_config_to_send
				.add("GAME" + "&!" + "CONFIG" + "&!" + "ask_nb_dices_turn=" + Setting_configuration.ask_nb_dices_turn);
		messages_config_to_send.add(
				"GAME" + "&!" + "CONFIG" + "&!" + "jail_if_sum_3_dices=" + Setting_configuration.jail_if_sum_3_dices);
		messages_config_to_send.add("GAME" + "&!" + "CONFIG" + "&!" + "nb_house=" + Setting_configuration.nb_house);
		messages_config_to_send.add("GAME" + "&!" + "CONFIG" + "&!" + "nb_hotel=" + Setting_configuration.nb_hotel);
		messages_config_to_send.add(
				"GAME" + "&!" + "CONFIG" + "&!" + "nb_turn_before_end=" + Setting_configuration.nb_turn_before_end);
		messages_config_to_send
				.add("GAME" + "&!" + "CONFIG" + "&!" + "suicide_mod=" + Setting_configuration.suicide_mod);
		messages_config_to_send
				.add("GAME" + "&!" + "CONFIG" + "&!" + "rent_double=" + Setting_configuration.rent_double);
		messages_config_to_send
				.add("GAME" + "&!" + "CONFIG" + "&!" + "auction_allow=" + Setting_configuration.auction_allow);
		messages_config_to_send.add(
				"GAME" + "&!" + "CONFIG" + "&!" + "minutes_for_auction=" + Setting_configuration.minutes_for_auction);
		messages_config_to_send
				.add("GAME" + "&!" + "CONFIG" + "&!" + "random_board=" + Setting_configuration.random_board);
		messages_config_to_send.add("GAME" + "&!" + "CONFIG" + "&!" + "mask_board=" + Setting_configuration.mask_board);
		messages_config_to_send.add("GAME" + "&!" + "CONFIG" + "&!" + "no_care_equality_house="
				+ Setting_configuration.no_care_equality_house);
		messages_config_to_send
				.add("GAME" + "&!" + "CONFIG" + "&!" + "percentage_sell=" + Setting_configuration.percentage_sell);
		messages_config_to_send.add(
				"GAME" + "&!" + "CONFIG" + "&!" + "rate_rebuy_hypotheck=" + Setting_configuration.rate_rebuy_hypotheck);
		messages_config_to_send.add("GAME" + "&!" + "CONFIG" + "&!" + "teleportation_station="
				+ Setting_configuration.teleportation_station);
		messages_config_to_send
				.add("GAME" + "&!" + "CONFIG" + "&!" + "nb_turn_in_jail=" + Setting_configuration.nb_turn_in_jail);
		messages_config_to_send
				.add("GAME" + "&!" + "CONFIG" + "&!" + "sum_to_out_jail=" + Setting_configuration.sum_to_out_jail);
		messages_config_to_send
				.add("GAME" + "&!" + "CONFIG" + "&!" + "jail_3_double=" + Setting_configuration.jail_3_double);
		messages_config_to_send.add("GAME" + "&!" + "CONFIG" + "&!" + "allow_earn_money_during_jailing="
				+ Setting_configuration.allow_earn_money_during_jailing);
		messages_config_to_send.add("GAME" + "&!" + "CONFIG" + "&!" + "rent_each_turn_jailing="
				+ Setting_configuration.rent_each_turn_jailing);
		messages_config_to_send.add("GAME" + "&!" + "CONFIG" + "&!" + "double_sum_on_case_start="
				+ Setting_configuration.double_sum_on_case_start);
		messages_config_to_send.add("GAME" + "&!" + "CONFIG" + "&!" + "tax_according_to_player_money="
				+ Setting_configuration.tax_according_to_player_money);
		messages_config_to_send.add("GAME" + "&!" + "CONFIG" + "&!" + "earn_money_on_parking="
				+ Setting_configuration.earn_money_on_parking);
		messages_config_to_send.add("GAME" + "&!" + "CONFIG" + "&!" + "steal_card=" + Setting_configuration.steal_card);
		messages_config_to_send.add("GAME" + "&!" + "CONFIG" + "&!" + "mix_card=" + Setting_configuration.mix_card);
		messages_config_to_send.add("GAME" + "&!" + "CONFIG" + "&!" + "END");

		try {
			Thread.sleep(50);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		this.config_general = new Thread(new Runnable() {
			@Override
			public void run() {
				for (Client_network_manager c : clients_ref) {
					for (String str : messages_config_to_send) {
						go_on_config = false;
						c.send_message(str);
						while (!go_on_config) {
							try {
								Thread.sleep(10);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
					}
				}
			}
		});
		this.config_general.start();

	}

	/**
	 * Méthode permettant d'envoyer les propriétés qui ont été peut etre modifié
	 * par le serveur aux différents clients
	 */
	public void send_properties() {
		terrains = My_table_model.get_terrains();

		if (Setting_configuration.random_board) {
			Vector<Integer> numbers = new Vector<Integer>();
			for (int i = 1; i <= 40; i++) {
				numbers.add(i);
			}

			Collections.shuffle(numbers);

			// Replace GO
			{
				int index_target_1 = numbers.indexOf(1);
				Collections.swap(numbers, 0, index_target_1);
			}
			// Replace JAIL
			{
				int index_target_1 = numbers.indexOf(11);
				Collections.swap(numbers, 10, index_target_1);
			}
			// Replace PARKING
			{
				int index_target_1 = numbers.indexOf(21);
				Collections.swap(numbers, 20, index_target_1);
			}
			// Replace GO_JAIL
			{
				int index_target_1 = numbers.indexOf(31);
				Collections.swap(numbers, 30, index_target_1);
			}

			for (int k = 0; k < terrains.size(); k++) {
				terrains.get(k).set_number(numbers.get(k));
			}
		}

		try {
			Thread.sleep(50);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		this.config_terrain = new Thread(new Runnable() {
			@Override
			public void run() {
				for (Client_network_manager c : clients_ref) {
					for (Table_terrain_value t : terrains) {
						go_on_config_terrain = false;
						Gson g = new Gson();
						String json_format = g.toJson(t);
						c.send_message("GAME" + "&!" + "CONFIG" + "&!" + "TERRAINS" + "&!" + json_format);

						while (!go_on_config_terrain) {
							try {
								Thread.sleep(50);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
					}
				}

			}
		});
		this.config_terrain.start();
	}

	/**
	 * Méthode permettant d'envoyer l'ordre des terrains aux différents clients
	 */
	public void send_order_to_build_board() {

		ArrayList<Integer> pos_nbr = new ArrayList<Integer>();

		if (Setting_configuration.random_properties_gave) {
			for (Table_terrain_value terrain : terrains) {
				if (terrain.get_type() == Terrain_type.PROPERTY || terrain.get_type() == Terrain_type.COMPANY
						|| terrain.get_type() == Terrain_type.STATION) {
					pos_nbr.add(terrain.get_number());
				}
			}
			Collections.shuffle(pos_nbr);
		}

		String pseudos = "";
		int it = 0;
		for (int i = 0; i < clients_ref.size(); i++) {
			int pos = 1;
			if (Setting_configuration.random_position) {
				Random rand = new Random();
				pos = rand.nextInt(40) + 1;
			}

			if (Setting_configuration.random_properties_gave) {
				String props = "&!" + pos_nbr.get(it++) + "&!" + pos_nbr.get(it++) + "&!" + pos_nbr.get(it++);
				if (i == clients_ref.size() - 1) {
					pseudos += i + 1 + "&!" + clients_ref.get(i).get_pseudo() + "&!" + pos + props;
				} else {
					pseudos += i + 1 + "&!" + clients_ref.get(i).get_pseudo() + "&!" + pos + "" + props + ":";
				}
			} else {
				if (i == clients_ref.size() - 1) {
					pseudos += i + 1 + "&!" + clients_ref.get(i).get_pseudo() + "&!" + pos;
				} else {
					pseudos += i + 1 + "&!" + clients_ref.get(i).get_pseudo() + "&!" + pos + ":";
				}
			}
		}

		this.broadcast_to_all_player("GENERAL" + " " + "BUILD_BOARD" + " " + pseudos);

		this.build_board = new Thread(new Runnable() {
			@Override
			public void run() {
				boolean done = false;
				while (!done) {
					done = true;
					for (Boolean b : clients_ready) {
						if (!b) {
							done = false;
						}
					}
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});

		this.build_board.start();
	}

	@Override
	public void run() {
		// Launching game for players
		this.is_running = true;
		this.start_game();

		// Sending config
		this.send_config();

		try {
			config_general.join();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}

		// Send properties
		this.send_properties();
		// Waiting for properties
		try {
			this.config_terrain.join();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}

		// Sending order to create board
		this.send_order_to_build_board();

		// Wait board from all players
		try {
			this.build_board.join();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}

		broadcast_to_all_player("GAME&!IN_GAME&!FIRE_GO");

		new Thread(new Runnable() {
			@Override
			public void run() {
				int it = 0;
				while (is_running) {

					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					if (!in_pause) {
						it++;
						if (it >= 300000) { // 5 minutes = 300000
							it = 0;
							broadcast_to_all_player("GAME&!IN_GAME&!RENT_UP");
						}
					}
				}
			}
		}).start();

		// Starting game loop
		while (is_running) {
			try {

				Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
