package network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class Client {

	private String pseudo;
	private Socket socket_client;
	private BufferedReader in;
	private PrintWriter out;
	private InetAddress server_address;
	private int server_port;

	public Client(InetAddress address, int port, String pseudo) {
		this.pseudo = pseudo;

		this.socket_client = null;
		this.in = null;
		this.out = null;

		this.server_address = address;
		this.server_port = port;
	}

	/**
	 * Méthode permettant de se connecter au serveur que l'on a configurer dans
	 * le constructeur
	 * 
	 * @throws IOException
	 *             si la connexion n'est pas possible, on lance une exception
	 */
	public void connect_to_server() throws IOException {
		socket_client = new Socket(server_address, server_port);
	}

	/**
	 * Méthode permettant de recevoir un message, et renvoie ce messsage à la
	 * fin de la fonction
	 * 
	 * @return le message recu du serveur
	 */
	public String receive_message() {
		try {
			in = new BufferedReader(new InputStreamReader(
					socket_client.getInputStream()));
			String message = in.readLine();
			return message;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "ERROR";
	}

	public String get_pseudo() {
		return this.pseudo;
	}

	/**
	 * Méthode permettant d'envoyer un message au serveur
	 * 
	 * @param message
	 *            le message allant au serveur
	 */
	public void send_message(String message) {
		try {
			out = new PrintWriter(socket_client.getOutputStream());
			out.println(message);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
