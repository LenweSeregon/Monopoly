package UI_elements;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;

import utils.Resources;

@SuppressWarnings("serial")
public class Animated_piece_start extends JComponent {

	private BufferedImage image;
	private int pos_x;
	private int pos_y;
	private int width;
	private int height;

	/**
	 * Constructeur de la classe représentant un pion dans le menu démarré qui a
	 * la capacité de bouger horizontalement pour faire l'animation
	 * 
	 * @param name_image
	 *            le nom de l'image a charger
	 * @param pos_x
	 *            la position X du pion
	 * @param pos_y
	 *            la position Y du pion
	 * @param width
	 *            la largeur du pion
	 * @param height
	 *            la hauteur du pion
	 */
	public Animated_piece_start(String name_image, int pos_x, int pos_y,
			int width, int height) {
		image = Resources.get_image(name_image);

		this.pos_x = pos_x;
		this.pos_y = pos_y;
		this.width = width;
		this.height = height;
	}

	/**
	 * Méthode permettant de définir la position X de notre pion, elle va être
	 * appelée pour réaliser l'animation
	 * 
	 * @param x
	 *            la position X à mettre
	 */
	public void set_pos_x(int x) {
		this.pos_x = x;
		this.repaint();
	}

	/**
	 * Méthode permettant de récupérer à tout instant la position actuelle de
	 * notre pion dans son panel contenant
	 * 
	 * @return la position X du pion
	 */
	public int get_pos_x() {
		return this.pos_x;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(image, pos_x, pos_y, width, height, this);
	}
}
