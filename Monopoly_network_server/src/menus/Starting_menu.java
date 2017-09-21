package menus;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;

import UI_elements.Animated_pieces_panel_start;
import UI_elements.My_JButton;
import UI_elements.My_JLabel;
import mvc_network_model.Server;
import utils.Resources;
import utils.String_verifier;
import view.Window;

@SuppressWarnings("serial")
public class Starting_menu extends Abstract_panel {

	private Window win_ref;
	private JLabel error_message;

	/**
	 * Constructeur de la classe représentant la vue principale du panneau de
	 * démarrage lorsque l'on lance l'application. Cette vue met tout en forme
	 * et lance des animations.
	 * 
	 * @param width
	 *            la largeur du panneau
	 * @param height
	 *            la hauteur du panneau
	 */
	public Starting_menu(int width, int height, Window ref) {
		super(width, height);
		this.win_ref = ref;

		this.build_header(1.0f, 0.15f);
		this.build_center(0.60f, 0.7f);
		this.build_footer(1.0f, 0.15f);
		this.build_left(0.40f, 0.7f);
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

		JLabel title = new My_JLabel("Server Monopoly", 55.f);
		title.setForeground(Color.WHITE);

		header_content.add(title, BorderLayout.CENTER);

		this.header.add(header_content, BorderLayout.CENTER);
	}

