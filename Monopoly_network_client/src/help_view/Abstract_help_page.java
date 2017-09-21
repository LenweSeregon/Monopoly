package help_view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import UI_elements.My_JLabel;

@SuppressWarnings("serial")
public class Abstract_help_page extends JPanel {

	protected My_JLabel title;
	protected My_JLabel desc_1;
	protected My_JLabel desc_2;
	protected JLabel photo_1;
	protected JLabel photo_2;
	protected JLabel photo_3;

	private int width_photos;
	private int height_photos;

	/**
	 * Constructeur de la classe abstraite qui représente une aide pour le jeu.
	 * Cette classe abstraite est présente car tous les panneaux d'aide suivent
	 * la même structure
	 * 
	 * @param width
	 *            la largeur du panel
	 * @param height
	 *            la hauteur du panel
	 */
	public Abstract_help_page(int width, int height) {
		this.setPreferredSize(new Dimension(width, height));
		this.setLayout(new BorderLayout());
		this.setBorder(new EmptyBorder(20, 20, 20, 20));

		this.width_photos = width / 3 - (40 / 3);
		this.height_photos = width / 6;

		JPanel container = new JPanel();
		container.setLayout(new BorderLayout());

		JPanel title_pan = new JPanel();
		title_pan.setLayout(new BorderLayout());

		JPanel desc_pan = new JPanel();
		desc_pan.setLayout(new BorderLayout());

		JPanel desc_1_pan = new JPanel();
		desc_1_pan.setLayout(new BorderLayout());

		JPanel desc_2_pan = new JPanel();
		desc_2_pan.setLayout(new BorderLayout());

		JPanel desc_photos = new JPanel();
		desc_photos.setLayout(new GridLayout(1, 3));

		title = new My_JLabel("", 35.f);

		desc_1 = new My_JLabel("", 16.f);

		desc_2 = new My_JLabel("", 16.f);

		int w_photos = width / 3 - (40 / 3);
		int h_photos = width / 6;

		photo_1 = new JLabel();
		photo_1.setPreferredSize(new Dimension(w_photos, h_photos));
		photo_2 = new JLabel();
		photo_2.setPreferredSize(new Dimension(w_photos, h_photos));
		photo_3 = new JLabel();
		photo_3.setPreferredSize(new Dimension(w_photos, h_photos));

		title_pan.add(title, BorderLayout.CENTER);

		desc_1_pan.add(desc_1, BorderLayout.CENTER);
		desc_2_pan.add(desc_2, BorderLayout.CENTER);
		desc_photos.add(photo_1);
		desc_photos.add(photo_2);
		desc_photos.add(photo_3);

		desc_pan.add(desc_1_pan, BorderLayout.NORTH);
		desc_pan.add(desc_photos, BorderLayout.CENTER);
		desc_pan.add(desc_2_pan, BorderLayout.SOUTH);

		container.add(title_pan, BorderLayout.NORTH);
		container.add(desc_pan, BorderLayout.CENTER);

		this.add(container, BorderLayout.CENTER);
	}

	/**
	 * Méthode permettant de choisir le titre
	 * 
	 * @param text
	 *            le titre
	 */
	public void set_title(String text) {
		title.setText(text);
	}

	/**
	 * Méthode permettant de choisir la premiére description
	 * 
	 * @param text
	 *            la première description
	 */
	public void set_desc_1(String text) {
		desc_1.setText(text);
	}

	/**
	 * Méthode permettant de choisir la deuxième description
	 * 
	 * @param text
	 *            la deuxième description
	 */
	public void set_desc_2(String text) {
		desc_2.setText(text);
	}

	/**
	 * Méthode permettant de choisir la première photo
	 * 
	 * @param photo
	 *            l'image de la première photo
	 */
	public void set_photo_1(BufferedImage photo) {
		Image img = new ImageIcon(photo).getImage();
		Image newimg = img.getScaledInstance(width_photos, height_photos, java.awt.Image.SCALE_SMOOTH);

		this.photo_1.setIcon(new ImageIcon(newimg));
	}

	/**
	 * Méthode permettant de choisir la deuxieme photo
	 * 
	 * @param photo
	 *            l'image de la deuxieme photo
	 */
	public void set_photo_2(BufferedImage photo) {
		Image img = new ImageIcon(photo).getImage();
		Image newimg = img.getScaledInstance(width_photos, height_photos, java.awt.Image.SCALE_SMOOTH);

		this.photo_2.setIcon(new ImageIcon(newimg));
	}

	/**
	 * Méthode permettant de choisir la troisieme photo
	 * 
	 * @param photo
	 *            l'image de la troisieme photo
	 */
	public void set_photo_3(BufferedImage photo) {
		Image img = new ImageIcon(photo).getImage();
		Image newimg = img.getScaledInstance(width_photos, height_photos, java.awt.Image.SCALE_SMOOTH);

		this.photo_3.setIcon(new ImageIcon(newimg));
	}
}
