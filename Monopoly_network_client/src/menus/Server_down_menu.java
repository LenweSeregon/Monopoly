package menus;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import UI_elements.My_JLabel;

@SuppressWarnings("serial")
public class Server_down_menu extends JPanel {

	/**
	 * Constructeur de la classe qui représente un simple panel qui montre que
	 * le serveur est down
	 * 
	 * @param width
	 *            la largeur du panel
	 * @param height
	 *            la hauteur du panel
	 */
	public Server_down_menu(int width, int height) {
		this.setPreferredSize(new Dimension(width, height));
		this.setLayout(new BorderLayout());
		this.setBackground(new Color(129, 198, 134));
		this.setBorder(new LineBorder(Color.WHITE, 30));

		My_JLabel title = new My_JLabel("Server down . . .", 90.f);
		title.setForeground(Color.WHITE);

		this.add(title, BorderLayout.CENTER);
	}
}
