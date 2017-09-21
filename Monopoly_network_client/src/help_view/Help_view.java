package help_view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.MatteBorder;

import UI_elements.My_JButton;
import UI_elements.My_JLabel;
import lobby_view.Lobby_view;
import menus.Abstract_panel;
import utils.Resources;

@SuppressWarnings("serial")
public class Help_view extends Abstract_panel {

	private JPanel center_container;
	private CardLayout center_container_cl;
	private int current;
	private ArrayList<String> names;
	private Lobby_view parent_ref;

	private JLabel left_arrow;
	private JLabel right_arrow;

	/**
	 * Constructeur de la classe qui représente de manière général la vue d'aide
	 * du jeu. Ce panneau contient un cardlayout avec les différents aides du
	 * jeu qu'on peut changer facilement en cliquant sur les fleches
	 * 
	 * @param width
	 *            la largeur de la fenetre
	 * @param height
	 *            la hauteur de la fenetre
	 * @param parent_ref
	 *            le panneau parent du panneau d'aide
	 */
	public Help_view(int width, int height, Lobby_view parent_ref) {
		super(width, height);
		this.parent_ref = parent_ref;
		this.current = 0;
		this.names = new ArrayList<String>();

		this.build_header(1.0f, 0.15f);
		this.build_center(0.8f, 0.70f);
		this.build_left(0.1f, 0.70f);
		this.build_right(0.1f, 0.70f);
		this.build_footer(1.0f, 0.15f);
	}

	@Override
	protected void build_header(float w_mod, float h_mod) {
		super.build_header(w_mod, h_mod);
		this.header.setBackground(new Color(170, 203, 172));
		this.header.setLayout(new BorderLayout());
		this.header.setBorder(new MatteBorder(0, 0, 5, 0, new Color(117, 138, 119)));

		My_JLabel title = new My_JLabel("Help", 35.f);
		title.setForeground(Color.WHITE);

		this.header.add(title, BorderLayout.CENTER);
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

		Help_money money = new Help_money(w, h);
		Help_house house = new Help_house(w, h);
		Help_jail jail = new Help_jail(w, h);

		center_container.add(money, "money");
		center_container.add(house, "house");
		center_container.add(jail, "jail");
		this.names.add("money");
		this.names.add("house");
		this.names.add("jail");

		center_container_cl.show(center_container, "money");
		this.center.add(center_container, BorderLayout.CENTER);

	}

	@Override
	protected void build_footer(float w_mod, float h_mod) {
		super.build_footer(w_mod, h_mod);
		this.footer.setBackground(new Color(170, 203, 172));
		this.footer.setLayout(new BorderLayout());
		this.footer.setBorder(new MatteBorder(5, 0, 0, 0, new Color(117, 138, 119)));

		JPanel container = new JPanel();
		container.setOpaque(false);
		container.setLayout(new GridLayout(1, 4, 0, 35));

		My_JButton back = new My_JButton("Return", 25.f);
		back.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		back.setForeground(Color.WHITE);
		back.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				parent_ref.show_center_content("buttons");
			}
		});

		container.add(back);
		container.add(new JLabel());
		container.add(new JLabel());
		container.add(new JLabel());

		this.footer.add(container, BorderLayout.CENTER);

	}

	@Override
	protected void build_right(float w_mod, float h_mod) {
		super.build_right(w_mod, h_mod);
		this.right.setLayout(new BorderLayout());
		this.right.setBackground(new Color(170, 203, 172));
		this.right.setBorder(new MatteBorder(0, 5, 0, 0, new Color(117, 138, 119)));

		int w = (int) (width * w_mod);
		int h = (int) (height * h_mod);

		Image img = new ImageIcon(Resources.get_image("right_arrow.png")).getImage();
		Image newimg = img.getScaledInstance(w - 10, (int) (h / 5.5), java.awt.Image.SCALE_SMOOTH);

		right_arrow = new JLabel();
		right_arrow.setHorizontalAlignment(JLabel.CENTER);
		right_arrow.setIcon(new ImageIcon(newimg));
		right_arrow.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {
				switch_to_right();
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});

		this.right.add(right_arrow, BorderLayout.CENTER);
	}

	@Override
	protected void build_left(float w_mod, float h_mod) {
		super.build_left(w_mod, h_mod);
		this.left.setBackground(new Color(170, 203, 172));
		this.left.setLayout(new BorderLayout());
		this.left.setBorder(new MatteBorder(0, 0, 0, 5, new Color(117, 138, 119)));

		int w = (int) (width * w_mod);
		int h = (int) (height * h_mod);

		Image img = new ImageIcon(Resources.get_image("left_arrow.png")).getImage();
		Image newimg = img.getScaledInstance(w - 10, (int) (h / 5.5), java.awt.Image.SCALE_SMOOTH);

		left_arrow = new JLabel();
		left_arrow.setHorizontalAlignment(JLabel.CENTER);
		left_arrow.setIcon(new ImageIcon(newimg));
		left_arrow.setVisible(false);
		left_arrow.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {
				switch_to_left();
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});

		this.left.add(left_arrow, BorderLayout.CENTER);
	}

	/**
	 * Méthode permettant de voir l'aide précédente
	 */
	private void switch_to_left() {
		if (current > 0) {
			current--;
			if (current == 0) {
				left_arrow.setVisible(false);
			}
			right_arrow.setVisible(true);
			center_container_cl.show(center_container, this.names.get(current));
		}
	}

	/**
	 * Méthode permettant de voir l'aide suivante
	 */
	private void switch_to_right() {
		if (current < this.names.size() - 1) {
			current++;
			if (current == this.names.size() - 1) {
				right_arrow.setVisible(false);
			}
			left_arrow.setVisible(true);
			center_container_cl.show(center_container, this.names.get(current));
		}
	}

}
