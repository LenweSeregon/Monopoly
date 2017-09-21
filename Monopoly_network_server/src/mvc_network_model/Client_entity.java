package mvc_network_model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client_entity {

	private Client_state state;
	private String pseudo;

	private BufferedReader in;
	private PrintWriter out;
	private Socket socket;

	/**
	 * Constructeur de la classe représentant une entité de client dans le
	 * serveur, avec ces informations essentielles
	 * 
	 * @param pseudo
	 *            le pseudo unique du client
	 */
	public Client_entity(String pseudo) {
		this.pseudo = pseudo;
		this.state = Client_state.ONLINE;
		this.socket = null;
		this.in = null;
		this.out = null;
	}

	/**
	 * Méthode permettant de lier une socket de communication à ce client. Cette
	 * méthode est appelé lorsqu'un client se connecte à son compte
	 * 
	 * @param socket
	 *            le socket de communication du client
	 */
	public void bind_socket(Socket socket) {
		this.socket = socket;
	}

	/**
	 * Méthode permettant de recevoir un message sur le client
	 * 
	 * @return le mssage que l'on vient de recevoir
	 */
	public String receive_message() {
		if (socket != null) {
			try {
				in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				String s = in.readLine();
				return s;
			} catch (IOException e) {
			}
			return "ERROR";
		} else {
			return "ERROR";
		}
	}

	/**
	 * Méthode permettant d'envoyer un message depuis notre entité de client sur
	 * le serveur vers le client réel à distance
	 * 
	 * @param message
	 *            le mesage que l'on souhaite envoyer
	 */
	public void send_message(String message) {
		if (socket != null) {
			try {
				out = new PrintWriter(socket.getOutputStream());
				out.println(message);
				out.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Méthode permettant de choisir le statut du joueur
	 * 
	 * @param state
	 *            le status du joueur
	 */
	public void set_state(Client_state state) {
		this.state = state;
	}

	/**
	 * Méthode permettant de choisir le pseudo du joueur
	 * 
	 * @param pseudo
	 *            le pseudo du joueur
	 */
	public void set_pseudo(String pseudo) {
		this.pseudo = pseudo;
	}

	/**
	 * Méthode permettant de récupérer le pseudo du joueur
	 * 
	 * @return le pseudo du joueur
	 */
	public String get_pseudo() {
		return this.pseudo;
	}

	/**
	 * Méthode permettant de récupérer l'état du joueur
	 * 
	 * @return l'état du joueur
	 */
	public Client_state get_state() {
		return this.state;
	}

}
