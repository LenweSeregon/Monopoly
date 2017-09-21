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
public class Setting_configuration_cards_view extends JPanel {

	/**
	 * Constructeur de la classe preséentant la vue pour le parametrage des
	 * options pour les cartes
	 * 
	 * @param width
	 *            largeur du panel
	 * @param height
	 *            hauteur du panel
	 */
	public Setting_configuration_cards_view(int width, int height) {
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

		// LEFT

		// ALLOW STEAL CARD

		JPanel allow_steal_card = new JPanel();
		allow_steal_card.setLayout(new GridLayout(1, 2));

		My_JLabel title_allow_steal_card = new My_JLabel("Allow steal card ", 20.f);
		title_allow_steal_card.setHorizontalAlignment(JLabel.LEFT);

		JRadioButton enable_steal_card = new JRadioButton("Enable");
		enable_steal_card.setActionCommand("enable");
		enable_steal_card.setSelected(false);
		enable_steal_card.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Setting_configuration.steal_card = true;
			}
		});

		JRadioButton disable_steal_card = new JRadioButton("Disable");
		disable_steal_card.setActionCommand("disable");
		disable_steal_card.setSelected(true);
		disable_steal_card.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Setting_configuration.steal_card = false;
			}
		});

		ButtonGroup group_steal_card = new ButtonGroup();
		group_steal_card.add(enable_steal_card);
		group_steal_card.add(disable_steal_card);

		allow_steal_card.add(enable_steal_card);
		allow_steal_card.add(disable_steal_card);

		// ALLOW MIX CARD

		JPanel allow_mix_card = new JPanel();
		allow_mix_card.setLayout(new GridLayout(1, 2));

		My_JLabel title_allow_mix_card = new My_JLabel("Allow mix card ", 20.f);
		title_allow_mix_card.setHorizontalAlignment(JLabel.LEFT);

		JRadioButton enable_mix_card = new JRadioButton("Enable");
		enable_mix_card.setActionCommand("enable");
		enable_mix_card.setSelected(false);
		enable_mix_card.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Setting_configuration.mix_card = true;
			}
		});

		JRadioButton disable_mix_card = new JRadioButton("Disable");
		disable_mix_card.setActionCommand("disable");
		disable_mix_card.setSelected(true);
		disable_mix_card.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Setting_configuration.mix_card = false;
			}
		});

		ButtonGroup group_mix_card = new ButtonGroup();
		group_mix_card.add(enable_mix_card);
		group_mix_card.add(disable_mix_card);

		allow_mix_card.add(enable_mix_card);
		allow_mix_card.add(disable_mix_card);

		left.add(title_allow_steal_card);
		left.add(allow_steal_card);
		left.add(title_allow_mix_card);
		left.add(allow_mix_card);
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
