package mvc_network_controler;

import mvc_network_model.Client_entity;
import mvc_network_model.Client_state;
import mvc_network_model.Server;
import utils.DB;

public class Parser_account extends Parser_client_message {

	protected Server model_ref;
	protected Server_controler controler_ref;

	/**
	 * Constructeur de la classe qui représente le parsage de message associé
	 * aux comptes durant la communication entre le serveur et le client
	 * 
	 * @param m_r
	 *            référence vers le modele du serveur
	 * @param c_r
	 *            référence vers le controleur du serveur
	 */
	public Parser_account(Server m_r, Server_controler c_r) {
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

			if (parse_type.equals("ACCOUNT")) {
				if (parse_info.equals("CONNECTION")) {
					return analyse_connection(client, message);
				} else if (parse_info.equals("SUBSCRIPTION")) {
					return analyse_subscription(client, message);
				} else if (parse_info.equals("DISCONNECTION")) {
					controler_ref.ask_remove_client(client);
					return true;
				} else if (parse_info.equals("STATE_LOBBY")) {
					return analyse_lobby(client, message);
				} else {

					return false;
				}
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	/**
	 * Méthode privée permettant de savoir si l'analyse du message en tant que
	 * connexion c'est bien passé
	 * 
	 * @param client
	 *            le client qui a emis le message
	 * @param message
	 *            le message du client
	 * @return retourne vrai si le parsing a été réussi, faux sinon
	 */
	private boolean analyse_connection(Client_entity client, String message) {
		String[] splitted = message.split(" ");
		if (splitted.length == 4) {
			String pseudo = splitted[2];
			String pwd = splitted[3];

			if (model_ref.get_client_by_pseudo(pseudo) != null) {
				client.send_message("ALREADY_CONNECTED");
				return false;
			} else if (DB.get_instance().able_to_connect(pseudo, pwd)) {
				client.set_pseudo(pseudo);
				controler_ref.add_client_to_server(client);
				client.send_message("RIGHT_IDENTIFIANTS");
			} else {
				client.send_message("WRONG_IDENTIFIANTS");
			}
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Méthode privée permettant de savoir si l'analyse du message en tant que
	 * message de lobby c'est bien passé
	 * 
	 * @param client
	 *            le client qui a emis le message
	 * @param message
	 *            le message du client
	 * @return retourne vrai si le parsing a été réussi, faux sinon
	 */
	private boolean analyse_lobby(Client_entity client, String message) {
		String[] splitted = message.split(" ");
		if (splitted.length == 3) {
			String info_lobby = splitted[2];
			if (info_lobby.equals("ONLINE")) {
				client.set_state(Client_state.ONLINE);
				controler_ref.client_status_change(client);
			} else if (info_lobby.equals("OCCUPIED")) {
				client.set_state(Client_state.OCCUPIED);
				controler_ref.client_status_change(client);
			} else if (info_lobby.equals("MISSING")) {
				client.set_state(Client_state.MISSING);
				controler_ref.client_status_change(client);
			} else if (info_lobby.equals("SEARCH_GAME")) {
				client.set_state(Client_state.LOOKING_FOR_GAME);
				controler_ref.client_status_change(client);
			}
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Méthode privée permettant de savoir si l'analyse du message en tant
	 * qu'inscription c'est bien passé
	 * 
	 * @param client
	 *            le client qui a emis le message
	 * @param message
	 *            le message du client
	 * @return retourne vrai si le parsing a été réussi, faux sinon
	 */
	private boolean analyse_subscription(Client_entity client, String message) {
		String[] splitted = message.split(" ");
		if (splitted.length == 5) {
			String pseudo = splitted[2];
			String pwd = splitted[3];

			if (DB.get_instance().name_available(pseudo)) {
				DB.get_instance().insert_user(pseudo, pwd);
				client.send_message("SUBSCRIPTION_ACCEPTED");
			} else {
				client.send_message("PSEUDO_ALREADY_TAKEN");
			}

			return true;
		} else {
			return false;
		}
	}
}
