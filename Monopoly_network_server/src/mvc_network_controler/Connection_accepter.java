package mvc_network_controler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class Connection_accepter implements Runnable {

	private Server_controler controler;
	private ServerSocket server_socket;
	private boolean is_running;
	private boolean is_paused;

	/**
	 * Constructeur de classe acceptant les nouvelles connexions sur le serveur.
	 * Cette classe implément runnable puisqu'elle doit gérer de manière
	 * indépendante les nouvelles connexions au serveur sans interférer avec
	 * celui ci
	 * 
	 * @param controler
	 *            le controler des communications pour communiquer l'ajout d'un
	 *            nouveau client
	 */
	public Connection_accepter(Server_controler controler) {
		this.controler = controler;
		this.server_socket = null;
		this.is_running = false;
		this.is_paused = false;
	}

	/**
	 * Méthode permettant de lier un socket de communication qui représente le
	 * socket du serveur pour accepter les nouvelles connexions
	 * 
	 * @param socket
	 *            la socket de communication du serveur
	 */
	public void bind_socket(ServerSocket socket) {
		server_socket = socket;
	}

	/**
	 * Méthode permettant de choisir si le threa acceptant les connexions doit
	 * être arreté ou non
	 * 
	 * @param is_running
	 *            un booleen représentant si on doit arreté l'acceptation ou non
	 */
	public void set_is_running(boolean is_running) {
		this.is_running = is_running;
	}

	/**
	 * Méthode permettant de choisir si le thread acceptant les connexions doit
	 * être en pause (sans être arreté pour autant)
	 * 
	 * @param is_paused
	 *            un booleen représentant si on doit être en pause pour
	 *            l'acceptation ou non
	 */
	public void set_is_paused(boolean is_paused) {
		this.is_paused = is_paused;
	}

	@Override
	public void run() {
		is_running = true;
		is_paused = false;
		while (is_running) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e1) {

			}

			if (!this.is_paused && this.server_socket != null) {

				try {
					Socket socket_communication = server_socket.accept();
					controler.ask_new_client_connection(socket_communication);
				} catch (SocketException e2) {
					is_running = false;
				} catch (IOException e) {
					is_running = false;
				}
			}
		}
	}
}
