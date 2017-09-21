package mvc_network_controler;

import java.net.Socket;
import java.util.Vector;

import mvc_network_model.Client_entity;
import mvc_network_model.Client_network_manager;
import mvc_network_model.Client_state;
import mvc_network_model.Game_entity;
import mvc_network_model.Server;
import mvc_network_model.Server_state;

public class Server_controler {

	private Server server_model;
	private Connection_accepter conn_acc;

	private Parser_client_message account_parser;
	private Parser_client_message game_parser;

	/**
	 * Constructeur du controler du serveur qui va permettre de mettre en place
	 * le pattern MVC. Ce controler va gérer toutes les différentes actions
	 * lorsqu'un evenement intervient sur le sereur
	 * 
	 * @param model
	 *            une référence vers le modele
	 */
	public Server_controler(Server model) {
		this.server_model = model;

		account_parser = new Parser_account(server_model, this);
		game_parser = new Parser_game(server_model, this);
	}

	/**
	 * Methode permettant d'analyser avec les différents analyseurs présents
	 * dans le controleur le message d'un client
	 * 
	 * @param client
	 *            le client qui a envoyé un message
	 * @param message
	 *            le message envoyé
	 */
	public void message_analyser(Client_entity client, String message) {
		if (client.get_pseudo() == "") {
			server_model.notify_message_console("Stranger" + " : " + message);
		} else {
			server_model.notify_message_console(client.get_pseudo() + " : " + message);
		}

		if (message == null) {
			ask_remove_client(client);
		} else if (account_parser.analyse_string(client, message)) {
		} else if (game_parser.analyse_string(client, message)) {
		} else {
			server_model.notify_message_console("Impossible parsing message, error");
		}
	}

	/**
	 * Méthode permettant de recevoir la connexion d'un nouveau inconnu sur le
	 * serveur (celui ci vient de communiquer avec le serveur)
	 * 
	 * @param socket_comm
	 *            le socket de ocmmunication avec ce client
	 */
	public void ask_new_client_connection(Socket socket_comm) {
		Client_entity new_client = new Client_entity("");
		if (new_client != null) {
			server_model.notify_message_console("New stranger connection");
			new_client.bind_socket(socket_comm);

			// On recoit le message
			String msg = new_client.receive_message();
			message_analyser(new_client, msg);

		}
	}

	/**
	 * Méthode permettant de changer le status d'un client pour l'actualiser
	 * chez les autres clients
	 * 
	 * @param client
	 *            le client a qui il faut changer le status
	 */
	public void client_status_change(Client_entity client) {
		server_model.notify_client_status_change(client);
		server_model.notify_message_console(
				"Client " + client.get_pseudo() + " change status : " + client.get_state().toString());
	}

	/**
	 * Permet d'ajouter un client à la liste des clients connectés aux serveurs
	 * 
	 * @param client
	 *            le client à ajouter
	 */
	public void add_client_to_server(Client_entity client) {
		server_model.add_new_client(client);
		client.set_state(Client_state.ONLINE);
		server_model.notify_message_console("New member connected");
		server_model.notify_client_connexion(client);
	}

	/**
	 * Méthode permettant d'enlever un client de la liste des clients connectés
	 * aux servers
	 * 
	 * @param client
	 *            le client a retiré
	 */
	public void ask_remove_client(Client_entity client) {
		server_model.remove_client(client);
		server_model.notify_client_disconnected(client);
		server_model.notify_message_console("Member " + client.get_pseudo() + " disconnected");
	}

	/**
	 * Méthode permettant de demander au serveur de notifier un client en
	 * particulier qui se trouve dans un des serveurs de jeu. Pour trouver le
	 * serveur et ensuite donner ce message aux joueurs
	 * 
	 * @param pseudo
	 *            le pseudo du client qui doit etre notifié
	 * @param message
	 *            le message a lui envoyer
	 */
	public void ask_notify_client_in_game(String pseudo, String message) {
		server_model.dispatch_message_to_game_with_client(pseudo, message);
	}

	/**
	 * Méthode permettant de demander de lancer le serveur. Cette mthode va
	 * permettre de lancer un thread qui va vérifier toutes les 1 secondes 05
	 * qu'une partie ne peut pas etre lancée.
	 */
	public void ask_start_server() {
		if (this.server_model.start_server()) {
			server_model.notify_message_console("Server running");
			server_model.notify_server_start(server_model.get_server_state(), server_model.get_port(),
					server_model.get_nb_max_client());

			conn_acc = new Connection_accepter(this);
			conn_acc.bind_socket(this.server_model.get_socket_server());
			new Thread(conn_acc).start();
			new Thread(new Runnable() {
				@Override
				public void run() {
					while (true) {
						Vector<Client_network_manager> ready = server_model.get_clients_ready_to_play();
						if (ready.size() >= server_model.get_nb_player_per_game()) {
							Vector<Client_network_manager> players_account = new Vector<Client_network_manager>();
							for (int i = 0; i < server_model.get_nb_player_per_game(); i++) {
								players_account.add(ready.get(i));
								ready.get(i).get_client().set_state(Client_state.INGAME);
								server_model.notify_client_status_change(ready.get(i).get_client());
							}

							server_model.add_new_game(new Game_entity(players_account));
							server_model.notify_nb_game_playing_change(server_model.get_nb_game_playing());
						}
						try {
							Thread.sleep(1500);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}).start();
		} else {
			this.server_model.notify_message_console("Impossbile to start server");
		}
	}

	/**
	 * Méthode permettant de demander au serveur de s'arreter et d'avertir tous
	 * les joueurs que le serveur est coupé
	 */
	public void ask_stop_server() {
		if (this.server_model.stop_server()) {
			this.server_model.remove_all_client();
			this.server_model.notify_message_console("Server stopped");
			this.server_model.notify_server_status(Server_state.STOPPED.toString());

			conn_acc.set_is_running(false);
		} else {
			this.server_model.notify_message_console("Stop impossible");
		}
	}
}
