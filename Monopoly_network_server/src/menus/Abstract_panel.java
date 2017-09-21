package menus;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public abstract class Abstract_panel extends JPanel {
	protected int width;
	protected int height;
	protected JPanel header;
	protected JPanel center;
	protected JPanel right;
	protected JPanel left;
	protected JPanel footer;

	/**
	 * Constructeur de la classe représentant la classe abstraite de tout les
	 * panneaux qui se trouvent dans l'application. Cette classe permet de
	 * découper facilement sa fenetre via les 5 directions qui existe avec un
	 * borderLayout et permet de gagner du temps sur le développment des
	 * fenetres
	 * 
	 * @param width
	 *            la largeur de notre panel
	 * @param height
	 *            la hauteur de notre panel
	 */
	public Abstract_panel(int width, int height) {
		this.width = width;
		this.height = height;

		this.header = null;
		this.center = null;
		this.right = null;
		this.left = null;
		this.footer = null;

		this.setPreferredSize(new Dimension(width, height));
		this.setLayout(new BorderLayout());
	}

	/**
	 * Méthode permettant de construire la tete du panel en appliquant les deux
	 * modifier de largeur et de hauteur.
	 * 
	 * @param w_mod
	 *            la valeur de modification de la largeur (entre 0.0f et 1.0f)
	 * @param h_mod
	 *            la valeur de modification de la hauteur (entre 0.0f et 1.0f)
	 */
	protected void build_header(float w_mod, float h_mod) {
		int w = (int) (width * w_mod);
		int h = (int) (height * h_mod);

		this.header = new JPanel();
		this.header.setPreferredSize(new Dimension(w, h));
		this.add(header, BorderLayout.NORTH);
	}

	/**
	 * Méthode permettant de construire la centre du panel en appliquant les
	 * deux modifier de largeur et de hauteur.
	 * 
	 * @param w_mod
	 *            la valeur de modification de la largeur (entre 0.0f et 1.0f)
	 * @param h_mod
	 *            la valeur de modification de la hauteur (entre 0.0f et 1.0f)
	 */
	protected void build_center(float w_mod, float h_mod) {
		int w = (int) (width * w_mod);
		int h = (int) (height * h_mod);

		this.center = new JPanel();
		this.center.setPreferredSize(new Dimension(w, h));
		this.add(center, BorderLayout.CENTER);
	}

	/**
	 * Méthode permettant de construire la droite du panel en appliquant les
	 * deux modifier de largeur et de hauteur.
	 * 
	 * @param w_mod
	 *            la valeur de modification de la largeur (entre 0.0f et 1.0f)
	 * @param h_mod
	 *            la valeur de modification de la hauteur (entre 0.0f et 1.0f)
	 */
	protected void build_right(float w_mod, float h_mod) {
		int w = (int) (width * w_mod);
		int h = (int) (height * h_mod);

		this.right = new JPanel();
		this.right.setPreferredSize(new Dimension(w, h));
		this.add(right, BorderLayout.EAST);
	}

	/**
	 * Méthode permettant de construire la gauche du panel en appliquant les
	 * deux modifier de largeur et de hauteur.
	 * 
	 * @param w_mod
	 *            la valeur de modification de la largeur (entre 0.0f et 1.0f)
	 * @param h_mod
	 *            la valeur de modification de la hauteur (entre 0.0f et 1.0f)
	 */
	protected void build_left(float w_mod, float h_mod) {
		int w = (int) (width * w_mod);
		int h = (int) (height * h_mod);

		this.left = new JPanel();
		this.left.setPreferredSize(new Dimension(w, h));
		this.add(left, BorderLayout.WEST);
	}

	/**
	 * Méthode permettant de construire le bas du panel en appliquant les deux
	 * modifier de largeur et de hauteur.
	 * 
	 * @param w_mod
	 *            la valeur de modification de la largeur (entre 0.0f et 1.0f)
	 * @param h_mod
	 *            la valeur de modification de la hauteur (entre 0.0f et 1.0f)
	 */
	protected void build_footer(float w_mod, float h_mod) {
		int w = (int) (width * w_mod);
		int h = (int) (height * h_mod);

		this.footer = new JPanel();
		this.footer.setPreferredSize(new Dimension(w, h));
		this.add(footer, BorderLayout.SOUTH);
	}
}
