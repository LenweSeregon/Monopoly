package mvc_network_model;

import java.util.Vector;

public class Server_game_manager {

	private Vector<Game_entity> games;

	/**
	 * Constructeur de la classe qui représente le gestionnaire de partie du
	 * serveur. Cette classe va s'assurer le bon fonctionnement des différentes
	 * partie lancer en gardant une référence sur elles.
	 */
	public Server_game_manager() {
		this.games = new Vector<Game_entity>();
	}

	/**
	 * Méthode permettant d'ajouter une partie dans le gestionnaire et de lancer
	 * cette dernière
	 * 
	 * @param game
	 *            l'entité de la partie à ajouter
	 */
	public void add_game(Game_entity game) {
		this.games.add(game);
		new Thread(game).start();
	}

	/**
	 * Méthode permettant de dispatcher un message à une partie par rapport à un
	 * pseudo d'un client qui doit se trouver dans cette partie
	 * 
	 * @param pseudo
	 *            le pseudo du client que l'on recherche dans l'une des parties
	 * @param message
	 *            le message que l'on veut dispatcher
	 */
	public void dispatch_message_to_game_with_client(String pseudo, String message) {
		for (Game_entity g : games) {
			if (g.client_in_this_game(pseudo)) {
				g.dispatch_message_to_client(pseudo, message);
			}
		}
	}

	/**
	 * Méthode permettant de récupérer une entité de jeu par rapport à un pseudo
	 * de joueur qui doit se trouver dans cette partie
	 * 
	 * @param pseudo
	 *            le pseudo du joueur que l'on va rechercher dans une partie
	 * @return la partie si l'on a trouvé un joueur dans une partie avec ce
	 *         pseudo, null sinon
	 */
	public Game_entity get_game_in_client_presence(String pseudo) {
		for (Game_entity g : games) {
			if (g.client_in_this_game(pseudo)) {
				return g;
			}
		}
		return null;
	}

	/**
	 * Méthode permettant de supprimer un joueur d'une partie par rapport à son
	 * pseudo
	 * 
	 * @param pseudo
	 *            le pseudo du joueur que l'on veut supprimer d'une partie
	 * @return l'entité de la partie si l'on a trouvé le client, null sinon
	 */
	public Game_entity remove_client_from_game(String pseudo) {
		for (Game_entity g : games) {
			if (g.client_in_this_game(pseudo)) {
				g.remove_client(pseudo);
				return g;
			}
		}
		return null;
	}

	/**
	 * Méthode permettant de supprimer une partie du gestionnaire de partie
	 * 
	 * @param g
	 *            la partie a supprimer
	 */
	public void remove_game(Game_entity g) {
		g.stop_game();
		games.remove(g);
	}

	/**
	 * Méthode permettant de récupérer le nombre de client dans une partie
	 * spécifique
	 * 
	 * @param game
	 *            la partie dont l'on veut connaitre le nombre de joueur
	 * @return le nombre de joueur dans la partie
	 */
	public int get_nb_client_in_game(Game_entity game) {
		return game.get_nb_clients_in_game();
	}

	/**
	 * Méthode permettant de récupérer le nombre de partie actuellement en cours
	 * dans le gestionnaire de partie
	 * 
	 * @return le nombre de partie en corus
	 */
	public int get_nb_game() {
		return games.size();
	}
}
