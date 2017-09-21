package lobby_view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.MatteBorder;

import UI_elements.My_JButton;
import UI_elements.My_JLabel;
import credits_view.Credits_view;
import help_view.Help_view;
import me_view.Me_view;
import menus.Abstract_panel;
import mvc_network_controler.Network_controler;
import utils.Resources;
import view.Window;

@SuppressWarnings("serial")
public class Lobby_view extends Abstract_panel {

	private Network_controler controler;
	private JPanel center_container;
	private CardLayout center_container_cl;

	private Lobby_matchmaking_view center_match;
	private Lobby_buttons_view center_buttons;
	private Credits_view center_credits;
	private Help_view center_help;
	private Me_view center_me;

	private Window ref_win;
	private String name_client;

	/**
	 * Constructeur de la classe qui représente la vue du lobby. C'est ici que
	 * son contenu tout les elements de la fenetre qui représente le lobby de la
	 * fenetre.
	 * 
	 * @param width
	 *            la largeur du lobby
	 * @param height
	 *            la hauteur du lobby
	 * @param controler
	 *            une référence sur le controleur de réseau
	 * @param name_client
	 *            le nom du client
	 * @param ref_win
	 *            une référence sur la fenetre principale
	 */
	public Lobby_view(int width, int height, Network_controler controler, String name_client, Window ref_win) {
		super(width, height);
		this.controler = controler;
		this.name_client = name_client;
		this.ref_win = ref_win;

		this.build_header(1.0f, 0.15f);
		this.build_center(0.50f, 0.7f);
		this.build_footer(1.0f, 0.15f);
		this.build_left(0.25f, 0.7f);
		this.build_right(0.25f, 0.7f);
	}

	@Override
	protected void build_header(float w_mod, float h_mod) {
		super.build_header(w_mod, h_mod);
		this.header.setBackground(new Color(129, 198, 134));
		this.header.setLayout(new BorderLayout());
		this.header.setBorder(new MatteBorder(0, 0, 5, 0, new Color(117, 138, 119)));

		int w = (int) (width * w_mod);
		int h = (int) (height * h_mod);

		int w_left = (int) (w * 0.2);
		int w_center = (int) (w * 0.6);
		int w_right = (int) (w * 0.2);

		Image img = new ImageIcon(Resources.get_image("logo_monopoly.png")).getImage();
		Image newimg = img.getScaledInstance(w_left - 170, h - 20, java.awt.Image.SCALE_SMOOTH);

		JPanel left = new JPanel();
		left.setPreferredSize(new Dimension(w_left, h));
		left.setLayout(new BorderLayout());
		left.setOpaque(false);

		JLabel logo = new JLabel();
		logo.setHorizontalAlignment(JLabel.CENTER);
		logo.setPreferredSize(new Dimension(w_left, h));
		logo.setIcon(new ImageIcon(newimg));
		left.add(logo, BorderLayout.CENTER);

		JPanel center = new JPanel();
		center.setPreferredSize(new Dimension(w_center, h));
		center.setLayout(new BorderLayout());
		center.setOpaque(false);

		My_JLabel title = new My_JLabel("Lobby", 45.f);
		title.setForeground(Color.WHITE);
		center.add(title, BorderLayout.CENTER);

		JPanel right = new JPanel();
		right.setPreferredSize(new Dimension(w_right, h));
		right.setLayout(new BorderLayout());
		right.setOpaque(false);

		My_JLabel name = new My_JLabel(name_client, 25.f);
		name.setForeground(Color.WHITE);
		right.add(name, BorderLayout.CENTER);

		this.header.add(left, BorderLayout.WEST);
		this.header.add(center, BorderLayout.CENTER);
		this.header.add(right, BorderLayout.EAST);
	}

	@Override
	protected void build_center(float w_mod, float h_mod) {
		super.build_center(w_mod, h_mod);
		this.center.setLayout(new BorderLayout());
		this.center.setBackground(Color.WHITE);

		int w = (int) (width * w_mod);
		int h = (int) (height * h_mod);

		center_container = new JPanel();
		center_container_cl = new CardLayout();
		center_container.setLayout(center_container_cl);

		center_match = new Lobby_matchmaking_view(w, h);
		center_buttons = new Lobby_buttons_view(w, h, this);
		center_credits = new Credits_view(w, h, this);
		center_help = new Help_view(w, h, this);
		center_me = new Me_view(w, h, this);

		center_container.add(center_match, "match");
		center_container.add(center_buttons, "buttons");
		center_container.add(center_credits, "credits");
		center_container.add(center_help, "help");
		center_container.add(center_me, "me");

		center_container_cl.show(center_container, "buttons");

		this.center.add(center_container, BorderLayout.CENTER);
	}

	public void show_center_content(String name) {
		center_container_cl.show(center_container, name);
		if (name == "match") {
			center_match.launch_bar();
		} else {
			center_match.stop_bar();
		}
	}

