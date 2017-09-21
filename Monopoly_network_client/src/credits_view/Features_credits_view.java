package credits_view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

import UI_elements.My_JLabel;
import utils.Resources;

@SuppressWarnings("serial")
public class Features_credits_view extends JPanel {

	/**
	 * Constructeur de la classe qui représente la vue des fonctionnalités du
	 * projet
	 * 
	 * @param width
	 *            la largeur du panel
	 * @param height
	 *            la hauteur du panel
	 */
	public Features_credits_view(int width, int height) {
		this.setPreferredSize(new Dimension(width, height));
		this.setBackground(Color.WHITE);
		this.setLayout(new BorderLayout());

		int h_general = (int) (height * 0.5f);

		int width_left = (int) (width * 0.75f);
		int width_right = (int) (width * 0.25f);

		JPanel left = new JPanel();
		left.setLayout(new BorderLayout());
		left.setPreferredSize(new Dimension(width_left, height));
		left.setBorder(new MatteBorder(0, 0, 0, 5, new Color(117, 138, 119)));

		JPanel general_things = new JPanel();
		general_things.setBackground(Color.WHITE);
		general_things.setPreferredSize(new Dimension(width_left, h_general));
		general_things.setLayout(new GridLayout(5, 1));
		general_things.setBorder(new EmptyBorder(20, 20, 20, 20));

		My_JLabel title_general = new My_JLabel("<html>Asked features</html>", 26.f);
		title_general.setHorizontalAlignment(JLabel.LEFT);
		title_general.setForeground(Color.DARK_GRAY);

		My_JLabel ask1 = new My_JLabel("<html> - Monopoly game in general</html>", 16.f);
		ask1.setHorizontalAlignment(JLabel.LEFT);
		ask1.setForeground(Color.DARK_GRAY);

		My_JLabel ask2 = new My_JLabel("<html> - All menus with contents </html>", 16.f);
		ask2.setHorizontalAlignment(JLabel.LEFT);
		ask2.setForeground(Color.DARK_GRAY);

		My_JLabel ask3 = new My_JLabel("<html> - Nearly all variants of Monopoly </html>", 16.f);
		ask3.setHorizontalAlignment(JLabel.LEFT);
		ask3.setForeground(Color.DARK_GRAY);

		My_JLabel ask4 = new My_JLabel("<html> - MVC architecture </html>", 16.f);
		ask4.setHorizontalAlignment(JLabel.LEFT);
		ask4.setForeground(Color.DARK_GRAY);

		general_things.add(title_general);
		general_things.add(ask1);
		general_things.add(ask2);
		general_things.add(ask3);
		general_things.add(ask4);

		JPanel add_things_pan = new JPanel();
		add_things_pan.setBorder(new MatteBorder(5, 0, 0, 0, new Color(117, 138, 119)));
		add_things_pan.setBackground(Color.WHITE);
		add_things_pan.setLayout(new BorderLayout());

		JPanel add_things = new JPanel();
		add_things.setBackground(Color.WHITE);
		add_things.setPreferredSize(new Dimension(width_left, h_general));
		add_things.setLayout(new GridLayout(5, 1));
		add_things.setBorder(new EmptyBorder(20, 20, 20, 20));

		My_JLabel title_more = new My_JLabel("<html>Additionnal features</html>", 26.f);
		title_more.setHorizontalAlignment(JLabel.LEFT);
		title_more.setForeground(Color.DARK_GRAY);

		My_JLabel ask1_more = new My_JLabel("<html> - Monopoly game in network</html>", 16.f);
		ask1_more.setHorizontalAlignment(JLabel.LEFT);
		ask1_more.setForeground(Color.DARK_GRAY);

		My_JLabel ask2_more = new My_JLabel("<html> - Server with a good interface </html>", 16.f);
		ask2_more.setHorizontalAlignment(JLabel.LEFT);
		ask2_more.setForeground(Color.DARK_GRAY);

		My_JLabel ask3_more = new My_JLabel("<html> - Server can manage several games in same time </html>", 16.f);
		ask3_more.setHorizontalAlignment(JLabel.LEFT);
		ask3_more.setForeground(Color.DARK_GRAY);

		My_JLabel ask4_more = new My_JLabel("<html> - Connection and identification on server </html>", 16.f);
		ask4_more.setHorizontalAlignment(JLabel.LEFT);
		ask4_more.setForeground(Color.DARK_GRAY);

		add_things.add(title_more);
		add_things.add(ask1_more);
		add_things.add(ask2_more);
		add_things.add(ask3_more);
		add_things.add(ask4_more);

		add_things_pan.add(add_things, BorderLayout.CENTER);

		left.add(general_things, BorderLayout.NORTH);
		left.add(add_things_pan, BorderLayout.CENTER);

		JPanel right = new JPanel();
		right.setPreferredSize(new Dimension(width_right, height));
		right.setBackground(Color.WHITE);

		Image img = new ImageIcon(Resources.get_image("scroll.png")).getImage();
		Image newimg = img.getScaledInstance(width_right, height, java.awt.Image.SCALE_SMOOTH);

		JLabel scroll = new JLabel();
		scroll.setIcon(new ImageIcon(newimg));

		right.add(scroll, BorderLayout.CENTER);

		this.add(left, BorderLayout.WEST);
		this.add(right, BorderLayout.EAST);
	}
}
