package mvc_network_model;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Vector;

import mvc_network_controler.Server_controler;
import pattern_observer.Observable;
import pattern_observer.Observer;

public class Server implements Observable {

	private int port;
	private int nb_client_max;
	private int nb_client_per_game;
	private Server_state state;

	private Server_client_manager client_manager;
	private Server_game_manager game_manager;

	private ArrayList<Observer> observers;
	private ServerSocket server_socket;

	/**
	 * Constructeur de la classe Serveur, cette classe permet de mettre en place
	 * un environnement reseau ou un serveur est accessible en localhost par
	 * différents clients. Le serveur a pour vocation finale d'accueilir un
	 * nombre de joueur déterminé, et de pouvoir gérer différentes parties en
	 * même temps
	 * 
	 * @param port
	 *            le port sur lequel le serveur doit écouter
	 * @param nb_client_maximum
	 *            le nombre maximum de joueur qui peuvent être connecté en même
	 *            temps.
	 */
	public Server(int port, int nb_client_max, int nb_client_per_game) {
		this.port = port;
		this.nb_client_max = nb_client_max;
		this.nb_client_per_game = nb_client_per_game;
		this.state = Server_state.STOPPED;
		this.observers = new ArrayList<Observer>();
		this.server_socket = null;

		this.client_manager = null;
		this.game_manager = new Server_game_manager();
	}

	/**
	 * Méthode permettant de lier la référence du controler à notre serveur
	 * 
	 * @param ref_controler
	 *            la référence du controleur
	 */
	public void bind_controler_server(Server_controler ref_controler) {
		this.client_manager = new Server_client_manager(ref_controler);
	}

	/**
	 * Méthode static de la classe serveur permettant de savoir si il est
	 * possible de créer un socket de communicatin avec un port
	 * 
	 * @param port
	 *            le port que l'on veut tester pour l'ouverture d'un serveur
	 * @return vrai si le serveur peut etre ouvert avec le port en paramétre,
	 *         faux sinon
	 */
	public static boolean is_port_free(int port) {
		try {
			ServerSocket socket = new ServerSocket(port);
			socket.close();
		} catch (IllegalArgumentException e) {
			return false;
		} catch (IOException e) {
			return false;
		}
		return true;
	}

	/**
	 * Méthode permettant d'ajouter une nouvelle partie
	 * 
	 * @param game
	 *            la nouvelle partie
	 */
	public void add_new_game(Game_entity game) {
		this.game_manager.add_game(game);
	}

