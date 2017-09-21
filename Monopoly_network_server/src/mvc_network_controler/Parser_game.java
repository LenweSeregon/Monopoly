package mvc_network_controler;

import configure_settings.Setting_configuration;
import mvc_network_model.Client_entity;
import mvc_network_model.Server;

public class Parser_game extends Parser_client_message {

	protected Server model_ref;
	protected Server_controler controler_ref;

	/**
	 * Constructeur de la classe qui représente le parsage de message associé
	 * aux parties durant la communication entre le serveur et le client
	 * 
	 * @param m_r
	 *            référence vers le modele du serveur
	 * @param c_r
	 *            référence vers le controleur du serveur
	 */
	public Parser_game(Server m_r, Server_controler c_r) {
		super("Parser account");
		this.model_ref = m_r;
		this.controler_ref = c_r;
	}

	@Override
	public boolean analyse_string(Client_entity client, String message) {
		String[] splitted = message.split(" ");

		if (splitted.length >= 2) {
			String parse_type = splitted[0];
			String parse_info = splitted[1];
			String parse_info_bis = splitted[2];

			if (parse_type.equals("GAME")) {
				if (parse_info.equals("CONFIG")) {
					if (parse_info_bis.equals("TERRAINS")) {
						return analyse_config_terrain_message(client, message);
					} else {
						return analyse_config_message(client, message);
					}
				} else if (parse_info.equalsIgnoreCase("BUILD_BOARD")) {
					if (parse_info_bis.equals("OK")) {
						controler_ref.ask_notify_client_in_game(client.get_pseudo(), "GAME BUILD_BOARD OK");
						return true;
					}
				} else if (parse_info.equals("IN_GAME")) {
					return analyse_in_game(client, message);
				}
			} else {
				return false;
			}
		}
		return false;
	}

