package pattern_observer;

import mvc_network_model.Client_entity;

public interface Observable {

	/**
	 * Méthode permettant d'ajouter un observeur à notre observable
	 * 
	 * @param ob
	 *            l'observeur que l'on souhaite ajouter
	 */
	public void add_observer(Observer ob);

	/**
	 * Méthode permettant de retirer tout les observeurs de notre observable
	 */
	public void remove_all_observers();

	/**
	 * Méthode permettant de notifier de la connexion d'un client
	 * 
	 * @param client
	 *            le client qui vient de se connecter
	 */
	public void notify_client_connexion(Client_entity client);

	/**
	 * Méthode permettant de notifier de la déconnexion d'un client
	 * 
	 * @param client
	 *            le client qui vient de se deconnecter
	 */
	public void notify_client_disconnected(Client_entity client);

	/**
	 * Méthode permettant de notifier d'un changement de status du client
	 * 
	 * @param client
	 *            le client qui vient de changer de status
	 */
	public void notify_client_status_change(Client_entity client);

	/**
	 * Méthode permettant de notifier d'une différence de nombre de game en
	 * cours de partie
	 * 
	 * @param nb_game_playing
	 *            le nombre de partie
	 */
	public void notify_nb_game_playing_change(int nb_game_playing);

	/**
	 * Méthode permettant de notifier du démarrage du serveur
	 * 
	 * @param status
	 *            le status du serveur
	 * @param port
	 *            le port du serveur
	 * @param nb_max_accepted
	 *            le nombre de client accepté
	 */
	public void notify_server_start(String status, int port, int nb_max_accepted);

	/**
	 * Méthode permettant de notifier du status du serveur
	 * 
	 * @param status
	 *            le status du serveur
	 */
	public void notify_server_status(String status);

	/**
	 * Méthode permettant de notifier d'un message dans la console
	 * 
	 * @param message
	 *            le message dans la console
	 */
	public void notify_message_console(String message);
}
