package menus;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.MatteBorder;

import utils.Resources;
import view.Window;
import UI_elements.Animated_pieces_panel_start;
import UI_elements.My_JLabel;

@SuppressWarnings("serial")
public class Starting_menu extends Abstract_panel {

	private JPanel menu_container;
	private CardLayout menu_container_cl;
	private Window win_ref;

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
	public Starting_menu(int width, int height, Window win_ref) {
		super(width, height);
		this.win_ref = win_ref;

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
		this.header.setBorder(new MatteBorder(0, 0, 5, 0, new Color(117, 138,
				119)));

		JPanel header_content = new JPanel();
		header_content.setLayout(new BorderLayout());
		header_content.setOpaque(false);

		JLabel title = new My_JLabel("Client Monopoly", 55.f);
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

		menu_container = new JPanel();
		menu_container_cl = new CardLayout();
		menu_container.setLayout(menu_container_cl);

		Connect_panel connect = new Connect_panel(w, h, "subscribe", win_ref,
				this);
		connect.setOpaque(false);
		Subscribe_panel sub = new Subscribe_panel(w, h, "connect", this);
		connect.setOpaque(false);

		menu_container.add(connect, "connect");
		menu_container.add(sub, "subscribe");

		menu_container_cl.show(menu_container, "connect");

		this.center.add(menu_container, BorderLayout.CENTER);
	}

	public void show_center_content(String name) {
		menu_container_cl.show(menu_container, name);
	}

	@Override
	protected void build_footer(float w_mod, float h_mod) {
		super.build_footer(w_mod, h_mod);
		this.footer.setBackground(Color.RED);
		this.footer.setLayout(new BorderLayout());
		this.footer.setBorder(new MatteBorder(5, 0, 0, 0, new Color(117, 138,
				119)));

		int w = (int) (width * w_mod);
		int h = (int) (height * h_mod);

		JPanel footer_left = new JPanel() {
			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(Resources.get_image("dollar.jpg"), 0, 0, w / 4, h,
						this);
				g.drawImage(Resources.get_image("dollar.jpg"), w / 4, 0, w / 4,
						h, this);
			}
		};
		footer_left.setBorder(new MatteBorder(0, 0, 0, 3, new Color(117, 138,
				119)));
		footer_left.setBackground(Color.PINK);
		footer_left.setPreferredSize(new Dimension(new Dimension(w / 2, h)));
		footer_left.setLayout(new GridLayout(3, 1));

		JLabel copyright = new My_JLabel(
				"Copyright @ Nicolas Serf - All rights reserved", 20.f);
		copyright.setForeground(Color.WHITE);
		JLabel contact = new My_JLabel("Contact me : serf.nicolas@gmail.com",
				20.f);
		contact.setForeground(Color.WHITE);
		JLabel situation = new My_JLabel(
				"Formation : 'Licence 3 Informatique' - Lens", 20.f);
		situation.setForeground(Color.WHITE);

		footer_left.add(copyright);
		footer_left.add(contact);
		footer_left.add(situation);

		Animated_pieces_panel_start footer_right = new Animated_pieces_panel_start(
				w / 2, h);
		footer_right.setBackground(Color.CYAN);
		footer_right.setPreferredSize(new Dimension(new Dimension(w / 2, h)));
		footer_right.setBorder(new MatteBorder(0, 4, 0, 0, new Color(117, 138,
				119)));
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

		int w = (int) (width * w_mod);
		int h = (int) (height * h_mod);

		Image img = new ImageIcon(Resources.get_image("mrMonopoly.png"))
				.getImage();
		Image newimg = img.getScaledInstance(w, h, java.awt.Image.SCALE_SMOOTH);

		JLabel label = new JLabel();
		label.setIcon(new ImageIcon(newimg));

		this.left.add(label, BorderLayout.CENTER);

	}
}
