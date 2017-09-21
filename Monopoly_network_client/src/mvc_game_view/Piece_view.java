package mvc_game_view;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import utils.Resources;

@SuppressWarnings("serial")
public class Piece_view extends JLabel {
	private String pseudo_associated;
	private ImageIcon image;
	private int position;
	private int pos_x;
	private int pos_y;
	private int width;
	private int height;

	/**
	 * Constructeur de la classe qui représente la visualisation du pion dans le
	 * plateau
	 * 
	 * @param player_asso
	 *            le nom du joueur associé au pion
	 * @param path
	 *            le chemin de l'image du pion
	 * @param pos
	 *            la position sur le plateau du pion
	 * @param pos_x
	 *            la position X sur la vue du plateau
	 * @param pos_y
	 *            la position Y sur la vue du plateau
	 * @param width
	 *            la largeur du pion
	 * @param height
	 *            la hauteur du pion
	 */
	public Piece_view(String player_asso, String path, int pos, int pos_x, int pos_y, int width, int height) {
		this.setBounds(pos_x, pos_y, width, height);
		this.pseudo_associated = player_asso;
		this.width = width;
		this.height = height;
		this.position = pos;
		this.pos_x = pos_y;
		this.pos_y = pos_y;
		this.image = new ImageIcon(Resources.get_image(path).getScaledInstance(width, height, Image.SCALE_SMOOTH));
		// this.setIcon(this.image);
	}

	/**
	 * Méthode peremettant de récupérer le pseudo associé au pion
	 * 
	 * @return le pseudo associé au pion
	 */
	public String get_pseudo() {
		return this.pseudo_associated;
	}

	/**
	 * Méthode permettant de définir la position sur le plateau du pion
	 * 
	 * @param pos
	 *            la position
	 */
	public void set_pos(int pos) {
		this.position = pos;
	}

	/**
	 * Méthode qui récupére la position
	 * 
	 * @return la position
	 */
	public int get_pos() {
		return this.position;
	}

	/**
	 * Méthode permettant de définir la position relative du pion sur le plateau
	 * 
	 * @param x
	 *            la position X du pion
	 * @param y
	 *            la position Y du pion
	 */
	public void set_pos_relative(int x, int y) {
		this.pos_x = x;
		this.pos_y = y;
		this.setBounds(pos_x, pos_y, width, height);
		this.repaint();
		this.revalidate();
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(image.getImage(), 0, 0, width, height, null);

		Toolkit.getDefaultToolkit().sync();
	}

}