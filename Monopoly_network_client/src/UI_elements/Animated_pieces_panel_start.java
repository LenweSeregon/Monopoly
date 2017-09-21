package UI_elements;

import java.awt.Graphics;
import java.util.Vector;

import javax.swing.JPanel;

import utils.Resources;

@SuppressWarnings("serial")
public class Animated_pieces_panel_start extends JPanel implements Runnable {

	private int width;
	private int height;
	private Vector<Animated_piece_start> pieces;
	private boolean is_running;

	/**
	 * Constructeur de la classe qui représente le petit panneau en bas du menu
	 * démarré qui permet l'animation de celui ci. Cette classe s'assure de
	 * dessiner les différents pions et de les animer
	 * 
	 * @param width
	 *            la largeur du panneau
	 * @param height
	 *            la hauteur du panneau
	 */
	public Animated_pieces_panel_start(int width, int height) {
		this.width = width;
		this.height = height;
		this.is_running = false;
		this.pieces = new Vector<Animated_piece_start>();

		init_pieces();

	}

	/**
	 * Méthode privée permettant d'initialiser les différents pions du panneau
	 * d'animation en les mettant à leurs positions initales
	 */
	private void init_pieces() {
		int width_piece = width / 9;
		int height_piece = height / 2;

		int cur_pos_x = 10;
		int cur_pos_y = (height - height_piece) / 2;
		for (int i = 1; i <= 8; i++) {
			this.pieces.add(
					new Animated_piece_start("pion/" + i + ".png", cur_pos_x, cur_pos_y, width_piece, height_piece));
			cur_pos_x += width_piece + 19;
		}
	}

	/**
	 * Méthode permettant de choisir la valeur du booléen qui controle
	 * l'animation des pions. Si celui-ci est passé à faux, alors l'animation
	 * s'arrete complétement
	 * 
	 * @param running
	 *            la valeur que l'on souhaite mettre à running
	 */
	public void set_is_running(boolean running) {
		this.is_running = running;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(Resources.get_image("dollar.jpg"), 0, 0, width / 2, height, this);
		g.drawImage(Resources.get_image("dollar.jpg"), width / 2, 0, width / 2, height, this);
		for (Animated_piece_start a : pieces) {
			a.paintComponent(g);
		}
	}

	@Override
	public void run() {
		is_running = true;
		while (is_running) {
			for (Animated_piece_start a : pieces) {
				a.set_pos_x(a.get_pos_x() + 1);
				repaint();
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				if (a.get_pos_x() > 700) {
					a.set_pos_x(-70);
				}
			}
		}
	}

}
