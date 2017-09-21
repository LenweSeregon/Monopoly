package mvc_network_controler;

import mvc_game_controler.Game_controler;
import network.Client_network_manager;
import view.Window;

public class Network_controler {

	private Client_network_manager client_ref;
	private Game_controler controler_game_ref;

	private Parser_message general;
	private Parser_game game_parser;
	private Window ref_win;

	/**
	 * Constructeur de la classe qui représente le controleur de réseau, c'est
	 * via ce controleur qu'on passe pour réaliser toutes les actions de
	 * communications entrantes et sortantes avec notre serveur
	 * 
	 * @param client
	 *            la référence sur notre client
	 * @param ref_win
	 *            la référence vers notre fenetre
	 */
	public Network_controler(Client_network_manager client, Window ref_win) {
		this.client_ref = client;
		this.ref_win = ref_win;
		this.general = new Parser_general(this);
		this.game_parser = new Parser_game(this);

		this.controler_game_ref = null;
		this.client_ref.launch_client_communications(this);
	}

	/**
	 * 
	 * @param controler
	 */
	public void bind_game_controler(Game_controler controler) {
		this.controler_game_ref = controler;
	}

	/**
	 * 
	 * @return
	 */
	public Game_controler get_game_controler() {
		return this.controler_game_ref;
	}

	/**
	 * 
	 * @return
	 */
	public String get_pseudo_client() {
		return this.client_ref.get_pseudo();
	}

	/**
	 * 
	 * @param message
	 */
	public void message_analyser(String message) {
		if (message == null) {
			// CRASH SERVER
			this.server_down();
		} else if (general.analyse_string(message)) {
			// System.out.println("Parsing general ok");
		} else if (game_parser.analyse_string(message)) {
			// System.out.println("Parsing game ok");
		} else {
			System.err.println("PARSING ERROR");
		}
	}

	/**
	 * Méthode permettant d'envoyer un message au serveur en passant par notre
	 * référence de client
	 * 
	 * @param message
	 *            le message du client
	 */
	public void send_message_to_server(String message) {
		this.client_ref.send_message(message);
	}

	/**
	 * Méthode permettant de lancer le jeu
	 */
	public void game_ready() {
		this.ref_win.launch_game(this);
	}

	/**
	 * Méthode permetttant de notifier que le serveur est down et qu'il faut
	 * lancer le panel de serveur fermé
	 */
	public void server_down() {
		ref_win.launch_server_down_menu();
	}

	/**
	 * Méthode permettant de dire au serveru que le client est actuellement en
	 * ligne
	 */
	public void set_status_online() {
		this.client_ref.send_message("ACCOUNT" + " " + "STATE_LOBBY" + " " + "ONLINE");
	}

	/**
	 * Méthode permettant de dire au serveur que le client est actuellement
	 * occupé
	 */
	public void set_status_occupied() {
		this.client_ref.send_message("ACCOUNT" + " " + "STATE_LOBBY" + " " + "OCCUPIED");
	}

	/**
	 * Méthode permettant de dire au serveur que le client est actuellement
	 * absent
	 */
	public void set_status_missing() {
		this.client_ref.send_message("ACCOUNT" + " " + "STATE_LOBBY" + " " + "MISSING");
	}

	/**
	 * Méthode permettant de dire au serveur que le client est en recherche de
	 * partie
	 */
	public void set_status_search_game() {
		this.client_ref.send_message("ACCOUNT" + " " + "STATE_LOBBY" + " " + "SEARCH_GAME");
	}

	/**
	 * Méthode permettant de dire au serveur que le client se deconnecte
	 */
	public void disconnect() {
		this.client_ref.send_message("ACCOUNT" + " " + "DISCONNECTION");
	}
}
