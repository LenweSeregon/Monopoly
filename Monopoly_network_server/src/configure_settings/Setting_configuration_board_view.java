package configure_settings;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

import UI_elements.My_JLabel;

@SuppressWarnings("serial")
public class Setting_configuration_board_view extends JPanel {

	/**
	 * Constructeur de la classe preséentant la vue pour le parametrage des
	 * options pour le plateau
	 * 
	 * @param width
	 *            largeur du panel
	 * @param height
	 *            hauteur du panel
	 */
	public Setting_configuration_board_view(int width, int height) {
		this.setPreferredSize(new Dimension(width, height));
		this.setLayout(new BorderLayout());
		this.setBorder(new EmptyBorder(10, 10, 10, 10));
		this.setBackground(Color.BLACK);

		int width_left = (int) (width * 0.35);
		int width_center = (int) (width * 0.40);
		int width_right = (int) (width * 0.20);

		JPanel left = new JPanel();
		left.setPreferredSize(new Dimension(width_left, height));
		left.setLayout(new GridLayout(16, 1));
		left.setBorder(new MatteBorder(0, 0, 0, 3, Color.BLACK));

		JPanel center = new JPanel();
		center.setPreferredSize(new Dimension(width_center, height));
		center.setLayout(new GridLayout(16, 1));
		center.setBorder(new EmptyBorder(0, 20, 0, 20));

		JPanel right = new JPanel();
		right.setPreferredSize(new Dimension(width_right, height));
		right.setLayout(new GridLayout(5, 1));
		right.setBorder(new MatteBorder(0, 3, 0, 0, Color.BLACK));

		// RIGHT

		// CENTER

		// ALLOW FOG OF WAR

		JPanel allow_fog_war = new JPanel();
		allow_fog_war.setLayout(new GridLayout(1, 2));
		allow_fog_war.setVisible(false);

		My_JLabel title_fog = new My_JLabel("Allow fog of war ", 20.f);
		title_fog.setHorizontalAlignment(JLabel.LEFT);
		title_fog.setVisible(false);

		JRadioButton enable_fog = new JRadioButton("Enable");
		enable_fog.setActionCommand("enable");
		enable_fog.setSelected(false);
		enable_fog.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Setting_configuration.mask_board = true;
			}
		});

		JRadioButton disable_fog = new JRadioButton("Disable");
		disable_fog.setActionCommand("disable");
		disable_fog.setSelected(true);
		disable_fog.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Setting_configuration.mask_board = false;
			}
		});

		ButtonGroup group_fog = new ButtonGroup();
		group_fog.add(enable_fog);
		group_fog.add(disable_fog);

		allow_fog_war.add(enable_fog);
		allow_fog_war.add(disable_fog);

		// LEFT

		// ALLOW AUCTION

		JPanel allow_random_board = new JPanel();
		allow_random_board.setLayout(new GridLayout(1, 2));

		My_JLabel title_random_bord = new My_JLabel("Allow random board ", 20.f);
		title_random_bord.setHorizontalAlignment(JLabel.LEFT);

		JRadioButton enable_random_board = new JRadioButton("Enable");
		enable_random_board.setActionCommand("enable");
		enable_random_board.setSelected(false);
		enable_random_board.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				title_fog.setVisible(true);
				allow_fog_war.setVisible(true);
				Setting_configuration.random_board = true;
				disable_fog.setSelected(disable_fog.isSelected());
				enable_fog.setSelected(enable_fog.isSelected());
				Setting_configuration.mask_board = enable_fog.isSelected();
			}
		});

		JRadioButton disable_random_board = new JRadioButton("Disable");
		disable_random_board.setActionCommand("disable");
		disable_random_board.setSelected(true);
		disable_random_board.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				title_fog.setVisible(false);
				allow_fog_war.setVisible(false);
				Setting_configuration.random_board = false;
				Setting_configuration.mask_board = false;
				disable_fog.setSelected(true);
				enable_fog.setSelected(false);
			}
		});

		ButtonGroup group_random_board = new ButtonGroup();
		group_random_board.add(enable_random_board);
		group_random_board.add(disable_random_board);

		allow_random_board.add(enable_random_board);
		allow_random_board.add(disable_random_board);

		left.add(title_random_bord);
		left.add(allow_random_board);
		left.add(new JPanel());
		left.add(new JPanel());
		left.add(new JPanel());
		left.add(new JPanel());
		left.add(new JPanel());
		left.add(new JPanel());
		left.add(new JPanel());
		left.add(new JPanel());
		left.add(new JPanel());
		left.add(new JPanel());
		left.add(new JPanel());
		left.add(new JPanel());
		left.add(new JPanel());
		left.add(new JPanel());

		center.add(title_fog);
		center.add(allow_fog_war);
		center.add(new JPanel());
		center.add(new JPanel());
		center.add(new JPanel());
		center.add(new JPanel());
		center.add(new JPanel());
		center.add(new JPanel());
		center.add(new JPanel());
		center.add(new JPanel());
		center.add(new JPanel());
		center.add(new JPanel());
		center.add(new JPanel());
		center.add(new JPanel());
		center.add(new JPanel());
		center.add(new JPanel());

		right.add(new JPanel());
		right.add(new JPanel());
		right.add(new JPanel());
		right.add(new JPanel());
		right.add(new JPanel());

		this.add(left, BorderLayout.WEST);
		this.add(center, BorderLayout.CENTER);
		this.add(right, BorderLayout.EAST);
	}
}
