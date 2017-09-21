package mvc_network_model;

import java.util.Vector;

import mvc_network_controler.Server_controler;

public class Server_client_manager {

	private Vector<Client_network_manager> clients;
	private Server_controler ref_controler;

	/**
	 * Constructeur de la classe représentant le gestionnaire de client qui va
	 * gérer les différents clients qui sont présents dans le serveur. Cette
	 * classe permet à tout moment de savoir le nombre de client, leurs status
	 * et pouvoir facilement les gérer
	 * 
	 * @param ref_controler
	 *            la référence sur le controleur du serveur
	 */
	public Server_client_manager(Server_controler ref_controler) {
		this.ref_controler = ref_controler;
		this.clients = new Vector<Client_network_manager>();
	}

	/**
	 * Méthode permettant de récupérer le nombre de clients qui sont présents
	 * dans la partie
	 * 
	 * @return le nombre de client dans la partie
	 */
	public int get_nb_clients() {
		return clients.size();
	}

	/**
	 * Méthode permettant d'ajouter un nouveau client au gestionnaire de client,
	 * cette action est déclenché lorsque celui ci se connecte à son compte
	 * 
	 * @param client
	 *            l'entité du client qui vient de se connecter
	 */
	public void add_new_client(Client_entity client) {
		Client_network_manager c = new Client_network_manager(client, ref_controler);
		clients.addElement(c);
	}

	/**
	 * Méthode permettant de supprimer un client du gestionnaire de client,
	 * cette action est déclenché lorsque celui ci se déconnecte de sa partie /
	 * du lobby
	 * 
	 * @param client
	 *            la référence du client à supprimer
	 * @return vrai si le client a été supprimé (il se trouve dans le
	 *         gestionnaire) faux sinon
	 */
	public boolean remove_client(Client_entity client) {
		int res = get_pos_client_from_pseudo(client.get_pseudo());
		if (res != -1) {
			clients.get(res).stop_listening();
			clients.remove(res);
			return true;
		}
		return false;
	}

	/**
	 * Permet de supprimer tout les clients qui se trouvent dans le gestionnaire
	 * de client, cette action est déclenché quand le serveur est fermé
	 */
	public void remove_all_client() {
		for (Client_network_manager c : clients) {
			c.send_message("GENERAL" + " " + "DISCONNECTION");
			c.stop_listening();
		}

		clients.clear();
	}

	/**
	 * Méthode permettant de récupérer un vecteur contenant tout les clients qui
	 * sont pret pour lancer une partie. La fonction appellante lancement une
	 * partie si elle trouve un nombre suffisant de joueur
	 * 
	 * @return le vecteur des différents clients pret pour une partie
	 */
	public Vector<Client_network_manager> get_clients_ready_to_play() {
		Vector<Client_network_manager> players = new Vector<Client_network_manager>();
		for (Client_network_manager c : clients) {
			if (c.get_client().get_state() == Client_state.LOOKING_FOR_GAME) {
				players.add(c);
			}
		}
		return players;
	}

	/**
	 * Méthode permettant de récupérer l'entité client par rapport à son pseudo
	 * 
	 * @param pseudo
	 *            le pseudo cu client que l'on recherche
	 * @return l'entité du client par rapport à son pseudo si il existe, null
	 *         sinon
	 */
	public Client_entity get_client_by_pseudo(String pseudo) {
		for (Client_network_manager c : clients) {
			if (c.get_pseudo().equals(pseudo)) {
				return c.get_client();
			}
		}
		return null;
	}

	/**
	 * Méthode permettant de récupérer la position du client par rapport à son
	 * pseudo
	 * 
	 * @param pseudo
	 *            le pseudo du client que l'on recherche
	 * @return le position du client dans le vecteur si il existe, -1 sinon
	 */
	private int get_pos_client_from_pseudo(String pseudo) {
		for (int i = 0; i < clients.size(); i++) {
			if (clients.get(i).get_pseudo().equals(pseudo)) {
				return i;
			}
		}
		return -1;
	}
}
