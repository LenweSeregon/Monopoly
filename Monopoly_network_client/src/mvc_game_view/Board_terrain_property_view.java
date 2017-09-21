package mvc_game_view;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

import javax.swing.border.LineBorder;

import mvc_game_enums.Color_terrain;
import utils.Resources;
import utils.String_splitter;
import utils.String_swing;

@SuppressWarnings("serial")
public class Board_terrain_property_view extends Abstract_board_terrain_view {

	private Color color;
	private int price;
	private int house_price;
	private int[] rents;
	private Middle_board ref_middle;
	private int nb_house;

	/**
	 * Constructeur de la classe qui représente les propriétés qui sont affichés
	 * sur le terrain
	 * 
	 * @param number
	 *            le numéro du terrain
	 * @param o
	 *            l'orientation du terrain
	 * @param pos_x
	 *            la position X du terrain
	 * @param pos_y
	 *            la position Y du terrain
	 * @param width
	 *            la largeur du terrain
	 * @param height
	 *            la hauteur du terrain
	 * @param name
	 *            le nom du terrain
	 * @param color_b
	 *            la couleur du terrain
	 * @param price
	 *            le prix du terrain
	 * @param house_price
	 *            le prix d'une maison
	 * @param rents
	 *            les loyers avec les maisons
	 * @param middle_ref
	 *            la référence vers le milieu
	 */
	public Board_terrain_property_view(int number, Orientation o, int pos_x, int pos_y, int width, int height,
			String name, Color_terrain color_b, int price, int house_price, int[] rents, Middle_board middle_ref) {
		super(name, number, o, width, height, pos_x, pos_y);
		this.associate_color(color_b);
		this.price = price;
		this.house_price = house_price;
		this.rents = new int[rents.length];
		this.nb_house = 0;
		for (int i = 0; i < rents.length; i++) {
			this.rents[i] = rents[i];
		}
		this.ref_middle = middle_ref;

		this.setBorder(new LineBorder(Color.BLACK, 1));
		this.setBackground(Color.WHITE);

		this.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
				unshow_informations();
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				show_informations();
			}

			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
	}

	/**
	 * Méthode permettant de choisir le nombre de maison à afficher sur notre
	 * plateau
	 * 
	 * @param value
	 *            le nombre de maison
	 */
	public void set_nb_house(int value) {
		this.nb_house = value;
		this.repaint();
	}

	/**
	 * Méthode permettant de demander l'afficahge du zoom sur le milieu du
	 * plateau
	 */
	public void show_informations() {
		if (allow_show_informations && !fog_of_war) {
			ref_middle.build_center(name, color, price, rents, house_price);

		}
	}

	/**
	 * Méthode permettant de demander le déaffichage du zoom sur le milieu du
	 * plateau
	 */
	public void unshow_informations() {
		if (allow_show_informations) {
			ref_middle.build_center_empty();
		}
	}

	/**
	 * Méthode permettant de récupérer la couleur du terrain
	 * 
	 * @return la couleur du terrain
	 */
	public Color get_color() {
		return this.color;
	}

	/**
	 * Méthode permettant de récupérer le prix d'achat de la maison
	 * 
	 * @return le prix d'achat
	 */
	public int get_price() {
		return this.price;
	}

	/**
	 * Méthode permettant de récupérer tous les loyers associés au nombre de
	 * maisons
	 * 
	 * @return les différents loyers
	 */
	public int[] get_rents() {
		return this.rents;
	}

	/**
	 * Méthode permettant de récupérer le prix d'une maison du terrain
	 * 
	 * @return le prix d'un maison du terrain
	 */
	public int get_house_price() {
		return this.house_price;
	}

	private void associate_color(Color_terrain c) {

		if (c.equals(Color_terrain.BROWN)) {
			color = new Color(139, 69, 19);
		} else if (c.equals(Color_terrain.LIGHT_BLUE)) {
			color = new Color(35, 206, 250);
		} else if (c.equals(Color_terrain.PURPLE)) {
			color = new Color(128, 0, 128);
		} else if (c.equals(Color_terrain.ORANGE)) {
			color = Color.ORANGE;
		} else if (c.equals(Color_terrain.YELLOW)) {
			color = Color.YELLOW;
		} else if (c.equals(Color_terrain.RED)) {
			color = Color.RED;
		} else if (c.equals(Color_terrain.GREEN)) {
			color = Color.GREEN;
		} else if (c.equals(Color_terrain.DARK_BLUE)) {
			color = new Color(0, 0, 139);
		} else if (c.equals(Color_terrain.NONE)) {
			color = Color.WHITE;
		} else {
			System.err.println("ISSUE");
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(Color.WHITE);
		g2.drawRect(0, 0, width, height);
		if (!fog_of_war) {
			float part_banner = 0.25f;
			float part_name = 0.5f;
			float part_price = 0.20f;

			int width_banner = 0;
			int height_banner = 0;
			int x_banner = 0;
			int y_banner = 0;

			int width_price = 0;
			int height_price = 0;
			int x_price = 0;
			int y_price = 0;

			int width_name = 0;
			int height_name = 0;
			int x_name = 0;
			int y_name = 0;

			int rotation_radius = 0;

			if (orientation == Orientation.SOUTH || orientation == Orientation.NORTH) {
				width_banner = width;
				height_banner = (int) (height * part_banner);
				width_price = width;
				height_price = (int) (height * part_price);
				width_name = width;
				height_name = (int) (height * part_name);
			} else {
				width_banner = (int) (width * part_banner);
				height_banner = height;
				width_price = (int) (width * part_price);
				height_price = height;
				width_name = (int) (width * part_price);
				height_name = height;
			}

			if (orientation == Orientation.SOUTH) {
				x_banner = 0;
				y_banner = 0;

				x_price = 0;
				y_price = height - height_price;

				FontMetrics fm = g2.getFontMetrics(Resources.get_font("kabel.ttf").deriveFont(9.0f));
				x_name = 0;
				y_name = height_banner + fm.getHeight();

				rotation_radius = 0;

				g2.setColor(Color.BLACK);
				g2.drawLine(x_banner, y_banner + height_banner, x_banner + width_banner, y_banner + height_banner);
			} else if (orientation == Orientation.NORTH) {
				x_banner = 0;
				y_banner = height - height_banner;

				x_price = 0;
				y_price = 0;

				FontMetrics fm = g2.getFontMetrics(Resources.get_font("kabel.ttf").deriveFont(9.0f));
				x_name = 0;
				y_name = height - height_banner - fm.getHeight();

				rotation_radius = 180;

				g2.setColor(Color.BLACK);
				g2.drawLine(x_banner, y_banner - 1, x_banner + width_banner, y_banner - 1);
			} else if (orientation == Orientation.WEST) {
				x_banner = width - width_banner;
				y_banner = 0;

				x_price = 0;
				y_price = 0;

				FontMetrics fm = g2.getFontMetrics(Resources.get_font("kabel.ttf").deriveFont(9.0f));
				x_name = x_banner - fm.getHeight();
				y_name = 0;
				rotation_radius = 90;

				g2.setColor(Color.BLACK);
				g2.drawLine(x_banner - 1, 0, x_banner - 1, y_banner + height_banner);
			} else if (orientation == Orientation.EAST) {

				x_banner = 0;
				y_banner = 0;

				x_price = width - width_price;
				y_price = 0;

				FontMetrics fm = g2.getFontMetrics(Resources.get_font("kabel.ttf").deriveFont(9.0f));
				x_name = height_banner + fm.getHeight();
				y_name = 0;
				rotation_radius = 90;

				rotation_radius = 270;

				g2.setColor(Color.BLACK);
				g2.drawLine(x_banner + width_banner, 0, x_banner + width_banner, y_banner + height_banner);
			}

			// Drawing banner color
			g2.setColor(color);
			g2.fillRect(x_banner, y_banner, width_banner, height_banner);

			int size = 0;
			if (orientation == Orientation.SOUTH) {
				if (nb_house == 5) {
					size = height_banner - 5;
				} else {
					size = height_banner - 15;
				}
			} else if (orientation == Orientation.NORTH) {
				if (nb_house == 5) {
					size = height_banner - 5;
				} else {
					size = height_banner - 15;
				}
			} else if (orientation == Orientation.WEST) {
				if (nb_house == 5) {
					size = width_banner - 5;
				} else {
					size = width_banner - 15;
				}
			} else if (orientation == Orientation.EAST) {
				if (nb_house == 5) {
					size = width_banner - 5;
				} else {
					size = width_banner - 15;
				}
			}

			g2.rotate(Math.toRadians(rotation_radius), width / 2, height / 2);
			if (orientation == Orientation.EAST) {

				if (nb_house == 5) {
					g2.drawImage(Resources.get_image("hotel.png"), (width - size) / 2, -22, size, size, null);
				} else {
					for (int i = 0; i < nb_house; i++) {
						g2.drawImage(Resources.get_image("maison.png"), 28 + i * size + 5, -15, size, size, null);
					}
				}
			} else if (orientation == Orientation.WEST) {

				if (nb_house == 5) {
					g2.drawImage(Resources.get_image("hotel.png"), (width - size) / 2, -22, size, size, null);
				} else {
					for (int i = 0; i < nb_house; i++) {
						g2.drawImage(Resources.get_image("maison.png"), 25 + i * size + 5, -15, size, size, null);
					}
				}

			} else if (orientation == Orientation.NORTH) {

				if (nb_house == 5) {
					g2.drawImage(Resources.get_image("hotel.png"), (width - size) / 2, height_banner - size - 8, size,
							size, null);
				} else {
					for (int i = 0; i < nb_house; i++) {
						g2.drawImage(Resources.get_image("maison.png"), i * size + 5, height_banner - size - 6, size,
								size, null);
					}
				}

			} else if (orientation == Orientation.SOUTH) {
				if (nb_house == 5) {
					g2.drawImage(Resources.get_image("hotel.png"), (width - size) / 2, 2, size, size, null);
				} else {
					for (int i = 0; i < nb_house; i++) {
						g2.drawImage(Resources.get_image("maison.png"), i * size + 5, 7, size, size, null);
					}
				}
			}

			g2.rotate(-Math.toRadians(rotation_radius), width / 2, height / 2);

			// Drawing price
			FontMetrics f = g2.getFontMetrics(Resources.get_font("kabel.ttf").deriveFont(10.f));
			int w_price = f.stringWidth(price + "M");
			int h_price = f.getHeight();

			AffineTransform affine_transform = new AffineTransform();
			affine_transform.rotate(Math.toRadians(rotation_radius), w_price / 2, 0);
			Font rotated_font = Resources.get_font("kabel.ttf").deriveFont(10.f).deriveFont(affine_transform);

			g2.setColor(Color.BLACK);
			g2.setFont(rotated_font);
			if (orientation == Orientation.SOUTH || orientation == Orientation.NORTH) {
				g2.drawString(price + "M", (width - w_price) / 2, y_price + h_price);
			} else {
				g2.drawString(price + "M", x_price - h_price, ((width - w_price) / 2) - 3);
			}

			// Drawing name
			FontMetrics f2 = g2.getFontMetrics(Resources.get_font("kabel.ttf").deriveFont(9.f));

			AffineTransform affine_transform2 = new AffineTransform();
			affine_transform2.rotate(Math.toRadians(rotation_radius), w_price / 2, 0);
			Font rotated_font2 = Resources.get_font("kabel.ttf").deriveFont(9.f).deriveFont(affine_transform);

			g2.setColor(Color.BLACK);
			g2.setFont(rotated_font2);
			if (orientation == Orientation.SOUTH) {
				ArrayList<String_swing> strings_name = String_splitter.splitted_string(name,
						Resources.get_font("kabel.ttf").deriveFont(9.f), width_name - 15, width_name, x_name, y_name,
						1);
				for (String_swing s_w : strings_name) {
					g2.drawString(s_w.get_string(), s_w.get_x(), s_w.get_y());
				}
			} else if (orientation == Orientation.NORTH) {
				ArrayList<String_swing> strings_name = String_splitter.splitted_string(name,
						Resources.get_font("kabel.ttf").deriveFont(9.f), width_name - 15, width_name - 25, 200, y_name,
						-1);
				for (String_swing s_w : strings_name) {

					int width_text = f2.stringWidth(s_w.get_string());
					int x = ((int) (0.4f * width_text));
					g2.drawString(s_w.get_string(), x, s_w.get_y());
				}
			} else if (orientation == Orientation.WEST) {
				AffineTransform affine_transform3 = new AffineTransform();
				affine_transform3.rotate(Math.toRadians(rotation_radius), width / 2, height / 2);
				Font rotated_font3 = Resources.get_font("kabel.ttf").deriveFont(9.f).deriveFont(affine_transform);
				g2.setFont(rotated_font3);

				ArrayList<String_swing> strings_name = String_splitter.splitted_string(name,
						Resources.get_font("kabel.ttf").deriveFont(9.f), height_name - 15, width_name, x_name, y_name,
						-1);
				for (String_swing s_w : strings_name) {
					int width_text = f2.stringWidth(s_w.get_string());
					int y = height - 15 - (width_text / 2);// + (width_text /
															// 2);
					g2.drawString(s_w.get_string(), 60 + s_w.get_y(), y);
				}
			} else {
				AffineTransform affine_transform3 = new AffineTransform();
				affine_transform3.rotate(Math.toRadians(rotation_radius), width / 2, height / 2);
				Font rotated_font3 = Resources.get_font("kabel.ttf").deriveFont(9.f).deriveFont(affine_transform);
				g2.setFont(rotated_font3);

				ArrayList<String_swing> strings_name = String_splitter.splitted_string(name,
						Resources.get_font("kabel.ttf").deriveFont(9.f), height_name - 15, width_name, x_name, y_name,
						1);
				for (String_swing s_w : strings_name) {
					int width_text = f2.stringWidth(s_w.get_string());
					int y = 0 + (width_text / 2);// + (width_text / 2);
					g2.drawString(s_w.get_string(), s_w.get_y() + 25, y + 16);
				}

			}
		}
	}
}
