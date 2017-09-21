package view;

import javax.swing.JFrame;

import configure_settings.Setting_configuration_view;
import configure_terrain_view.Configure_terrains_values;
import menus.Starting_menu;
import mvc_network_controler.Server_controler;
import mvc_network_model.Server;
import mvc_network_view.Server_view;

@SuppressWarnings("serial")
public class Window extends JFrame {

	private int width;
	private int height;

	/**
	 * Le constructeur qui représente la fenetre général du jeu, C'est à dire
	 * l'endroit qui va servir à l'affichage des différents panels
	 * 
	 * @param width
	 *            la largeur de notre fenetre
	 * @param height
	 *            la hauteur de notre fenetre
	 */
	public Window(int width, int height) {
		this.width = width;
		this.height = height;

		this.setTitle("Client Monopoly");
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		launch_starting_menu();
	}

	/**
	 * Méthode permettant de lancer la fenetre de configuration des terrains
	 * 
	 * @param port
	 *            le port de notre serveur
	 */
	public void launch_configure_terrain_menu(int port, int per_game) {
		Configure_terrains_values menu = new Configure_terrains_values(width, height, this, port, per_game);
		this.setContentPane(menu);
		this.pack();
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		menu.setFocusable(true);
		menu.requestFocusInWindow();
	}

	/**
	 * Méthode permettant de lancer la fenetre de configuration des variantes du
	 * serveur
	 * 
	 * @param port
	 *            le port de notre serveur
	 */
	public void launch_configure_settings_menu(int port, int per_game) {
		Setting_configuration_view menu = new Setting_configuration_view(width, height, this, port, per_game);
		this.setContentPane(menu);
		this.pack();
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		menu.setFocusable(true);
		menu.requestFocusInWindow();
	}

	/**
	 * Méthode permettant de lancer le menu de démarrage du serveur.
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
	 * Méthode permettant de lancer la fenetre du serveur, et de lancer le
	 * pattern MVC en meme temps
	 * 
	 * @param port
	 *            le port de notre serveur
	 */
	public void launch_server(int port, int per_game) {

		Server serv = new Server(port, 100, per_game);
		Server_controler ctrl = new Server_controler(serv);
		Server_view s_v = new Server_view(width, height, ctrl);

		serv.bind_controler_server(ctrl);

		serv.add_observer(s_v);
		ctrl.ask_start_server();

		this.setContentPane(s_v);
		this.pack();
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		s_v.setFocusable(true);
		s_v.requestFocusInWindow();
	}
}
