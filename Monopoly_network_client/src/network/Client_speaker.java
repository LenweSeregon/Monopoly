package network;

public class Client_speaker {

	private Client client_ref;

	/**
	 * Constructeur de la classe qui représente le speaker de notre client
	 * 
	 * @param c_ref
	 *            la référence de notre client
	 */
	public Client_speaker(Client c_ref) {
		this.client_ref = c_ref;
	}

	/**
	 * Méthode permettant d'envoyer un message à notre serveur
	 * 
	 * @param message
	 *            le message a envoyer
	 */
	public void send_message(String message) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				client_ref.send_message(message);
			}
		}).start();
	}

}
