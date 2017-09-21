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
public class General_credits_view extends JPanel {

	/**
	 * Constructeur de la classe qui représente la vue général des crédits du
	 * projet
	 * 
	 * @param width
	 *            la largeur du panel
	 * @param height
	 *            la hauteur du panel
	 */
	public General_credits_view(int width, int height) {
		this.setPreferredSize(new Dimension(width, height));
		this.setBackground(Color.WHITE);
		this.setLayout(new BorderLayout());

		int h_general = (int) (height * 0.5f);
		int h_desc = (int) (height * 0.5f);

		int width_left = (int) (width * 0.75f);
		int width_right = (int) (width * 0.25f);

		JPanel left = new JPanel();
		left.setLayout(new BorderLayout());
		left.setPreferredSize(new Dimension(width_left, height));
		left.setBorder(new MatteBorder(0, 0, 0, 5, new Color(117, 138, 119)));

		JPanel general_things = new JPanel();
		general_things.setBackground(Color.WHITE);
		general_things.setPreferredSize(new Dimension(width_left, h_general));
		general_things.setLayout(new GridLayout(4, 1));
		general_things.setBorder(new EmptyBorder(20, 20, 20, 20));

		My_JLabel title_general = new My_JLabel("<html>General</html>", 26.f);
		title_general.setHorizontalAlignment(JLabel.LEFT);
		title_general.setForeground(Color.DARK_GRAY);

		My_JLabel dev = new My_JLabel("<html>Developer : Nicolas Serf</html>", 16.f);
		dev.setHorizontalAlignment(JLabel.LEFT);
		dev.setForeground(Color.DARK_GRAY);

		My_JLabel teacher = new My_JLabel("<html>Teacher : Eric Piette </html>", 16.f);
		teacher.setHorizontalAlignment(JLabel.LEFT);
		teacher.setForeground(Color.DARK_GRAY);

		My_JLabel place = new My_JLabel("<html>Place : Jean Perrin </html>", 16.f);
		place.setHorizontalAlignment(JLabel.LEFT);
		place.setForeground(Color.DARK_GRAY);

		general_things.add(title_general);
		general_things.add(dev);
		general_things.add(teacher);
		general_things.add(place);

		JPanel desc = new JPanel();
		desc.setBorder(new MatteBorder(5, 0, 0, 0, new Color(117, 138, 119)));
		desc.setLayout(new BorderLayout());
		desc.setBackground(Color.WHITE);
		desc.setPreferredSize(new Dimension(width_left, h_desc));

		My_JLabel title_desc = new My_JLabel("<html> Description</html>", 26.f);
		title_desc.setBorder(new EmptyBorder(20, 20, 0, 0));
		title_desc.setHorizontalAlignment(JLabel.LEFT);
		title_desc.setPreferredSize(new Dimension(width_left, (int) (h_desc * 0.25)));
		title_desc.setForeground(Color.DARK_GRAY);

		My_JLabel text_desc = new My_JLabel("<html> <div style='text-align: justify;'>"
				+ "The monopoly project is carried out as part of an end-of-study project for "
				+ " computer license last year at Jean-Perrin Lens, in the COO module supervised by Mr Piette. "
				+ "This project does not start from any base and must allow to apply all notions seen at the university. "
				+ "This project brought me a lot because I find it in the end quite successful with "
				+ "its network part." + "</div></html>", 13.f);
		text_desc.setBorder(new EmptyBorder(0, 20, 20, 20));
		text_desc.setForeground(Color.DARK_GRAY);
		text_desc.setPreferredSize(new Dimension(width_left, (int) (h_desc * 0.75)));
		text_desc.setMaximumSize(new Dimension(width_left, (int) (h_desc * 0.75)));

		desc.add(title_desc, BorderLayout.NORTH);
		desc.add(text_desc, BorderLayout.CENTER);

		left.add(general_things, BorderLayout.NORTH);
		left.add(desc, BorderLayout.CENTER);

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
