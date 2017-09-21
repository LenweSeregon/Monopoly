package credits_view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

import UI_elements.My_JLabel;
import utils.Resources;

@SuppressWarnings("serial")
public class Difficulties_credits_view extends JPanel {

	/**
	 * Constructeur de la classe qui représente la vue des difficultés du projet
	 * 
	 * @param width
	 *            la largeur du panel
	 * @param height
	 *            la hauteur du panel
	 */
	public Difficulties_credits_view(int width, int height) {
		this.setPreferredSize(new Dimension(width, height));
		this.setBackground(Color.ORANGE);
		this.setLayout(new BorderLayout());

		JPanel container = new JPanel();
		container.setPreferredSize(new Dimension(width, height));
		container.setLayout(new BorderLayout());

		int w_left = (int) (width * 0.5);
		int w_right = (int) (width * 0.5);

		JPanel left = new JPanel();
		left.setBackground(Color.WHITE);
		left.setBorder(new MatteBorder(0, 0, 0, 5, new Color(117, 138, 119)));
		left.setLayout(new BorderLayout());
		left.setPreferredSize(new Dimension(w_left, height));

		My_JLabel tl = new My_JLabel("<html>List of difficulties :</html>", 27.f);
		tl.setHorizontalAlignment(JLabel.LEFT);
		tl.setForeground(Color.DARK_GRAY);
		tl.setBorder(new EmptyBorder(20, 20, 20, 10));
		tl.setPreferredSize(new Dimension(w_left, (int) (height * 0.18)));

		My_JLabel problems = new My_JLabel("<html>" + "<ul>" + "<li> Network communication and message protocole</li>"
				+ "<li> Network mvc on server side</li>" + "<li> Communication between network mvc and game mvc </li>"
				+ "<li> Thread managing for waiting when needed </li>"
				+ "<li> Game rules transmission from server </li>" + "<li> Keep as must as possible a clear code </li>"
				+ "</ul>" + "</html>", 15.f);
		problems.setForeground(Color.DARK_GRAY);
		problems.setHorizontalAlignment(JLabel.LEFT);
		problems.setPreferredSize(new Dimension(w_left, (int) (height * 0.82)));

		left.add(tl, BorderLayout.NORTH);
		left.add(problems, BorderLayout.CENTER);

		JPanel right = new JPanel();
		right.setBackground(Color.WHITE);
		right.setLayout(new BorderLayout());
		right.setPreferredSize(new Dimension(w_right, height));

		Image img = new ImageIcon(Resources.get_image("difficulties.png")).getImage();
		Image newimg = img.getScaledInstance(w_right, height, java.awt.Image.SCALE_SMOOTH);

		JLabel warning = new JLabel();
		warning.setIcon(new ImageIcon(newimg));
		right.add(warning, BorderLayout.CENTER);

		container.add(left, BorderLayout.WEST);
		container.add(right, BorderLayout.EAST);

		this.add(container, BorderLayout.CENTER);
	}
}
