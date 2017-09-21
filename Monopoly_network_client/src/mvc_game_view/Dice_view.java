package mvc_game_view;

import java.awt.Graphics;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import utils.Resources;

@SuppressWarnings("serial")
public class Dice_view extends JLabel {

	private ImageIcon img;

	/**
	 * Constructeur de la classe qui permet d'afficher les dés et leurs valeurs
	 */
	public Dice_view() {
		this.setVisible(false);
	}

	/**
	 * Constructeur de la classe qui permet de choisir la valeur du dé
	 * 
	 * @param value
	 *            la valeure du dé
	 */
	public void set_value(int value) {
		this.setVisible(true);
		img = new ImageIcon(Resources.get_image("dice-0" + value + ".png"));
		this.repaint();
	}

	@Override
	public void paintComponent(Graphics g) {
		if (img != null) {
			g.drawImage(img.getImage(), 0, 0, getWidth(), getHeight(), null);
			Toolkit.getDefaultToolkit().sync();
		}
	}
}