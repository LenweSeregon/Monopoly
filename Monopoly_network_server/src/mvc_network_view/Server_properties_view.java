package mvc_network_view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.border.MatteBorder;

import UI_elements.My_JLabel;

@SuppressWarnings("serial")
public class Server_properties_view extends JPanel {

	private My_JLabel port;
	private My_JLabel max_accepted;
	private My_JLabel status;
	private My_JLabel nb_game;

	/**
	 * Constructeur de la classe qui représente la vue des différentes
	 * propriétés du serveur. C'est à dire le nombre de client, le nombre de
	 * partie, le statut du serveur, le nombre de client maximum, etc..
	 * 
	 * @param width
	 *            la largeur du panel
	 * @param height
	 *            la hauteur du panel
	 */
	public Server_properties_view(int width, int height) {
		this.setPreferredSize(new Dimension(width, height));
		this.setLayout(new BorderLayout());
		this.setOpaque(false);

		int h_up = (int) (height * 0.20);
		int h_center = (int) (height * 0.80);

		JPanel up = new JPanel();
		up.setOpaque(false);
		up.setLayout(new BorderLayout());
		up.setPreferredSize(new Dimension(width, h_up));
		up.setBorder(new MatteBorder(0, 0, 5, 0, new Color(117, 138, 119)));

		My_JLabel title = new My_JLabel("Properties", 25.f);
		title.setForeground(Color.WHITE);

		up.add(title, BorderLayout.CENTER);

		JPanel center = new JPanel();
		center.setLayout(new GridLayout(4, 1, 50, 50));
		center.setPreferredSize(new Dimension(width, h_center));
		center.setOpaque(false);

		port = new My_JLabel("Server port : " + "Unknow", 20.f);
		port.setForeground(Color.WHITE);
		max_accepted = new My_JLabel("Max charge : " + "Unknow", 20.f);
		max_accepted.setForeground(Color.WHITE);
		status = new My_JLabel("Server status : " + "Unknow", 20.f);
		status.setForeground(Color.WHITE);
		nb_game = new My_JLabel("Ongoing game : " + "Unknow", 20.f);
		nb_game.setForeground(Color.WHITE);

		center.add(port);
		center.add(max_accepted);
		center.add(status);
		center.add(nb_game);

		this.add(up, BorderLayout.NORTH);
		this.add(center, BorderLayout.CENTER);

	}

	/**
	 * Méthode permettant de choisir le numéro de port du serveur a afficher
	 * 
	 * @param port_val
	 *            le port du serveur
	 */
	public void set_port_number(int port_val) {
		port.setText("Server port : " + port_val);
		revalidate();
	}

	/**
	 * Méthode permetatnt de choisir le nombre de client maximum accepté a
	 * afficher
	 * 
	 * @param max
	 *            le nombre de client maximum
	 */
	public void set_nb_max_accepted(int max) {
		max_accepted.setText("Max charge : " + max);
		revalidate();
	}

	/**
	 * Méthode permettant de choisir le status du serveur a afficher
	 * 
	 * @param status_val
	 *            le status du serveur
	 */
	public void set_server_status(String status_val) {
		status.setText("Server status : " + status_val);
		revalidate();
	}

	/**
	 * Méthode permettant de choisir le nombre de partie en cours à afficher
	 * 
	 * @param nb
	 *            le nombre de partie
	 */
	public void set_nb_game_running(int nb) {
		nb_game.setText("Ongoing game : " + nb);
		revalidate();
	}
}
