package mvc_game_view;

import java.awt.Dimension;

import javax.swing.JPanel;

import settings.Setting_configuration;

@SuppressWarnings("serial")
public abstract class Abstract_board_terrain_view extends JPanel {

	protected Orientation orientation;
	protected int width;
	protected int height;
	protected int pos_x;
	protected int pos_y;
	protected int nb_piece_in;
	protected int number;
	protected String name;
	protected boolean allow_show_informations;
	protected boolean fog_of_war;

	/**
	 * Constructeur de la classe abstraire qui représente un terrain sur le
	 * plateau de jeu
	 * 
	 * @param name
	 *            le nom du terrain
	 * @param number
	 *            le numéro du terrain
	 * @param o
	 *            l'orientation du terrain
	 * @param width
	 *            la largeur du terrain
	 * @param height
	 *            la hauteur du terrain
	 * @param pos_x
	 *            la position en X du terrain
	 * @param pos_y
	 *            la position en Y du terrain
	 */
	public Abstract_board_terrain_view(String name, int number, Orientation o, int width, int height, int pos_x,
			int pos_y) {
		this.number = number;
		this.orientation = o;
		this.width = width;
		this.height = height;
		this.pos_x = pos_x;
		this.pos_y = pos_y;
		this.nb_piece_in = 0;
		this.name = name;
		this.allow_show_informations = true;
		if (Setting_configuration.mask_board) {
			fog_of_war = true;
		} else {
			fog_of_war = false;
		}

		this.setBounds(this.pos_x, this.pos_y, width, height);
	}

	/**
	 * Méthode permettant de définir si il faut activer le brouillard de guerre
	 * ou non sur la cellule
	 * 
	 * @param value
	 *            rai si on doit l'activer faux sinon
	 */
	public void set_fog_of_war(boolean value) {
		fog_of_war = value;
	}

	/**
	 * Méthode permettant d'autoriser le zoom sur une propriété au milieu du
	 * plateau
	 * 
	 * @param value
	 *            valeure booléenne pour l'autorisation
	 */
	public void set_allow_show_informations(boolean value) {
		this.allow_show_informations = value;
	}

	/**
	 * Méthode permettant de connaitre le nom du terrain
	 * 
	 * @return le nombre du terrain
	 */
	public String get_name() {
		return this.name;
	}

	/**
	 * Méthode permettant de connaitre le numéro du terrain
	 * 
	 * @return le numéro du terrain
	 */
	public int get_number() {
		return this.number;
	}

	/**
	 * Méthode permettant de connaitre la position X du terrain
	 * 
	 * @return la position X du terrain
	 */
	public int get_position_x() {
		return this.pos_x;
	}

	/**
	 * Méthode permettant de connaitre la position Y du terrain
	 * 
	 * @return la position Y du terrain
	 */
	public int get_position_y() {
		return this.pos_y;
	}

	/**
	 * Mthode permettant de connaitre la largeur du terrain
	 * 
	 * @return la largeur du terrain
	 */
	public int get_width() {
		return this.width;
	}

	/**
	 * Méthode permettant de connaitre la hauteur du terrain
	 * 
	 * @return la hauteur du terrain
	 */
	public int get_height() {
		return this.height;
	}

	/**
	 * Méthode permettant de connaitre le nombre de pions sur le terrain
	 * 
	 * @return le nombre de pion sur le terrain
	 */
	public int get_nb_piece() {
		return this.nb_piece_in;
	}

	/**
	 * Méthode permettant de mettre le nombre de pion sur le terrain
	 * 
	 * @param nb
	 *            le nombre de pion
	 */
	public void set_nb_piece(int nb) {
		this.nb_piece_in = nb;
	}

	/**
	 * Méthode permettant de connaitre a quelle position on peut placer le pion
	 * par rapport aux nombres de pions qui sont déjà présents sur le terrain
	 * 
	 * @return un objet Dimension qui contient la position X et Y pour placer le
	 *         pion
	 */
	public Dimension get_free_position() {
		int size_piece = 30;
		int width_bis = width / 2;
		int height_bis = height / 2;
		if (nb_piece_in == 0) {
			return new Dimension((width_bis - size_piece) / 2, (height_bis - size_piece) / 2);
		} else if (nb_piece_in == 1) {
			return new Dimension(width_bis + ((width_bis - size_piece) / 2), (height_bis - size_piece) / 2);
		} else if (nb_piece_in == 2) {
			return new Dimension((width_bis - size_piece) / 2, height_bis + ((height_bis - size_piece) / 2));
		} else if (nb_piece_in == 3) {
			return new Dimension(width_bis + ((width_bis - size_piece) / 2),
					height_bis + ((height_bis - size_piece) / 2));
		} else {
			return null;
		}
	}

}