	@Override
	protected void build_center(float w_mod, float h_mod) {
		super.build_center(w_mod, h_mod);
		this.center.setLayout(new BorderLayout());
		this.center.setBackground(new Color(170, 203, 172));

		int w = (int) (width * w_mod);
		int h = (int) (height * h_mod);

		int h_up = (int) (h * 0.6f);
		int h_mid = (int) (h * 0.20f);
		int h_down = (int) (h * 0.20f);

		JPanel container = new JPanel();
		container.setLayout(new BorderLayout());

		JPanel up_container = new JPanel();
		up_container.setPreferredSize(new Dimension(w, h_up));
		up_container.setLayout(new GridLayout(2, 2, 50, 50));
		up_container.setBorder(new EmptyBorder(130, 60, 130, 60));

		My_JLabel server_port = new My_JLabel("Server port ", 25.f);
		server_port.setBackground(Color.WHITE);
		My_JLabel server_nb_per_game = new My_JLabel("Player per game ", 25.f);
		server_nb_per_game.setBackground(Color.WHITE);

		JTextField port_value = new JTextField("2000");
		JTextField per_game_value = new JTextField("2");

		port_value.setFont(Resources.get_font("kabel.ttf").deriveFont(Font.BOLD, 20.f));
		port_value.setHorizontalAlignment(JTextField.CENTER);
		port_value.setBorder(new LineBorder(Color.BLACK, 6));
		port_value.setBackground(Color.WHITE);
		port_value.setEditable(false);
		port_value.setForeground(Color.BLACK);
		port_value.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					try_to_launch_server(port_value.getText(), per_game_value.getText());
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}
		});

		per_game_value.setFont(Resources.get_font("kabel.ttf").deriveFont(Font.BOLD, 20.f));
		per_game_value.setHorizontalAlignment(JTextField.CENTER);
		per_game_value.setBorder(new LineBorder(Color.BLACK, 6));
		per_game_value.setBackground(Color.WHITE);
		per_game_value.setForeground(Color.BLACK);
		per_game_value.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					try_to_launch_server(port_value.getText(), per_game_value.getText());
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}
		});

		up_container.add(server_port);
		up_container.add(port_value);
		up_container.add(server_nb_per_game);
		up_container.add(per_game_value);

		JPanel mid_container = new JPanel();
		mid_container.setLayout(new BorderLayout());
		mid_container.setPreferredSize(new Dimension(w, h_mid));
		mid_container.setBorder(new EmptyBorder(40, 280, 40, 280));

		My_JButton start = new My_JButton("Start server", 30.f);
		start.setBorder(new LineBorder(Color.BLACK, 3));
		start.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		start.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try_to_launch_server(port_value.getText(), per_game_value.getText());
			}
		});

		mid_container.add(start, BorderLayout.CENTER);

		JPanel down_container = new JPanel();
		down_container.setLayout(new BorderLayout());
		down_container.setPreferredSize(new Dimension(w, h_down));

		error_message = new My_JLabel("", 25.f);
		error_message.setVisible(false);

		down_container.add(error_message, BorderLayout.CENTER);

		container.add(up_container, BorderLayout.NORTH);
		container.add(mid_container, BorderLayout.CENTER);
		container.add(down_container, BorderLayout.SOUTH);

		this.center.add(container, BorderLayout.NORTH);
	}

	private void show_error_message(String message) {
		error_message.setText(message);
		error_message.setForeground(Color.RED);
		error_message.setVisible(true);
	}

	/**
	 * Méthode permettant d'essayer de lancer le serveur
	 * 
	 * @param port
	 *            le port du serveur
	 * @param nb_per
	 *            le nombre de joeuur par partie
	 */
	private void try_to_launch_server(String port, String nb_per) {
		if (!port.isEmpty() && !nb_per.isEmpty()) {
			if (!nb_per.equals("2") && !nb_per.equals("3") && !nb_per.equals("4")) {
				show_error_message("Only 2|3|4 for number of player");
				return;
			}
			if (String_verifier.string_is_numeric(port)) {
				int port_value = Integer.valueOf(port);
				int per_game_value = Integer.valueOf(nb_per);
				if (Server.is_port_free(port_value)) {
					win_ref.launch_configure_terrain_menu(port_value, per_game_value);
				} else {
					show_error_message("Port unavailable, server is already start");
				}
			} else {
				show_error_message("Port input should be compose with digit only");
			}
		} else {
			show_error_message("Some inputs are empty");
		}
	}

	@Override
	protected void build_footer(float w_mod, float h_mod) {
		super.build_footer(w_mod, h_mod);
		this.footer.setBackground(Color.RED);
		this.footer.setLayout(new BorderLayout());
		this.footer.setBorder(new MatteBorder(5, 0, 0, 0, new Color(117, 138, 119)));

		int w = (int) (width * w_mod);
		int h = (int) (height * h_mod);

		JPanel footer_left = new JPanel();
		footer_left.setBorder(new MatteBorder(0, 0, 0, 3, new Color(117, 138, 119)));
		footer_left.setBackground(new Color(129, 198, 134));
		footer_left.setPreferredSize(new Dimension(new Dimension(w / 2, h)));
		footer_left.setLayout(new GridLayout(3, 1));

		JLabel copyright = new My_JLabel("Copyright @ Nicolas Serf - All rights reserved", 20.f);
		copyright.setForeground(Color.WHITE);
		JLabel contact = new My_JLabel("Contact me : serf.nicolas@gmail.com", 20.f);
		contact.setForeground(Color.WHITE);
		JLabel situation = new My_JLabel("Formation : 'Licence 3 Informatique' - Lens", 20.f);
		situation.setForeground(Color.WHITE);

		footer_left.add(copyright);
		footer_left.add(contact);
		footer_left.add(situation);

		Animated_pieces_panel_start footer_right = new Animated_pieces_panel_start(w / 2, h);
		footer_right.setBackground(new Color(129, 198, 134));
		footer_right.setPreferredSize(new Dimension(new Dimension(w / 2, h)));
		footer_right.setBorder(new MatteBorder(0, 4, 0, 0, new Color(117, 138, 119)));
		new Thread(footer_right).start();

		this.footer.add(footer_left, BorderLayout.WEST);
		this.footer.add(footer_right, BorderLayout.EAST);
	}

	@Override
	protected void build_right(float w_mod, float h_mod) {
		super.build_right(w_mod, h_mod);
		this.right.setBackground(Color.YELLOW);
	}

	@Override
	protected void build_left(float w_mod, float h_mod) {
		super.build_left(w_mod, h_mod);
		this.left.setBackground(Color.WHITE);
		this.left.setLayout(new BorderLayout());
		this.left.setBorder(new MatteBorder(0, 0, 0, 5, new Color(117, 138, 119)));

		int w = (int) (width * w_mod);
		int h = (int) (height * h_mod);

		Image img = new ImageIcon(Resources.get_image("network.jpg")).getImage();
		Image newimg = img.getScaledInstance(w, h, java.awt.Image.SCALE_SMOOTH);

		JLabel label = new JLabel();
		label.setIcon(new ImageIcon(newimg));

		this.left.add(label, BorderLayout.CENTER);

	}
}
