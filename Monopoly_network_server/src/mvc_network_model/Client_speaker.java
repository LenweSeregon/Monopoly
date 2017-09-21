package mvc_network_model;

public class Client_speaker {

	private Client_entity client;

	/**
	 * Constructeur de la classe qui représentant le moyen d'envoyer les
	 * messages aux clients
	 * 
	 * @param c
	 *            la référécen sur l'entité client
	 */
	public Client_speaker(Client_entity c) {
		this.client = c;
	}

	/**
	 * Méthode permettant d'envoyer un message au client
	 * 
	 * @param message
	 *            le message a envoyer
	 */
	public void send_message(String message) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				client.send_message(message);
			}
		}).start();
	}
}
