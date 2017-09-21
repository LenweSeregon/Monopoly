package mvc_game_view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import utils.Resources;

@SuppressWarnings("serial")
public class Miniature_terrain extends JPanel {

	private Color color;
	private Image image;
	private Abstract_board_terrain_view ref_terrain;
	private boolean mortgage_mod;
	private int nb_house;

	/**
	 * Constructeur de la classe qui permet de représenter une miniature de
	 * terrain pour afficher dans les cartes détenus par le joueur
	 * 
	 * @param ref
	 *            la référence sur le terrain
	 */
	public Miniature_terrain(Abstract_board_terrain_view ref) {
		this.ref_terrain = ref;
		this.mortgage_mod = false;
		this.nb_house = 0;
		if (ref instanceof Board_terrain_property_view) {
			Board_terrain_property_view v = (Board_terrain_property_view) (ref);
			this.color = v.get_color();
			this.image = null;
		} else {
			Board_terrain_image_view v = (Board_terrain_image_view) (ref);
			this.color = null;
			this.image = Resources.get_image(v.get_path());
		}

		this.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
				if (ref_terrain instanceof Board_terrain_property_view) {
					((Board_terrain_property_view) ref_terrain).unshow_informations();
				} else if (ref_terrain instanceof Board_terrain_image_view) {
					((Board_terrain_image_view) ref_terrain).unshow_informations();
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				if (ref_terrain instanceof Board_terrain_property_view) {
					((Board_terrain_property_view) ref_terrain).show_informations();
				} else if (ref_terrain instanceof Board_terrain_image_view) {
					((Board_terrain_image_view) ref_terrain).show_informations();
				}
			}

			@Override
			public void mouseClicked(MouseEvent e) {
			}

		});
		this.ref_terrain = ref;

		this.setBorder(new LineBorder(Color.BLACK, 2));
	}

	/**
	 * Méthode permettant de récupérer la référence sur le terrain de la
	 * miniature
	 * 
	 * @return
	 */
	public Abstract_board_terrain_view get_ref_terrain() {
		return this.ref_terrain;
	}

	/**
	 * Méthode permettant de mettre la valeur d'hypothéque ou déhypothéque a la
	 * miniature
	 * 
	 * @param value
	 *            la valeure boolénne pour l'hypotheque
	 */
	public void set_mortagage_mod(boolean value) {
		this.mortgage_mod = value;
		this.repaint();
	}

	/**
	 * Méthode permettant de mettre le nombre de maison sur la miniature
	 * 
	 * @param nb_house
	 *            le nombre de maison à afficher
	 */
	public void set_nb_house(int nb_house) {
		this.nb_house = nb_house;
		if (this.ref_terrain instanceof Board_terrain_property_view) {
			((Board_terrain_property_view) this.ref_terrain).set_nb_house(nb_house);
		}
		this.repaint();
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		if (mortgage_mod) {
			FontMetrics fm = g2.getFontMetrics(Resources.get_font("kabel.ttf").deriveFont(10.f));
			int width_text = fm.stringWidth("Mortgaged");

			g2.setColor(Color.RED);
			g2.fillRect(0, 0, getWidth(), getHeight());
			g2.setColor(Color.BLACK);
			g2.setStroke(new BasicStroke(2));
			g2.drawLine(0, 0, getWidth(), getHeight());
			g2.drawLine(getWidth(), 0, 0, getHeight());
			g2.setStroke(new BasicStroke(1));
			g2.setFont(Resources.get_font("kabel.ttf").deriveFont(10.f));
			g2.setColor(Color.WHITE);
			g2.drawString("Mortgaged", (getWidth() - width_text) / 2,
					((getHeight() - fm.getHeight()) / 2) + fm.getHeight());

			FontMetrics fm2 = g2.getFontMetrics(Resources.get_font("kabel.ttf").deriveFont(9.f));
			String[] name = ref_terrain.get_name().split(" ");
			String target = name[name.length - 1];

			int width_target = fm2.stringWidth(target);
			g2.setFont(Resources.get_font("kabel.ttf").deriveFont(9.f));
			g2.setColor(Color.WHITE);
			g2.drawString(target, (getWidth() - width_target) / 2, (getHeight() / 7) + fm.getHeight() + 5);
		} else {
			if (color != null) {
				FontMetrics fm = g2.getFontMetrics(Resources.get_font("kabel.ttf").deriveFont(9.f));

				g2.setColor(color);
				g2.fillRect(2, 2, getWidth() - 4, getHeight() / 7);
				g2.setColor(Color.BLACK);
				g2.drawLine(0, getHeight() / 7, getWidth(), getHeight() / 7);

				String[] name = ref_terrain.get_name().split(" ");
				String target = name[name.length - 1];

				int width_target = fm.stringWidth(target);
				g2.setFont(Resources.get_font("kabel.ttf").deriveFont(9.f));
				g2.setColor(Color.BLACK);
				g2.drawString(target, (getWidth() - width_target) / 2, (getHeight() / 7) + fm.getHeight() + 5);

				if (nb_house == 5) {
					int size = getHeight() / 7 - 4;
					g2.drawImage(Resources.get_image("hotel.png"), (getWidth() - size) / 2, 2, size, size, null);
				} else {
					int size = getHeight() / 7 - 4;
					for (int i = 0; i < nb_house; i++) {
						g2.drawImage(Resources.get_image("maison.png"), i * size + 2, 2, size, size, null);
					}
				}
			} else {
				g2.drawImage(image, 2, 2, getWidth() - 4, getHeight() - 4, this);
			}

			FontMetrics fm = g2.getFontMetrics(Resources.get_font("kabel.ttf").deriveFont(10.f));
			int width_text = fm.stringWidth("Hover for");
			int width_text_bis = fm.stringWidth("informations");

			g2.setFont(Resources.get_font("kabel.ttf").deriveFont(10.f));
			g2.setColor(Color.RED);
			g2.drawString("Hover for", (getWidth() - width_text) / 2,
					((getHeight() - fm.getHeight()) / 2) + fm.getHeight());

			g2.drawString("informations", (getWidth() - width_text_bis) / 2,
					((getHeight() - fm.getHeight()) / 2) + fm.getHeight() + fm.getHeight() + 5);
		}
	}
}
