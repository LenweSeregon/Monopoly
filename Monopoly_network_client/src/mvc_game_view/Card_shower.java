package mvc_game_view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import UI_elements.My_JButton;
import UI_elements.My_JLabel;
import mvc_game_controler.Game_controler;
import utils.Resources;

@SuppressWarnings("serial")
public class Card_shower extends JPanel {

	private ArrayList<Miniature_terrain> terrains;

	private My_JButton arrow_left;
	private My_JButton arrow_right;

	private int indice;

	private CardLayout card_layout;
	private JPanel card_container;

	private Player_view ref_parent;

	/**
	 * Le constructeur de la classe qui wrap toutes les fonctionnalités
	 * d'affichage des cartes et les actions associés
	 * 
	 * @param ref_controler
	 *            une référence vers le controler
	 * @param parent
	 *            une référence vers le conteneur SWING parent
	 * @param width
	 *            la largeur du panel
	 * @param height
	 *            la hauteur du panel
	 */
	public Card_shower(Game_controler ref_controler, Player_view parent, int width, int height) {

		this.setPreferredSize(new Dimension(width, height));
		this.setLayout(new BorderLayout());
		this.terrains = new ArrayList<Miniature_terrain>();
		this.ref_parent = parent;

		this.indice = 0;

		int w_left = (int) (width * 0.15);
		int w_center = (int) (width * 0.58);
		int w_right = (int) (width * 0.27);

		JPanel left = new JPanel();
		left.setPreferredSize(new Dimension(w_left, height));
		left.setLayout(new GridLayout(2, 1, 5, 5));

		Image img_left = new ImageIcon(Resources.get_image("flecheG.png")).getImage();
		Image newimg_left = img_left.getScaledInstance(w_left - 6, (int) (height / 3) - 10,
				java.awt.Image.SCALE_SMOOTH);

		arrow_left = new My_JButton("", 10.f);
		arrow_left.setIcon(new ImageIcon(newimg_left));
		arrow_left.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		arrow_left.setRolloverEnabled(false);
		arrow_left.setFocusPainted(false);
		arrow_left.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				left_arrow_clicked();
			}
		});

		Image img_right = new ImageIcon(Resources.get_image("flecheD.png")).getImage();
		Image newimg_right = img_right.getScaledInstance(w_left - 6, (int) (height / 3) - 10,
				java.awt.Image.SCALE_SMOOTH);

		arrow_right = new My_JButton("", 10.f);
		arrow_right.setIcon(new ImageIcon(newimg_right));
		arrow_right.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		arrow_right.setRolloverEnabled(false);
		arrow_right.setFocusPainted(false);
		arrow_right.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				right_arrow_clicked();
			}
		});

		left.add(arrow_left);
		left.add(arrow_right);

		JPanel right = new JPanel();
		right.setPreferredSize(new Dimension(w_right, height));
		right.setLayout(new GridLayout(4, 1, 5, 5));

		JButton sell = new JButton("Sell");
		sell.setFont(Resources.get_font("kabel.ttf").deriveFont(11.f));
		sell.setBorder(new LineBorder(Color.BLACK, 2));
		sell.setBackground(new Color(117, 138, 119));
		sell.setForeground(Color.WHITE);
		sell.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (terrains.size() > 0) {

					ref_controler.sell_property(terrains.get(indice).get_ref_terrain().get_number());
				}
			}
		});
		JButton hypo = new JButton("Morgage");
		hypo.setFont(Resources.get_font("kabel.ttf").deriveFont(11.f));
		hypo.setBorder(new LineBorder(Color.BLACK, 2));
		hypo.setBackground(new Color(117, 138, 119));
		hypo.setForeground(Color.WHITE);
		hypo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (terrains.size() > 0) {
					ref_controler.mortgage(terrains.get(indice).get_ref_terrain().get_number());
				}
			}
		});
		JButton unhypo = new JButton("Unmorgage");
		unhypo.setFont(Resources.get_font("kabel.ttf").deriveFont(11.f));
		unhypo.setBorder(new LineBorder(Color.BLACK, 2));
		unhypo.setBackground(new Color(117, 138, 119));
		unhypo.setForeground(Color.WHITE);
		unhypo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (terrains.size() > 0) {
					ref_controler.unmortgage(terrains.get(indice).get_ref_terrain().get_number());
				}
			}
		});

		JPanel houses = new JPanel();
		houses.setLayout(new GridLayout(1, 3, 5, 5));
		houses.setPreferredSize(new Dimension(w_right, height));

		int size1 = (height / 4) - 15;
		int size2 = (w_right / 3) - 10;

		int size = Math.min(size1, size2);

		Image img_house = new ImageIcon(Resources.get_image("maison.png")).getImage();
		Image newimg_house = img_house.getScaledInstance(size, size, java.awt.Image.SCALE_SMOOTH);

		Image img_plus = new ImageIcon(Resources.get_image("plus.png")).getImage();
		Image newimg_plus = img_plus.getScaledInstance(size, size, java.awt.Image.SCALE_SMOOTH);

		Image img_minus = new ImageIcon(Resources.get_image("minus.png")).getImage();
		Image newimg_minus = img_minus.getScaledInstance(size, size, java.awt.Image.SCALE_SMOOTH);

		JLabel house = new JLabel();
		house.setIcon(new ImageIcon(newimg_house));
		house.setHorizontalAlignment(JLabel.CENTER);

		JButton plus = new My_JButton("", 10.f);
		plus.setHorizontalAlignment(JButton.CENTER);
		plus.setVerticalAlignment(JButton.CENTER);
		plus.setIcon(new ImageIcon(newimg_plus));
		plus.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (terrains.size() > 0) {
					ref_controler.add_house(terrains.get(indice).get_ref_terrain().get_number());
				}
			}
		});

		JButton minus = new My_JButton("", 10.f);
		minus.setHorizontalAlignment(JButton.CENTER);
		minus.setVerticalAlignment(JButton.CENTER);
		minus.setIcon(new ImageIcon(newimg_minus));
		minus.setRolloverEnabled(false);
		minus.setContentAreaFilled(false);
		minus.setBorderPainted(false);
		minus.setFocusPainted(false);
		minus.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (terrains.size() > 0) {
					ref_controler.remove_house(terrains.get(indice).get_ref_terrain().get_number());
				}
			}
		});

		houses.add(minus);
		houses.add(house);
		houses.add(plus);

		right.add(sell);
		right.add(hypo);
		right.add(unhypo);
		right.add(houses);

		JPanel center = new JPanel();
		center.setPreferredSize(new Dimension(w_center, height));
		center.setLayout(new BorderLayout());
		center.setOpaque(false);
		center.setBorder(new EmptyBorder(0, 15, 0, 15));

		card_layout = new CardLayout();
		card_container = new JPanel();
		card_container.setBorder(new EmptyBorder(0, 35, 0, 35));
		card_container.setPreferredSize(new Dimension(w_center, height));
		card_container.setLayout(card_layout);

		JPanel test = new JPanel();
		test.setBackground(Color.BLUE);

		center.add(card_container, BorderLayout.CENTER);
		rebuild();

		this.add(left, BorderLayout.WEST);
		this.add(center, BorderLayout.CENTER);
		this.add(right, BorderLayout.EAST);
	}

	/**
	 * Méthode permettant de choisir le nombre de maison a afficher dans une
	 * miniature
	 * 
	 * @param number
	 *            le numéro du terrain
	 * @param nb_house
	 *            le nombre de maison
	 */
	public void set_nb_house(int number, int nb_house) {
		for (Miniature_terrain minia : terrains) {
			if (minia.get_ref_terrain().get_number() == number) {
				minia.set_nb_house(nb_house);
			}
		}
	}

	/**
	 * Méthode permettant de choisir via le numéro d'une propriété de
	 * l'hypothéquer
	 * 
	 * @param number
	 *            le numéro de la maison
	 */
	public void mortgage_from_number(int number) {
		for (Miniature_terrain minia : terrains) {
			if (minia.get_ref_terrain().get_number() == number) {
				minia.set_mortagage_mod(true);
			}
		}
	}

	/**
	 * Méthode permettant de choisir via le numéro d'une propriété de la
	 * déhypothéquer
	 * 
	 * @param number
	 *            le numéro de la maison
	 */
	public void unmortgage_from_number(int number) {
		for (Miniature_terrain minia : terrains) {
			if (minia.get_ref_terrain().get_number() == number) {
				minia.set_mortagage_mod(false);
			}
		}
	}

	/**
	 * Méthode permettant de voir la carte d'avant
	 */
	private void left_arrow_clicked() {
		indice--;
		if (indice == -1) {
			if (terrains.size() == 0) {
				indice = 0;
			} else {
				indice = terrains.size() - 1;
			}
		}
		card_layout.previous(card_container);
	}

	/**
	 * Méthode permettant de voir la carte d'aprés
	 */
	private void right_arrow_clicked() {
		indice++;
		if (indice == terrains.size()) {
			indice = 0;
		}
		card_layout.next(card_container);
	}

	/**
	 * Méthdoe pouvant etre appelé aprés avoir retirer / ajouter une nouvelle
	 * carte pou reconstruire le panel
	 */
	public void rebuild() {
		card_container.removeAll();
		if (terrains.size() == 0) {
			JPanel pan = new JPanel();
			pan.setLayout(new BorderLayout());

			My_JLabel lab = new My_JLabel("No property", 11.f);
			pan.add(lab, BorderLayout.CENTER);
			card_container.add(pan);

		} else {
			for (Miniature_terrain v : terrains) {
				card_container.add(v);
			}
			card_layout.first(card_container);
		}
		indice = 0;
		this.repaint();
	}

	/**
	 * Méthode permettant d'ajouter une minaiture au gestionnaire de propriété
	 * 
	 * @param ref
	 *            la référence sur le terrain a ajouter
	 */
	public void add_miniature(Abstract_board_terrain_view ref) {
		Miniature_terrain ter = new Miniature_terrain(ref);
		terrains.add(ter);
		rebuild();
	}

	/**
	 * Méthode permettant de retirer une miniature au gestionnaire de propriété
	 * 
	 * @param ref
	 *            la référence sur le terrain a ajouter
	 */
	public void remove_miniature(Abstract_board_terrain_view ref) {
		int it = -1;
		for (int i = 0; i < terrains.size(); i++) {
			if (terrains.get(i).get_ref_terrain() == ref) {
				it = i;
				break;
			}
			it++;
		}

		if (it != -1) {
			terrains.remove(it);
			rebuild();
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		ref_parent.repaint();
	}
}
