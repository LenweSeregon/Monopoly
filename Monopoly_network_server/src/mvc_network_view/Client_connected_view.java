package mvc_network_view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

import UI_elements.My_JLabel;
import mvc_network_model.Client_entity;
import utils.Resources;

@SuppressWarnings("serial")
public class Client_connected_view extends JPanel {

	private JTextArea clients_pseudo_list;
	private My_JLabel nb_connected;
	private Vector<Client_entity> clients;

	/**
	 * Constructeur de la classe qui représente la vue des clients qui sont
	 * connectés au serveur. Grace à cette classe, on peut voir les différents
	 * joueurs qui sont présents dans le serveur a tout instant? Cette classe
	 * utilise un JScrollPane pour pouvoir afficher autant de joueur que l'on
	 * souhaite
	 * 
	 * @param width
	 *            la largeur de la vue
	 * @param height
	 *            la hauteur de la vue
	 */
	public Client_connected_view(int width, int height) {
		this.setPreferredSize(new Dimension(width, height));
		this.setLayout(new BorderLayout());
		this.setOpaque(false);
		this.clients = new Vector<Client_entity>();

		int h_up = (int) (height * 0.1);
		int h_center = (int) (height * 0.1);
		int h_down = (int) (height * 0.8);

		JPanel up = new JPanel();
		up.setOpaque(false);
		up.setLayout(new BorderLayout());
		up.setPreferredSize(new Dimension(width, h_up));
		up.setBorder(new MatteBorder(0, 0, 5, 0, new Color(117, 138, 119)));

		My_JLabel title = new My_JLabel("Connected clients", 20.f);
		title.setForeground(Color.WHITE);
		up.add(title, BorderLayout.CENTER);

		JPanel center = new JPanel();
		center.setOpaque(false);
		center.setLayout(new BorderLayout());
		center.setPreferredSize(new Dimension(width, h_center));
		center.setBorder(new MatteBorder(0, 0, 5, 0, new Color(117, 138, 119)));

		nb_connected = new My_JLabel("Nb connected : " + "0", 20.f);
		nb_connected.setForeground(Color.WHITE);
		center.add(nb_connected, BorderLayout.CENTER);

		JPanel down = new JPanel();
		down.setPreferredSize(new Dimension(width, h_down));
		down.setOpaque(false);
		down.setLayout(new BorderLayout());

		clients_pseudo_list = new JTextArea();
		clients_pseudo_list.setLineWrap(true);
		clients_pseudo_list.setWrapStyleWord(true);
		clients_pseudo_list.setFont(Resources.get_font("kabel.ttf").deriveFont(Font.BOLD, 18.f));
		clients_pseudo_list.setBorder(new EmptyBorder(20, 20, 20, 20));
		clients_pseudo_list.setForeground(Color.WHITE);
		clients_pseudo_list.setEditable(false);
		clients_pseudo_list.setBackground(new Color(170, 203, 172));

		JScrollPane scroll = new JScrollPane(clients_pseudo_list, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.setPreferredSize(new Dimension(width, h_down));
		scroll.setOpaque(true);
		scroll.setBackground(Color.BLACK);

		scroll.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));
		scroll.getViewport().setPreferredSize(new Dimension(width, h_down));
		scroll.getViewport().setBorder(null);
		scroll.setViewportBorder(null);
		scroll.setBorder(null);
		scroll.getVerticalScrollBar().setUnitIncrement(4);

		down.add(scroll, BorderLayout.CENTER);

		this.add(up, BorderLayout.NORTH);
		this.add(center, BorderLayout.CENTER);
		this.add(down, BorderLayout.SOUTH);
	}

	/**
	 * Méthode permettant d'ajouter un client à notre vue de joueurs
	 * 
	 * @param client
	 *            l'entité du client a ajouter
	 */
	public void add_client(Client_entity client) {

		this.clients.add(client);
		update_client_list();
	}

	/**
	 * Méthode permettant de changer le status actuel d'un client a tout instant
	 * 
	 * @param client
	 *            le client dont l'on souhaite changer le status
	 */
	public void change_client_status(Client_entity client) {
		int pos = find_position_pseudo(client.get_pseudo());
		if (pos != -1) {
			this.clients.set(pos, client);
			update_client_list();
		}
	}

	/**
	 * Méthode permettant de supprimer un client de notre liste visible de
	 * client
	 * 
	 * @param client
	 *            l'entité du client a supprimer
	 */
	public void remove_client(Client_entity client) {

		int res = find_position_pseudo(client.get_pseudo());
		if (res != -1) {
			this.clients.remove(res);
		}
		update_client_list();
	}

	/**
	 * Méthode permettant de supprimer tout les clients de la liste
	 */
	public void remove_all_client() {
		this.clients.clear();
		update_client_list();
	}

	/**
	 * Méthode permettant de trouver la position d'un client par rapport à son
	 * pseudo pour pouvoir le supprimer
	 * 
	 * @param pseudo
	 *            le pseudo du client a supprimer
	 * @return la position dans le vecteur si il existe, -1 sinon
	 */
	private int find_position_pseudo(String pseudo) {
		int it = 0;
		for (Client_entity s : this.clients) {
			if (s.get_pseudo().equals(pseudo)) {
				return it;
			}
			it++;
		}
		return -1;
	}

	/**
	 * Methode permettant d'update notre liste de client connecté
	 */
	private void update_client_list() {
		nb_connected.setText("Nb connected : " + clients.size());
		clients_pseudo_list.setText("");

		for (int i = 0; i < clients.size(); i++) {
			String name = clients.get(i).get_pseudo();
			String state = clients.get(i).get_state().toString();
			String string_to_display = name + "  :  " + state;

			if (i == clients.size() - 1) {
				clients_pseudo_list.append(string_to_display);
			} else {
				clients_pseudo_list.append(string_to_display + "\n\n");
			}
		}
	}
}
