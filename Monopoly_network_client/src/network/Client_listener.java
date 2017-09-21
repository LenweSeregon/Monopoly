package network;

import mvc_network_controler.Network_controler;

public class Client_listener implements Runnable {

	private Client client_ref;
	private Network_controler net;
	private boolean is_listening;

	/**
	 * Constructeur de la classe qui représente le listener d'un client qui
	 * utilise une instance de runnable pour pouvoir toujours écouter
	 * 
	 * @param c_ref
	 *            la référence sur le client
	 * @param net
	 *            la référence sur le controleur de réseau
	 */
	public Client_listener(Client c_ref, Network_controler net) {
		this.client_ref = c_ref;
		this.net = net;
		this.is_listening = false;
	}

	@Override
	public void run() {
		this.is_listening = true;
		while (is_listening) {
			String receive = client_ref.receive_message();
			if (receive == null) {
				is_listening = false;
			}
			net.message_analyser(receive);
		}
	}

}
