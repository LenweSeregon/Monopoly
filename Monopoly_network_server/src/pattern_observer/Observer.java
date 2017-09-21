package pattern_observer;

import mvc_network_model.Client_entity;

public interface Observer {

	/**
	 * Méthode permettant d'etre notifier de la connexion d'un client
	 * 
	 * @param client
	 *            le client connecté
	 */
	public void update_client_connexion(Client_entity client);

	/**
	 * Méthode permettant d'etre notifier de déconnexion d'un client
	 * 
	 * @param client
	 *            le client connecté
	 */
	public void update_client_disconnected(Client_entity client);

	/**
	 * Méthode permettant d'etre notifier du status changeant d'un client
	 * 
	 * @param client
	 *            le client qui a subit un status changeant
	 */
	public void update_client_status_change(Client_entity client);

	/**
	 * Méthode permettant d'etre notifier du nombre de partie en cours changeant
	 * 
	 * @param nb_game_playing
	 *            nombre de partie en cours de jeu
	 */
	public void update_nb_game_playing_change(int nb_game_playing);

	/**
	 * Méthode permettant d'etre notifier de la création d'un serveur
	 * 
	 * @param status
	 *            le status du serveur
	 * @param port
	 *            le port du serveur
	 * @param nb_max_accepted
	 *            le nombre max de connection
	 */
	public void update_server_start(String status, int port, int nb_max_accepted);

	/**
	 * Méthode permettant d'etre notifier du status du serveur
	 * 
	 * @param status
	 *            le status du serveur
	 */
	public void update_server_status(String status);

	/**
	 * Méthode permettant d'etre notifier de l'ajout d'un message dans la
	 * console
	 * 
	 * @param message
	 *            le message à ajouter
	 */
	public void update_message_console(String message);
}
