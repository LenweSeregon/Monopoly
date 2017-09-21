package mvc_network_view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

import UI_elements.My_JButton;
import UI_elements.My_JLabel;
import menus.Abstract_panel;
import mvc_network_controler.Server_controler;
import mvc_network_model.Client_entity;
import mvc_network_model.Server_state;
import pattern_observer.Observer;

@SuppressWarnings("serial")
public class Server_view extends Abstract_panel implements Observer {

	private Server_properties_view properties;
	private Client_connected_view clients;
	private Server_communication_view communication;

	private Server_controler controler;

	/**
	 * Constructeur de la clase qui représente la vue général du serveur c'est à
	 * dire les différents composants qui comporte la fenetre de vue du serveur
	 * 
	 * @param width
	 *            la largeur du panel
	 * @param height
	 *            la hauteur du panel
	 * @param controler
	 *            le controler de notre serveur
	 */
	public Server_view(int width, int height, Server_controler controler) {
		super(width, height);
		this.controler = controler;

		this.build_header(1.0f, 0.15f);
		this.build_center(0.5f, 0.7f);
		this.build_footer(1.0f, 0.15f);
		this.build_right(0.25f, 0.7f);
		this.build_left(0.25f, 0.7f);
	}

	@Override
	protected void build_header(float w_mod, float h_mod) {
		super.build_header(w_mod, h_mod);
		this.header.setBackground(new Color(129, 198, 134));
		this.header.setLayout(new BorderLayout());
		this.header.setBorder(new MatteBorder(0, 0, 5, 0, new Color(117, 138, 119)));

		JPanel header_content = new JPanel();
		header_content.setLayout(new BorderLayout());
		header_content.setOpaque(false);

		JLabel title = new My_JLabel("DashBoard Server", 55.f);
		title.setForeground(Color.WHITE);

		header_content.add(title, BorderLayout.CENTER);

		this.header.add(header_content, BorderLayout.CENTER);
	}

	@Override
	protected void build_center(float w_mod, float h_mod) {
		super.build_center(w_mod, h_mod);
		this.center.setLayout(new BorderLayout());
		this.center.setBackground(Color.WHITE);

		int w = (int) (width * w_mod);
		int h = (int) (height * h_mod);

		communication = new Server_communication_view(w, h);
		this.center.add(communication, BorderLayout.CENTER);
	}

	@Override
	protected void build_footer(float w_mod, float h_mod) {
		super.build_footer(w_mod, h_mod);
		this.footer.setLayout(new BorderLayout());
		this.footer.setBackground(new Color(129, 198, 134));
		this.footer.setBorder(new MatteBorder(5, 0, 0, 0, new Color(117, 138, 119)));

		int w = (int) (width * w_mod);
		int h = (int) (height * h_mod);

		JPanel buttons_container = new JPanel();
		buttons_container.setOpaque(false);
		buttons_container.setLayout(new GridLayout(1, 3, 200, 0));
		buttons_container.setPreferredSize(new Dimension(w, h));
		buttons_container.setBorder(new EmptyBorder(30, 70, 30, 70));

		My_JButton start_stop = new My_JButton("Stop", 25.f);
		start_stop.setForeground(Color.WHITE);
		start_stop.setBorderPainted(true);
		start_stop.setBorder(new MatteBorder(5, 0, 5, 0, Color.WHITE));
		start_stop.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (start_stop.getText().equals("Stop")) {
					start_stop.setText("Start");
					controler.ask_stop_server();
				} else {
					start_stop.setText("Stop");
					controler.ask_start_server();
				}
			}
		});

		buttons_container.add(start_stop);
		buttons_container.add(new JLabel());
		buttons_container.add(new JLabel());

		this.footer.add(buttons_container, BorderLayout.CENTER);

	}

	@Override
	protected void build_right(float w_mod, float h_mod) {
		super.build_right(w_mod, h_mod);
		this.right.setBackground(new Color(170, 203, 172));
		this.right.setLayout(new BorderLayout());
		this.right.setBorder(new MatteBorder(0, 5, 0, 0, new Color(117, 138, 119)));

		int w = (int) (width * w_mod);
		int h = (int) (height * h_mod);

		properties = new Server_properties_view(w, h);
		this.right.add(properties, BorderLayout.CENTER);
	}

	@Override
	protected void build_left(float w_mod, float h_mod) {
		super.build_left(w_mod, h_mod);
		this.left.setBackground(new Color(170, 203, 172));
		this.left.setLayout(new BorderLayout());
		this.left.setBorder(new MatteBorder(0, 0, 0, 5, new Color(117, 138, 119)));

		int w = (int) (width * w_mod);
		int h = (int) (height * h_mod);

		clients = new Client_connected_view(w, h);
		this.left.add(clients, BorderLayout.CENTER);

	}

	@Override
	public void update_server_start(String status, int port, int nb_max_accepted) {
		this.properties.set_port_number(port);
		this.properties.set_nb_game_running(0);
		this.properties.set_server_status(status);
		this.properties.set_nb_max_accepted(nb_max_accepted);
	}

	@Override
	public void update_server_status(String status) {
		if (status.equals(Server_state.STOPPED.toString())) {
			this.clients.remove_all_client();
			this.properties.set_nb_game_running(0);
			this.properties.set_server_status(status);
		} else {
			this.properties.set_server_status(status);
		}
	}

	@Override
	public void update_message_console(String message) {
		this.communication.add_message(message);
	}

	@Override
	public void update_client_connexion(Client_entity client) {
		this.clients.add_client(client);
	}

	@Override
	public void update_client_status_change(Client_entity client) {
		this.clients.change_client_status(client);
	}

	@Override
	public void update_client_disconnected(Client_entity client) {
		this.clients.remove_client(client);
	}

	@Override
	public void update_nb_game_playing_change(int nb_game_playing) {
		this.properties.set_nb_game_running(nb_game_playing);
	}
}