	@Override
	protected void build_footer(float w_mod, float h_mod) {
		super.build_footer(w_mod, h_mod);
		this.footer.setBackground(new Color(129, 198, 134));
		this.footer.setLayout(new BorderLayout());
		this.footer.setBorder(new MatteBorder(5, 0, 0, 0, new Color(117, 138, 119)));

		int w = (int) (width * w_mod);
		int h = (int) (height * h_mod);

		JPanel buttons_container = new JPanel();
		buttons_container.setOpaque(false);
		buttons_container.setPreferredSize(new Dimension(w, h));
		buttons_container.setLayout(new GridLayout(1, 4, 170, 50));

		My_JButton disconnect = new My_JButton("Disconnect", 25.f);
		disconnect.setForeground(Color.WHITE);
		disconnect.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		disconnect.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controler.disconnect();
				ref_win.launch_starting_menu();
			}
		});

		buttons_container.add(disconnect);
		buttons_container.add(new JLabel());
		buttons_container.add(new JLabel());
		buttons_container.add(new JLabel());

		this.footer.add(buttons_container, BorderLayout.NORTH);

	}

	@Override
	protected void build_right(float w_mod, float h_mod) {
		super.build_right(w_mod, h_mod);
		this.right.setBackground(new Color(170, 203, 172));
		this.right.setBorder(new MatteBorder(0, 5, 0, 0, new Color(117, 138, 119)));

		int w = (int) (width * w_mod);
		int h = (int) (height * h_mod);

		Image img = new ImageIcon(Resources.get_image("mrMonopoly2.png")).getImage();
		Image newimg = img.getScaledInstance(w, h, java.awt.Image.SCALE_SMOOTH);

		JLabel label = new JLabel();
		label.setIcon(new ImageIcon(newimg));

		this.right.add(label, BorderLayout.CENTER);
	}

	@Override
	protected void build_left(float w_mod, float h_mod) {
		super.build_left(w_mod, h_mod);
		this.left.setBackground(new Color(170, 203, 172));
		this.left.setLayout(new BorderLayout());
		this.left.setBorder(new MatteBorder(0, 0, 0, 5, new Color(117, 138, 119)));

		int w = (int) (width * w_mod);
		int h = (int) (height * h_mod);

		int h_up = (int) (h * 0.15);
		int h_center = (int) (h * 0.15);
		int h_footer = (int) (h * 0.70);

		JPanel up = new JPanel();
		up.setLayout(new BorderLayout());
		up.setOpaque(true);
		up.setPreferredSize(new Dimension(w, h_up));

		My_JLabel status_title = new My_JLabel("Status", 35.f);
		status_title.setForeground(Color.WHITE);
		up.add(status_title);

		JPanel center = new JPanel();
		center.setOpaque(true);
		center.setLayout(new BorderLayout());
		center.setPreferredSize(new Dimension(w, h_center));
		center.setBorder(new MatteBorder(0, 0, 5, 0, new Color(117, 138, 119)));

		My_JLabel status_value = new My_JLabel("<html>Your status : <font color='green'>Online</font></html>", 20.f);
		status_value.setForeground(Color.WHITE);
		center.add(status_value);

		JPanel down = new JPanel();
		down.setOpaque(false);
		down.setLayout(new GridLayout(4, 1));
		down.setPreferredSize(new Dimension(w, h_footer));

		My_JLabel online = new My_JLabel("Online", 20.f);
		My_JLabel missing = new My_JLabel("Missing", 20.f);
		My_JLabel occupied = new My_JLabel("Occupied", 20.f);
		My_JLabel search_game = new My_JLabel("Search game", 20.f);

		online.setForeground(Color.WHITE);
		online.setBackground(new Color(108, 125, 110));
		online.setOpaque(true);
		online.setBorder(new MatteBorder(0, 0, 2, 0, Color.WHITE));
		online.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		online.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				missing.setBackground(new Color(170, 203, 172));
				occupied.setBackground(new Color(170, 203, 172));
				search_game.setBackground(new Color(170, 203, 172));
				online.setBackground(new Color(108, 125, 110));
				status_value.setText("<html>Your status : <font color='green'>Online</font></html>");
				show_center_content("buttons");
				controler.set_status_online();
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}
		});

		missing.setForeground(Color.WHITE);
		missing.setBackground(new Color(170, 203, 172));
		missing.setOpaque(true);
		missing.setBorder(new MatteBorder(0, 0, 2, 0, Color.WHITE));
		missing.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		missing.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				missing.setBackground(new Color(108, 125, 110));
				occupied.setBackground(new Color(170, 203, 172));
				search_game.setBackground(new Color(170, 203, 172));
				online.setBackground(new Color(170, 203, 172));
				status_value.setText("<html>Your status : <font color='yellow'>Missing</font></html>");
				show_center_content("buttons");
				controler.set_status_missing();
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}
		});

		occupied.setForeground(Color.WHITE);
		occupied.setBackground(new Color(170, 203, 172));
		occupied.setOpaque(true);
		occupied.setBorder(new MatteBorder(0, 0, 2, 0, Color.WHITE));
		occupied.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		occupied.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				missing.setBackground(new Color(170, 203, 172));
				occupied.setBackground(new Color(108, 125, 110));
				search_game.setBackground(new Color(170, 203, 172));
				online.setBackground(new Color(170, 203, 172));
				status_value.setText("<html>Your status : <font color='red'>Occupied</font></html>");
				show_center_content("buttons");
				controler.set_status_occupied();
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}
		});

		search_game.setForeground(Color.WHITE);
		search_game.setBackground(new Color(170, 203, 172));
		search_game.setOpaque(true);
		search_game.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		search_game.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				missing.setBackground(new Color(170, 203, 172));
				occupied.setBackground(new Color(170, 203, 172));
				search_game.setBackground(new Color(108, 125, 110));
				online.setBackground(new Color(170, 203, 172));
				status_value.setText("<html>Your status : <font color='orange'>Search game</font></html>");
				show_center_content("match");
				controler.set_status_search_game();
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}
		});

		down.add(online);
		down.add(missing);
		down.add(occupied);
		down.add(search_game);

		this.left.add(up, BorderLayout.NORTH);
		this.left.add(center, BorderLayout.CENTER);
		this.left.add(down, BorderLayout.SOUTH);

	}
}
