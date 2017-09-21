package mvc_network_model;

import mvc_network_controler.Server_controler;

public class Client_network_manager {

	private Client_entity client;
	private Client_listener listener;
	private Client_speaker speaker;

	/**
	 * Constructeur de la classe représentant l'entité mére de la communication
	 * puisque cette classe regroupe le listener et l'écouter de la
	 * communication permet de centraliser toutes les informations de
	 * communication
	 * 
	 * @param c
	 *            la référence vers l'entité client
	 * @param ref_controler
	 *            une référence vers le controleur du serveur
	 */
	public Client_network_manager(Client_entity c, Server_controler ref_controler) {

		this.client = c;
		this.listener = new Client_listener(c, ref_controler);
		this.speaker = new Client_speaker(c);

		this.launch_listener();
	}

	/**
	 * Méthode permettant de demander au listeneur d'arreter d'écouter
	 */
	public void stop_listening() {
		this.listener.set_is_listening(false);
	}

	/**
	 * Méthode permettant de demander au listener de se démarrer
	 */
	private void launch_listener() {
		new Thread(this.listener).start();
	}

	/**
	 * Méthode permettant de demander d'envoyer un message
	 * 
	 * @param message
	 *            le message a envoyer
	 */
	public void send_message(String message) {
		this.speaker.send_message(message);
	}

	/**
	 * Méhtode permettant de récupérer la réfénrece sur l'entité de client
	 * 
	 * @return l'entité client
	 */
	public Client_entity get_client() {
		return this.client;
	}

	/**
	 * Méthode permettant de récupérer le pseudo du client
	 * 
	 * @return le pseudo du client
	 */
	public String get_pseudo() {
		return this.client.get_pseudo();
	}
}
