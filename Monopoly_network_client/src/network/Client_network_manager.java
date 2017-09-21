package network;

import mvc_network_controler.Network_controler;

public class Client_network_manager {

	private Client client;
	private Client_listener listener;
	private Client_speaker speaker;

	/**
	 * Constructeur de la classe qui représente un manager de client. Ce manager
	 * wrap à la fois le listener et le speaker d'u client pour centraliser
	 * toutes les fonctionnaltiés de communication
	 * 
	 * @param client
	 */
	public Client_network_manager(Client client) {
		this.client = client;
		this.listener = null;
		this.speaker = new Client_speaker(client);
	}

	/**
	 * Méthode permettant de lancer la communication, c'est à dire que le
	 * listener se met à écouter
	 * 
	 * @param net
	 */
	public void launch_client_communications(Network_controler net) {
		this.listener = new Client_listener(client, net);
		new Thread(this.listener).start();
	}

	/**
	 * Méthode permettant de récupérer le pseudo du client
	 * 
	 * @return le pseudo du client
	 */
	public String get_pseudo() {
		return client.get_pseudo();
	}

	/**
	 * Méthode permettant d'envoyer un message au serveur
	 * 
	 * @param message
	 *            le message a envoyer
	 */
	public void send_message(String message) {
		this.speaker.send_message(message);
	}
}