	/**
	 * Méthode permettant d'ajouter un nouveau client au serveur
	 * 
	 * @param client
	 *            le client a ajouter au serveur
	 * @return vrai si le client a pu etre ajouté, faux sinon
	 */
	public boolean add_new_client(Client_entity client) {
		if (client_manager.get_nb_clients() <= nb_client_max) {
			client_manager.add_new_client(client);
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Méthode permettant d'enlever un client du serveur
	 * 
	 * @param client
	 *            l'entité du client que l'on veut supprimer
	 * @return vrai si le client a été supprimé (ie: trouvé), faux sinon
	 */
	public boolean remove_client(Client_entity client) {
		if (client_manager.remove_client(client)) {
			Game_entity g = game_manager.remove_client_from_game(client.get_pseudo());
			if (g != null) {
				if (game_manager.get_nb_client_in_game(g) == 0) {
					game_manager.remove_game(g);
					notify_nb_game_playing_change(game_manager.get_nb_game());
				}
			}
			return true;
		} else {
			return false;
		}

	}

	/**
	 * Méthode permettant de supprimer tout les clients du serveur
	 */
	public void remove_all_client() {
		client_manager.remove_all_client();
	}

	/**
	 * Méthode permettant de récupérer un vecteur de tout les clients pret pour
	 * lancer une partie
	 * 
	 * @return un vecteur des clients pret à lancer une partie
	 */
	public Vector<Client_network_manager> get_clients_ready_to_play() {
		return this.client_manager.get_clients_ready_to_play();
	}

	/**
	 * Méthode permettant de récupérer une entité de client par rapport à son
	 * pseudo
	 * 
	 * @param pseudo
	 *            le pseudo du client que l'on veut récupérer
	 * @return le client si celui ci a été trouvé, null sinon
	 */
	public Client_entity get_client_by_pseudo(String pseudo) {
		return client_manager.get_client_by_pseudo(pseudo);
	}

	/**
	 * Méthode permettant de dispatcher un message à une partie avec la présence
	 * d'un client dedans
	 * 
	 * @param pseudo
	 *            le pseudo du client qui doit etre présent dans la partie
	 * @param message
	 *            le message a dispatcher
	 */
	public void dispatch_message_to_game_with_client(String pseudo, String message) {
		this.game_manager.dispatch_message_to_game_with_client(pseudo, message);
	}

	/**
	 * Méthode pour démarre le serveur
	 * 
	 * @return vrai si le serveur a été démarré, faux sinon
	 */
	public boolean start_server() {
		try {
			this.server_socket = new ServerSocket(port);
			this.state = Server_state.RUNNING;
			return true;
		} catch (IOException e) {
			return false;
		}
	}

	/**
	 * Méthode permettant d'arreter le serveur
	 * 
	 * @return vrai si le serveur a été arrété, faux sinon
	 */
	public boolean stop_server() {
		try {
			if (!this.server_socket.isClosed()) {
				server_socket.close();
			}
			this.state = Server_state.STOPPED;
			return true;
		} catch (IOException e) {
			return false;
		}
	}

	/**
	 * Méthode permettant de récupérer la socket de communication du serveur
	 * 
	 * @return la socket de communication du serveur
	 */
	public ServerSocket get_socket_server() {
		return this.server_socket;
	}

	/**
	 * Méthode permettant de récupérer le port du serveur
	 * 
	 * @return le port du serveur
	 */
	public int get_port() {
		return this.port;
	}

	/**
	 * Méthode permettant de récupérer le nombre de partie en corus dans le
	 * serveur
	 * 
	 * @return le nombre de partie dans le serveur
	 */
	public int get_nb_game_playing() {
		return this.game_manager.get_nb_game();
	}

	/**
	 * Méthode permettant de récupérer le nombre maximum de client dans le
	 * serveur autorisé
	 * 
	 * @return le nombre maximum de client autorisé
	 */
	public int get_nb_max_client() {
		return this.nb_client_max;
	}

	/**
	 * Méthode permettant de écupérer le nombre de joueur demandé par partie
	 * dans le serveur
	 * 
	 * @return le nombre de joueur nécessaire par partie
	 */
	public int get_nb_player_per_game() {
		return this.nb_client_per_game;
	}

	/**
	 * Méthode permettant de récupérer le status du serveur à un instant T
	 * 
	 * @return le status du serveur
	 */
	public String get_server_state() {
		return state.toString();
	}

	@Override
	public void add_observer(Observer ob) {
		observers.add(ob);
	}

	@Override
	public void remove_all_observers() {
		observers.clear();
	}

	@Override
	public void notify_server_start(String status, int port, int nb_max_accepted) {
		for (Observer ob : observers) {
			ob.update_server_start(status, port, nb_max_accepted);
		}
	}

	@Override
	public void notify_server_status(String status) {
		for (Observer ob : observers) {
			ob.update_server_status(status);
		}
	}

	@Override
	public void notify_message_console(String message) {
		for (Observer ob : observers) {
			ob.update_message_console(message);
		}
	}

	@Override
	public void notify_client_connexion(Client_entity client) {
		for (Observer ob : observers) {
			ob.update_client_connexion(client);
		}
	}

	@Override
	public void notify_client_status_change(Client_entity client) {
		for (Observer ob : observers) {
			ob.update_client_status_change(client);
		}
	}

	@Override
	public void notify_client_disconnected(Client_entity client) {
		for (Observer ob : observers) {
			ob.update_client_disconnected(client);
		}
	}

	@Override
	public void notify_nb_game_playing_change(int nb_game_playing) {
		for (Observer ob : observers) {
			ob.update_nb_game_playing_change(nb_game_playing);
		}
	}
}
