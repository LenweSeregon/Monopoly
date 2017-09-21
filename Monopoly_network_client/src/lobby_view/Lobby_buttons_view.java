package lobby_view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import UI_elements.My_JButton;
import UI_elements.My_JLabel;

@SuppressWarnings("serial")
public class Lobby_buttons_view extends JPanel {

	/**
	 * Constructeur de la classe qui contient tout les boutons du lobby. C'est
	 * avec ces classes qu'on va choisir si l'on veut afficher le menu de
	 * crédit, d'aide, etc...
	 * 
	 * @param width
	 *            lar largeur du panel
	 * @param height
	 *            la hauteur du panel
	 * @param parent_ref
	 *            la référence vers le parent SWING contenant
	 */
	public Lobby_buttons_view(int width, int height, Lobby_view parent_ref) {
		this.setPreferredSize(new Dimension(width, height));
		this.setLayout(new BorderLayout());

		int h_up = (int) (height * 0.25);
		int h_center = (int) (height * 0.75);

		JPanel up = new JPanel();
		up.setPreferredSize(new Dimension(width, h_up));
		up.setLayout(new BorderLayout());

		My_JLabel title = new My_JLabel("Menu", 40.f);
		title.setForeground(new Color(129, 198, 134));
		up.add(title, BorderLayout.CENTER);

		JPanel center = new JPanel();
		center.setBorder(new EmptyBorder(65, 120, 65, 120));
		center.setPreferredSize(new Dimension(width, h_center));
		center.setLayout(new GridLayout(3, 1, 50, 50));

		My_JButton credits = new My_JButton("Credits", 30.f);
		credits.setBorderPainted(true);
		credits.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				parent_ref.show_center_content("credits");
			}
		});
		My_JButton help = new My_JButton("Help", 30.f);
		help.setBorderPainted(true);
		help.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				parent_ref.show_center_content("help");
			}
		});
		My_JButton me = new My_JButton("Me", 30.f);
		me.setBorderPainted(true);
		me.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				parent_ref.show_center_content("me");
			}
		});

		center.add(credits);
		center.add(help);
		center.add(me);

		this.add(up, BorderLayout.NORTH);
		this.add(center, BorderLayout.CENTER);

	}
}