	/**
	 * Méthode privée permettant de savoir si l'analyse du message en tant que
	 * message en jeu c'est bien passé
	 * 
	 * @param client
	 *            le client qui a emis le message
	 * @param message
	 *            le message du client
	 * @return retourne vrai si le parsing a été réussi, faux sinon
	 */
	private boolean analyse_in_game(Client_entity client, String message) {
		String[] splitted = message.split(" ");

		if (splitted[2].equals("SURREND")) {
			controler_ref.ask_notify_client_in_game(client.get_pseudo(), "SURREND");
			return true;
		} else if (splitted[2].equals("NEXT_PLAYER")) {
			controler_ref.ask_notify_client_in_game(client.get_pseudo(), "NEXT_PLAYER");
			return true;
		} else if (splitted[2].equals("WINNER")) {
			controler_ref.ask_notify_client_in_game(client.get_pseudo(), "WINNER");
			return true;
		} else if (splitted[2].equals("DICE_ROLLING")) {
			controler_ref.ask_notify_client_in_game(client.get_pseudo(), "DICE_ROLLING");
			return true;
		} else if (splitted[2].equals("DICE_VALUES")) {
			String send = "DICE_VALUES&!";
			for (int i = 1; i <= Setting_configuration.nb_dices; i++) {
				send += splitted[2 + i] + "&!";
			}
			controler_ref.ask_notify_client_in_game(client.get_pseudo(), send);
			return true;
		} else if (splitted[2].equals("PIECE_MOVE")) {
			controler_ref.ask_notify_client_in_game(client.get_pseudo(),
					"PIECE_MOVE" + "&!" + splitted[3] + "&!" + splitted[4]);
			return true;
		} else if (splitted[2].equals("PLAYER_BUY")) {
			controler_ref.ask_notify_client_in_game(client.get_pseudo(),
					"PLAYER_BUY" + "&!" + splitted[3] + "&!" + splitted[4]);
			return true;
		} else if (splitted[2].equals("MORTGAGE")) {
			controler_ref.ask_notify_client_in_game(client.get_pseudo(),
					"MORTGAGE" + "&!" + splitted[3] + "&!" + splitted[4] + "&!" + splitted[5]);
			return true;
		} else if (splitted[2].equals("UNMORTGAGE")) {
			controler_ref.ask_notify_client_in_game(client.get_pseudo(),
					"UNMORTGAGE" + "&!" + splitted[3] + "&!" + splitted[4] + "&!" + splitted[5]);
			return true;
		} else if (splitted[2].equals("ADD_HOUSE")) {
			controler_ref.ask_notify_client_in_game(client.get_pseudo(),
					"ADD_HOUSE" + "&!" + splitted[3] + "&!" + splitted[4] + "&!" + splitted[5]);
			return true;
		} else if (splitted[2].equals("JAIL_OUT")) {
			controler_ref.ask_notify_client_in_game(client.get_pseudo(), "JAIL_OUT&!" + splitted[3]);
			return true;
		} else if (splitted[2].equals("REMOVE_PROP")) {
			controler_ref.ask_notify_client_in_game(client.get_pseudo(),
					"REMOVE_PROP" + "&!" + splitted[3] + "&!" + splitted[4]);
			return true;
		} else if (splitted[2].equals("PAY")) {
			controler_ref.ask_notify_client_in_game(client.get_pseudo(),
					"PAY" + "&!" + splitted[3] + "&!" + splitted[4] + "&!" + splitted[5]);
			return true;
		} else if (splitted[2].equals("AUCTION_START")) {
			controler_ref.ask_notify_client_in_game(client.get_pseudo(), "AUCTION_START" + "&!" + splitted[3]);
			return true;
		} else if (splitted[2].equals("AUCTION_VALUE")) {
			controler_ref.ask_notify_client_in_game(client.get_pseudo(),
					"AUCTION_VALUE" + "&!" + splitted[3] + "&!" + splitted[4]);
			return true;
		} else if (splitted[2].equals("AUCTION_END")) {
			controler_ref.ask_notify_client_in_game(client.get_pseudo(), "AUCTION_END");
			return true;
		} else if (splitted[2].equals("TRADE")) {
			controler_ref.ask_notify_client_in_game(client.get_pseudo(),
					"TRADE" + "&!" + splitted[3] + "&!" + splitted[4]);
			return true;
		} else if (splitted[2].equals("TRADE_PROP")) {
			controler_ref.ask_notify_client_in_game(client.get_pseudo(),
					"TRADE_PROP" + "&!" + splitted[3] + "&!" + splitted[4] + "&!" + splitted[5]);
			return true;
		} else if (splitted[2].equals("TRADE_MONEY")) {
			controler_ref.ask_notify_client_in_game(client.get_pseudo(),
					"TRADE_MONEY" + "&!" + splitted[3] + "&!" + splitted[4]);
			return true;
		} else if (splitted[2].equals("TRADE_CANCEL")) {
			controler_ref.ask_notify_client_in_game(client.get_pseudo(), "TRADE_CANCEL");
			return true;
		} else if (splitted[2].equals("TRADE_VALIDATE")) {
			controler_ref.ask_notify_client_in_game(client.get_pseudo(),
					"TRADE_VALIDATE" + "&!" + splitted[3] + "&!" + splitted[4]);
			return true;
		} else if (splitted[2].equals("TRADE_END")) {
			controler_ref.ask_notify_client_in_game(client.get_pseudo(), "TRADE_END");
			return true;
		} else if (splitted[2].equals("PAUSE")) {
			controler_ref.ask_notify_client_in_game(client.get_pseudo(), "PAUSE" + "&!" + splitted[3]);
			return true;
		} else if (splitted[2].equals("MESSAGE")) {
			controler_ref.ask_notify_client_in_game(client.get_pseudo(), message);
			return true;
		} else if (splitted[2].equals("BANKRUPTCY")) {
			controler_ref.ask_notify_client_in_game(client.get_pseudo(),
					"BANKRUPTCY" + "&!" + splitted[3] + "&!" + splitted[4] + "&!" + splitted[5]);
			return true;
		} else if (splitted[2].equals("STOLE")) {
			controler_ref.ask_notify_client_in_game(client.get_pseudo(),
					"STOLE" + "&!" + splitted[3] + "&!" + splitted[4] + "&!" + splitted[5]);
			return true;
		} else if (splitted[2].equals("TELEPORT")) {
			controler_ref.ask_notify_client_in_game(client.get_pseudo(),
					"TELEPORT" + "&!" + splitted[3] + "&!" + splitted[4]);
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Méthode privée permettant de savoir si l'analyse du message en tant que
	 * configuration des terrains de jeu c'est bien passé
	 * 
	 * @param client
	 *            le client qui a emis le message
	 * @param message
	 *            le message du client
	 * @return retourne vrai si le parsing a été réussi, faux sinon
	 */
	public boolean analyse_config_terrain_message(Client_entity client, String message) {
		String[] splitted = message.split(" ");

		if (splitted[3].equals("OK")) {
			controler_ref.ask_notify_client_in_game(client.get_pseudo(), "GAME CONFIG TERRAINS OK");
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Méthode privée permettant de savoir si l'analyse du message en tant que
	 * configration des variantes de jeu c'est bien passé
	 * 
	 * @param client
	 *            le client qui a emis le message
	 * @param message
	 *            le message du client
	 * @return retourne vrai si le parsing a été réussi, faux sinon
	 */
	public boolean analyse_config_message(Client_entity client, String message) {
		String[] splitted = message.split(" ");

		if (splitted[2].equals("OK")) {
			controler_ref.ask_notify_client_in_game(client.get_pseudo(), "GAME CONFIG OK");
			return true;
		} else {
			return false;
		}
	}
}
