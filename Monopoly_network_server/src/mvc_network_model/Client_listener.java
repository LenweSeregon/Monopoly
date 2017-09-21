package mvc_network_model;

import mvc_network_controler.Server_controler;

public class Client_listener implements Runnable {

	private Client_entity client;
	private boolean is_listening;
	private Server_controler ref_controler;

	/**
	 * Constructeur de la classe représentant l'entité maitresse de la
	 * communication. Ce classe représente l'écouteur du client pour être à tout
	 * moment pret à ecouter des informations
	 * 
	 * @param c
	 *            la référence vers l'entité du client
	 * @param ref_controler
	 *            la référence vers le controleur du serveur
	 */
	public Client_listener(Client_entity c, Server_controler ref_controler) {
		this.client = c;
		this.is_listening = false;
		this.ref_controler = ref_controler;
	}

	/**
	 * Méthode permettant de savoir si le client doit écouter
	 * 
	 * @param b
	 *            la valeur à attribuer à écoute
	 */
	public void set_is_listening(boolean b) {
		this.is_listening = b;
	}

	@Override
	public void run() {
		this.is_listening = true;
		while (is_listening) {
			String message = client.receive_message();
			if (message == null) {
				is_listening = false;
			}
			ref_controler.message_analyser(client, message);
		}
	}
}
