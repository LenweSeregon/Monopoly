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
public class Setting_configuration_start_view extends JPanel {

	/**
	 * Constructeur de la classe preséentant la vue pour le parametrage des
	 * options pour le départ, etc
	 * 
	 * @param width
	 *            largeur du panel
	 * @param height
	 *            hauteur du panel
	 */
	public Setting_configuration_start_view(int width, int height) {
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

		// START * 2

		JPanel start_mult = new JPanel();
		start_mult.setLayout(new GridLayout(1, 2));

		My_JLabel title_start_mult = new My_JLabel("Multiply rent by 2 on start case ", 20.f);
		title_start_mult.setHorizontalAlignment(JLabel.LEFT);

		JRadioButton enable_start_mult = new JRadioButton("Enable");
		enable_start_mult.setActionCommand("enable");
		enable_start_mult.setSelected(false);
		enable_start_mult.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Setting_configuration.double_sum_on_case_start = true;
			}
		});

		JRadioButton disable_start_mult = new JRadioButton("Disable");
		disable_start_mult.setActionCommand("disable");
		disable_start_mult.setSelected(true);
		disable_start_mult.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Setting_configuration.double_sum_on_case_start = false;
			}
		});

		ButtonGroup group_start_mult = new ButtonGroup();
		group_start_mult.add(enable_start_mult);
		group_start_mult.add(disable_start_mult);

		start_mult.add(enable_start_mult);
		start_mult.add(disable_start_mult);

		// TAX BY money

		JPanel tax_by_money = new JPanel();
		tax_by_money.setLayout(new GridLayout(1, 2));

		My_JLabel title_tax_by_money = new My_JLabel("Enable tax according to money ", 20.f);
		title_tax_by_money.setHorizontalAlignment(JLabel.LEFT);

		JRadioButton enable_tax_money = new JRadioButton("Enable");
		enable_tax_money.setActionCommand("enable");
		enable_tax_money.setSelected(false);
		enable_tax_money.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Setting_configuration.tax_according_to_player_money = true;
			}
		});

		JRadioButton disable_tax_money = new JRadioButton("Disable");
		disable_tax_money.setActionCommand("disable");
		disable_tax_money.setSelected(true);
		disable_tax_money.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Setting_configuration.tax_according_to_player_money = false;
			}
		});

		ButtonGroup group_tax_money = new ButtonGroup();
		group_tax_money.add(enable_tax_money);
		group_tax_money.add(disable_tax_money);

		tax_by_money.add(enable_tax_money);
		tax_by_money.add(disable_tax_money);

		// PARKING MONEY

		JPanel parking_money = new JPanel();
		parking_money.setLayout(new GridLayout(1, 2));

		My_JLabel title_parking_money = new My_JLabel("Enable money on parking ", 20.f);
		title_parking_money.setHorizontalAlignment(JLabel.LEFT);

		JRadioButton enable_parking_money = new JRadioButton("Enable");
		enable_parking_money.setActionCommand("enable");
		enable_parking_money.setSelected(false);
		enable_parking_money.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Setting_configuration.earn_money_on_parking = true;
			}
		});

		JRadioButton disable_parking_money = new JRadioButton("Disable");
		disable_parking_money.setActionCommand("disable");
		disable_parking_money.setSelected(true);
		disable_parking_money.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Setting_configuration.earn_money_on_parking = false;
			}
		});

		ButtonGroup group_parking_money = new ButtonGroup();
		group_parking_money.add(enable_parking_money);
		group_parking_money.add(disable_parking_money);

		parking_money.add(enable_parking_money);
		parking_money.add(disable_parking_money);

		left.add(title_start_mult);
		left.add(start_mult);
		left.add(title_tax_by_money);
		left.add(tax_by_money);
		left.add(title_parking_money);
		left.add(parking_money);
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
