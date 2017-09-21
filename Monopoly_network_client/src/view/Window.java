package view;

import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import lobby_view.Lobby_view;
import menus.Server_down_menu;
import menus.Starting_menu;
import mvc_game_controler.Game_controler;
import mvc_game_model.Game_model;
import mvc_game_view.Game_view;
import mvc_network_controler.Network_controler;
import network.Client;
import network.Client_network_manager;
import utils.Sound_player;

@SuppressWarnings("serial")
public class Window extends JFrame {

	private int width;
	private int height;
	private boolean call_disco;

	private Client_network_manager client;
	private Game_controler ref_controler_game;

	private Sound_player sound_player;

	/**
	 * Constructeur de la classe représentant la fenetre principale de
	 * l'application. C'est cette fenetre qui va permettre d'afficher les
	 * différents panneaux de l'applications
	 * 
	 * @param width
	 *            la largeur de la fenetre
	 * @param height
	 *            la hauteur de la fenetre
	 */
	public Window(int width, int height) {
		this.width = width;
		this.height = height;
		this.client = null;
		this.ref_controler_game = null;
		this.sound_player = null;
		this.call_disco = false;
		this.getToolkit().sync();

		this.setTitle("Client Monopoly");
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				exit();
				if (!call_disco) {
					close_window_and_connexion();
				}
			}
		});

		launch_starting_menu();
	}

	/**
	 * Methode étant appelé à chaque fois que l'on ferme la fenetre ou lorsqu'un
	 * joueur qui est en partie appuie sur abandonner
	 */
	public void exit() {
		if (sound_player != null) {
			sound_player.stopper();
		}

		if (ref_controler_game != null) {
			ref_controler_game.set_true_voluntary_surrend();
			ref_controler_game.surrend();
		} else {
			close_window_and_connexion();
		}
	}

	public void close_window_and_connexion() {
		call_disco = true;
		if (client != null) {
			try {
				Thread.sleep(400);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			client.send_message("ACCOUNT DISCONNECTION");
		}
		this.setVisible(false);
		this.dispose();
		System.exit(0);
	}

	/**
	 * Méthode permettant de lancer le menu de démarrage des clients, c'est à
	 * dire l'endroit ou ils peuvent se connecter ou s'inscrire
	 */
	public void launch_starting_menu() {
		Starting_menu menu = new Starting_menu(width, height, this);
		this.setContentPane(menu);
		this.pack();
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		menu.setFocusable(true);
		menu.requestFocusInWindow();
	}

	/**
	 * Méthode permettant de lancer le menu de lobby la ou les clients peuvent
	 * consulter les informations et se tagger comme étant pret pour une partie
	 * 
	 * @param client
	 *            le client qui vient de se connecter
	 */
	public void launch_lobby_menu(Client client) {

		Client_network_manager client_net = new Client_network_manager(client);
		this.client = client_net;
		Network_controler ctrl = new Network_controler(client_net, this);
		Lobby_view lobby = new Lobby_view(width, height, ctrl, client.get_pseudo(), this);
		this.setContentPane(lobby);
		this.pack();
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		lobby.setFocusable(true);
		lobby.requestFocusInWindow();
	}

	/**
	 * Méthode permettant de lancer le panel qui affiche la fermeture du serveur
	 * pour montrer que celui ci est down
	 */
	public void launch_server_down_menu() {
		Server_down_menu down = new Server_down_menu(width, height);
		this.setContentPane(down);
		this.pack();
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		down.setFocusable(true);
		down.requestFocusInWindow();
	}

	/**
	 * Méthode permettant de lancer le jeu a proprement parlé, c'est à dire que
	 * cette méthode lance en même temps le MVC
	 * 
	 * @param network
	 *            la référence vers le controleur de réseau du client
	 */
	public void launch_game(Network_controler network) {
		Game_model model = new Game_model();
		Game_controler controler = new Game_controler(model, network, this);
		Game_view game_view = new Game_view(width, height, controler, this);

		sound_player = new Sound_player("/sounds/freeride.wav", true);
		new Thread(new Runnable() {
			@Override
			public void run() {
				sound_player.play();
			}
		}).start();

		this.ref_controler_game = controler;

		model.add_observer(game_view);
		network.bind_game_controler(controler);

		this.setContentPane(game_view);
		this.pack();
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		game_view.setFocusable(true);
		game_view.requestFocusInWindow();
	}
}
